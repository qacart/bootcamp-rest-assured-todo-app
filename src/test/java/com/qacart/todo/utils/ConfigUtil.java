package com.qacart.todo.utils;

import java.util.Properties;

public class ConfigUtil {
    private final Properties prop;
    static ConfigUtil configUtil;

    private ConfigUtil() {
        String env = System.getProperty("env" , "production");
        switch (env.toUpperCase()) {
            case "PRODUCTION" -> prop = PropertiesUtil.loadProperties("prod.properties");
            case "LOCAL" -> prop = PropertiesUtil.loadProperties("local.properties");
            default -> throw new IllegalArgumentException("Environment is not supported");
        }
    }

    public static ConfigUtil getInstance() {
        if(configUtil == null) {
            return new ConfigUtil();
        }
        return configUtil;
    }

    public String getBaseUrl() {
        String baseUrl = prop.getProperty("baseUrl");
        if(baseUrl == null) {
            throw new IllegalArgumentException("Could not read the base URL");
        }
        return baseUrl;
    }
}
