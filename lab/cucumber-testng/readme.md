# API Test Automation Framework

A comprehensive API testing framework using **Cucumber** (BDD) and **TestNG** for REST API testing with RestAssured.

## 🚀 Project Overview

This project provides a dual approach to API testing:

- **Cucumber Tests**: BDD-style tests using Gherkin syntax for business-readable test scenarios
- **TestNG Tests**: Traditional unit-style tests with comprehensive setup/teardown methods

## 📁 Project Structure

```
api-test-automation/
├── src/
│   ├── main/java/                    # Main application code (if any)
│   └── test/
│       ├── java/
│       │   └── org/vm/test/
│       │       ├── TestRunner.java              # Cucumber test runner
│       │       ├── TestNGAuthenticationTests.java # TestNG test class
│       │       ├── stepdefs/                    # Cucumber step definitions
│       │       │   └── AuthenticationTests.java
│       │       └── plugins/                     # Custom Cucumber plugins
│       │           └── DetailedReporter.java
│       └── resources/
│           ├── features/                        # Cucumber feature files
│           │   └── AuthenticationTests.feature
│           ├── testng.xml                      # TestNG suite configuration
│           └── cucumber-only.xml              # Cucumber-only suite
├── target/                                    # Generated reports and compiled classes
├── pom.xml                                   # Maven configuration
└── README.md                                 # This file
```

## 🛠️ Technologies Used

- **Java 21**
- **Maven** - Build and dependency management
- **Cucumber 7.23.0** - BDD testing framework
- **TestNG 7.10.2** - Testing framework
- **RestAssured 5.5.6** - API testing library
- **Jackson** - JSON serialization/deserialization

## 📋 Prerequisites

- Java 21 or higher
- Maven 3.6 or higher
- Access to the API server (configured for WSL to Windows host communication)

## 🚀 Quick Start

### 1. Clone and Setup

```bash
git clone <repository-url>
cd api-test-automation
```

### 2. Configure API Endpoint

Update the `BASE_URI` in your test classes to match your API server:

```java
private static final String BASE_URI = "http://your-api-server:port";
```

### 3. Run Tests

#### Run All Tests

```bash
mvn test
```

#### Run Only Cucumber Tests

```bash
mvn test -Dtest=TestRunner
# OR
mvn test -Pcucumber-only
```

#### Run Only TestNG Tests

```bash
mvn test -Dtest=TestNGAuthenticationTests
# OR
mvn test -Ptestng-only
```

## 📊 Test Reports

After running tests, reports are generated in:

- **HTML Reports**: `target/cucumber-reports/cucumber-pretty.html`
- **JSON Reports**: `target/cucumber-reports/cucumber.json`
- **Detailed API Report**: `target/cucumber-reports/detailed-api-report.txt`
- **TestNG Reports**: `target/surefire-reports/`

## 🔧 Configuration

### Maven Profiles

The project includes Maven profiles for different test execution modes:

- `cucumber-only`: Runs only Cucumber tests
- `testng-only`: Runs only TestNG tests
- Default: Runs all tests

### TestNG Configuration

- Sequential execution (no parallel processing)
- Comprehensive setup/teardown methods
- Detailed logging for API requests/responses

### Cucumber Configuration

- Multiple report formats (HTML, JSON, XML)
- Custom detailed reporter for API calls
- BDD-style feature files with step definitions

## 📚 Documentation

- [Cucumber Testing Guide](docs/CUCUMBER.md) - Detailed Cucumber setup and usage
- [TestNG Testing Guide](docs/TESTNG.md) - TestNG configuration and best practices
- [Setup and Configuration](docs/SETUP.md) - Environment setup and configuration

## 🐛 Troubleshooting

### Common Issues

1. **Connection Refused**: Ensure your API server is running and accessible
2. **WSL to Windows**: Update `BASE_URI` to use Windows host IP instead of localhost
3. **Compilation Errors**: Ensure Java 21 and Maven are properly installed

### Getting Help

Check the individual documentation files for framework-specific issues:

- [Cucumber Issues](docs/CUCUMBER.md#troubleshooting)
- [TestNG Issues](docs/TESTNG.md#troubleshooting)

## 🤝 Contributing

1. Follow the existing code structure
2. Add appropriate documentation for new features
3. Ensure tests pass before submitting PRs
4. Update relevant documentation files

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.
