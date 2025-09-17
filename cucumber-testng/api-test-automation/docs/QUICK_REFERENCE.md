# Quick Reference Guide

This guide provides quick commands and references for common tasks in the API Test Automation framework.

## 🚀 Quick Commands

### Running Tests

```bash
# Run all tests
mvn test

# Run only Cucumber tests
mvn test -Dtest=TestRunner
mvn test -Pcucumber-only

# Run only TestNG tests
mvn test -Dtest=TestNGAuthenticationTests
mvn test -Ptestng-only

# Run specific test method
mvn test -Dtest=TestNGAuthenticationTests#testValidAuthentication

# Run with custom base URI
mvn test -Dbase.uri=http://your-api-server:3000
```

### Project Management

```bash
# Clean and compile
mvn clean compile test-compile

# Install dependencies
mvn clean install

# Skip tests during build
mvn clean install -DskipTests

# Run with debug output
mvn test -X
```

### Report Generation

```bash
# Generate all reports
mvn test

# View HTML reports
open target/cucumber-reports/cucumber-pretty.html
open target/surefire-reports/html/index.html

# View detailed API report
cat target/cucumber-reports/detailed-api-report.txt
```

## 📁 File Locations

### Test Files

- **Cucumber Features**: `src/test/resources/features/`
- **Step Definitions**: `src/test/java/org/vm/test/stepdefs/`
- **TestNG Tests**: `src/test/java/org/vm/test/TestNGAuthenticationTests.java`
- **Test Runner**: `src/test/java/org/vm/test/TestRunner.java`

### Configuration Files

- **Maven Config**: `pom.xml`
- **TestNG Suite**: `src/test/resources/testng.xml`
- **Cucumber Suite**: `src/test/resources/cucumber-only.xml`

### Reports

- **Cucumber HTML**: `target/cucumber-reports/cucumber-pretty.html`
- **Cucumber JSON**: `target/cucumber-reports/cucumber.json`
- **TestNG HTML**: `target/surefire-reports/html/index.html`
- **Detailed API**: `target/cucumber-reports/detailed-api-report.txt`

## 🔧 Configuration Quick Reference

### Base URI Configuration

```java
// Local development
private static final String BASE_URI = "http://localhost:3000";

// WSL to Windows
private static final String BASE_URI = "http://192.168.176.1:3000";

// Docker
private static final String BASE_URI = "http://host.docker.internal:3000";
```

### Maven Profiles

```xml
<!-- In pom.xml -->
<profiles>
    <profile>
        <id>cucumber-only</id>
        <!-- Cucumber-only configuration -->
    </profile>
    <profile>
        <id>testng-only</id>
        <!-- TestNG-only configuration -->
    </profile>
</profiles>
```

### TestNG Annotations

```java
@BeforeClass    // Runs once before all tests
@BeforeTest     // Runs before each test
@BeforeMethod   // Runs before each test method
@Test           // Marks a test method
@AfterMethod    // Runs after each test method
@AfterTest      // Runs after each test
@AfterClass     // Runs once after all tests
```

### Cucumber Annotations

```java
@Given("step description")    // Setup step
@When("step description")     // Action step
@Then("step description")     // Verification step
@And("step description")      // Additional step
@But("step description")      // Negative step
```

## 🏷️ Test Tags and Groups

### Cucumber Tags

```gherkin
@smoke @regression
Scenario: Critical test
    # Test steps
```

### TestNG Groups

```java
@Test(groups = {"smoke", "regression"})
public void testMethod() {
    // Test implementation
}
```

### Running with Tags/Groups

```bash
# Cucumber tags
mvn test -Dcucumber.options="--tags @smoke"

# TestNG groups
mvn test -Dgroups=smoke
```

## 📊 Common Assertions

### RestAssured Assertions

```java
// Status code
Assert.assertEquals(response.getStatusCode(), 200);

// Response body
Map<String, Object> body = response.getBody().as(Map.class);
Assert.assertNotNull(body.get("token"));

// Response time
Assert.assertTrue(response.getTime() < 1000);

// Headers
Assert.assertEquals(response.getHeader("Content-Type"), "application/json");
```

### TestNG Assertions

```java
// Basic assertions
Assert.assertEquals(actual, expected);
Assert.assertNotNull(object);
Assert.assertTrue(condition);
Assert.assertFalse(condition);

// With messages
Assert.assertEquals(actual, expected, "Custom error message");
```

## 🐛 Common Issues and Solutions

### Issue: Connection Refused

```bash
# Check if API server is running
curl http://localhost:3000/health

# Check Windows host IP from WSL
ip route show | grep default | awk '{print $3}'
```

### Issue: Compilation Errors

```bash
# Clean and recompile
mvn clean compile test-compile

# Check Java version
java -version
```

### Issue: Test Not Found

```bash
# Check test class name
mvn test -Dtest=TestNGAuthenticationTests

# List available test classes
mvn test -Dtest=*
```

### Issue: Step Definition Not Found

```bash
# Check glue package in TestRunner
# Ensure step definition package is correct
```

## 📝 Logging Quick Reference

### Console Logging

```java
System.out.println("=== API REQUEST ===");
System.out.println("Request Body: " + requestBody);
System.out.println("=== API RESPONSE ===");
System.out.println("Status Code: " + response.getStatusCode());
System.out.println("Response Body: " + response.getBody().asString());
```

### RestAssured Logging

```java
// Log all request details
request = RestAssured.given().log().all();

// Log only if validation fails
RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
```

## 🔍 Debugging Commands

```bash
# Run with verbose output
mvn test -X

# Run single test with debug
mvn test -Dtest=TestNGAuthenticationTests#testValidAuthentication -X

# Check Maven dependencies
mvn dependency:tree

# Check test compilation
mvn test-compile
```

## 📚 Documentation Links

- [Main README](../README.md)
- [Cucumber Guide](CUCUMBER.md)
- [TestNG Guide](TESTNG.md)
- [Setup Guide](SETUP.md)

## 🆘 Emergency Commands

```bash
# Reset everything
mvn clean
rm -rf target/

# Rebuild from scratch
mvn clean install

# Run with minimal output
mvn test -q

# Skip tests completely
mvn clean install -DskipTests
```
