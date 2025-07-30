package com.emergent.pages;

import com.emergent.utils.TestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Page object for the Emergent project settings page
 */
public class ProjectSettingsPage {
    private final WebDriver driver;
    private final Logger logger = LogManager.getLogger(ProjectSettingsPage.class);
    private static final Duration TIMEOUT_DURATION = Duration.ofSeconds(10);
    private static final int TIMEOUT = 10;

    // Locators
    private final By settingsTitle = By.xpath("//h1[contains(text(), 'Project Settings')]");
    private final By projectNameField = By.id("projectName");
    private final By projectDescriptionField = By.id("projectDescription");
    private final By saveChangesButton = By.xpath("//button[contains(text(), 'Save Changes')]");
    private final By deleteProjectButton = By.xpath("//button[contains(text(), 'Delete Project')]");
    private final By confirmDeleteButton = By.xpath("//button[contains(text(), 'Confirm Delete')]");
    private final By cancelDeleteButton = By.xpath("//button[contains(text(), 'Cancel')]");
    private final By backToProjectButton = By.xpath("//button[contains(text(), 'Back to Project')]");
    private final By successMessage = By.xpath("//div[contains(@class, 'success-message')]");
    private final By errorMessage = By.xpath("//div[contains(@class, 'error-message')]");

    /**
     * Constructor
     *
     * @param driver WebDriver instance
     */
    public ProjectSettingsPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Gets the current project name
     *
     * @return current project name
     */
    public String getProjectName() {
        logger.info("Getting current project name");
        WebElement element = TestUtils.waitForElementClickable(driver, projectNameField, TIMEOUT);
        return element.getAttribute("value");
    }

    /**
     * Gets the current project description
     *
     * @return current project description
     */
    public String getProjectDescription() {
        logger.info("Getting current project description");
        WebElement element = TestUtils.waitForElementClickable(driver, projectDescriptionField, TIMEOUT);
        return element.getAttribute("value");
    }

    /**
     * Enters project name in the project name field
     *
     * @param projectName project name to enter
     * @return ProjectSettingsPage instance
     */
    public ProjectSettingsPage enterProjectName(String projectName) {
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
     * @return ProjectSettingsPage instance
     */
    public ProjectSettingsPage enterProjectDescription(String projectDescription) {
        logger.info("Entering project description");
        WebElement element = TestUtils.waitForElementClickable(driver, projectDescriptionField, TIMEOUT);
        element.clear();
        element.sendKeys(projectDescription);
        return this;
    }

    /**
     * Clicks on the save changes button
     *
     * @return ProjectSettingsPage instance
     */
    public ProjectSettingsPage clickSaveChanges() {
        logger.info("Clicking on save changes button");
        WebElement element = TestUtils.waitForElementClickable(driver, saveChangesButton, TIMEOUT);
        element.click();
        return this;
    }

    /**
     * Updates project settings
     *
     * @param projectName        project name
     * @param projectDescription project description
     * @return ProjectSettingsPage instance
     */
    public ProjectSettingsPage updateProjectSettings(String projectName, String projectDescription) {
        logger.info("Updating project settings");
        enterProjectName(projectName);
        enterProjectDescription(projectDescription);
        return clickSaveChanges();
    }

    /**
     * Clicks on the delete project button
     *
     * @return ProjectSettingsPage instance
     */
    public ProjectSettingsPage clickDeleteProject() {
        logger.info("Clicking on delete project button");
        WebElement element = TestUtils.waitForElementClickable(driver, deleteProjectButton, TIMEOUT);
        element.click();
        return this;
    }

    /**
     * Clicks on the confirm delete button
     *
     * @return DashboardPage instance
     */
    public DashboardPage clickConfirmDelete() {
        logger.info("Clicking on confirm delete button");
        WebElement element = TestUtils.waitForElementClickable(driver, confirmDeleteButton, TIMEOUT);
        element.click();
        return new DashboardPage(driver);
    }

    /**
     * Clicks on the cancel delete button
     *
     * @return ProjectSettingsPage instance
     */
    public ProjectSettingsPage clickCancelDelete() {
        logger.info("Clicking on cancel delete button");
        WebElement element = TestUtils.waitForElementClickable(driver, cancelDeleteButton, TIMEOUT);
        element.click();
        return this;
    }

    /**
     * Deletes the project
     *
     * @return DashboardPage instance
     */
    public DashboardPage deleteProject() {
        logger.info("Deleting project");
        clickDeleteProject();
        return clickConfirmDelete();
    }

    /**
     * Gets the success message text
     *
     * @return success message text
     */
    public String getSuccessMessage() {
        logger.info("Getting success message");
        try {
            WebElement element = TestUtils.waitForElementClickable(driver, successMessage, TIMEOUT);
            return element.getText();
        } catch (Exception e) {
            logger.error("Success message not found: {}", e.getMessage());
            return "";
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
     * Verifies that the project settings page is loaded
     *
     * @return true if the project settings page is loaded, false otherwise
     */
    public boolean isLoaded() {
        logger.info("Verifying that the project settings page is loaded");
        try {
            return TestUtils.waitForElementClickable(driver, settingsTitle, TIMEOUT).isDisplayed() &&
                    TestUtils.waitForElementClickable(driver, projectNameField, TIMEOUT).isDisplayed() &&
                    TestUtils.waitForElementClickable(driver, projectDescriptionField, TIMEOUT).isDisplayed() &&
                    TestUtils.waitForElementClickable(driver, saveChangesButton, TIMEOUT).isDisplayed();
        } catch (Exception e) {
            logger.error("Project settings page is not loaded: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Alias for isLoaded() to maintain consistency with other page objects
     *
     * @return true if the project settings page is loaded, false otherwise
     */
    public boolean isProjectSettingsPageLoaded() {
        logger.info("Checking if project settings page is loaded");
        return isLoaded();
    }

    /**
     * Checks if the settings title is displayed
     *
     * @return true if the settings title is displayed, false otherwise
     */
    public boolean isSettingsTitleDisplayed() {
        logger.info("Checking if settings title is displayed");
        try {
            return TestUtils.waitForElementClickable(driver, settingsTitle, TIMEOUT).isDisplayed();
        } catch (Exception e) {
            logger.error("Settings title is not displayed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Gets the locator for the success message
     *
     * @return the success message locator
     */
    public By getSuccessMessageLocator() {
        return successMessage;
    }

    /**
     * Gets the locator for the error message
     *
     * @return the error message locator
     */
    public By getErrorMessageLocator() {
        return errorMessage;
    }

    /**
     * Gets the locator for the confirm delete button
     *
     * @return the confirm delete button locator
     */
    public By getConfirmDeleteButtonLocator() {
        return confirmDeleteButton;
    }

    /**
     * Gets the locator for the cancel delete button
     *
     * @return the cancel delete button locator
     */
    public By getCancelDeleteButtonLocator() {
        return cancelDeleteButton;
    }

    /**
     * Clicks on the save button (alias for clickSaveChanges)
     *
     * @return ProjectSettingsPage instance
     */
    public ProjectSettingsPage clickSaveButton() {
        return clickSaveChanges();
    }

    /**
     * Clicks on the delete button (alias for clickDeleteProject)
     *
     * @return ProjectSettingsPage instance
     */
    public ProjectSettingsPage clickDeleteButton() {
        return clickDeleteProject();
    }

    /**
     * Clicks on the confirm delete button (alias for clickConfirmDelete)
     *
     * @return DashboardPage instance
     */
    public DashboardPage clickConfirmDeleteButton() {
        return clickConfirmDelete();
    }

    /**
     * Clicks on the cancel delete button (alias for clickCancelDelete)
     *
     * @return ProjectSettingsPage instance
     */
    public ProjectSettingsPage clickCancelDeleteButton() {
        return clickCancelDelete();
    }
}