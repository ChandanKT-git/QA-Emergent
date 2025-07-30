package com.emergent.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class to handle configuration properties for the test framework.
 * Loads properties from config.properties file.
 */
public class ConfigProperties {
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/main/resources/config.properties";
    
    static {
        try {
            loadProperties();
        } catch (IOException e) {
            System.err.println("Failed to load config.properties file: " + e.getMessage());
        }
    }
    
    /**
     * Loads properties from the config file
     * @throws IOException if the file cannot be read
     */
    private static void loadProperties() throws IOException {
        properties = new Properties();
        FileInputStream inputStream = new FileInputStream(CONFIG_FILE_PATH);
        properties.load(inputStream);
        inputStream.close();
    }
    
    /**
     * Gets a property value by key
     * @param key the property key
     * @return the property value
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    /**
     * Gets a property value by key with a default value if not found
     * @param key the property key
     * @param defaultValue the default value to return if key not found
     * @return the property value or default value
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * Gets the base URL for the application
     * @return the base URL
     */
    public static String getBaseUrl() {
        return getProperty("base.url", "https://emergent.sh");
    }
    
    /**
     * Gets the browser to use for tests
     * @return the browser name
     */
    public static String getBrowser() {
        return getProperty("browser", "chrome");
    }
    
    /**
     * Checks if tests should run in headless mode
     * @return true if headless mode is enabled
     */
    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "false"));
    }
    
    /**
     * Gets the default timeout in seconds
     * @return the timeout in seconds
     */
    public static int getDefaultTimeout() {
        return Integer.parseInt(getProperty("default.timeout", "30"));
    }
    
    /**
     * Gets the test username
     * @return the test username
     */
    public static String getTestUsername() {
        return getProperty("test.username", "test@example.com");
    }
    
    /**
     * Gets the test password
     * @return the test password
     */
    public static String getTestPassword() {
        return getProperty("test.password", "Password123!");
    }
}