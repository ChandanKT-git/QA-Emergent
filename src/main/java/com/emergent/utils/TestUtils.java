package com.emergent.utils;

import com.github.javafaker.Faker;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

/**
 * Utility class providing common methods for test automation.
 */
public class TestUtils {
    private static final Faker faker = new Faker();

    /**
     * Waits for an element to be visible.
     *
     * @param driver  the WebDriver instance
     * @param locator the element locator
     * @param timeout the timeout in seconds
     * @return the visible WebElement
     */
    public static WebElement waitForElementVisible(WebDriver driver, By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits for an element to be visible with default timeout.
     *
     * @param driver  the WebDriver instance
     * @param locator the element locator
     * @return the visible WebElement
     */
    public static WebElement waitForElementVisible(WebDriver driver, By locator) {
        return waitForElementClickable(driver, locator, ConfigProperties.getDefaultTimeout());
    }

    /**
     * Waits for an element to be clickable.
     *
     * @param driver  the WebDriver instance
     * @param locator the element locator
     * @param timeout the timeout in seconds
     * @return the clickable WebElement
     */
    public static WebElement waitForElementClickable(WebDriver driver, By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Waits for an element to be clickable with default timeout.
     *
     * @param driver  the WebDriver instance
     * @param locator the element locator
     * @return the clickable WebElement
     */
    public static WebElement waitForElementClickable(WebDriver driver, By locator) {
        return waitForElementClickable(driver, locator, ConfigProperties.getDefaultTimeout());
    }

    /**
     * Waits for a custom condition with specified timeout.
     *
     * @param driver    the WebDriver instance
     * @param condition the expected condition
     * @param timeout   the timeout in seconds
     * @param <T>       the type of the expected condition result
     * @return the result of the condition
     */
    public static <T> T waitFor(WebDriver driver, ExpectedCondition<T> condition, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(condition);
    }

    /**
     * Waits for a custom condition with default timeout.
     *
     * @param driver    the WebDriver instance
     * @param condition the expected condition
     * @param <T>       the type of the expected condition result
     * @return the result of the condition
     */
    public static <T> T waitFor(WebDriver driver, ExpectedCondition<T> condition) {
        return waitFor(driver, condition, ConfigProperties.getDefaultTimeout());
    }

    /**
     * Clicks an element using JavaScript.
     * Useful when regular click doesn't work due to overlays or other issues.
     *
     * @param driver  the WebDriver instance
     * @param element the element to click
     */
    public static void clickUsingJS(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    /**
     * Scrolls to an element using JavaScript.
     *
     * @param driver  the WebDriver instance
     * @param element the element to scroll to
     */
    public static void scrollToElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Scrolls to the bottom of the page using JavaScript.
     *
     * @param driver the WebDriver instance
     */
    public static void scrollToBottom(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    /**
     * Scrolls to the top of the page using JavaScript.
     *
     * @param driver the WebDriver instance
     */
    public static void scrollToTop(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0);");
    }

    /**
     * Hovers over an element using Actions.
     *
     * @param driver  the WebDriver instance
     * @param element the element to hover over
     */
    public static void hoverOverElement(WebDriver driver, WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    /**
     * Captures a screenshot and saves it to the specified directory.
     *
     * @param driver   the WebDriver instance
     * @param testName the name of the test (used in the filename)
     * @return the path to the saved screenshot
     */
    public static String captureScreenshot(WebDriver driver, String testName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = testName + "_" + timestamp + ".png";
            String directory = ConfigProperties.getProperty("screenshot.path", Constants.SCREENSHOT_PATH);

            // Create directory if it doesn't exist
            File screenshotDir = new File(directory);
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            String destination = directory + File.separator + fileName;
            File target = new File(destination);
            FileUtils.copyFile(source, target);
            TestListener.logInfo("Screenshot saved to: " + destination);
            return destination;
        } catch (IOException e) {
            TestListener.logError("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }

    /**
     * Generates a random email address.
     *
     * @return a random email address
     */
    public static String generateRandomEmail() {
        return faker.internet().emailAddress();
    }

    /**
     * Generates a random password.
     *
     * @return a random password
     */
    public static String generateRandomPassword() {
        return faker.internet().password(8, 12, true, true, true);
    }

    /**
     * Generates a random name.
     *
     * @return a random name
     */
    public static String generateRandomName() {
        return faker.name().fullName();
    }

    /**
     * Generates a random project name.
     *
     * @return a random project name
     */
    public static String generateRandomProjectName() {
        return "Project " + faker.app().name() + " " + faker.number().digits(4);
    }

    /**
     * Generates a random project description.
     *
     * @return a random project description
     */
    public static String generateRandomProjectDescription() {
        return faker.lorem().paragraph();
    }

    /**
     * Checks if an element exists on the page.
     *
     * @param driver  the WebDriver instance
     * @param locator the element locator
     * @return true if the element exists, false otherwise
     */
    public static boolean isElementPresent(WebDriver driver, By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Checks if an element is displayed on the page.
     *
     * @param driver  the WebDriver instance
     * @param locator the element locator
     * @return true if the element is displayed, false otherwise
     */
    public static boolean isElementDisplayed(WebDriver driver, By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    /**
     * Waits for a page to load completely.
     *
     * @param driver  the WebDriver instance
     * @param timeout the timeout in seconds
     */
    public static void waitForPageToLoad(WebDriver driver, int timeout) {
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(pageLoadCondition);
    }

    /**
     * Waits for a page to load completely with default timeout.
     *
     * @param driver the WebDriver instance
     */
    public static void waitForPageToLoad(WebDriver driver) {
        waitForPageToLoad(driver, ConfigProperties.getDefaultTimeout());
    }

    /**
     * Refreshes the current page.
     *
     * @param driver the WebDriver instance
     */
    public static void refreshPage(WebDriver driver) {
        driver.navigate().refresh();
        waitForPageToLoad(driver);
    }

    /**
     * Clears a text field and enters new text.
     *
     * @param element the text field element
     * @param text    the text to enter
     */
    public static void clearAndType(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Gets the current URL.
     *
     * @param driver the WebDriver instance
     * @return the current URL
     */
    public static String getCurrentUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    /**
     * Gets the current page title.
     *
     * @param driver the WebDriver instance
     * @return the current page title
     */
    public static String getPageTitle(WebDriver driver) {
        return driver.getTitle();
    }

    public static void waitForElementClickable(WebDriver webDriver, int testResultCount, int defaultTimeout) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'waitForElementClickable'");
    }
}