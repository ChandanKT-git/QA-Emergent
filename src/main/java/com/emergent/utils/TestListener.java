package com.emergent.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Custom TestNG listener to enhance test reporting and handle test events.
 */
public class TestListener implements ITestListener {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    
    /**
     * Initialize the ExtentReports instance
     */
    private static synchronized ExtentReports getExtentReports() {
        if (extent == null) {
            String reportPath = Constants.REPORT_PATH + "/ExtentReport_" + 
                    new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".html";
            
            // Create the reports directory if it doesn't exist
            File reportsDir = new File(Constants.REPORT_PATH);
            if (!reportsDir.exists()) {
                reportsDir.mkdirs();
            }
            
            ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
            reporter.config().setReportName("Emergent.sh Test Automation Report");
            reporter.config().setDocumentTitle("Emergent.sh Test Results");
            
            extent = new ExtentReports();
            extent.attachReporter(reporter);
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Browser", ConfigProperties.getBrowser());
            extent.setSystemInfo("Environment", ConfigProperties.getBaseUrl());
            extent.setSystemInfo("User", System.getProperty("user.name"));
        }
        return extent;
    }
    
    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test Suite started: " + context.getName());
    }
    
    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test Suite finished: " + context.getName());
        getExtentReports().flush();
    }
    
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test started: " + result.getName());
        ExtentTest extentTest = getExtentReports().createTest(result.getMethod().getMethodName(),
                result.getMethod().getDescription());
        test.set(extentTest);
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test passed: " + result.getName());
        test.get().log(Status.PASS, "Test passed");
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test failed: " + result.getName());
        test.get().log(Status.FAIL, result.getThrowable());
        
        // Take screenshot on failure if configured
        if (Boolean.parseBoolean(ConfigProperties.getProperty("take.screenshot.on.failure", "true"))) {
            try {
                WebDriver driver = (WebDriver) result.getTestClass().getRealClass()
                        .getDeclaredField("webDriver").get(result.getInstance());
                
                if (driver != null) {
                    String screenshotPath = TestUtils.captureScreenshot(driver, result.getName());
                    test.get().addScreenCaptureFromPath(screenshotPath);
                }
            } catch (Exception e) {
                System.err.println("Failed to capture screenshot: " + e.getMessage());
            }
        }
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test skipped: " + result.getName());
        test.get().log(Status.SKIP, "Test skipped");
    }
    
    /**
     * Logs a message to the current test report
     * @param message the message to log
     */
    public static void logInfo(String message) {
        if (test.get() != null) {
            test.get().log(Status.INFO, message);
        }
    }
    
    /**
     * Logs a warning message to the current test report
     * @param message the warning message to log
     */
    public static void logWarning(String message) {
        if (test.get() != null) {
            test.get().log(Status.WARNING, message);
        }
    }
    
    /**
     * Logs an error message to the current test report
     * @param message the error message to log
     */
    public static void logError(String message) {
        if (test.get() != null) {
            test.get().log(Status.FAIL, message);
        }
    }
}