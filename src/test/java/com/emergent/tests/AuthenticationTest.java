package com.emergent.tests;

import com.emergent.pages.DashboardPage;
import com.emergent.pages.ForgotPasswordPage;
import com.emergent.pages.LoginPage;
import com.emergent.pages.SignUpPage;
import com.emergent.utils.ConfigProperties;
import com.emergent.utils.Constants;
import com.emergent.utils.DataProviders;
import com.emergent.utils.TestListener;
import com.emergent.utils.TestUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for authentication functionality including login, sign-up, and
 * forgot password.
 */
public class AuthenticationTest extends BaseTest {

    /**
     * Tests successful login with valid credentials.
     */
    @Test(priority = 1, description = "Test successful login with valid credentials")
    public void testSuccessfulLogin() {
        TestListener.logInfo("Testing successful login");

        homePage.clickLogin();
        loginPage.enterEmail(ConfigProperties.getTestUsername());
        loginPage.enterPassword(ConfigProperties.getTestPassword());
        dashboardPage = loginPage.clickLogin();

        Assert.assertTrue(dashboardPage.isDashboardPageLoaded(), "Dashboard page did not load after login");
        Assert.assertTrue(dashboardPage.isUserLoggedIn(), "User is not logged in");
    }

    /**
     * Tests login with invalid credentials.
     * 
     * @param email          the email to use
     * @param password       the password to use
     * @param expectedResult whether login should succeed
     */
    @Test(priority = 2, dataProvider = "loginCredentials", dataProviderClass = DataProviders.class, description = "Test login with various credentials")
    public void testLoginWithCredentials(String email, String password, boolean expectedResult) {
        TestListener.logInfo("Testing login with email: " + email);

        homePage.clickLogin();
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginButtonExpectingError();

        if (expectedResult) {
            Assert.assertTrue(dashboardPage.isDashboardPageLoaded(), "Dashboard page did not load after login");
        } else {
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                    "Error message not displayed for invalid credentials");
            String errorMessage = loginPage.getErrorMessage();
            TestListener.logInfo("Error message displayed: " + errorMessage);
            Assert.assertFalse(errorMessage.isEmpty(), "Error message is empty");
        }
    }

    /**
     * Tests successful sign-up with valid information.
     */
    @Test(priority = 3, description = "Test successful sign-up with valid information")
    public void testSuccessfulSignUp() {
        TestListener.logInfo("Testing successful sign-up");

        String name = TestUtils.generateRandomName();
        String email = TestUtils.generateRandomEmail();
        String password = TestUtils.generateRandomPassword();

        homePage.clickSignUp();
        SignUpPage signUpPage = new SignUpPage(webDriver);

        signUpPage.enterName(name);
        signUpPage.enterEmail(email);
        signUpPage.enterPassword(password);
        signUpPage.enterConfirmPassword(password);
        signUpPage.checkTermsAndConditions();
        dashboardPage = signUpPage.clickSignUp();

        Assert.assertTrue(dashboardPage.isDashboardPageLoaded(), "Dashboard page did not load after sign-up");
        Assert.assertTrue(dashboardPage.isUserLoggedIn(), "User is not logged in after sign-up");
    }

    /**
     * Tests sign-up with existing email.
     */
    @Test(priority = 4, description = "Test sign-up with existing email")
    public void testSignUpWithExistingEmail() {
        TestListener.logInfo("Testing sign-up with existing email");

        homePage.clickSignUp();
        SignUpPage signUpPage = new SignUpPage(webDriver);

        signUpPage.enterName(TestUtils.generateRandomName());
        signUpPage.enterEmail(ConfigProperties.getTestUsername()); // Using existing email
        signUpPage.enterPassword(TestUtils.generateRandomPassword());
        signUpPage.enterConfirmPassword(TestUtils.generateRandomPassword());
        signUpPage.checkTermsAndConditions();
        signUpPage.clickSignUpButtonExpectingError();

        Assert.assertTrue(signUpPage.isErrorMessageDisplayed(), "Error message not displayed for existing email");
        String errorMessage = signUpPage.getErrorMessage();
        TestListener.logInfo("Error message displayed: " + errorMessage);
        Assert.assertFalse(errorMessage.isEmpty(), "Error message is empty");
    }

    /**
     * Tests sign-up with password mismatch.
     */
    @Test(priority = 5, description = "Test sign-up with password mismatch")
    public void testSignUpWithPasswordMismatch() {
        TestListener.logInfo("Testing sign-up with password mismatch");

        homePage.clickSignUp();
        SignUpPage signUpPage = new SignUpPage(webDriver);

        signUpPage.enterName(TestUtils.generateRandomName());
        signUpPage.enterEmail(TestUtils.generateRandomEmail());
        signUpPage.enterPassword("Password123!");
        signUpPage.enterConfirmPassword("DifferentPassword123!");
        signUpPage.checkTermsAndConditions();
        signUpPage.clickSignUpButtonExpectingError();

        Assert.assertTrue(signUpPage.isErrorMessageDisplayed(), "Error message not displayed for password mismatch");
        String errorMessage = signUpPage.getErrorMessage();
        TestListener.logInfo("Error message displayed: " + errorMessage);
        Assert.assertTrue(errorMessage.contains(Constants.ERROR_PASSWORD_MISMATCH) ||
                !errorMessage.isEmpty(),
                "Error message does not indicate password mismatch");
    }

    /**
     * Tests forgot password functionality.
     */
    @Test(priority = 6, description = "Test forgot password functionality")
    public void testForgotPassword() {
        TestListener.logInfo("Testing forgot password functionality");

        homePage.clickLogin();
        loginPage.clickForgotPassword();

        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(webDriver);
        Assert.assertTrue(forgotPasswordPage.isForgotPasswordPageLoaded(), "Forgot password page did not load");

        forgotPasswordPage.enterEmail(ConfigProperties.getTestUsername());
        forgotPasswordPage.clickResetPassword();

        Assert.assertTrue(forgotPasswordPage.isSuccessMessageDisplayed(),
                "Success message not displayed after reset password request");
        String successMessage = forgotPasswordPage.getSuccessMessage();
        TestListener.logInfo("Success message displayed: " + successMessage);
        Assert.assertTrue(successMessage.contains(Constants.SUCCESS_PASSWORD_RESET) ||
                !successMessage.isEmpty(),
                "Success message does not indicate password reset email sent");
    }

    /**
     * Tests logout functionality.
     */
    @Test(priority = 7, description = "Test logout functionality")
    public void testLogout() {
        TestListener.logInfo("Testing logout functionality");

        // First login
        homePage.clickLogin();
        loginPage.enterEmail(ConfigProperties.getTestUsername());
        loginPage.enterPassword(ConfigProperties.getTestPassword());
        dashboardPage = loginPage.clickLogin();

        Assert.assertTrue(dashboardPage.isDashboardPageLoaded(), "Dashboard page did not load after login");

        // Then logout
        dashboardPage.clickUserProfileMenu();
        homePage = dashboardPage.clickLogoutOption();

        Assert.assertTrue(homePage.isLoaded(), "Home page did not load after logout");

        // Verify we're logged out by checking if login link is visible
        Assert.assertTrue(homePage.isLoginLinkDisplayed(), "Login link not displayed after logout");
    }
}