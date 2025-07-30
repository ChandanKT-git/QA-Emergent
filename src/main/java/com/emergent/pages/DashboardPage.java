package com.emergent.pages;

import com.emergent.utils.TestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

/**
 * Page object for the Emergent dashboard page
 */
public class DashboardPage {
    private final WebDriver driver;
    private final Logger logger = LogManager.getLogger(DashboardPage.class);
    private static final Duration TIMEOUT_DURATION = Duration.ofSeconds(10);
    private static final int TIMEOUT = 10;

    // Locators
    private final By createProjectButton = By.xpath("//button[contains(text(), 'Create Project')]");
    private final By projectsList = By.xpath("//div[contains(@class, 'project-card')]");
    private final By userProfileMenu = By.xpath("//div[contains(@class, 'user-profile')]");
    private final By logoutOption = By.xpath("//button[contains(text(), 'Logout')]");
    private final By dashboardTitle = By.xpath("//h1[contains(text(), 'Dashboard')]");
    private final By searchBox = By.xpath("//input[@placeholder='Search projects']");

    /**
     * Constructor
     *
     * @param driver WebDriver instance
     */
    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Navigates to the dashboard page
     *
     * @return DashboardPage instance
     */
    public DashboardPage navigateTo() {
        logger.info("Navigating to Emergent dashboard page");
        driver.get("https://app.emergent.sh/dashboard");
        return this;
    }

    /**
     * Clicks on the create project button
     *
     * @return ProjectCreationPage instance
     */
    public ProjectCreationPage clickCreateProject() {
        logger.info("Clicking on create project button");
        WebElement element = TestUtils.waitForElementClickable(driver, createProjectButton, TIMEOUT);
        element.click();
        return new ProjectCreationPage(driver);
    }

    /**
     * Alias for clickCreateProject() to maintain consistency with test naming
     *
     * @return ProjectCreationPage instance
     */
    public ProjectCreationPage clickCreateNewProjectButton() {
        return clickCreateProject();
    }

    /**
     * Gets the list of projects
     *
     * @return list of project WebElements
     */
    public List<WebElement> getProjects() {
        logger.info("Getting list of projects");
        return driver.findElements(projectsList);
    }

    /**
     * Gets the number of projects
     *
     * @return number of projects
     */
    public int getProjectCount() {
        logger.info("Getting project count");
        return getProjects().size();
    }

    /**
     * Opens a project by name
     *
     * @param projectName name of the project to open
     * @return ProjectDetailsPage instance
     */
    public ProjectDetailsPage openProject(String projectName) {
        logger.info("Opening project: {}", projectName);
        By projectLocator = By.xpath(String.format(
                "//div[contains(@class, 'project-card') and contains(., '%s')]//button[contains(text(), 'Open')]",
                projectName));
        WebElement element = TestUtils.waitForElementClickable(driver, projectLocator, TIMEOUT);
        element.click();
        return new ProjectDetailsPage(driver);
    }

    /**
     * Searches for a project
     *
     * @param searchTerm search term
     * @return DashboardPage instance
     */
    public DashboardPage searchProject(String searchTerm) {
        logger.info("Searching for project: {}", searchTerm);
        WebElement element = TestUtils.waitForElementClickable(driver, searchBox, TIMEOUT);
        element.clear();
        element.sendKeys(searchTerm);
        return this;
    }

    /**
     * Logs out
     *
     * @return HomePage instance
     */
    public HomePage logout() {
        logger.info("Logging out");
        WebElement profileMenu = TestUtils.waitForElementClickable(driver, userProfileMenu, TIMEOUT);
        profileMenu.click();

        WebElement logoutBtn = TestUtils.waitForElementClickable(driver, logoutOption, TIMEOUT);
        logoutBtn.click();
        return new HomePage(driver);
    }

    /**
     * Verifies that the dashboard page is loaded
     *
     * @return true if the dashboard page is loaded, false otherwise
     */
    public boolean isLoaded() {
        logger.info("Verifying that the dashboard page is loaded");
        try {
            return TestUtils.waitForElementClickable(driver, dashboardTitle, TIMEOUT).isDisplayed() &&
                    TestUtils.waitForElementClickable(driver, createProjectButton, TIMEOUT).isDisplayed();
        } catch (Exception e) {
            logger.error("Dashboard page is not loaded: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Alias for isLoaded() to maintain consistency with other page objects
     *
     * @return true if the dashboard page is loaded, false otherwise
     */
    public boolean isDashboardPageLoaded() {
        return isLoaded();
    }

    /**
     * Waits for the dashboard to load completely
     *
     * @return DashboardPage instance
     */
    public DashboardPage waitForPageToLoad() {
        logger.info("Waiting for dashboard page to load completely");
        TestUtils.waitForElementClickable(driver, dashboardTitle, TIMEOUT);
        TestUtils.waitForElementClickable(driver, createProjectButton, TIMEOUT);
        // Wait for projects to load if any
        try {
            Thread.sleep(2000); // Additional wait for dynamic content
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrupted while waiting for page to load", e);
        }
        return this;
    }

    public boolean isUserLoggedIn() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isUserLoggedIn'");
    }

    public void clickUserProfileMenu() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clickUserProfileMenu'");
    }

    public HomePage clickLogoutOption() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clickLogoutOption'");
    }

    public boolean isProjectInList(String initialProjectName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isProjectInList'");
    }
}