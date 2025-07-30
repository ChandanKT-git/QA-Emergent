package com.emergent.tests;

import com.emergent.pages.DashboardPage;
import com.emergent.pages.HomePage;
import com.emergent.pages.LoginPage;
import com.emergent.utils.ConfigProperties;
import com.emergent.utils.Constants;
import com.emergent.utils.TestListener;
import com.emergent.utils.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.time.Duration;

/**
 * Base class for all test classes.
 * Handles common setup and teardown operations.
 */
@Listeners(TestListener.class)
public class BaseTest {
    protected WebDriver webDriver;
    protected String baseUrl;
    protected HomePage homePage;
    protected LoginPage loginPage;
    protected DashboardPage dashboardPage;

    /**
     * Setup method to initialize WebDriver and common pages before each test class.
     */
    @BeforeClass
    public void setUp() {
        // Initialize WebDriver based on configuration
        String browser = ConfigProperties.getBrowser();
        boolean headless = ConfigProperties.isHeadless();
        webDriver = WebDriverFactory.createDriver(browser, headless);

        // Configure timeouts
        int defaultTimeout = ConfigProperties.getDefaultTimeout();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(
                Integer.parseInt(ConfigProperties.getProperty("implicit.wait", "10"))));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(
                Integer.parseInt(ConfigProperties.getProperty("page.load.timeout", "60"))));
        webDriver.manage().timeouts().scriptTimeout(Duration.ofSeconds(
                Integer.parseInt(ConfigProperties.getProperty("script.timeout", "30"))));

        // Maximize window
        webDriver.manage().window().maximize();

        // Set base URL from configuration
        baseUrl = ConfigProperties.getBaseUrl();

        // Initialize common pages
        homePage = new HomePage(webDriver);
        loginPage = new LoginPage(webDriver);
        dashboardPage = new DashboardPage(webDriver);

        TestListener.logInfo("Test setup completed with browser: " + browser);
    }

    /**
     * Cleanup method to quit WebDriver after each test class.
     */
    @AfterClass
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
            TestListener.logInfo("WebDriver quit successfully");
        }
    }

    /**
     * Method to navigate to the base URL before each test method.
     */
    @BeforeMethod
    public void navigateToBaseUrl() {
        webDriver.get(baseUrl);
        TestListener.logInfo("Navigated to base URL: " + baseUrl);
    }

    /**
     * Method to perform common cleanup after each test method.
     */
    @AfterMethod
    public void methodCleanup() {
        // Clear cookies or perform other cleanup if needed
        // webDriver.manage().deleteAllCookies();
    }

    /**
     * Helper method to log in with the default test credentials.
     * 
     * @return the DashboardPage after successful login
     */
    protected DashboardPage loginWithDefaultCredentials() {
        homePage.clickLogin();
        loginPage.enterEmail(ConfigProperties.getTestUsername());
        loginPage.enterPassword(ConfigProperties.getTestPassword());
        return loginPage.clickLogin();
    }

    /**
     * Helper method to log in with custom credentials.
     * 
     * @param email    the email to use
     * @param password the password to use
     * @return the DashboardPage after successful login
     */
    protected DashboardPage loginWithCredentials(String email, String password) {
        homePage.clickLogin();
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        return loginPage.clickLogin();
    }
}