package com.emergent.tests;

import com.emergent.pages.DashboardPage;
import com.emergent.pages.ProjectCreationPage;
import com.emergent.pages.ProjectDetailsPage;
import com.emergent.utils.ConfigProperties;
import com.emergent.utils.Constants;
import com.emergent.utils.DataProviders;
import com.emergent.utils.TestListener;
import com.emergent.utils.TestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for code generation functionality.
 */
public class CodeGenerationTest extends BaseTest {

        private ProjectDetailsPage projectDetailsPage;

        /**
         * Set up method to log in and create a project before each test.
         */
        @BeforeMethod
        public void setUp() {
                TestListener.logInfo("Setting up CodeGenerationTest");

                // Login with default credentials
                loginWithDefaultCredentials();

                // Create a new project for testing
                DashboardPage dashboardPage = new DashboardPage(webDriver);
                Assert.assertTrue(dashboardPage.isDashboardPageLoaded(), "Dashboard page did not load");

                // Create a new project or open an existing one
                String projectName = TestUtils.generateRandomProjectName();
                ProjectCreationPage projectCreationPage = dashboardPage.clickCreateNewProjectButton();
                projectCreationPage.enterProjectName(projectName);
                projectCreationPage.enterProjectDescription(TestUtils.generateRandomProjectDescription());
                projectDetailsPage = projectCreationPage.clickCreate();

                Assert.assertTrue(projectDetailsPage.isProjectDetailsPageLoaded(),
                                "Project details page did not load after project creation");
        }

        /**
         * Tests generating a simple web application.
         */
        @Test(priority = 1, description = "Test generating a simple web application")
        public void testGenerateSimpleWebApplication() {
                TestListener.logInfo("Testing generation of a simple web application");

                // Send prompt to generate a simple web application
                String prompt = "Create a simple HTML page with a header, navigation menu, and a contact form";
                projectDetailsPage.enterPrompt(prompt);
                projectDetailsPage.clickSendPromptButton();

                // Wait for AI response
                TestUtils.waitForElementVisible(webDriver, projectDetailsPage.getAIResponseLocator(),
                                ConfigProperties.getDefaultTimeout());

                // Verify AI response is not empty
                String aiResponse = projectDetailsPage.getAIResponse();
                TestListener.logInfo("AI response received: "
                                + (aiResponse.length() > 100 ? aiResponse.substring(0, 100) + "..." : aiResponse));
                Assert.assertFalse(aiResponse.isEmpty(), "AI response is empty");

                // Verify code preview is displayed
                Assert.assertTrue(projectDetailsPage.isCodePreviewDisplayed(),
                                "Code preview is not displayed after AI response");

                // Verify the response contains relevant HTML elements
                String codePreview = projectDetailsPage.getCodePreview();
                Assert.assertTrue(codePreview.contains("<html") &&
                                codePreview.contains("<header") &&
                                codePreview.contains("<form"),
                                "Generated code does not contain required HTML elements");
        }

        /**
         * Tests generating a React application.
         */
        @Test(priority = 2, description = "Test generating a React application")
        public void testGenerateReactApplication() {
                TestListener.logInfo("Testing generation of a React application");

                // Send prompt to generate a React application
                String prompt = "Create a React application with a navigation bar and a product listing page";
                projectDetailsPage.enterPrompt(prompt);
                projectDetailsPage.clickSendPromptButton();

                // Wait for AI response (complex prompts may take longer)
                TestUtils.waitForElementVisible(webDriver, projectDetailsPage.getAIResponseLocator(),
                                ConfigProperties.getDefaultTimeout() * 2);

                // Verify AI response is not empty
                String aiResponse = projectDetailsPage.getAIResponse();
                TestListener.logInfo("AI response received: "
                                + (aiResponse.length() > 100 ? aiResponse.substring(0, 100) + "..." : aiResponse));
                Assert.assertFalse(aiResponse.isEmpty(), "AI response is empty");

                // Verify code preview is displayed
                Assert.assertTrue(projectDetailsPage.isCodePreviewDisplayed(),
                                "Code preview is not displayed after AI response");

                // Verify the response contains React-specific code
                String codePreview = projectDetailsPage.getCodePreview();
                Assert.assertTrue(codePreview.contains("import React") ||
                                codePreview.contains("from 'react'") ||
                                codePreview.contains("useState") ||
                                codePreview.contains("useEffect"),
                                "Generated code does not contain React-specific elements");
        }

        /**
         * Tests generating a backend API.
         */
        @Test(priority = 3, description = "Test generating a backend API")
        public void testGenerateBackendAPI() {
                TestListener.logInfo("Testing generation of a backend API");

                // Send prompt to generate a backend API
                String prompt = "Create a Node.js Express API with endpoints for user authentication and product management";
                projectDetailsPage.enterPrompt(prompt);
                projectDetailsPage.clickSendPromptButton();

                // Wait for AI response
                TestUtils.waitForElementVisible(webDriver, projectDetailsPage.getAIResponseLocator(),
                                ConfigProperties.getDefaultTimeout() * 2);

                // Verify AI response is not empty
                String aiResponse = projectDetailsPage.getAIResponse();
                TestListener.logInfo("AI response received: "
                                + (aiResponse.length() > 100 ? aiResponse.substring(0, 100) + "..." : aiResponse));
                Assert.assertFalse(aiResponse.isEmpty(), "AI response is empty");

                // Verify code preview is displayed
                Assert.assertTrue(projectDetailsPage.isCodePreviewDisplayed(),
                                "Code preview is not displayed after AI response");

                // Verify the response contains API-specific code
                String codePreview = projectDetailsPage.getCodePreview();
                Assert.assertTrue(codePreview.contains("express") &&
                                (codePreview.contains("app.get") ||
                                                codePreview.contains("app.post") ||
                                                codePreview.contains("router.get") ||
                                                codePreview.contains("router.post")),
                                "Generated code does not contain Express API endpoints");
        }

        /**
         * Tests generating a database schema.
         */
        @Test(priority = 4, description = "Test generating a database schema")
        public void testGenerateDatabaseSchema() {
                TestListener.logInfo("Testing generation of a database schema");

                // Send prompt to generate a database schema
                String prompt = "Create a SQL database schema for an e-commerce application with tables for users, products, orders, and reviews";
                projectDetailsPage.enterPrompt(prompt);
                projectDetailsPage.clickSendPromptButton();

                // Wait for AI response
                TestUtils.waitForElementVisible(webDriver, projectDetailsPage.getAIResponseLocator(),
                                ConfigProperties.getDefaultTimeout());

                // Verify AI response is not empty
                String aiResponse = projectDetailsPage.getAIResponse();
                TestListener.logInfo("AI response received: "
                                + (aiResponse.length() > 100 ? aiResponse.substring(0, 100) + "..." : aiResponse));
                Assert.assertFalse(aiResponse.isEmpty(), "AI response is empty");

                // Verify code preview is displayed
                Assert.assertTrue(projectDetailsPage.isCodePreviewDisplayed(),
                                "Code preview is not displayed after AI response");

                // Verify the response contains SQL schema elements
                String codePreview = projectDetailsPage.getCodePreview();
                Assert.assertTrue(codePreview.contains("CREATE TABLE") &&
                                codePreview.contains("PRIMARY KEY"),
                                "Generated code does not contain SQL schema elements");

                // Verify the schema includes the requested tables
                Assert.assertTrue(codePreview.toLowerCase().contains("users") &&
                                codePreview.toLowerCase().contains("products") &&
                                codePreview.toLowerCase().contains("orders"),
                                "Generated schema does not include all requested tables");
        }

        /**
         * Tests generating code with specific technology requirements.
         * 
         * @param technology       the technology to use
         * @param prompt           the prompt to send
         * @param expectedKeywords the keywords expected in the response
         */
        @Test(priority = 5, dataProvider = "codeGenerationTechnologies", dataProviderClass = DataProviders.class, description = "Test generating code with specific technology requirements")
        public void testGenerateCodeWithSpecificTechnology(String technology, String prompt,
                        String[] expectedKeywords) {
                TestListener.logInfo("Testing generation of code with specific technology: " + technology);

                // Send prompt to generate code with specific technology
                projectDetailsPage.enterPrompt(prompt);
                projectDetailsPage.clickSendPromptButton();

                // Wait for AI response
                TestUtils.waitForElementVisible(webDriver, projectDetailsPage.getAIResponseLocator(),
                                ConfigProperties.getDefaultTimeout() * 2);

                // Verify AI response is not empty
                String aiResponse = projectDetailsPage.getAIResponse();
                TestListener.logInfo("AI response received: "
                                + (aiResponse.length() > 100 ? aiResponse.substring(0, 100) + "..." : aiResponse));
                Assert.assertFalse(aiResponse.isEmpty(), "AI response is empty");

                // Verify code preview is displayed
                Assert.assertTrue(projectDetailsPage.isCodePreviewDisplayed(),
                                "Code preview is not displayed after AI response");

                // Verify the response contains technology-specific keywords
                String codePreview = projectDetailsPage.getCodePreview().toLowerCase();
                boolean containsKeywords = false;
                for (String keyword : expectedKeywords) {
                        if (codePreview.contains(keyword.toLowerCase())) {
                                containsKeywords = true;
                                break;
                        }
                }

                Assert.assertTrue(containsKeywords,
                                "Generated code does not contain any of the expected keywords for " + technology);
        }
}