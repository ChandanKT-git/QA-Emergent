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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * Test class for AI agent interaction functionality.
 */
public class AIAgentInteractionTest extends BaseTest {

        private ProjectDetailsPage projectDetailsPage;

        /**
         * Set up method to log in and create a project before each test.
         */
        @BeforeClass
        public void setUpClass() {
                TestListener.logInfo("Setting up AIAgentInteractionTest class");
                // Override the browser type to use Firefox instead of Chrome
                System.setProperty("browser.type", "firefox");
                super.setUp();
        }

        @BeforeMethod
        public void setUp() {
                TestListener.logInfo("Setting up AIAgentInteractionTest method");
                try {
                        // Navigate to base URL
                        super.navigateToBaseUrl();
                        // Login with default credentials
                        loginWithDefaultCredentials();
                        // Create a new project for testing
                        DashboardPage dashboardPage = new DashboardPage(webDriver);
                        if (!dashboardPage.isDashboardPageLoaded()) {
                                throw new RuntimeException("Dashboard page failed to load");
                        }
                        // Create a new project or open an existing one
                        String projectName = TestUtils.generateRandomProjectName();
                        ProjectCreationPage projectCreationPage = dashboardPage.clickCreateNewProjectButton();
                        projectCreationPage.enterProjectName(projectName);
                        projectCreationPage.enterProjectDescription(TestUtils.generateRandomProjectDescription());
                        projectDetailsPage = projectCreationPage.clickCreate();
                        Assert.assertTrue(projectDetailsPage.isProjectDetailsPageLoaded(),
                                        "Project details page did not load after project creation");
                } finally {
                        // Ensure any cleanup is performed even if setup fails
                }
        }

        /**
         * Tests sending a simple prompt to the AI agent.
         */
        @Test(priority = 1, description = "Test sending a simple prompt to the AI agent")
        public void testSendingSimplePrompt() {
                TestListener.logInfo("Testing sending a simple prompt to the AI agent");

                // Send a simple prompt
                String prompt = "Create a simple hello world program";
                projectDetailsPage.enterPrompt(prompt);
                projectDetailsPage.clickSendPrompt();

                // Wait for AI response
                TestUtils.waitForElementClickable(webDriver, projectDetailsPage.getAIResponseLocator(),
                                ConfigProperties.getDefaultTimeout());

                // Verify AI response is not empty
                String aiResponse = projectDetailsPage.getAIResponse();
                TestListener.logInfo("AI response received: "
                                + (aiResponse.length() > 100 ? aiResponse.substring(0, 100) + "..." : aiResponse));
                Assert.assertFalse(aiResponse.isEmpty(), "AI response is empty");

                // Verify code preview is displayed
                Assert.assertTrue(projectDetailsPage.isCodePreviewDisplayed(),
                                "Code preview is not displayed after AI response");
        }

        /**
         * Tests sending a complex prompt to the AI agent.
         */
        @Test(priority = 2, description = "Test sending a complex prompt to the AI agent")
        public void testSendingComplexPrompt() {
                TestListener.logInfo("Testing sending a complex prompt to the AI agent");

                // Send a complex prompt
                String prompt = "Create a RESTful API with Node.js and Express that has endpoints for CRUD operations on a 'users' resource. Include input validation and error handling.";
                projectDetailsPage.enterPrompt(prompt);
                projectDetailsPage.clickSendPrompt();

                // Wait for AI response (complex prompts may take longer)
                TestUtils.waitForElementClickable(webDriver, projectDetailsPage.getAIResponseLocator(),
                                ConfigProperties.getDefaultTimeout() * 2);

                // Verify AI response is not empty
                String aiResponse = projectDetailsPage.getAIResponse();
                TestListener.logInfo("AI response received: "
                                + (aiResponse.length() > 100 ? aiResponse.substring(0, 100) + "..." : aiResponse));
                Assert.assertFalse(aiResponse.isEmpty(), "AI response is empty");

                // Verify code preview is displayed
                Assert.assertTrue(projectDetailsPage.isCodePreviewDisplayed(),
                                "Code preview is not displayed after AI response");

                // Verify the response contains relevant keywords for the complex prompt
                Assert.assertTrue(aiResponse.toLowerCase().contains("api") ||
                                aiResponse.toLowerCase().contains("express") ||
                                aiResponse.toLowerCase().contains("node") ||
                                aiResponse.toLowerCase().contains("crud"),
                                "AI response does not contain relevant keywords for the complex prompt");
        }

        /**
         * Tests sending a follow-up prompt to the AI agent.
         */
        @Test(priority = 3, description = "Test sending a follow-up prompt to the AI agent")
        public void testSendingFollowUpPrompt() {
                TestListener.logInfo("Testing sending a follow-up prompt to the AI agent");

                // Send an initial prompt
                String initialPrompt = "Create a simple React component";
                projectDetailsPage.enterPrompt(initialPrompt);
                projectDetailsPage.clickSendPrompt();

                // Wait for AI response
                TestUtils.waitForElementClickable(webDriver, projectDetailsPage.getAIResponseLocator(),
                                ConfigProperties.getDefaultTimeout());

                // Verify initial AI response
                String initialResponse = projectDetailsPage.getAIResponse();
                TestListener.logInfo("Initial AI response received: "
                                + (initialResponse.length() > 100 ? initialResponse.substring(0, 100) + "..."
                                                : initialResponse));
                Assert.assertFalse(initialResponse.isEmpty(), "Initial AI response is empty");

                // Send a follow-up prompt
                String followUpPrompt = "Add state management to the component";
                projectDetailsPage.enterPrompt(followUpPrompt);
                projectDetailsPage.clickSendPrompt();

                // Wait for follow-up AI response
                TestUtils.waitForElementClickable(webDriver, projectDetailsPage.getAIResponseLocator(),
                                ConfigProperties.getDefaultTimeout());

                // Verify follow-up AI response
                String followUpResponse = projectDetailsPage.getAIResponse();
                TestListener.logInfo("Follow-up AI response received: "
                                + (followUpResponse.length() > 100 ? followUpResponse.substring(0, 100) + "..."
                                                : followUpResponse));
                Assert.assertFalse(followUpResponse.isEmpty(), "Follow-up AI response is empty");

                // Verify the follow-up response contains relevant keywords
                Assert.assertTrue(followUpResponse.toLowerCase().contains("state") ||
                                followUpResponse.toLowerCase().contains("usestate") ||
                                followUpResponse.toLowerCase().contains("setstate"),
                                "Follow-up AI response does not contain relevant keywords");
        }

        /**
         * Tests sending an empty prompt to the AI agent.
         */
        @Test(priority = 4, description = "Test sending an empty prompt to the AI agent")
        public void testSendingEmptyPrompt() {
                TestListener.logInfo("Testing sending an empty prompt to the AI agent");

                // Send an empty prompt
                projectDetailsPage.enterPrompt("");
                projectDetailsPage.clickSendPrompt();

                // Verify error message is displayed
                Assert.assertTrue(projectDetailsPage.isErrorMessageDisplayed(),
                                "Error message not displayed for empty prompt");
                String errorMessage = projectDetailsPage.getErrorMessage();
                TestListener.logInfo("Error message displayed: " + errorMessage);
                Assert.assertTrue(errorMessage.contains(Constants.ERROR_EMPTY_PROMPT) ||
                                !errorMessage.isEmpty(),
                                "Error message does not indicate empty prompt");
        }

        /**
         * Tests sending prompts with special instructions to the AI agent.
         * 
         * @param promptTemplate the prompt template to use
         */
        @Test(priority = 5, dataProvider = "promptTemplates", dataProviderClass = DataProviders.class, description = "Test sending prompts with special instructions")
        public void testSendingPromptsWithSpecialInstructions(String promptTemplate) {
                TestListener.logInfo("Testing sending prompt with special instructions: " + promptTemplate);

                // Send a prompt with special instructions
                projectDetailsPage.enterPrompt(promptTemplate);
                projectDetailsPage.clickSendPrompt();

                // Wait for AI response
                TestUtils.waitForElementClickable(webDriver, projectDetailsPage.getAIResponseLocator(),
                                ConfigProperties.getDefaultTimeout() * 2);

                // Verify AI response is not empty
                String aiResponse = projectDetailsPage.getAIResponse();
                TestListener.logInfo("AI response received: "
                                + (aiResponse.length() > 100 ? aiResponse.substring(0, 100) + "..." : aiResponse));
                Assert.assertFalse(aiResponse.isEmpty(), "AI response is empty");

                // Verify code preview is displayed
                Assert.assertTrue(projectDetailsPage.isCodePreviewDisplayed(),
                                "Code preview is not displayed after AI response");
        }
}