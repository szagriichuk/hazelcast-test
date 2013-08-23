package com.playtika.hazelcast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author szagriichuk
 */
public class PlaytikaProperties {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlaytikaProperties.class);
    private Properties properties;

    public PlaytikaProperties(String fileName) {
        properties = loadProperties(fileName);
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public void put(String key, String value) {
        properties.put(key, value);
    }

    private Properties loadProperties(String name) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(name));
        } catch (IOException e) {
            tryLoadPropertiesFromClassPath(name, properties);
        }
        return properties;
    }

    private void tryLoadPropertiesFromClassPath(String name, Properties properties) {
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(name));
        } catch (IOException e) {
            LOGGER.info(e.getMessage(), e);
        }
    }
}
