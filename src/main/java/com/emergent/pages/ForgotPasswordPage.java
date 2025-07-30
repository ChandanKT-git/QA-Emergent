package com.emergent.pages;

import com.emergent.utils.TestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Page object for the Emergent forgot password page
 */
public class ForgotPasswordPage {
    private final WebDriver driver;
    private final Logger logger = LogManager.getLogger(ForgotPasswordPage.class);
    private static final Duration TIMEOUT_DURATION = Duration.ofSeconds(10);
    private static final int TIMEOUT = 10;

    // Locators
    private final By emailField = By.id("email");
    private final By resetPasswordButton = By.xpath("//button[contains(text(), 'Reset Password')]");
    private final By successMessage = By.xpath("//div[contains(@class, 'success-message')]");
    private final By errorMessage = By.xpath("//div[contains(@class, 'error-message')]");
    private final By backToLoginLink = By.xpath("//a[contains(text(), 'Back to Login')]");
    private final By forgotPasswordTitle = By.xpath("//h1[contains(text(), 'Forgot Password')]");

    /**
     * Constructor
     *
     * @param driver WebDriver instance
     */
    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Navigates to the forgot password page
     *
     * @return ForgotPasswordPage instance
     */
    public ForgotPasswordPage navigateTo() {
        logger.info("Navigating to Emergent forgot password page");
        driver.get("https://emergent.sh/forgot-password");
        return this;
    }

    /**
     * Enters email in the email field
     *
     * @param email email to enter
     * @return ForgotPasswordPage instance
     */
    public ForgotPasswordPage enterEmail(String email) {
        logger.info("Entering email: {}", email);
        WebElement element = TestUtils.waitForElementClickable(driver, emailField, TIMEOUT);
        element.clear();
        element.sendKeys(email);
        return this;
    }

    /**
     * Clicks on the reset password button
     *
     * @return ForgotPasswordPage instance
     */
    public ForgotPasswordPage clickResetPassword() {
        logger.info("Clicking on reset password button");
        WebElement element = TestUtils.waitForElementClickable(driver, resetPasswordButton, TIMEOUT);
        element.click();
        return this;
    }

    /**
     * Performs reset password with the given email
     *
     * @param email email to use
     * @return ForgotPasswordPage instance
     */
    public ForgotPasswordPage resetPassword(String email) {
        logger.info("Resetting password for email: {}", email);
        enterEmail(email);
        return clickResetPassword();
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
     * Clicks on the back to login link
     *
     * @return LoginPage instance
     */
    public LoginPage clickBackToLogin() {
        logger.info("Clicking on back to login link");
        WebElement element = TestUtils.waitForElementClickable(driver, backToLoginLink, TIMEOUT);
        element.click();
        return new LoginPage(driver);
    }

    /**
     * Verifies that the forgot password page is loaded
     *
     * @return true if the forgot password page is loaded, false otherwise
     */
    public boolean isLoaded() {
        logger.info("Verifying that the forgot password page is loaded");
        try {
            return TestUtils.waitForElementClickable(driver, forgotPasswordTitle, TIMEOUT).isDisplayed() &&
                    TestUtils.waitForElementClickable(driver, emailField, TIMEOUT).isDisplayed() &&
                    TestUtils.waitForElementClickable(driver, resetPasswordButton, TIMEOUT).isDisplayed();
        } catch (Exception e) {
            logger.error("Forgot password page is not loaded: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Alias for isLoaded() to maintain consistency with other page objects
     *
     * @return true if the forgot password page is loaded, false otherwise
     */
    public boolean isForgotPasswordPageLoaded() {
        logger.info("Checking if forgot password page is loaded");
        return isLoaded();
    }

    public boolean isSuccessMessageDisplayed() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isSuccessMessageDisplayed'");
    }
}