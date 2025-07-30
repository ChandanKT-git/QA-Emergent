package com.emergent.tests;

import com.emergent.pages.DashboardPage;
import com.emergent.pages.ProjectCreationPage;
import com.emergent.pages.ProjectDetailsPage;
import com.emergent.pages.ProjectSettingsPage;
import com.emergent.utils.ConfigProperties;
import com.emergent.utils.Constants;
import com.emergent.utils.DataProviders;
import com.emergent.utils.TestListener;
import com.emergent.utils.TestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for project settings functionality of the platform.
 */
public class ProjectSettingsTest extends BaseTest {

        private ProjectDetailsPage projectDetailsPage;
        private ProjectSettingsPage projectSettingsPage;
        private String initialProjectName;

        /**
         * Set up method to log in and create a project before each test.
         */
        @BeforeMethod
        public void setUp() {
                TestListener.logInfo("Setting up ProjectSettingsTest");

                // Login with default credentials
                loginWithDefaultCredentials();

                // Create a new project for testing
                DashboardPage dashboardPage = new DashboardPage(webDriver);
                Assert.assertTrue(dashboardPage.isDashboardPageLoaded(), "Dashboard page did not load");

                // Create a new project or open an existing one
                initialProjectName = TestUtils.generateRandomProjectName();
                ProjectCreationPage projectCreationPage = dashboardPage.clickCreateNewProjectButton();
                projectCreationPage.enterProjectName(initialProjectName);
                projectCreationPage.enterProjectDescription(TestUtils.generateRandomProjectDescription());
                projectDetailsPage = projectCreationPage.clickCreateButton();

                Assert.assertTrue(projectDetailsPage.isProjectDetailsPageLoaded(),
                                "Project details page did not load after project creation");
        }

        /**
         * Tests navigating to the project settings page.
         */
        @Test(priority = 1, description = "Test navigating to the project settings page")
        public void testNavigateToProjectSettingsPage() {
                TestListener.logInfo("Testing navigation to the project settings page");

                // Navigate to project settings page
                projectSettingsPage = projectDetailsPage.clickSettings();

                // Verify settings page is loaded
                Assert.assertTrue(projectSettingsPage.isProjectSettingsPageLoaded(),
                                "Project settings page did not load properly");

                // Verify settings page title is displayed
                Assert.assertTrue(projectSettingsPage.isSettingsTitleDisplayed(),
                                "Project settings page title is not displayed");

                // Verify project name field contains the correct value
                String projectName = projectSettingsPage.getProjectName();
                Assert.assertEquals(projectName, initialProjectName,
                                "Project name in settings does not match the initial project name");
        }

        /**
         * Tests updating project name and description.
         * 
         * @param newName        the new project name
         * @param newDescription the new project description
         */
        @Test(priority = 2, dataProvider = "projectSettingsUpdates", dataProviderClass = DataProviders.class, description = "Test updating project name and description")
        public void testUpdateProjectSettings(String newName, String newDescription) {
                TestListener.logInfo("Testing updating project settings with name: " + newName +
                                " and description: " + newDescription);

                // Navigate to project settings page
                projectSettingsPage = projectDetailsPage.clickSettings();
                Assert.assertTrue(projectSettingsPage.isProjectSettingsPageLoaded(),
                                "Project settings page did not load properly");

                // Update project settings
                projectSettingsPage.enterProjectName(newName);
                projectSettingsPage.enterProjectDescription(newDescription);
                projectSettingsPage.clickSaveButton();

                // Wait for success message
                TestUtils.waitForElementVisible(webDriver, projectSettingsPage.getSuccessMessageLocator(),
                                ConfigProperties.getDefaultTimeout());

                // Verify success message is displayed
                String successMessage = projectSettingsPage.getSuccessMessage();
                TestListener.logInfo("Success message: " + successMessage);
                Assert.assertTrue(successMessage.contains("success") || successMessage.contains("updated"),
                                "Success message does not indicate successful update: " + successMessage);

                // Verify project name and description were updated
                Assert.assertEquals(projectSettingsPage.getProjectName(), newName,
                                "Project name was not updated correctly");
                Assert.assertEquals(projectSettingsPage.getProjectDescription(), newDescription,
                                "Project description was not updated correctly");
        }

        /**
         * Tests updating project with an empty name.
         */
        @Test(priority = 3, description = "Test updating project with an empty name")
        public void testUpdateProjectWithEmptyName() {
                TestListener.logInfo("Testing updating project with an empty name");

                // Navigate to project settings page
                projectSettingsPage = projectDetailsPage.clickSettings();
                Assert.assertTrue(projectSettingsPage.isProjectSettingsPageLoaded(),
                                "Project settings page did not load properly");

                // Try to update project with empty name
                projectSettingsPage.enterProjectName("");
                projectSettingsPage.clickSaveButton();

                // Wait for error message
                TestUtils.waitForElementVisible(webDriver, projectSettingsPage.getErrorMessageLocator(),
                                ConfigProperties.getDefaultTimeout());

                // Verify error message is displayed
                String errorMessage = projectSettingsPage.getErrorMessage();
                TestListener.logInfo("Error message: " + errorMessage);
                Assert.assertTrue(errorMessage.contains("name") &&
                                (errorMessage.contains("required") || errorMessage.contains("empty")),
                                "Error message does not indicate name is required: " + errorMessage);

                // Verify project name was not updated
                Assert.assertEquals(projectSettingsPage.getProjectName(), initialProjectName,
                                "Project name should not have been updated");
        }

        /**
         * Tests deleting a project.
         */
        @Test(priority = 4, description = "Test deleting a project")
        public void testDeleteProject() {
                TestListener.logInfo("Testing deleting a project");

                // Navigate to project settings page
                projectSettingsPage = projectDetailsPage.clickSettings();
                Assert.assertTrue(projectSettingsPage.isProjectSettingsPageLoaded(),
                                "Project settings page did not load properly");

                // Click delete button
                projectSettingsPage.clickDeleteButton();

                // Wait for confirmation dialog
                TestUtils.waitForElementVisible(webDriver, projectSettingsPage.getConfirmDeleteButtonLocator(),
                                ConfigProperties.getDefaultTimeout());

                // Confirm deletion
                DashboardPage dashboardPage = projectSettingsPage.clickConfirmDeleteButton();

                // Verify redirected to dashboard
                Assert.assertTrue(dashboardPage.isDashboardPageLoaded(),
                                "Dashboard page did not load after project deletion");

                // Verify project is no longer in the list
                Assert.assertFalse(dashboardPage.isProjectInList(initialProjectName),
                                "Project still appears in dashboard after deletion");
        }

        /**
         * Tests canceling project deletion.
         */
        @Test(priority = 5, description = "Test canceling project deletion")
        public void testCancelProjectDeletion() {
                TestListener.logInfo("Testing canceling project deletion");

                // Navigate to project settings page
                projectSettingsPage = projectDetailsPage.clickSettings();
                Assert.assertTrue(projectSettingsPage.isProjectSettingsPageLoaded(),
                                "Project settings page did not load properly");

                // Click delete button
                projectSettingsPage.clickDeleteButton();

                // Wait for confirmation dialog
                TestUtils.waitForElementVisible(webDriver, projectSettingsPage.getCancelDeleteButtonLocator(),
                                ConfigProperties.getDefaultTimeout());

                // Cancel deletion
                projectSettingsPage.clickCancelDeleteButton();

                // Verify still on settings page
                Assert.assertTrue(projectSettingsPage.isProjectSettingsPageLoaded(),
                                "Project settings page did not remain loaded after canceling deletion");

                // Verify project name is still displayed
                Assert.assertEquals(projectSettingsPage.getProjectName(), initialProjectName,
                                "Project name should still be displayed after canceling deletion");
        }
}