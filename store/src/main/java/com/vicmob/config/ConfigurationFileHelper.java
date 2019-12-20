package com.vicmob.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author keith
 * @date 2018-09-04
 */
@Component
public class ConfigurationFileHelper {

    /**
     * Swagger2配置
     */
    private static Boolean enable;

    public static Boolean getEnable() {
        return enable;
    }

    @Value("${swagger.enable}")
    public  void setEnable(Boolean enable) {
        ConfigurationFileHelper.enable = enable;
    }



}
