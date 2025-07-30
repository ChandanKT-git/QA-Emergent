package com.emergent.tests;

import com.emergent.pages.DashboardPage;
import com.emergent.pages.DeploymentPage;
import com.emergent.pages.ProjectCreationPage;
import com.emergent.pages.ProjectDetailsPage;
import com.emergent.utils.ConfigProperties;
import com.emergent.utils.Constants;
import com.emergent.utils.DataProviders;
import com.emergent.utils.TestListener;
import com.emergent.utils.TestUtils;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for deployment functionality of the platform.
 */
public class DeploymentTest extends BaseTest {

    private static final int TIMEOUT = Constants.DEFAULT_TIMEOUT;

    private ProjectDetailsPage projectDetailsPage;
    private DeploymentPage deploymentPage;

    /**
     * Set up method to log in and create a project before each test.
     */
    @BeforeMethod
    public void setUp() {
        TestListener.logInfo("Setting up DeploymentTest");
        
        // Login with default credentials
        loginWithDefaultCredentials();
        
        // Create a new project for testing
        DashboardPage dashboardPage = new DashboardPage(webDriver);
        Assert.assertTrue(dashboardPage.isLoaded(), "Dashboard page did not load");
        
        // Create a new project or open an existing one
        String projectName = TestUtils.generateRandomProjectName();
        ProjectCreationPage projectCreationPage = dashboardPage.clickCreateProject();
        projectCreationPage.enterProjectName(projectName);
        projectCreationPage.enterProjectDescription(TestUtils.generateRandomProjectDescription());
        projectDetailsPage = projectCreationPage.clickCreate();
        
        Assert.assertTrue(projectDetailsPage.isLoaded(), 
                "Project details page did not load after project creation");
        
        // Generate some code to deploy
        String prompt = "Create a simple hello world web application";
        projectDetailsPage.sendPrompt(prompt);
        
        // Wait for AI response
        projectDetailsPage.waitForAIResponse();
    }

    /**
     * Tests navigating to the deployment page.
     */
    @Test(priority = 1, description = "Test navigating to the deployment page")
    public void testNavigateToDeploymentPage() {
        TestListener.logInfo("Testing navigation to the deployment page");
        
        // Navigate to deployment page
        deploymentPage = projectDetailsPage.clickDeploy();
        
        // Verify deployment page is loaded
        Assert.assertTrue(deploymentPage.isLoaded(), 
                "Deployment page did not load properly");
        
        // Verify deployment page title is displayed
        Assert.assertTrue(deploymentPage.isLoaded(), 
                "Deployment page title is not displayed");
    }

    /**
     * Tests deploying to different environments.
     * 
     * @param environment the environment to deploy to
     */
    @Test(priority = 2, dataProvider = "deploymentEnvironments", dataProviderClass = DataProviders.class, 
          description = "Test deploying to different environments")
    public void testDeployToEnvironment(String environment) {
        TestListener.logInfo("Testing deployment to environment: " + environment);
        
        // Navigate to deployment page
        deploymentPage = projectDetailsPage.clickDeploy();
        Assert.assertTrue(deploymentPage.isLoaded(), 
                "Deployment page did not load properly");
        
        // Select environment
        deploymentPage.selectEnvironment(environment);
        
        // Click deploy button
        deploymentPage.clickDeploy();
        
        // Wait for deployment to start
        // Using waitForPageToLoad instead of directly accessing locators
        deploymentPage.waitForPageToLoad();
        
        // Wait for deployment to complete
        deploymentPage.waitForDeploymentToComplete();
        
        // Verify deployment status
        String status = deploymentPage.getDeploymentStatus();
        TestListener.logInfo("Deployment status: " + status);
        Assert.assertTrue(status.contains("Success") || status.contains("Deployed") || 
                status.contains("Complete"), "Deployment was not successful. Status: " + status);
    }

    /**
     * Tests viewing deployment logs.
     */
    @Test(priority = 3, description = "Test viewing deployment logs")
    public void testViewDeploymentLogs() {
        TestListener.logInfo("Testing viewing deployment logs");
        
        // Navigate to deployment page
        deploymentPage = projectDetailsPage.clickDeploy();
        Assert.assertTrue(deploymentPage.isLoaded(), 
                "Deployment page did not load properly");
        
        // Deploy to development environment if no deployment exists
        String status = deploymentPage.getDeploymentStatus();
        if (status.isEmpty()) {
            deploymentPage.selectEnvironment(Constants.ENV_DEVELOPMENT);
            deploymentPage.clickDeploy();
            deploymentPage.waitForDeploymentToComplete();
        }
        
        // Get deployment logs
        String logs = deploymentPage.getDeploymentLogs();
        TestListener.logInfo("Deployment logs (first 100 chars): " + 
                (logs.length() > 100 ? logs.substring(0, 100) + "..." : logs));
        
        // Verify logs are not empty
        Assert.assertFalse(logs.isEmpty(), "Deployment logs are empty");
    }

    /**
     * Tests accessing the deployed application URL.
     */
    @Test(priority = 4, description = "Test accessing the deployed application URL")
    public void testAccessDeployedApplicationUrl() {
        TestListener.logInfo("Testing accessing the deployed application URL");
        
        // Navigate to deployment page
        deploymentPage = projectDetailsPage.clickDeploy();
        Assert.assertTrue(deploymentPage.isLoaded(), 
                "Deployment page did not load properly");
        
        // Deploy to development environment if no deployment exists
        String deploymentUrl = deploymentPage.getDeploymentUrl();
        if (deploymentUrl.isEmpty()) {
            deploymentPage.selectEnvironment(Constants.ENV_DEVELOPMENT);
            deploymentPage.clickDeploy();
            deploymentPage.waitForDeploymentToComplete();
            
            // Get deployment URL after deployment
            deploymentUrl = deploymentPage.getDeploymentUrl();
        }
        TestListener.logInfo("Deployment URL: " + deploymentUrl);
        
        // Verify URL is not empty
        Assert.assertFalse(deploymentUrl.isEmpty(), "Deployment URL is empty");
        
        // Verify URL format is valid
        Assert.assertTrue(deploymentUrl.startsWith("http"), 
                "Deployment URL does not start with http: " + deploymentUrl);
    }

    /**
     * Tests navigating back to the project page.
     */
    @Test(priority = 5, description = "Test navigating back to the project page")
    public void testNavigateBackToProjectPage() {
        TestListener.logInfo("Testing navigation back to the project page");
        
        // Navigate to deployment page
        deploymentPage = projectDetailsPage.clickDeploy();
        Assert.assertTrue(deploymentPage.isLoaded(), 
                "Deployment page did not load properly");
        
        // Navigate back to project page
        projectDetailsPage = deploymentPage.clickBackToProject();
        
        // Verify project details page is loaded
        Assert.assertTrue(projectDetailsPage.isLoaded(), 
                "Project details page did not load after navigating back");
        
        // Verify prompt input is available
        WebElement promptElement = TestUtils.waitForElementVisible(webDriver, projectDetailsPage.getPromptInputLocator(), TIMEOUT);
        Assert.assertTrue(promptElement.isDisplayed(), 
                "Prompt input is not displayed on project details page");
    }
}