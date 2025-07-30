package com.emergent.pages;

import com.emergent.utils.TestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Page object for the Emergent login page
 */
public class LoginPage {
    private final WebDriver driver;
    private final Logger logger = LogManager.getLogger(LoginPage.class);
    private static final Duration TIMEOUT_DURATION = Duration.ofSeconds(10);
    private static final int TIMEOUT = 10;

    // Locators
    private final By emailField = By.id("email");
    private final By passwordField = By.id("password");
    private final By loginButton = By.xpath("//button[contains(text(), 'Log in')]");
    private final By errorMessage = By.xpath("//div[contains(@class, 'error-message')]");
    private final By signUpLink = By.xpath("//a[contains(text(), 'Sign up')]");
    private final By forgotPasswordLink = By.xpath("//a[contains(text(), 'Forgot Password')]");

    /**
     * Constructor
     *
     * @param driver WebDriver instance
     */
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Navigates to the login page
     *
     * @return LoginPage instance
     */
    public LoginPage navigateTo() {
        logger.info("Navigating to Emergent login page");
        driver.get("https://emergent.sh/login");
        return this;
    }

    /**
     * Enters email in the email field
     *
     * @param email email to enter
     * @return LoginPage instance
     */
    public LoginPage enterEmail(String email) {
        logger.info("Entering email: {}", email);
        WebElement element = TestUtils.waitForElementClickable(driver, emailField, TIMEOUT);
        element.clear();
        element.sendKeys(email);
        return this;
    }

    /**
     * Enters password in the password field
     *
     * @param password password to enter
     * @return LoginPage instance
     */
    public LoginPage enterPassword(String password) {
        logger.info("Entering password");
        WebElement element = TestUtils.waitForElementClickable(driver, passwordField, TIMEOUT);
        element.clear();
        element.sendKeys(password);
        return this;
    }

    /**
     * Clicks on the login button
     *
     * @return DashboardPage instance
     */
    public DashboardPage clickLogin() {
        logger.info("Clicking on login button");
        WebElement element = TestUtils.waitForElementClickable(driver, loginButton, TIMEOUT);
        element.click();
        return new DashboardPage(driver);
    }

    /**
     * Performs login with the given credentials
     *
     * @param email    email to use
     * @param password password to use
     * @return DashboardPage instance
     */
    public DashboardPage login(String email, String password) {
        logger.info("Logging in with email: {}", email);
        enterEmail(email);
        enterPassword(password);
        return clickLogin();
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
     * Clicks on the sign up link
     *
     * @return SignUpPage instance
     */
    public SignUpPage clickSignUp() {
        logger.info("Clicking on sign up link");
        WebElement element = TestUtils.waitForElementClickable(driver, signUpLink, TIMEOUT);
        element.click();
        return new SignUpPage(driver);
    }

    /**
     * Clicks on the forgot password link
     *
     * @return ForgotPasswordPage instance
     */
    public ForgotPasswordPage clickForgotPassword() {
        logger.info("Clicking on forgot password link");
        WebElement element = TestUtils.waitForElementClickable(driver, forgotPasswordLink, TIMEOUT);
        element.click();
        return new ForgotPasswordPage(driver);
    }

    /**
     * Verifies that the login page is loaded
     *
     * @return true if the login page is loaded, false otherwise
     */
    public boolean isLoaded() {
        logger.info("Verifying that the login page is loaded");
        try {
            return TestUtils.waitForElementClickable(driver, emailField, TIMEOUT).isDisplayed() &&
                    TestUtils.waitForElementClickable(driver, passwordField, TIMEOUT).isDisplayed() &&
                    TestUtils.waitForElementClickable(driver, loginButton, TIMEOUT).isDisplayed();
        } catch (Exception e) {
            logger.error("Login page is not loaded: {}", e.getMessage());
            return false;
        }
    }

    public void clickLoginButtonExpectingError() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clickLoginButtonExpectingError'");
    }

    public boolean isErrorMessageDisplayed() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isErrorMessageDisplayed'");
    }

    /**
     * Alias for isLoaded() to maintain consistency with other page objects
     *
     * @return true if the login page is loaded, false otherwise
     */
    public boolean isLoginPageLoaded() {
        logger.info("Checking if login page is loaded");
        return isLoaded();
    }
}