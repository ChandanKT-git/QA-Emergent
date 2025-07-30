# Emergent.sh QA Testing Project

This project contains automated test cases for the Emergent.sh platform using Selenium, Java, and TestNG.

## Project Structure

```
├── pom.xml                 # Maven configuration file
├── testng.xml              # TestNG configuration file
├── run_tests.bat           # Windows batch file to run tests
├── run_tests.ps1           # PowerShell script to run tests with options
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── emergent/
│   │   │           ├── pages/      # Page objects
│   │   │           └── utils/      # Utility classes
│   │   └── resources/
│   │       ├── config.properties   # Test configuration
│   │       └── log4j2.xml          # Logging configuration
│   └── test/
│       └── java/
│           └── com/
│               └── emergent/
│                   └── tests/      # Test classes
└── target/
    ├── logs/                # Test execution logs
    ├── screenshots/         # Failure screenshots
    └── extent-reports/      # HTML test reports
```

## Test Cases

The automation test suite covers the following areas:

1. **Authentication Tests** - Login, signup, forgot password, and logout functionality
2. **Project Creation Tests** - Creating projects with different configurations
3. **AI Agent Interaction Tests** - Sending prompts and receiving responses
4. **Code Generation Tests** - Generating different types of applications
5. **Testing Functionality Tests** - Creating and running tests within the platform
6. **Deployment Tests** - Deploying applications to different environments
7. **Project Settings Tests** - Updating and deleting projects

## Prerequisites

- Java JDK 11 or higher
- Maven 3.6 or higher
- Chrome, Firefox, or Edge browser

## Setup and Execution

### 1. Clone the repository

```bash
git clone <repository-url>
cd QA-Emergent
```

### 2. Update test credentials

Before running the tests, update the test credentials in `src/main/resources/config.properties`.

### 3. Run tests using provided scripts

#### Windows users:

Double-click the `run_tests.bat` file or run it from the command line:

```bash
.\run_tests.bat
```

#### PowerShell script with options:

```powershell
# Run all tests
.\run_tests.ps1

# Run a specific test class
.\run_tests.ps1 -TestClass AuthenticationTest

# Run a specific test method
.\run_tests.ps1 -TestClass AuthenticationTest -TestMethod testSuccessfulLogin

# Run tests in headless mode
.\run_tests.ps1 -Headless
```

### 4. Run tests using Maven

To run all tests:

```bash
mvn clean test
```

To run a specific test class:

```bash
mvn clean test -Dtest=AuthenticationTest
```

To run a specific test method:

```bash
mvn clean test -Dtest=AuthenticationTest#testSuccessfulLogin
```

### 5. Run tests using TestNG XML

```bash
mvn clean test -DsuiteXmlFile=testng.xml
```

## Test Reports

After test execution, the following reports are generated:

- **TestNG Reports**: `target/surefire-reports`
- **ExtentReports HTML Report**: `target/extent-reports`
- **Log Files**: `target/logs`
- **Failure Screenshots**: `target/screenshots`

The PowerShell script automatically opens the latest ExtentReports HTML report after test execution.

## Configuration

### config.properties

The `src/main/resources/config.properties` file contains various configuration options:

```properties
# Base URL for the application
base.url=https://emergent.sh

# Browser configuration
browser=chrome
headless=false

# Timeouts (in seconds)
default.timeout=30
implicit.wait=10
page.load.timeout=60
script.timeout=30

# Test credentials
test.username=test@example.com
test.password=Password123!
```

### log4j2.xml

The `src/main/resources/log4j2.xml` file configures the logging behavior. Logs are written to both the console and log files in the `target/logs` directory.

## Framework Features

- **Page Object Model**: Each page of the application has a corresponding Page Object class
- **Data-Driven Testing**: Using TestNG's `@DataProvider` annotation
- **Configurable**: Easy to configure through properties files
- **Cross-Browser Testing**: Support for Chrome, Firefox, and Edge
- **Reporting**: Detailed HTML reports using ExtentReports
- **Logging**: Comprehensive logging using Log4j2
- **Screenshots**: Automatic screenshot capture on test failure
- **Utilities**: Helper methods for common operations

## Notes

- The tests use the Page Object Model design pattern for better maintainability
- WebDriverFactory manages browser instances and supports Chrome, Firefox, Edge, and Safari
- TestUtils provides common utility methods for the test automation framework
- TestListener handles test events and reporting
- ConfigProperties provides access to configuration values
- Constants centralizes common constants used throughout the framework