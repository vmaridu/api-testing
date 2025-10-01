# Cucumber Testing Guide

This guide explains how to write, configure, and run Cucumber tests in this API automation framework.

## 🥒 What is Cucumber?

Cucumber is a BDD (Behavior-Driven Development) testing framework that allows you to write tests in plain English using Gherkin syntax. It bridges the gap between business stakeholders and technical teams.

## 📁 Cucumber Project Structure

```
src/test/
├── java/org/vm/test/
│   ├── TestRunner.java                    # Main test runner
│   └── stepdefs/
│       └── AuthenticationTests.java       # Step definitions
└── resources/
    ├── features/
    │   └── AuthenticationTests.feature    # Feature files
    └── testng.xml                         # TestNG configuration
```

## 🎯 Key Components

### 1. Feature Files (.feature)

Located in `src/test/resources/features/`

**Example: AuthenticationTests.feature**

```gherkin
Feature: Authentication Tests

  Scenario: Valid Authentication
    Given The user have valid credentials
    When User passed user-name and password in api request
    Then the response should have http 200 with json response containing token

  Scenario: Invalid Authentication
    Given The user have invalid credentials
    When User passed user-name and password in api request
    Then the response should have http 401 with json response containing error message
```

### 2. Step Definitions

Located in `src/test/java/org/vm/test/stepdefs/`

**Example: AuthenticationTests.java**

```java
@Given("The user have valid credentials")
public void theUserHaveValidCredentials() {
    this.credentials = VALID_CREDENTIALS;
    System.out.println("=== SETUP: Valid Credentials ===");
    System.out.println("Using credentials: " + credentials);
}

@When("User passed user-name and password in api request")
public void userPassedUserNameAndPasswordInApiRequest() {
    // API request implementation
    response = request.body(credentials).post(AUTH_ENDPOINT);
}

@Then("the response should have http 200 with json response containing token")
public void theResponseShouldHaveHttp200WithJsonResponseContainingToken() {
    Assert.assertEquals(response.getStatusCode(), 200);
    Assert.assertNotNull(response.getBody().as(Map.class).get("token"));
}
```

### 3. Test Runner

Located in `src/test/java/org/vm/test/TestRunner.java`

```java
@CucumberOptions(
    features = "src/test/resources/features",
    glue = { "org.vm.test.stepdefs" },
    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber-pretty.html",
        "json:target/cucumber-reports/cucumber.json",
        "junit:target/cucumber-reports/cucumber.xml",
        "timeline:target/cucumber-reports/timeline",
        "usage:target/cucumber-reports/usage.json",
        "rerun:target/cucumber-reports/rerun.txt",
        "org.vm.test.plugins.DetailedReporter"
    },
    monochrome = true,
    publish = false
)
public class TestRunner extends AbstractTestNGCucumberTests {
    // Test runner implementation
}
```

## 🚀 Running Cucumber Tests

### Command Line Options

#### Run All Cucumber Tests

```bash
mvn test -Dtest=TestRunner
```

#### Run with Maven Profile

```bash
mvn test -Pcucumber-only
```

#### Run with Custom Suite

```bash
mvn test -DsuiteXmlFile=src/test/resources/cucumber-only.xml
```

#### Run from IDE

1. Right-click on `TestRunner.java`
2. Select "Run TestRunner"
3. Or use Command Palette: `Ctrl+Shift+P` → "Java: Run Tests"

### Advanced Running Options

#### Run Specific Feature

```bash
mvn test -Dtest=TestRunner -Dcucumber.options="--name 'Valid Authentication'"
```

#### Run with Tags

```bash
mvn test -Dtest=TestRunner -Dcucumber.options="--tags @smoke"
```

## 📊 Reporting

### Report Types Generated

1. **HTML Report**: `target/cucumber-reports/cucumber-pretty.html`

   - Beautiful, interactive HTML report
   - Shows step-by-step execution
   - Includes screenshots and logs

2. **JSON Report**: `target/cucumber-reports/cucumber.json`

   - Machine-readable format
   - Used for CI/CD integration
   - Can be processed by other tools

3. **JUnit XML**: `target/cucumber-reports/cucumber.xml`

   - Standard JUnit format
   - Compatible with most CI systems

4. **Detailed API Report**: `target/cucumber-reports/detailed-api-report.txt`

   - Custom report with API request/response details
   - Includes timing and error information

5. **Timeline Report**: `target/cucumber-reports/timeline/`

   - Visual timeline of test execution
   - Shows parallel execution details

6. **Usage Report**: `target/cucumber-reports/usage.json`
   - Step definition usage statistics
   - Helps identify unused steps

### Viewing Reports

```bash
# Open HTML report in browser
open target/cucumber-reports/cucumber-pretty.html

# View detailed API report
cat target/cucumber-reports/detailed-api-report.txt
```

## ✍️ Writing Cucumber Tests

### 1. Create Feature File

Create a new `.feature` file in `src/test/resources/features/`:

```gherkin
Feature: User Management

  @smoke @positive
  Scenario: Create new user
    Given The user has valid authentication token
    When User creates a new user with name "John Doe" and email "john@example.com"
    Then The response should have status code 201
    And The response should contain user ID

  @negative
  Scenario: Create user with invalid email
    Given The user has valid authentication token
    When User creates a new user with name "John Doe" and email "invalid-email"
    Then The response should have status code 400
    And The response should contain error message "Invalid email format"
```

### 2. Implement Step Definitions

Create corresponding step definitions in `src/test/java/org/vm/test/stepdefs/`:

```java
@Given("The user has valid authentication token")
public void theUserHasValidAuthenticationToken() {
    // Setup authentication token
    authToken = "valid-token-123";
}

@When("User creates a new user with name {string} and email {string}")
public void userCreatesNewUserWithNameAndEmail(String name, String email) {
    Map<String, String> userData = new HashMap<>();
    userData.put("name", name);
    userData.put("email", email);

    response = request
        .header("Authorization", "Bearer " + authToken)
        .body(userData)
        .post("/users");
}

@Then("The response should have status code {int}")
public void theResponseShouldHaveStatusCode(int expectedStatusCode) {
    Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
}

@Then("The response should contain user ID")
public void theResponseShouldContainUserId() {
    Map<String, Object> responseBody = response.getBody().as(Map.class);
    Assert.assertNotNull(responseBody.get("id"));
}
```

### 3. Best Practices

#### Feature File Best Practices

- Use descriptive feature and scenario names
- Keep scenarios focused and atomic
- Use tags for test categorization
- Include background steps for common setup

#### Step Definition Best Practices

- Keep step definitions simple and focused
- Use proper assertions with meaningful messages
- Include detailed logging for debugging
- Handle exceptions gracefully

#### Naming Conventions

- Feature files: `PascalCase.feature`
- Step definitions: `PascalCase.java`
- Methods: `camelCase()`
- Variables: `camelCase`

## 🔧 Configuration

### Cucumber Options

Key configuration options in `@CucumberOptions`:

```java
@CucumberOptions(
    features = "src/test/resources/features",    // Feature file location
    glue = { "org.vm.test.stepdefs" },          // Step definition packages
    plugin = { "pretty", "html:target/reports" }, // Report plugins
    monochrome = true,                          // Clean console output
    publish = false,                            // Disable cloud publishing
    dryRun = false,                             // Skip actual execution
    strict = true                               // Fail on undefined steps
)
```

### Parallel Execution

To run tests in parallel:

```java
@Override
@DataProvider(parallel = true)
public Object[][] scenarios() {
    return super.scenarios();
}
```

### Tags

Use tags to categorize and filter tests:

```gherkin
@smoke @regression
Scenario: Critical authentication test
    # Test steps

@api @integration
Scenario: User creation flow
    # Test steps
```

Run specific tags:

```bash
mvn test -Dcucumber.options="--tags @smoke"
```

## 🐛 Troubleshooting

### Common Issues

#### 1. Step Definition Not Found

```
UndefinedStepException: The step 'Given the user has valid credentials' is undefined
```

**Solution**: Ensure step definition method exists and package is in glue path.

#### 2. Feature File Not Found

```
No features found at [src/test/resources/features]
```

**Solution**: Check feature file location and `features` path in `@CucumberOptions`.

#### 3. Duplicate Step Definitions

```
DuplicateStepDefinitionException: Duplicate step definitions
```

**Solution**: Remove duplicate step definitions or use different step text.

#### 4. JSON Serialization Error

```
Cannot serialize object because no JSON serializer found
```

**Solution**: Ensure Jackson dependencies are included in `pom.xml`.

### Debug Tips

1. **Enable Debug Logging**:

   ```java
   System.setProperty("cucumber.publish.quiet", "true");
   ```

2. **Use Dry Run**:

   ```java
   dryRun = true  // In @CucumberOptions
   ```

3. **Check Step Definition Mapping**:

   ```bash
   mvn test -Dcucumber.options="--dry-run"
   ```

4. **View Detailed Logs**:
   ```bash
   mvn test -X  # Enable debug mode
   ```

## 📚 Additional Resources

- [Cucumber Official Documentation](https://cucumber.io/docs/cucumber/)
- [Gherkin Syntax Reference](https://cucumber.io/docs/gherkin/)
- [RestAssured Documentation](https://rest-assured.io/)
- [TestNG Documentation](https://testng.org/doc/)

## 🤝 Contributing

When adding new Cucumber tests:

1. Follow the existing naming conventions
2. Add appropriate tags for categorization
3. Include comprehensive logging
4. Update this documentation if needed
5. Ensure tests are maintainable and readable
