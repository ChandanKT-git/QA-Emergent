package com.emergent.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

/**
 * Factory class to create WebDriver instances for different browsers.
 */
public class WebDriverFactory {

    /**
     * Creates a WebDriver instance for the specified browser.
     *
     * @param browserType the browser type (chrome, firefox, edge, safari)
     * @param headless    whether to run in headless mode
     * @return a WebDriver instance
     */
    public static WebDriver createDriver(String browserType, boolean headless) {
        WebDriver driver;

        switch (browserType.toLowerCase()) {
            case Constants.BROWSER_CHROME:
                driver = createChromeDriver(headless);
                break;
            case Constants.BROWSER_FIREFOX:
                driver = createFirefoxDriver(headless);
                break;
            case Constants.BROWSER_EDGE:
                driver = createEdgeDriver(headless);
                break;
            case Constants.BROWSER_SAFARI:
                driver = createSafariDriver();
                break;
            default:
                System.out.println("Browser type not supported: " + browserType + ". Defaulting to Chrome.");
                driver = createChromeDriver(headless);
        }

        return driver;
    }

    /**
     * Creates a ChromeDriver instance.
     *
     * @param headless whether to run in headless mode
     * @return a ChromeDriver instance
     */
    private static WebDriver createChromeDriver(boolean headless) {
        // Use WebDriverManager to automatically download and setup the appropriate ChromeDriver version
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        
        // Set Chrome binary location to a specific version if needed
        // options.setBinary("path/to/chrome/binary");
        
        // Add version-specific capability
        options.addArguments("--ignore-certificate-errors");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        
        if (headless) {
            options.addArguments("--headless");
        }
        
        // Add common Chrome options
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--remote-allow-origins=*");
        
        return new ChromeDriver(options);
    }

    /**
     * Creates a FirefoxDriver instance.
     *
     * @param headless whether to run in headless mode
     * @return a FirefoxDriver instance
     */
    private static WebDriver createFirefoxDriver(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        
        if (headless) {
            options.addArguments("-headless");
        }
        
        // Add common Firefox options
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        
        return new FirefoxDriver(options);
    }

    /**
     * Creates an EdgeDriver instance.
     *
     * @param headless whether to run in headless mode
     * @return an EdgeDriver instance
     */
    private static WebDriver createEdgeDriver(boolean headless) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        
        if (headless) {
            options.addArguments("--headless");
        }
        
        // Add common Edge options
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        
        return new EdgeDriver(options);
    }

    /**
     * Creates a SafariDriver instance.
     * Note: Safari does not support headless mode.
     *
     * @return a SafariDriver instance
     */
    private static WebDriver createSafariDriver() {
        // Safari doesn't support WebDriverManager, so we don't call setup()
        SafariOptions options = new SafariOptions();
        // Safari doesn't support headless mode
        System.out.println("Note: Safari does not support headless mode.");
        
        return new SafariDriver(options);
    }
}