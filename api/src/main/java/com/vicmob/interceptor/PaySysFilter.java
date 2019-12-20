package com.vicmob.interceptor;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.vicmob.config.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.vicmob.config.SymmetricEncoder.AESDncode;

/**
 * @author peter
 * @version 1.1
 * @date 2019/5/23 16:36
 */

@Component
public class PaySysFilter extends ZuulFilter {

    @Autowired
    private RedisUtil redis;

    @Override
    public String filterType() {
        return "pre";
    }


    /**
     * 过滤顺序 0 最高
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否进行过滤
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        RequestContext rc = RequestContext.getCurrentContext();
        HttpServletRequest request = rc.getRequest();
        String uri = request.getRequestURI();

        Boolean f = false;
        if (uri.contains("payagent") || uri.contains("paystore") || uri.contains("operation")) {
            f = true;
        }
        if ( uri.contains("login") || uri.contains("v2") || uri.contains("download") || uri.contains("writeOff") || uri.contains("storeInfo")|| uri.contains("RarFile")) {
            f = false;
        }
        f = false;
        return f;
    }


    /**
     * 拦截的具体业务代码
     *
     * @return
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse res = ctx.getResponse();
        String cacheUUID = "";
        String loginId = "";

        try {
            String token = request.getHeader("token");
            //判断token是否存在
            if (token == null || "".equals(token)) {
                System.out.println("===账号未登录===");
                // 过滤该请求，不对其进行路由
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(501);
            } else {
                //对token 解密
                String tokenDncode = AESDncode("vicmobv2", token);
                //得到 user对应的 uuid，判断是否和redis一致
                String str[] = tokenDncode.split(",");
                loginId = str[0];
                String uuid = str[1];
                if (!redis.hasKey(loginId)) {
                    System.out.println("===账号已过期===");
                    // 过滤该请求，不对其进行路由
                    ctx.setSendZuulResponse(false);
                    ctx.setResponseStatusCode(502);
                } else {
                    cacheUUID = redis.get(loginId).toString();
                    if (!uuid.equals(cacheUUID)) { //账号互踢，需要重新登录
                        System.out.println("===账号互踢===");
                        // 过滤该请求，不对其进行路由
                        ctx.setSendZuulResponse(false);
                        ctx.setResponseStatusCode(503);
                    }
                }
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}