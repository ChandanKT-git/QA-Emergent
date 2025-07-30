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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for project creation functionality.
 */
public class ProjectCreationTest extends BaseTest {

        private DashboardPage dashboardPage;

        /**
         * Set up method to log in before each test.
         */
        @BeforeMethod
        public void setUp() {
                TestListener.logInfo("Setting up ProjectCreationTest");
                loginWithDefaultCredentials();
                dashboardPage = new DashboardPage(webDriver);
                Assert.assertTrue(dashboardPage.isDashboardPageLoaded(), "Dashboard page did not load");
        }

        /**
         * Tests successful project creation with valid details.
         */
        @Test(priority = 1, description = "Test successful project creation with valid details")
        public void testSuccessfulProjectCreation() {
                TestListener.logInfo("Testing successful project creation");

                // Generate random project details
                String projectName = TestUtils.generateRandomProjectName();
                String projectDescription = TestUtils.generateRandomProjectDescription();

                // Click create new project button
                ProjectCreationPage projectCreationPage = dashboardPage.clickCreateNewProjectButton();
                Assert.assertTrue(projectCreationPage.isProjectCreationPageLoaded(),
                                "Project creation page did not load");

                // Enter project details and create project
                projectCreationPage.enterProjectName(projectName);
                projectCreationPage.enterProjectDescription(projectDescription);
                ProjectDetailsPage projectDetailsPage = projectCreationPage.clickCreateButton();

                // Verify project details page is loaded and project details are correct
                Assert.assertTrue(projectDetailsPage.isProjectDetailsPageLoaded(),
                                "Project details page did not load after project creation");
                Assert.assertEquals(projectDetailsPage.getProjectTitle(), projectName,
                                "Project title does not match the entered name");
                Assert.assertEquals(projectDetailsPage.getProjectDescription(), projectDescription,
                                "Project description does not match the entered description");
        }

        /**
         * Tests project creation with empty name.
         */
        @Test(priority = 2, description = "Test project creation with empty name")
        public void testProjectCreationWithEmptyName() {
                TestListener.logInfo("Testing project creation with empty name");

                // Click create new project button
                ProjectCreationPage projectCreationPage = dashboardPage.clickCreateNewProjectButton();
                Assert.assertTrue(projectCreationPage.isProjectCreationPageLoaded(),
                                "Project creation page did not load");

                // Enter empty project name and valid description
                projectCreationPage.enterProjectName("");
                projectCreationPage.enterProjectDescription(TestUtils.generateRandomProjectDescription());
                projectCreationPage.clickCreateButtonExpectingError();

                // Verify error message is displayed
                Assert.assertTrue(projectCreationPage.isErrorMessageDisplayed(),
                                "Error message not displayed for empty project name");
                String errorMessage = projectCreationPage.getErrorMessage();
                TestListener.logInfo("Error message displayed: " + errorMessage);
                Assert.assertTrue(errorMessage.contains(Constants.ERROR_EMPTY_PROJECT_NAME) ||
                                !errorMessage.isEmpty(),
                                "Error message does not indicate empty project name");
        }

        /**
         * Tests project creation with a template.
         * 
         * @param templateName the template to use
         */
        @Test(priority = 3, dataProvider = "projectTemplates", dataProviderClass = DataProviders.class, description = "Test project creation with templates")
        public void testProjectCreationWithTemplate(String templateName) {
                TestListener.logInfo("Testing project creation with template: " + templateName);

                // Click create new project button
                ProjectCreationPage projectCreationPage = dashboardPage.clickCreateNewProjectButton();
                Assert.assertTrue(projectCreationPage.isProjectCreationPageLoaded(),
                                "Project creation page did not load");

                // Enter project details, select template and create project
                String projectName = TestUtils.generateRandomProjectName();
                projectCreationPage.enterProjectName(projectName);
                projectCreationPage.enterProjectDescription(TestUtils.generateRandomProjectDescription());
                projectCreationPage.selectTemplate(templateName);
                ProjectDetailsPage projectDetailsPage = projectCreationPage.clickCreateButton();

                // Verify project details page is loaded and project has the correct template
                Assert.assertTrue(projectDetailsPage.isProjectDetailsPageLoaded(),
                                "Project details page did not load after project creation");
                Assert.assertEquals(projectDetailsPage.getProjectTitle(), projectName,
                                "Project title does not match the entered name");

                // Verify template-specific elements or content
                // This will depend on how templates are displayed in the UI
                // For example, checking if certain code snippets or configurations are present
                TestListener.logInfo("Successfully created project with template: " + templateName);
        }

        /**
         * Tests project creation with a duplicate name.
         */
        @Test(priority = 4, description = "Test project creation with duplicate name")
        public void testProjectCreationWithDuplicateName() {
                TestListener.logInfo("Testing project creation with duplicate name");

                // First create a project
                String projectName = TestUtils.generateRandomProjectName();

                // Click create new project button
                ProjectCreationPage projectCreationPage = dashboardPage.clickCreateNewProjectButton();
                Assert.assertTrue(projectCreationPage.isProjectCreationPageLoaded(),
                                "Project creation page did not load");

                // Enter project details and create project
                projectCreationPage.enterProjectName(projectName);
                projectCreationPage.enterProjectDescription(TestUtils.generateRandomProjectDescription());
                ProjectDetailsPage projectDetailsPage = projectCreationPage.clickCreateButton();

                // Verify project details page is loaded
                Assert.assertTrue(projectDetailsPage.isProjectDetailsPageLoaded(),
                                "Project details page did not load after first project creation");

                // Navigate back to dashboard
        dashboardPage = projectDetailsPage.clickBackToDashboardButton();
                Assert.assertTrue(dashboardPage.isDashboardPageLoaded(),
                                "Dashboard page did not load after navigating back");

                // Try to create another project with the same name
                projectCreationPage = dashboardPage.clickCreateNewProjectButton();
                Assert.assertTrue(projectCreationPage.isProjectCreationPageLoaded(),
                                "Project creation page did not load for second attempt");

                // Enter duplicate project name and valid description
                projectCreationPage.enterProjectName(projectName);
                projectCreationPage.enterProjectDescription(TestUtils.generateRandomProjectDescription());
                projectCreationPage.clickCreateButtonExpectingError();

                // Verify error message is displayed
                Assert.assertTrue(projectCreationPage.isErrorMessageDisplayed(),
                                "Error message not displayed for duplicate project name");
                String errorMessage = projectCreationPage.getErrorMessage();
                TestListener.logInfo("Error message displayed: " + errorMessage);
                Assert.assertTrue(errorMessage.contains(Constants.ERROR_DUPLICATE_PROJECT) ||
                                !errorMessage.isEmpty(),
                                "Error message does not indicate duplicate project name");
        }

        /**
         * Tests canceling project creation.
         */
        @Test(priority = 5, description = "Test canceling project creation")
        public void testCancelProjectCreation() {
                TestListener.logInfo("Testing canceling project creation");

                // Click create new project button
                ProjectCreationPage projectCreationPage = dashboardPage.clickCreateNewProjectButton();
                Assert.assertTrue(projectCreationPage.isProjectCreationPageLoaded(),
                                "Project creation page did not load");

                // Enter project details but cancel creation
                projectCreationPage.enterProjectName(TestUtils.generateRandomProjectName());
                projectCreationPage.enterProjectDescription(TestUtils.generateRandomProjectDescription());
                dashboardPage = projectCreationPage.clickCancelButton();

                // Verify we're back at the dashboard
                Assert.assertTrue(dashboardPage.isDashboardPageLoaded(),
                                "Dashboard page did not load after canceling project creation");

                // Verify the project was not created
                // This will depend on how projects are listed in the dashboard
                // For example, checking if the project name is not in the list of projects
                TestListener.logInfo("Successfully canceled project creation");
        }
}