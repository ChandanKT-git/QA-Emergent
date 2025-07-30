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

/**
 * Page object for the Emergent deployment page
 */
public class DeploymentPage {
    private final WebDriver driver;
    private final Logger logger = LogManager.getLogger(DeploymentPage.class);
    private static final Duration TIMEOUT_DURATION = Duration.ofSeconds(10);
    private static final int TIMEOUT = 10;
    private static final Duration LONG_TIMEOUT_DURATION = Duration.ofSeconds(120);
    private static final int LONG_TIMEOUT = 120;

    // Locators
    private final By deploymentTitle = By.xpath("//h1[contains(text(), 'Deployment')]");
    private final By deployButton = By.xpath("//button[contains(text(), 'Deploy')]");
    private final By deploymentEnvironmentDropdown = By.xpath("//select[@id='environment']");
    private final By deploymentStatusIndicator = By.xpath("//div[contains(@class, 'deployment-status')]");
    private final By deploymentLogsArea = By.xpath("//div[contains(@class, 'deployment-logs')]");
    private final By backToProjectButton = By.xpath("//button[contains(text(), 'Back to Project')]");
    private final By deploymentProgressIndicator = By.xpath("//div[contains(@class, 'progress-indicator')]");
    private final By deploymentUrlLink = By.xpath("//a[contains(@class, 'deployment-url')]");

    /**
     * Constructor
     *
     * @param driver WebDriver instance
     */
    public DeploymentPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Selects a deployment environment
     *
     * @param environment environment to select
     * @return DeploymentPage instance
     */
    public DeploymentPage selectEnvironment(String environment) {
        logger.info("Selecting environment: {}", environment);
        WebElement dropdown = TestUtils.waitForElementClickable(driver, deploymentEnvironmentDropdown, TIMEOUT);
        dropdown.click();

        By environmentOption = By.xpath(String.format("//option[text()='%s']", environment));
        WebElement option = TestUtils.waitForElementClickable(driver, environmentOption, TIMEOUT);
        option.click();
        return this;
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
        return this;
    }

    /**
     * Deploys to the given environment
     *
     * @param environment environment to deploy to
     * @return DeploymentPage instance
     */
    public DeploymentPage deploy(String environment) {
        logger.info("Deploying to environment: {}", environment);
        selectEnvironment(environment);
        return clickDeploy();
    }

    /**
     * Gets the deployment status
     *
     * @return deployment status text
     */
    public String getDeploymentStatus() {
        logger.info("Getting deployment status");
        try {
            WebElement element = TestUtils.waitForElementClickable(driver, deploymentStatusIndicator, TIMEOUT);
            return element.getText();
        } catch (Exception e) {
            logger.error("Deployment status not found: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Gets the deployment logs
     *
     * @return deployment logs text
     */
    public String getDeploymentLogs() {
        logger.info("Getting deployment logs");
        try {
            WebElement element = TestUtils.waitForElementClickable(driver, deploymentLogsArea, TIMEOUT);
            return element.getText();
        } catch (Exception e) {
            logger.error("Deployment logs not found: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Gets the deployment URL
     *
     * @return deployment URL
     */
    public String getDeploymentUrl() {
        logger.info("Getting deployment URL");
        try {
            WebElement element = TestUtils.waitForElementClickable(driver, deploymentUrlLink, TIMEOUT);
            return element.getAttribute("href");
        } catch (Exception e) {
            logger.error("Deployment URL not found: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Waits for deployment to complete
     *
     * @return DeploymentPage instance
     */
    public DeploymentPage waitForDeploymentToComplete() {
        logger.info("Waiting for deployment to complete");
        try {
            // Wait for progress indicator to disappear
            WebDriverWait wait = new WebDriverWait(driver, LONG_TIMEOUT_DURATION);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(deploymentProgressIndicator));
            logger.info("Deployment completed");
        } catch (Exception e) {
            logger.error("Error waiting for deployment to complete: {}", e.getMessage());
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
     * Verifies that the deployment page is loaded
     *
     * @return true if the deployment page is loaded, false otherwise
     */
    public boolean isLoaded() {
        logger.info("Verifying that the deployment page is loaded");
        try {
            return TestUtils.waitForElementClickable(driver, deploymentTitle, TIMEOUT).isDisplayed() &&
                    TestUtils.waitForElementClickable(driver, deployButton, TIMEOUT).isDisplayed();
        } catch (Exception e) {
            logger.error("Deployment page is not loaded: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Waits for the deployment page to load completely
     *
     * @return DeploymentPage instance
     */
    public DeploymentPage waitForPageToLoad() {
        logger.info("Waiting for deployment page to load completely");
        TestUtils.waitForElementClickable(driver, deploymentTitle, TIMEOUT);
        TestUtils.waitForElementClickable(driver, deployButton, TIMEOUT);
        // Wait for deployment status to load if any
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrupted while waiting for page to load", e);
        }
        return this;
    }

    /**
     * Alias for isLoaded() to maintain consistency with other page objects
     *
     * @return true if the deployment page is loaded, false otherwise
     */
    public boolean isDeploymentPageLoaded() {
        logger.info("Checking if deployment page is loaded");
        return isLoaded();
    }
}