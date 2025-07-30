package com.emergent.utils;

import org.testng.annotations.DataProvider;

/**
 * Class containing data providers for test cases.
 */
public class DataProviders {

    /**
     * Data provider for login tests with valid and invalid credentials
     * @return Object array containing email, password, and expected result
     */
    @DataProvider(name = "loginCredentials")
    public static Object[][] loginCredentials() {
        return new Object[][] {
            // email, password, expectedResult
            {Constants.TEST_USERNAME, Constants.TEST_PASSWORD, true},  // Valid credentials
            {"invalid@example.com", Constants.TEST_PASSWORD, false},  // Invalid email
            {Constants.TEST_USERNAME, "wrongpassword", false},        // Invalid password
            {"", Constants.TEST_PASSWORD, false},                      // Empty email
            {Constants.TEST_USERNAME, "", false},                    // Empty password
            {"notanemail", Constants.TEST_PASSWORD, false}           // Malformed email
        };
    }
    
    /**
     * Data provider for sign-up tests
     * @return Object array containing name, email, password, confirm password, and expected result
     */
    @DataProvider(name = "signupCredentials")
    public static Object[][] signupCredentials() {
        return new Object[][] {
            // name, email, password, confirmPassword, expectedResult
            {"New User", "newuser@example.com", "Password123!", "Password123!", true},  // Valid data
            {"", "newuser@example.com", "Password123!", "Password123!", false},        // Empty name
            {"New User", "", "Password123!", "Password123!", false},                  // Empty email
            {"New User", "newuser@example.com", "", "Password123!", false},           // Empty password
            {"New User", "newuser@example.com", "Password123!", "", false},           // Empty confirm password
            {"New User", "newuser@example.com", "Password123!", "DifferentPass!", false}, // Password mismatch
            {"New User", "notanemail", "Password123!", "Password123!", false},        // Invalid email format
            {"New User", "newuser@example.com", "short", "short", false}              // Password too short
        };
    }
    
    /**
     * Data provider for project creation tests
     * @return Object array containing project name, description, template, and expected result
     */
    @DataProvider(name = "projectData")
    public static Object[][] projectData() {
        return new Object[][] {
            // name, description, template, expectedResult
            {"Test Project", "A test project description", Constants.TEMPLATE_WEB_APP, true},  // Valid data
            {"", "A test project description", Constants.TEMPLATE_WEB_APP, false},           // Empty name
            {"Test Project", "", Constants.TEMPLATE_WEB_APP, true},                         // Empty description (should still work)
            {"Test Project", "A test project description", Constants.TEMPLATE_API, true},     // Different template
            {"Test Project", "A test project description", Constants.TEMPLATE_DATABASE, true}, // Different template
            {"Duplicate Project", "This project name already exists", Constants.TEMPLATE_WEB_APP, false} // Duplicate name
        };
    }
    
    /**
     * Data provider for AI prompt tests
     * @return Object array containing prompt text and expected response check
     */
    @DataProvider(name = "promptData")
    public static Object[][] promptData() {
        return new Object[][] {
            // promptText, expectedResponseCheck
            {Constants.PROMPT_SIMPLE_WEB_APP, "HTML"},  // Simple web app prompt
            {Constants.PROMPT_REACT_APP, "React"},      // React app prompt
            {Constants.PROMPT_BACKEND_API, "Express"},   // Backend API prompt
            {Constants.PROMPT_DATABASE_SCHEMA, "schema"} // Database schema prompt
        };
    }
    
    /**
     * Data provider for AI prompt templates
     * @return Object array containing prompt templates
     */
    @DataProvider(name = "promptTemplates")
    public static Object[][] promptTemplates() {
        return new Object[][] {
            {Constants.PROMPT_SIMPLE_WEB_APP},
            {Constants.PROMPT_REACT_APP},
            {Constants.PROMPT_BACKEND_API},
            {Constants.PROMPT_DATABASE_SCHEMA}
        };
    }
    
    /**
     * Data provider for deployment environment tests
     * @return Object array containing environment name and expected URL pattern
     */
    @DataProvider(name = "deploymentEnvironments")
    public static Object[][] deploymentEnvironments() {
        return new Object[][] {
            // environment, expectedUrlPattern
            {Constants.ENV_DEVELOPMENT, "dev"},  // Development environment
            {Constants.ENV_STAGING, "staging"}  // Staging environment
        };
    }
    
    /**
     * Data provider for project settings update tests
     * @return Object array containing original name, new name, original description, new description, and expected result
     */
    @DataProvider(name = "projectUpdateData")
    public static Object[][] projectUpdateData() {
        return new Object[][] {
            // originalName, newName, originalDesc, newDesc, expectedResult
            {"Test Project", "Updated Project", "Original description", "Updated description", true},  // Valid update
            {"Test Project", "", "Original description", "Updated description", false},              // Empty new name
            {"Test Project", "Updated Project", "Original description", "", true}                    // Empty new description (should still work)
        };
    }
    
    /**
     * Data provider for browser types
     * @return Object array containing browser names
     */
    @DataProvider(name = "browsers")
    public static Object[][] browsers() {
        return new Object[][] {
            {Constants.BROWSER_CHROME},
            {Constants.BROWSER_FIREFOX},
            {Constants.BROWSER_EDGE}
            // Safari is excluded as it typically requires additional setup on non-macOS systems
        };
    }
}