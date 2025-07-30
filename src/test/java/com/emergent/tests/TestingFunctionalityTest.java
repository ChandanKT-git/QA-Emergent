package com.emergent.tests;

import com.emergent.pages.DashboardPage;
import com.emergent.pages.ProjectCreationPage;
import com.emergent.pages.ProjectDetailsPage;
import com.emergent.pages.TestingPage;
import com.emergent.utils.ConfigProperties;
import com.emergent.utils.Constants;
import com.emergent.utils.DataProviders;
import com.emergent.utils.TestListener;
import com.emergent.utils.TestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for testing functionality of the platform.
 */
public class TestingFunctionalityTest extends BaseTest {

        private ProjectDetailsPage projectDetailsPage;
        private TestingPage testingPage;

        /**
         * Set up method to log in and create a project before each test.
         */
        @BeforeMethod
        public void setUp() {
                TestListener.logInfo("Setting up TestingFunctionalityTest");

                loginWithDefaultCredentials();

                DashboardPage dashboardPage = new DashboardPage(webDriver);
                Assert.assertTrue(dashboardPage.isDashboardPageLoaded(), "Dashboard page did not load");

                String projectName = TestUtils.generateRandomProjectName();
                ProjectCreationPage projectCreationPage = dashboardPage.clickCreateNewProjectButton();
                projectCreationPage.enterProjectName(projectName);
                projectCreationPage.enterProjectDescription(TestUtils.generateRandomProjectDescription());
                projectDetailsPage = projectCreationPage.clickCreateButton();

                Assert.assertTrue(projectDetailsPage.isProjectDetailsPageLoaded(),
                                "Project details page did not load after project creation");

                String prompt = "Create a simple hello world function in JavaScript";
                projectDetailsPage.enterPrompt(prompt);
                projectDetailsPage.clickSendPromptButton();

                TestUtils.waitForElementVisible(webDriver, projectDetailsPage.getAIResponseLocator(),
                                ConfigProperties.getDefaultTimeout());
        }

        @Test(priority = 1, description = "Test navigating to the testing page")
        public void testNavigateToTestingPage() {
                TestListener.logInfo("Testing navigation to the testing page");

                testingPage = projectDetailsPage.clickTest();

                Assert.assertTrue(testingPage.isTestingPageLoaded(), "Testing page did not load properly");
                Assert.assertTrue(testingPage.isTestingTitleDisplayed(), "Testing page title is not displayed");
        }

        @Test(priority = 2, description = "Test creating a new test case")
        public void testCreateNewTestCase() {
                TestListener.logInfo("Testing creation of a new test case");

                testingPage = projectDetailsPage.clickTest();
                Assert.assertTrue(testingPage.isLoaded(), "Testing page did not load properly");

                String testPrompt = "Test that the hello world function returns the correct string";
                testingPage.clickCreateTest();
                testingPage.enterTestPrompt(testPrompt);
                testingPage.clickCreateTestPrompt();

                TestUtils.waitForElementClickable(webDriver, testingPage.getTestResultCount(),
                                ConfigProperties.getDefaultTimeout());

                Assert.assertTrue(testingPage.getTestResultsCount() > 0, "No test cases were created");
                Assert.assertTrue(testingPage.getTestResults().contains(testPrompt),
                                "Test prompt is not displayed in results");
        }

        @Test(priority = 3, description = "Test running all tests")
        public void testRunAllTests() {
                TestListener.logInfo("Testing running all tests");

                testingPage = projectDetailsPage.clickTest();
                Assert.assertTrue(testingPage.isTestingPageLoaded(), "Testing page did not load properly");

                if (testingPage.getTestResultsCount() == 0) {
                        String testPrompt = "Test that the hello world function returns the correct string";
                        testingPage.clickCreateTest();
                        testingPage.enterTestPrompt(testPrompt);
                        testingPage.clickCreateTestPrompt();

                        TestUtils.waitForElementClickable(webDriver, testingPage.getTestResultCount(),
                                        ConfigProperties.getDefaultTimeout());
                }

                testingPage.clickRunAllTests();

                TestUtils.waitForElementClickable(webDriver, testingPage.getTestResultCount(),
                                ConfigProperties.getDefaultTimeout());

                Assert.assertFalse(testingPage.getTestStatus().isEmpty(), "Test status is not displayed");
                Assert.assertTrue(testingPage.getTestResultsCount() > 0, "No test results are displayed");
        }

        @Test(priority = 4, description = "Test viewing test results")
        public void testViewTestResults() {
                TestListener.logInfo("Testing viewing test results");

                testingPage = projectDetailsPage.clickTest();
                Assert.assertTrue(testingPage.isTestingPageLoaded(), "Testing page did not load properly");

                if (testingPage.getTestResultsCount() == 0) {
                        String testPrompt = "Test that the hello world function returns the correct string";
                        testingPage.clickCreateTest();
                        testingPage.enterTestPrompt(testPrompt);
                        testingPage.clickCreateTestPrompt();

                        TestUtils.waitForElementClickable(webDriver, testingPage.getTestResultCount(),
                                        ConfigProperties.getDefaultTimeout());

                        testingPage.clickRunAllTests();
                        TestUtils.waitForElementClickable(webDriver, testingPage.getTestResultCount(),
                                        ConfigProperties.getDefaultTimeout());
                }

                String testResults = testingPage.getTestResults();
                TestListener.logInfo("Test results: "
                                + (testResults.length() > 100 ? testResults.substring(0, 100) + "..." : testResults));

                Assert.assertFalse(testResults.isEmpty(), "Test results are empty");

                String testStatus = testingPage.getTestStatus();
                TestListener.logInfo("Test status: " + testStatus);
                Assert.assertFalse(testStatus.isEmpty(), "Test status is not displayed");
        }

        @Test(priority = 5, dataProvider = "testCaseTemplates", dataProviderClass = DataProviders.class, description = "Test creating multiple test cases")
        public void testCreateMultipleTestCases(String testPrompt) {
                TestListener.logInfo("Testing creation of multiple test cases with prompt: " + testPrompt);

                testingPage = projectDetailsPage.clickTest();
                Assert.assertTrue(testingPage.isTestingPageLoaded(), "Testing page did not load properly");

                int initialTestCount = testingPage.getTestResultsCount();

                testingPage.clickCreateTest();
                testingPage.enterTestPrompt(testPrompt);
                testingPage.clickCreateTestPrompt();

                TestUtils.waitForElementClickable(webDriver, testingPage.getTestResultCount(),
                                ConfigProperties.getDefaultTimeout());

                int newTestCount = testingPage.getTestResultsCount();
                Assert.assertTrue(newTestCount > initialTestCount, "New test case was not created");
                Assert.assertTrue(testingPage.getTestResults().contains(testPrompt),
                                "Test prompt is not displayed in results");
        }

        @Test(priority = 6, description = "Test navigating back to the project page")
        public void testNavigateBackToProjectPage() {
                TestListener.logInfo("Testing navigation back to the project page");

                testingPage = projectDetailsPage.clickTest();
                Assert.assertTrue(testingPage.isTestingPageLoaded(), "Testing page did not load properly");

                projectDetailsPage = testingPage.clickBackToProject();

                Assert.assertTrue(projectDetailsPage.isLoaded(),
                                "Project details page did not load after navigating back");
                Assert.assertTrue(projectDetailsPage.isPromptInputDisplayed(),
                                "Prompt input is not displayed on project details page");
        }
}