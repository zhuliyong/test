package com.vicmob.config;

/**
 * @author peter
 * @version 1.1
 * @date 2019/6/15 13:44
 */
public class ResultServer {

    public static ResultResponse succes(Object obj){
        ResultResponse rrs = new ResultResponse();
        rrs.setStatus(1);
        rrs.setMessage("成功");
        rrs.setData(obj);
        return rrs;
    }

    public static ResultResponse error(String msg){
        ResultResponse rrs = new ResultResponse();
        rrs.setStatus(0);
        rrs.setMessage(msg);
        return rrs;
    }
}
