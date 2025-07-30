package com.emergent.pages;

import com.emergent.utils.TestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Page object for the Emergent project creation page
 */
public class ProjectCreationPage {
    private final WebDriver driver;
    private final Logger logger = LogManager.getLogger(ProjectCreationPage.class);
    private static final Duration TIMEOUT_DURATION = Duration.ofSeconds(10);
    private static final int TIMEOUT = 10;

    // Locators
    private final By projectNameField = By.id("projectName");
    private final By projectDescriptionField = By.id("projectDescription");
    private final By createButton = By.xpath("//button[contains(text(), 'Create')]");
    private final By cancelButton = By.xpath("//button[contains(text(), 'Cancel')]");
    private final By templateOptions = By.xpath("//div[contains(@class, 'template-option')]");
    private final By errorMessage = By.xpath("//div[contains(@class, 'error-message')]");
    private final By createProjectTitle = By.xpath("//h1[contains(text(), 'Create Project')]");

    /**
     * Constructor
     *
     * @param driver WebDriver instance
     */
    public ProjectCreationPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Enters project name in the project name field
     *
     * @param projectName project name to enter
     * @return ProjectCreationPage instance
     */
    public ProjectCreationPage enterProjectName(String projectName) {
        logger.info("Entering project name: {}", projectName);
        WebElement element = TestUtils.waitForElementClickable(driver, projectNameField, TIMEOUT);
        element.clear();
        element.sendKeys(projectName);
        return this;
    }

    /**
     * Enters project description in the project description field
     *
     * @param projectDescription project description to enter
     * @return ProjectCreationPage instance
     */
    public ProjectCreationPage enterProjectDescription(String projectDescription) {
        logger.info("Entering project description");
        WebElement element = TestUtils.waitForElementClickable(driver, projectDescriptionField, TIMEOUT);
        element.clear();
        element.sendKeys(projectDescription);
        return this;
    }

    /**
     * Selects a template by name
     *
     * @param templateName name of the template to select
     * @return ProjectCreationPage instance
     */
    public ProjectCreationPage selectTemplate(String templateName) {
        logger.info("Selecting template: {}", templateName);
        By templateLocator = By
                .xpath(String.format("//div[contains(@class, 'template-option') and contains(., '%s')]", templateName));
        WebElement element = TestUtils.waitForElementClickable(driver, templateLocator, TIMEOUT);
        element.click();
        return this;
    }

    /**
     * Clicks on the create button
     *
     * @return ProjectDetailsPage instance
     */
    public ProjectDetailsPage clickCreate() {
        logger.info("Clicking on create button");
        WebElement element = TestUtils.waitForElementClickable(driver, createButton, TIMEOUT);
        element.click();
        return new ProjectDetailsPage(driver);
    }

    /**
     * Clicks on the create button
     * Alias for clickCreate() for better readability in tests
     *
     * @return ProjectDetailsPage instance
     */
    public ProjectDetailsPage clickCreateButton() {
        logger.info("Clicking on create button");
        return clickCreate();
    }

    /**
     * Clicks on the cancel button
     *
     * @return DashboardPage instance
     */
    public DashboardPage clickCancel() {
        logger.info("Clicking on cancel button");
        WebElement element = TestUtils.waitForElementClickable(driver, cancelButton, TIMEOUT);
        element.click();
        return new DashboardPage(driver);
    }

    /**
     * Clicks on the cancel button
     * Alias for clickCancel() for better readability in tests
     *
     * @return DashboardPage instance
     */
    public DashboardPage clickCancelButton() {
        logger.info("Clicking on cancel button");
        return clickCancel();
    }

    /**
     * Creates a project with the given details
     *
     * @param projectName        project name
     * @param projectDescription project description
     * @param templateName       template name (optional, can be null)
     * @return ProjectDetailsPage instance
     */
    public ProjectDetailsPage createProject(String projectName, String projectDescription, String templateName) {
        logger.info("Creating project: {}", projectName);
        enterProjectName(projectName);
        enterProjectDescription(projectDescription);
        if (templateName != null && !templateName.isEmpty()) {
            selectTemplate(templateName);
        }
        return clickCreate();
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

    /**
     * Verifies that the project creation page is loaded
     *
     * @return true if the project creation page is loaded, false otherwise
     */
    public boolean isLoaded() {
        logger.info("Verifying that the project creation page is loaded");
        try {
            return TestUtils.waitForElementClickable(driver, createProjectTitle, TIMEOUT).isDisplayed() &&
                    TestUtils.waitForElementClickable(driver, projectNameField, TIMEOUT).isDisplayed() &&
                    TestUtils.waitForElementClickable(driver, projectDescriptionField, TIMEOUT).isDisplayed() &&
                    TestUtils.waitForElementClickable(driver, createButton, TIMEOUT).isDisplayed();
        } catch (Exception e) {
            logger.error("Project creation page is not loaded: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Checks if the project creation page is loaded
     * 
     * @return true if the project creation page is loaded, false otherwise
     */
    public boolean isProjectCreationPageLoaded() {
        logger.info("Checking if project creation page is loaded");
        return isLoaded();
    }

    /**
     * Clicks on the create button expecting an error
     * Used for negative test cases where we expect the creation to fail
     */
    public void clickCreateButtonExpectingError() {
        logger.info("Clicking on create button expecting an error");
        WebElement element = TestUtils.waitForElementClickable(driver, createButton, TIMEOUT);
        element.click();
        // Wait for error message to appear
        TestUtils.waitForElementClickable(driver, errorMessage, TIMEOUT);
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
}