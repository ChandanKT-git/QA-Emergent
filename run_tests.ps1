# PowerShell script to run Emergent.sh QA tests

# Define parameters
param (
    [string]$TestClass = "",
    [string]$TestMethod = "",
    [switch]$Headless = $false
)

# Set headless mode in config.properties if specified
if ($Headless) {
    $configFile = "src/main/resources/config.properties"
    $content = Get-Content $configFile
    $content = $content -replace "headless=false", "headless=true"
    Set-Content -Path $configFile -Value $content
    Write-Host "Set headless mode to true"
}

# Build the Maven command
$mvnCommand = "mvn clean test"

# Add specific test class if provided
if ($TestClass -ne "") {
    if ($TestMethod -ne "") {
        $mvnCommand += " -Dtest=$TestClass#$TestMethod"
        Write-Host "Running test method: $TestClass#$TestMethod"
    } else {
        $mvnCommand += " -Dtest=$TestClass"
        Write-Host "Running test class: $TestClass"
    }
} else {
    Write-Host "Running all tests"
}

# Execute the command
Write-Host "Executing: $mvnCommand"
Invoke-Expression $mvnCommand

# Reset headless mode if it was changed
if ($Headless) {
    $content = Get-Content $configFile
    $content = $content -replace "headless=true", "headless=false"
    Set-Content -Path $configFile -Value $content
    Write-Host "Reset headless mode to false"
}

# Open test report if it exists
$latestReport = Get-ChildItem -Path "target/extent-reports" -Filter "*.html" | Sort-Object LastWriteTime -Descending | Select-Object -First 1
if ($latestReport) {
    Write-Host "Opening test report: $($latestReport.FullName)"
    Start-Process $latestReport.FullName
} else {
    Write-Host "No test report found"
}