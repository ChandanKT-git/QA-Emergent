package com.emergent.pages;

import com.emergent.utils.TestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Page object for the Emergent sign up page
 */
public class SignUpPage {
    private final WebDriver driver;
    private final Logger logger = LogManager.getLogger(SignUpPage.class);
    private static final Duration TIMEOUT_DURATION = Duration.ofSeconds(10);
    private static final int TIMEOUT = 10;

    // Locators
    private final By nameField = By.id("name");
    private final By emailField = By.id("email");
    private final By passwordField = By.id("password");
    private final By confirmPasswordField = By.id("confirmPassword");
    private final By signUpButton = By.xpath("//button[contains(text(), 'Sign up')]");
    private final By errorMessage = By.xpath("//div[contains(@class, 'error-message')]");
    private final By loginLink = By.xpath("//a[contains(text(), 'Log in')]");
    private final By termsCheckbox = By.xpath("//input[@type='checkbox']");

    /**
     * Constructor
     *
     * @param driver WebDriver instance
     */
    public SignUpPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Navigates to the sign up page
     *
     * @return SignUpPage instance
     */
    public SignUpPage navigateTo() {
        logger.info("Navigating to Emergent sign up page");
        driver.get("https://emergent.sh/signup");
        return this;
    }

    /**
     * Enters name in the name field
     *
     * @param name name to enter
     * @return SignUpPage instance
     */
    public SignUpPage enterName(String name) {
        logger.info("Entering name: {}", name);
        WebElement element = TestUtils.waitForElementClickable(driver, nameField, TIMEOUT);
        element.clear();
        element.sendKeys(name);
        return this;
    }

    /**
     * Enters email in the email field
     *
     * @param email email to enter
     * @return SignUpPage instance
     */
    public SignUpPage enterEmail(String email) {
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
     * @return SignUpPage instance
     */
    public SignUpPage enterPassword(String password) {
        logger.info("Entering password");
        WebElement element = TestUtils.waitForElementClickable(driver, passwordField, TIMEOUT);
        element.clear();
        element.sendKeys(password);
        return this;
    }

    /**
     * Enters confirm password in the confirm password field
     *
     * @param confirmPassword confirm password to enter
     * @return SignUpPage instance
     */
    public SignUpPage enterConfirmPassword(String confirmPassword) {
        logger.info("Entering confirm password");
        WebElement element = TestUtils.waitForElementClickable(driver, confirmPasswordField, TIMEOUT);
        element.clear();
        element.sendKeys(confirmPassword);
        return this;
    }

    /**
     * Checks the terms and conditions checkbox
     *
     * @return SignUpPage instance
     */
    public SignUpPage checkTerms() {
        logger.info("Checking terms and conditions checkbox");
        WebElement element = TestUtils.waitForElementClickable(driver, termsCheckbox, TIMEOUT);
        if (!element.isSelected()) {
            element.click();
        }
        return this;
    }

    /**
     * Clicks on the sign up button
     *
     * @return DashboardPage instance
     */
    public DashboardPage clickSignUp() {
        logger.info("Clicking on sign up button");
        WebElement element = TestUtils.waitForElementClickable(driver, signUpButton, TIMEOUT);
        element.click();
        return new DashboardPage(driver);
    }

    /**
     * Performs sign up with the given credentials
     *
     * @param name            name to use
     * @param email           email to use
     * @param password        password to use
     * @param confirmPassword confirm password to use
     * @return DashboardPage instance
     */
    public DashboardPage signUp(String name, String email, String password, String confirmPassword) {
        logger.info("Signing up with email: {}", email);
        enterName(name);
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(confirmPassword);
        checkTerms();
        return clickSignUp();
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
     * Clicks on the login link
     *
     * @return LoginPage instance
     */
    public LoginPage clickLogin() {
        logger.info("Clicking on login link");
        WebElement element = TestUtils.waitForElementClickable(driver, loginLink, TIMEOUT);
        element.click();
        return new LoginPage(driver);
    }

    /**
     * Verifies that the sign up page is loaded
     *
     * @return true if the sign up page is loaded, false otherwise
     */
    public boolean isLoaded() {
        logger.info("Verifying that the sign up page is loaded");
        try {
            return TestUtils.waitForElementClickable(driver, nameField, TIMEOUT).isDisplayed() &&
                    TestUtils.waitForElementClickable(driver, emailField, TIMEOUT).isDisplayed() &&
                    TestUtils.waitForElementClickable(driver, passwordField, TIMEOUT).isDisplayed() &&
                    TestUtils.waitForElementClickable(driver, confirmPasswordField, TIMEOUT).isDisplayed() &&
                    TestUtils.waitForElementClickable(driver, signUpButton, TIMEOUT).isDisplayed();
        } catch (Exception e) {
            logger.error("Sign up page is not loaded: {}", e.getMessage());
            return false;
        }
    }

    public void checkTermsAndConditions() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkTermsAndConditions'");
    }

    public void clickSignUpButtonExpectingError() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clickSignUpButtonExpectingError'");
    }

    public boolean isErrorMessageDisplayed() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isErrorMessageDisplayed'");
    }

    /**
     * Alias for isLoaded() to maintain consistency with other page objects
     *
     * @return true if the sign up page is loaded, false otherwise
     */
    public boolean isSignUpPageLoaded() {
        logger.info("Checking if sign up page is loaded");
        return isLoaded();
    }
}