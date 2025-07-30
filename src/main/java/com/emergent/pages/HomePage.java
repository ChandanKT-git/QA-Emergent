package com.emergent.pages;

import com.emergent.utils.TestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Page object for the Emergent home page
 */
public class HomePage {
    private final WebDriver driver;
    private final Logger logger = LogManager.getLogger(HomePage.class);
    private static final Duration TIMEOUT_DURATION = Duration.ofSeconds(10);
    private static final int TIMEOUT = 10;

    // Locators
    private final By signUpLink = By.xpath("//a[contains(text(), 'Don\'t have an account')]");
    private final By loginLink = By.xpath("//a[contains(text(), 'Log in with email')]");
    private final By forgotPasswordLink = By.xpath("//a[contains(text(), 'Forgot Password')]");

    /**
     * Constructor
     *
     * @param driver WebDriver instance
     */
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Navigates to the home page
     *
     * @return HomePage instance
     */
    public HomePage navigateTo() {
        logger.info("Navigating to Emergent home page");
        driver.get("https://emergent.sh/");
        return this;
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
     * Verifies that the home page is loaded
     *
     * @return true if the home page is loaded, false otherwise
     */
    public boolean isLoaded() {
        logger.info("Verifying that the home page is loaded");
        try {
            return TestUtils.waitForElementClickable(driver, signUpLink, TIMEOUT).isDisplayed() &&
                    TestUtils.waitForElementClickable(driver, loginLink, TIMEOUT).isDisplayed();
        } catch (Exception e) {
            logger.error("Home page is not loaded: {}", e.getMessage());
            return false;
        }
    }

    public boolean isLoginLinkDisplayed() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isLoginLinkDisplayed'");
    }

    /**
     * Alias for isLoaded() to maintain consistency with other page objects
     *
     * @return true if the home page is loaded, false otherwise
     */
    public boolean isHomePageLoaded() {
        logger.info("Checking if home page is loaded");
        return isLoaded();
    }
}