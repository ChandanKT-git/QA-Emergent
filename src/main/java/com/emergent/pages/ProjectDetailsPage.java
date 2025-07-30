package com.emergent.pages;

import com.emergent.utils.TestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Page object for the Emergent project details page
 */
public class ProjectDetailsPage {
    private final WebDriver driver;
    private final Logger logger = LogManager.getLogger(ProjectDetailsPage.class);
    private static final Duration TIMEOUT_DURATION = Duration.ofSeconds(10);
    private static final int TIMEOUT = 10;
    private static final Duration LONG_TIMEOUT_DURATION = Duration.ofSeconds(30);
    private static final int LONG_TIMEOUT = 30;

    // Locators
    private final By projectTitle = By.xpath("//h1[@class='project-title']");
    private final By projectDescription = By.xpath("//div[@class='project-description']");
    private final By backToDashboardButton = By.xpath("//button[contains(text(), 'Back to Dashboard')]");
    private final By promptInput = By.xpath("//textarea[@placeholder='Enter your prompt here']");

    /**
     * Gets the prompt input locator
     *
     * @return prompt input locator
     */
    public By getPromptInputLocator() {
        return promptInput;
    }

    /**
     * Gets the AI response locator
     *
     * @return AI response locator
     */
    public By getAIResponseLocator() {
        return aiResponseArea;
    }

    private final By sendPromptButton = By.xpath("//button[contains(text(), 'Send')]");
    private final By aiResponseArea = By.xpath("//div[contains(@class, 'ai-response')]");
    private final By errorMessage = By.xpath("//div[contains(@class, 'error-message')]");
    private final By codePreviewArea = By.xpath("//div[contains(@class, 'code-preview')]");
    private final By deployButton = By.xpath("//button[contains(text(), 'Deploy')]");
    private final By testButton = By.xpath("//button[contains(text(), 'Test')]");
    private final By settingsButton = By.xpath("//button[contains(text(), 'Settings')]");

    /**
     * Constructor
     *
     * @param driver WebDriver instance
     */
    public ProjectDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Gets the project title
     *
     * @return project title
     */
    public String getProjectTitle() {
        logger.info("Getting project title");
        WebElement element = TestUtils.waitForElementClickable(driver, projectTitle, TIMEOUT);
        return element.getText();
    }

    /**
     * Gets the project description
     *
     * @return project description
     */
    public String getProjectDescription() {
        logger.info("Getting project description");
        WebElement element = TestUtils.waitForElementClickable(driver, projectDescription, TIMEOUT);
        return element.getText();
    }

    /**
     * Clicks on the back to dashboard button
     *
     * @return DashboardPage instance
     */
    public DashboardPage clickBackToDashboard() {
        logger.info("Clicking on back to dashboard button");
        WebElement element = TestUtils.waitForElementClickable(driver, backToDashboardButton, TIMEOUT);
        element.click();
        return new DashboardPage(driver);
    }

    /**
     * Clicks on the back to dashboard button
     * Alias for clickBackToDashboard() for better readability in tests
     *
     * @return DashboardPage instance
     */
    public DashboardPage clickBackToDashboardButton() {
        logger.info("Clicking on back to dashboard button");
        return clickBackToDashboard();
    }

    /**
     * Enters a prompt in the prompt input field
     *
     * @param prompt prompt to enter
     * @return ProjectDetailsPage instance
     */
    public ProjectDetailsPage enterPrompt(String prompt) {
        logger.info("Entering prompt: {}", prompt);
        WebElement element = TestUtils.waitForElementClickable(driver, promptInput, TIMEOUT);
        element.clear();
        element.sendKeys(prompt);
        return this;
    }

    /**
     * Clicks on the send prompt button
     *
     * @return ProjectDetailsPage instance
     */
    public ProjectDetailsPage clickSendPrompt() {
        logger.info("Clicking on send prompt button");
        WebElement element = TestUtils.waitForElementClickable(driver, sendPromptButton, TIMEOUT);
        element.click();
        return this;
    }

    /**
     * Clicks on the send prompt button (alias for clickSendPrompt)
     *
     * @return ProjectDetailsPage instance
     */
    public ProjectDetailsPage clickSendPromptButton() {
        logger.info("Clicking on send prompt button");
        return clickSendPrompt();
    }

    /**
     * Sends a prompt
     *
     * @param prompt prompt to send
     * @return ProjectDetailsPage instance
     */
    public ProjectDetailsPage sendPrompt(String prompt) {
        logger.info("Sending prompt: {}", prompt);
        enterPrompt(prompt);
        return clickSendPrompt();
    }

    /**
     * Waits for AI response
     *
     * @return AI response text
     */
    public String waitForAIResponse() {
        logger.info("Waiting for AI response");
        WebElement element = TestUtils.waitForElementClickable(driver, aiResponseArea, LONG_TIMEOUT);
        return element.getText();
    }

    /**
     * Gets the AI response text
     *
     * @return AI response text
     */
    public String getAIResponse() {
        logger.info("Getting AI response");
        try {
            WebElement element = TestUtils.waitForElementClickable(driver, aiResponseArea, TIMEOUT);
            return element.getText();
        } catch (Exception e) {
            logger.error("AI response not found: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Gets the code preview
     *
     * @return code preview text
     */
    public String getCodePreview() {
        logger.info("Getting code preview");
        try {
            WebElement element = TestUtils.waitForElementClickable(driver, codePreviewArea, TIMEOUT);
            return element.getText();
        } catch (Exception e) {
            logger.error("Code preview not found: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Clicks on the deploy button
     *
     * @return DeploymentPage instance
     */
    public DeploymentPage clickDeploy() {
        logger.info("Clicking on deploy button");
        WebElement element = TestUtils.waitForElementClickable(driver, deployButton, TIMEOUT);
        element.click();
        return new DeploymentPage(driver);
    }

    /**
     * Clicks on the test button
     *
     * @return TestingPage instance
     */
    public TestingPage clickTest() {
        logger.info("Clicking on test button");
        WebElement element = TestUtils.waitForElementClickable(driver, testButton, TIMEOUT);
        element.click();
        return new TestingPage(driver);
    }

    /**
     * Clicks on the settings button
     *
     * @return ProjectSettingsPage instance
     */
    public ProjectSettingsPage clickSettings() {
        logger.info("Clicking on settings button");
        WebElement element = TestUtils.waitForElementClickable(driver, settingsButton, TIMEOUT);
        element.click();
        return new ProjectSettingsPage(driver);
    }

    /**
     * Verifies that the project details page is loaded
     *
     * @return true if the project details page is loaded, false otherwise
     */
    public boolean isLoaded() {
        logger.info("Verifying that the project details page is loaded");
        try {
            return TestUtils.waitForElementClickable(driver, projectTitle, TIMEOUT).isDisplayed() &&
                    TestUtils.waitForElementClickable(driver, promptInput, TIMEOUT).isDisplayed() &&
                    TestUtils.waitForElementClickable(driver, sendPromptButton, TIMEOUT).isDisplayed();
        } catch (Exception e) {
            logger.error("Project details page is not loaded: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Alias for isLoaded() to maintain consistency with other page objects
     *
     * @return true if the project details page is loaded, false otherwise
     */
    public boolean isProjectDetailsPageLoaded() {
        return isLoaded();
    }

    /**
     * Waits for the project details page to load completely
     *
     * @return ProjectDetailsPage instance
     */
    public ProjectDetailsPage waitForPageToLoad() {
        logger.info("Waiting for project details page to load completely");
        TestUtils.waitForElementClickable(driver, projectTitle, TIMEOUT);
        TestUtils.waitForElementClickable(driver, promptInput, TIMEOUT);
        TestUtils.waitForElementClickable(driver, sendPromptButton, TIMEOUT);
        // Wait for dynamic content to load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrupted while waiting for page to load", e);
        }
        return this;
    }

    /**
     * Checks if the prompt input is displayed
     *
     * @return true if the prompt input is displayed, false otherwise
     */
    public boolean isPromptInputDisplayed() {
        logger.info("Checking if prompt input is displayed");
        try {
            return TestUtils.waitForElementClickable(driver, promptInput, TIMEOUT).isDisplayed();
        } catch (Exception e) {
            logger.error("Prompt input is not displayed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Checks if the code preview is displayed
     *
     * @return true if the code preview is displayed, false otherwise
     */
    public boolean isCodePreviewDisplayed() {
        logger.info("Checking if code preview is displayed");
        try {
            return TestUtils.waitForElementClickable(driver, codePreviewArea, TIMEOUT).isDisplayed();
        } catch (Exception e) {
            logger.error("Code preview is not displayed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Checks if an error message is displayed
     *
     * @return true if an error message is displayed, false otherwise
     */
    public boolean isErrorMessageDisplayed() {
        logger.info("Checking if error message is displayed");
        try {
            return TestUtils.waitForElementClickable(driver, errorMessage, TIMEOUT).isDisplayed();
        } catch (Exception e) {
            logger.error("Error message is not displayed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Gets the error message text
     *
     * @return error message text
     */
    public String getErrorMessage() {
        logger.info("Getting error message");
        try {
            WebElement element = TestUtils.waitForElementClickable(driver, errorMessage, TIMEOUT);
            return element.getText();
        } catch (Exception e) {
            logger.error("Error message not found: {}", e.getMessage());
            return "";
        }
    }
}