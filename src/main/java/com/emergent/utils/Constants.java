package com.emergent.utils;

/**
 * Class to store constants used throughout the test framework.
 */
public class Constants {
    
    // Timeouts
    public static final int DEFAULT_TIMEOUT = 30;
    public static final int SHORT_TIMEOUT = 5;
    public static final int LONG_TIMEOUT = 60;
    
    // URLs
    public static final String BASE_URL = "https://emergent.sh";
    public static final String LOGIN_URL = BASE_URL + "/login";
    public static final String SIGNUP_URL = BASE_URL + "/signup";
    public static final String DASHBOARD_URL = BASE_URL + "/dashboard";
    public static final String FORGOT_PASSWORD_URL = BASE_URL + "/forgot-password";
    
    // File paths
    public static final String SCREENSHOT_PATH = "target/screenshots";
    public static final String REPORT_PATH = "target/extent-reports";
    
    // Test data
    public static final String TEST_USERNAME = "test@example.com";
    public static final String TEST_PASSWORD = "Password123!";
    public static final String TEST_NAME = "Test User";
    
    // Project templates
    public static final String TEMPLATE_WEB_APP = "Web Application";
    public static final String TEMPLATE_MOBILE_APP = "Mobile Application";
    public static final String TEMPLATE_API = "API";
    public static final String TEMPLATE_DATABASE = "Database";
    
    // Error messages
    public static final String ERROR_INVALID_CREDENTIALS = "Invalid email or password";
    public static final String ERROR_EMPTY_EMAIL = "Email is required";
    public static final String ERROR_EMPTY_PASSWORD = "Password is required";
    public static final String ERROR_INVALID_EMAIL = "Please enter a valid email address";
    public static final String ERROR_PASSWORD_MISMATCH = "Passwords do not match";
    public static final String ERROR_EMPTY_PROJECT_NAME = "Project name is required";
    public static final String ERROR_DUPLICATE_PROJECT = "A project with this name already exists";
    public static final String ERROR_EMPTY_PROMPT = "Prompt cannot be empty";
    
    // Success messages
    public static final String SUCCESS_PASSWORD_RESET = "Password reset email sent";
    public static final String SUCCESS_PROJECT_CREATED = "Project created successfully";
    public static final String SUCCESS_PROJECT_UPDATED = "Project updated successfully";
    public static final String SUCCESS_PROJECT_DELETED = "Project deleted successfully";
    public static final String SUCCESS_DEPLOYMENT = "Deployment completed successfully";
    
    // Prompt templates
    public static final String PROMPT_SIMPLE_WEB_APP = "Create a simple web application with HTML, CSS, and JavaScript";
    public static final String PROMPT_REACT_APP = "Create a React application with a login form and dashboard";
    public static final String PROMPT_BACKEND_API = "Create a RESTful API with Node.js and Express";
    public static final String PROMPT_DATABASE_SCHEMA = "Create a database schema for an e-commerce application";
    
    // Test case templates
    public static final String TEST_CASE_TEMPLATE = "Create a test case for the login functionality";
    
    // Deployment environments
    public static final String ENV_DEVELOPMENT = "Development";
    public static final String ENV_STAGING = "Staging";
    public static final String ENV_PRODUCTION = "Production";
    
    // Browser types
    public static final String BROWSER_CHROME = "chrome";
    public static final String BROWSER_FIREFOX = "firefox";
    public static final String BROWSER_EDGE = "edge";
    public static final String BROWSER_SAFARI = "safari";
}