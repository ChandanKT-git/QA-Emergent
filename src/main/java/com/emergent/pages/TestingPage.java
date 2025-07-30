package com.emergent.pages;

import com.emergent.utils.TestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page object for the Emergent testing page
 */
public class TestingPage {
    private final WebDriver driver;
    private final Logger logger = LogManager.getLogger(TestingPage.class);
    private static final Duration TIMEOUT_DURATION = Duration.ofSeconds(10);
    private static final int TIMEOUT = 10;
    private static final Duration LONG_TIMEOUT_DURATION = Duration.ofSeconds(60);
    private static final int LONG_TIMEOUT = 60;

    // Locators
    private final By testingTitle = By.xpath("//h1[contains(text(), 'Testing')]");
    private final By runAllTestsButton = By.xpath("//button[contains(text(), 'Run All Tests')]");
    private final By createTestButton = By.xpath("//button[contains(text(), 'Create Test')]");
    private final By testResultsList = By.xpath("//div[contains(@class, 'test-result')]");
    private final By testStatusIndicator = By.xpath("//div[contains(@class, 'test-status')]");
    private final By backToProjectButton = By.xpath("//button[contains(text(), 'Back to Project')]");
    private final By testPromptInput = By.xpath("//textarea[@placeholder='Enter test description']");
    private final By createTestPromptButton = By.xpath("//button[contains(text(), 'Create')]");
    private final By testProgressIndicator = By.xpath("//div[contains(@class, 'progress-indicator')]");

    /**
     * Constructor
     *
     * @param driver WebDriver instance
     */
    public TestingPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Clicks on the run all tests button
     *
     * @return TestingPage instance
     */
    public TestingPage clickRunAllTests() {
        logger.info("Clicking on run all tests button");
        WebElement element = TestUtils.waitForElementClickable(driver, runAllTestsButton, TIMEOUT);
        element.click();
        return this;
    }

    /**
     * Clicks on the create test button
     *
     * @return TestingPage instance
     */
    public TestingPage clickCreateTest() {
        logger.info("Clicking on create test button");
        WebElement element = TestUtils.waitForElementClickable(driver, createTestButton, TIMEOUT);
        element.click();
        return this;
    }

    /**
     * Enters test prompt in the test prompt input field
     *
     * @param testPrompt test prompt to enter
     * @return TestingPage instance
     */
    public TestingPage enterTestPrompt(String testPrompt) {
        logger.info("Entering test prompt: {}", testPrompt);
        WebElement element = TestUtils.waitForElementClickable(driver, testPromptInput, TIMEOUT);
        element.clear();
        element.sendKeys(testPrompt);
        return this;
    }

    /**
     * Clicks on the create test prompt button
     *
     * @return TestingPage instance
     */
    public TestingPage clickCreateTestPrompt() {
        logger.info("Clicking on create test prompt button");
        WebElement element = TestUtils.waitForElementClickable(driver, createTestPromptButton, TIMEOUT);
        element.click();
        return this;
    }

    /**
     * Creates a test with the given prompt
     *
     * @param testPrompt test prompt to use
     * @return TestingPage instance
     */
    public TestingPage createTest(String testPrompt) {
        logger.info("Creating test with prompt: {}", testPrompt);
        clickCreateTest();
        enterTestPrompt(testPrompt);
        return clickCreateTestPrompt();
    }

    /**
     * Gets the list of test results as WebElements
     *
     * @return list of test result WebElements
     */
    public List<WebElement> getTestResultElements() {
        logger.info("Getting list of test result elements");
        return driver.findElements(testResultsList);
    }

    /**
     * Gets the test results as a concatenated string
     *
     * @return string containing all test results text
     */
    public String getTestResults() {
        logger.info("Getting test results text");
        StringBuilder results = new StringBuilder();
        try {
            List<WebElement> elements = getTestResultElements();
            for (WebElement element : elements) {
                results.append(element.getText()).append("\n");
            }
            return results.toString();
        } catch (Exception e) {
            logger.error("Error getting test results: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Gets the number of test results
     *
     * @return number of test results
     */
    public int getTestResultCount() {
        logger.info("Getting test result count");
        try {
            List<WebElement> elements = driver.findElements(testResultsList);
            return elements.size();
        } catch (Exception e) {
            logger.error("Error getting test result count: {}", e.getMessage());
            return 0;
        }
    }

    /**
     * Alias for getTestResultCount() to maintain consistency with test naming
     * 
     * 
     * @return number of test results
     */
    public int getTestResultsCount() {
        logger.info("Getting test results count");
        return getTestResultCount();
    }

    /**
     * Gets the test status
     *
     * @return test status text
     */
    public String getTestStatus() {
        logger.info("Getting test status");
        try {
            WebElement element = TestUtils.waitForElementClickable(driver, testStatusIndicator, TIMEOUT);
            return element.getText();
        } catch (Exception e) {
            logger.error("Test status not found: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Waits for tests to complete using the default long timeout
     *
     * @return TestingPage instance
     */
    public TestingPage waitForTestsToComplete() {
        logger.info("Waiting for tests to complete with default timeout");
        try {
            // Wait for progress indicator to disappear
            WebDriverWait wait = new WebDriverWait(driver, LONG_TIMEOUT_DURATION);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(testProgressIndicator));
            logger.info("Tests completed");
        } catch (Exception e) {
            logger.error("Error waiting for tests to complete: {}", e.getMessage());
        }
        return this;
    }

    /**
     * Waits for tests to complete with a specified timeout
     *
     * @param timeoutInSeconds the timeout in seconds
     * @return TestingPage instance
     */
    public TestingPage waitForTestsToComplete(Duration timeoutInSeconds) {
        logger.info("Waiting for tests to complete with timeout: {} seconds", timeoutInSeconds);
        try {
            // Wait for progress indicator to disappear
            WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(testProgressIndicator));
            logger.info("Tests completed");
        } catch (Exception e) {
            logger.error("Error waiting for tests to complete: {}", e.getMessage());
        }
        return this;
    }

    /**
     * Clicks on the back to project button
     *
     * @return ProjectDetailsPage instance
     */
    public ProjectDetailsPage clickBackToProject() {
        logger.info("Clicking on back to project button");
        WebElement element = TestUtils.waitForElementClickable(driver, backToProjectButton, TIMEOUT);
        element.click();
        return new ProjectDetailsPage(driver);
    }

    /**
     * Verifies that the testing page is loaded
     *
     * @return true if the testing page is loaded, false otherwise
     */
    public boolean isLoaded() {
        logger.info("Verifying that the testing page is loaded");
        try {
            return TestUtils.waitForElementClickable(driver, testingTitle, TIMEOUT).isDisplayed() &&
                    TestUtils.waitForElementClickable(driver, runAllTestsButton, TIMEOUT).isDisplayed() &&
                    TestUtils.waitForElementClickable(driver, createTestButton, TIMEOUT).isDisplayed();
        } catch (Exception e) {
            logger.error("Testing page is not loaded: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Waits for the testing page to load completely
     *
     * @return TestingPage instance
     */
    public TestingPage waitForPageToLoad() {
        logger.info("Waiting for testing page to load completely");
        TestUtils.waitForElementClickable(driver, testingTitle, TIMEOUT);
        TestUtils.waitForElementClickable(driver, runAllTestsButton, TIMEOUT);
        TestUtils.waitForElementClickable(driver, createTestButton, TIMEOUT);
        // Wait for test results to load if any
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrupted while waiting for page to load", e);
        }
        return this;
    }

    /**
     * Checks if the testing title is displayed
     *
     * @return true if the testing title is displayed, false otherwise
     */
    public boolean isTestingTitleDisplayed() {
        logger.info("Checking if testing title is displayed");
        try {
            return TestUtils.waitForElementClickable(driver, testingTitle, TIMEOUT).isDisplayed();
        } catch (Exception e) {
            logger.error("Testing title is not displayed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Alias for isLoaded() to maintain consistency with other page objects
     *
     * @return true if the testing page is loaded, false otherwise
     */
    public boolean isTestingPageLoaded() {
        logger.info("Checking if testing page is loaded");
        return isLoaded();
    }
    
    /**
     * Gets the locator for the test results list
     *
     * @return the By locator for the test results list
     */
    public By getTestResultsListLocator() {
        logger.info("Getting test results list locator");
        return testResultsList;
    }
}