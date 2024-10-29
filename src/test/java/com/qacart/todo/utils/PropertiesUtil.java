package com.qacart.todo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    public static Properties loadProperties(String file) {
        Properties properties = new Properties();
        try(InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(file)) {
            if(inputStream == null) {
                throw new IllegalArgumentException("Sorry, We did not find the file");
            }
            properties.load(inputStream);
        } catch (IOException ex) {
            throw new RuntimeException("Sorry, Something went wrong reading the file");
        }
        return properties;
    }
}
