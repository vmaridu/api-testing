# TestNG Testing Guide

This guide explains how to write, configure, and run TestNG tests in this API automation framework.

## 🧪 What is TestNG?

TestNG is a testing framework inspired by JUnit and NUnit, but introducing new functionality that makes it more powerful and easier to use. It supports parallel execution, data-driven testing, and comprehensive reporting.

## 📁 TestNG Project Structure

```
src/test/
├── java/org/vm/test/
│   ├── TestNGAuthenticationTests.java    # TestNG test class
│   └── TestRunner.java                   # Cucumber runner (separate)
└── resources/
    ├── testng.xml                        # TestNG suite configuration
    └── cucumber-only.xml                 # Cucumber-only suite
```

## 🎯 Key Components

### 1. TestNG Test Class

Located in `src/test/java/org/vm/test/TestNGAuthenticationTests.java`

**Example Structure:**

```java
public class TestNGAuthenticationTests {

    // Test configuration and setup
    @BeforeClass
    public void setUpClass() {
        // One-time setup for the entire test class
    }

    @BeforeTest
    public void setUpTest() {
        // Setup before each test method
    }

    @BeforeMethod
    public void setUpMethod() {
        // Setup before each test method
    }

    // Test methods
    @Test(description = "Test valid authentication", priority = 1)
    public void testValidAuthentication() {
        // Test implementation
    }

    @Test(description = "Test invalid authentication", priority = 2)
    public void testInvalidAuthentication() {
        // Test implementation
    }

    // Cleanup methods
    @AfterMethod
    public void tearDownMethod() {
        // Cleanup after each test method
    }

    @AfterTest
    public void tearDownTest() {
        // Cleanup after test
    }

    @AfterClass
    public void tearDownClass() {
        // One-time cleanup for the entire test class
    }
}
```

### 2. TestNG Suite Configuration

Located in `src/test/resources/testng.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="API Test Automation Suite" parallel="false">
    <test name="TestNG Authentication Tests">
        <classes>
            <class name="org.vm.test.TestNGAuthenticationTests"/>
        </classes>
    </test>
</suite>
```

## 🚀 Running TestNG Tests

### Command Line Options

#### Run All TestNG Tests

```bash
mvn test -Dtest=TestNGAuthenticationTests
```

#### Run with Maven Profile

```bash
mvn test -Ptestng-only
```

#### Run with TestNG Suite

```bash
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
```

#### Run Specific Test Method

```bash
mvn test -Dtest=TestNGAuthenticationTests#testValidAuthentication
```

#### Run from IDE

1. Right-click on `TestNGAuthenticationTests.java`
2. Select "Run TestNGAuthenticationTests"
3. Or use Command Palette: `Ctrl+Shift+P` → "Java: Run Tests"

### Advanced Running Options

#### Run with Specific Groups

```bash
mvn test -Dtest=TestNGAuthenticationTests -Dgroups=smoke
```

#### Run with Parameters

```bash
mvn test -Dtest=TestNGAuthenticationTests -Dtestng.dtd.http=true
```

## 📊 Reporting

### Report Types Generated

1. **Surefire Reports**: `target/surefire-reports/`

   - Standard Maven test reports
   - XML and text formats
   - Test execution summary

2. **TestNG HTML Reports**: `target/surefire-reports/html/`

   - Interactive HTML reports
   - Test results with pass/fail status
   - Execution timeline

3. **Console Output**: Enhanced logging with:
   - Request/response details
   - Validation results
   - Error messages and stack traces

### Viewing Reports

```bash
# Open HTML report in browser
open target/surefire-reports/html/index.html

# View test results summary
cat target/surefire-reports/TestNGAuthenticationTests.txt
```

## ✍️ Writing TestNG Tests

### 1. Test Class Structure

Create a new test class extending no base class:

```java
public class TestNGUserManagementTests {

    private static final String BASE_URI = "http://192.168.176.1:3000";
    private RequestSpecification request;
    private Response response;

    // Setup and teardown methods
    // Test methods
}
```

### 2. Test Annotations

#### Setup Annotations

```java
@BeforeClass
public void setUpClass() {
    // Runs once before all tests in the class
    System.out.println("Setting up test class");
}

@BeforeTest
public void setUpTest() {
    // Runs before each test method
    System.out.println("Setting up test");
}

@BeforeMethod
public void setUpMethod() {
    // Runs before each test method
    System.out.println("Setting up method");
}
```

#### Test Annotations

```java
@Test(description = "Test user creation", priority = 1)
public void testUserCreation() {
    // Test implementation
}

@Test(description = "Test user retrieval", priority = 2, dependsOnMethods = "testUserCreation")
public void testUserRetrieval() {
    // Test implementation
}

@Test(description = "Test user update", priority = 3, groups = {"smoke", "regression"})
public void testUserUpdate() {
    // Test implementation
}
```

#### Cleanup Annotations

```java
@AfterMethod
public void tearDownMethod() {
    // Runs after each test method
    System.out.println("Cleaning up method");
}

@AfterTest
public void tearDownTest() {
    // Runs after test
    System.out.println("Cleaning up test");
}

@AfterClass
public void tearDownClass() {
    // Runs once after all tests in the class
    System.out.println("Cleaning up class");
}
```

### 3. Complete Test Example

```java
@Test(description = "Test valid authentication with correct credentials", priority = 1)
public void testValidAuthentication() {
    System.out.println("=== TEST: Valid Authentication ===");
    System.out.println("Testing authentication with valid credentials");

    // Prepare test data
    Map<String, String> credentials = new HashMap<>();
    credentials.put("user_name", "user");
    credentials.put("password", "password");

    // Make API request
    response = request
        .header("Content-Type", "application/json")
        .body(credentials)
        .post("/auth");

    // Log response details
    System.out.println("Status Code: " + response.getStatusCode());
    System.out.println("Response Body: " + response.getBody().asString());

    // Assertions
    Assert.assertEquals(response.getStatusCode(), 200, "Expected HTTP 200 status code");

    Map<String, Object> responseBody = response.getBody().as(Map.class);
    Assert.assertNotNull(responseBody.get("token"), "Token should not be null");

    System.out.println("✅ Test PASSED: Valid authentication");
}
```

### 4. Data-Driven Testing

#### Using @DataProvider

```java
@DataProvider(name = "userCredentials")
public Object[][] getUserCredentials() {
    return new Object[][] {
        {"user1", "password1", 200},
        {"user2", "password2", 200},
        {"invalid", "password", 401},
        {"user", "invalid", 401}
    };
}

@Test(dataProvider = "userCredentials")
public void testAuthenticationWithData(String username, String password, int expectedStatus) {
    Map<String, String> credentials = new HashMap<>();
    credentials.put("user_name", username);
    credentials.put("password", password);

    response = request.body(credentials).post("/auth");

    Assert.assertEquals(response.getStatusCode(), expectedStatus);
}
```

#### Using @Parameters

```java
@Test
@Parameters({"username", "password", "expectedStatus"})
public void testAuthenticationWithParameters(String username, String password, int expectedStatus) {
    // Test implementation using parameters
}
```

### 5. Best Practices

#### Test Method Best Practices

- Use descriptive method names
- Include comprehensive logging
- Use meaningful assertion messages
- Handle exceptions gracefully
- Keep tests independent and atomic

#### Setup/Cleanup Best Practices

- Use appropriate annotation levels
- Clean up resources properly
- Reset state between tests
- Use static variables for class-level setup

#### Naming Conventions

- Test classes: `TestNG[Feature]Tests.java`
- Test methods: `test[Scenario]()`
- Variables: `camelCase`
- Constants: `UPPER_SNAKE_CASE`

## 🔧 Configuration

### TestNG XML Configuration

#### Basic Suite Configuration

```xml
<suite name="API Test Suite" parallel="false">
    <test name="Authentication Tests">
        <classes>
            <class name="org.vm.test.TestNGAuthenticationTests"/>
        </classes>
    </test>
</suite>
```

#### Advanced Configuration

```xml
<suite name="API Test Suite" parallel="methods" thread-count="2">
    <parameter name="baseUrl" value="http://localhost:3000"/>
    <test name="Authentication Tests">
        <groups>
            <run>
                <include name="smoke"/>
                <exclude name="slow"/>
            </run>
        </groups>
        <classes>
            <class name="org.vm.test.TestNGAuthenticationTests">
                <methods>
                    <include name="testValidAuthentication"/>
                    <exclude name="testSlowOperation"/>
                </methods>
            </class>
        </classes>
    </test>
</suite>
```

### Maven Configuration

#### Surefire Plugin Configuration

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.2.5</version>
    <configuration>
        <testFailureIgnore>true</testFailureIgnore>
        <parallel>none</parallel>
        <threadCount>1</threadCount>
        <includes>
            <include>**/TestNGAuthenticationTests.java</include>
        </includes>
    </configuration>
</plugin>
```

### Parallel Execution

#### Method-Level Parallel Execution

```xml
<suite name="Parallel Suite" parallel="methods" thread-count="3">
    <test name="Parallel Tests">
        <classes>
            <class name="org.vm.test.TestNGAuthenticationTests"/>
        </classes>
    </test>
</suite>
```

#### Class-Level Parallel Execution

```xml
<suite name="Parallel Suite" parallel="classes" thread-count="2">
    <test name="Parallel Tests">
        <classes>
            <class name="org.vm.test.TestNGAuthenticationTests"/>
            <class name="org.vm.test.TestNGUserManagementTests"/>
        </classes>
    </test>
</suite>
```

## 🏷️ Test Groups and Categories

### Using Groups

```java
@Test(groups = {"smoke", "regression"})
public void testCriticalFunctionality() {
    // Test implementation
}

@Test(groups = {"api", "integration"})
public void testAPIIntegration() {
    // Test implementation
}
```

### Running Specific Groups

```bash
mvn test -Dgroups=smoke
mvn test -Dgroups=smoke,regression
```

## 🐛 Troubleshooting

### Common Issues

#### 1. Test Not Found

```
No tests found in class TestNGAuthenticationTests
```

**Solution**: Ensure test methods are annotated with `@Test`.

#### 2. Setup Method Not Running

```
NullPointerException in test method
```

**Solution**: Check `@BeforeMethod` annotation and method visibility.

#### 3. Parallel Execution Issues

```
Test failures due to shared state
```

**Solution**: Ensure tests are independent or use proper synchronization.

#### 4. Suite Configuration Error

```
Cannot find class in classpath
```

**Solution**: Verify class names in `testng.xml` match actual class names.

### Debug Tips

1. **Enable Verbose Logging**:

   ```bash
   mvn test -X
   ```

2. **Run Single Test Method**:

   ```bash
   mvn test -Dtest=TestNGAuthenticationTests#testValidAuthentication
   ```

3. **Check TestNG Reports**:

   ```bash
   open target/surefire-reports/html/index.html
   ```

4. **Use TestNG Listeners**:
   ```java
   @Listeners({TestListener.class})
   public class TestNGAuthenticationTests {
       // Test class
   }
   ```

## 📚 Additional Resources

- [TestNG Official Documentation](https://testng.org/doc/)
- [TestNG Annotations Reference](https://testng.org/doc/documentation-main.html#annotations)
- [Maven Surefire Plugin](https://maven.apache.org/surefire/maven-surefire-plugin/)
- [RestAssured Documentation](https://rest-assured.io/)

## 🤝 Contributing

When adding new TestNG tests:

1. Follow the existing naming conventions
2. Use appropriate annotations for setup/cleanup
3. Include comprehensive logging and assertions
4. Ensure tests are independent and repeatable
5. Update this documentation if needed
6. Add appropriate test groups for categorization
