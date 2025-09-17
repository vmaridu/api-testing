# Setup and Configuration Guide

This guide provides detailed instructions for setting up the API Test Automation framework in different environments.

## 📋 Prerequisites

### Required Software

- **Java 21** or higher
- **Maven 3.6** or higher
- **Git** (for version control)
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code)

### Optional Software

- **Docker** (for containerized testing)
- **Node.js** (if testing Node.js APIs)

## 🚀 Environment Setup

### 1. Java Installation

#### Windows

```bash
# Download and install OpenJDK 21
# Set JAVA_HOME environment variable
set JAVA_HOME=C:\Program Files\Java\jdk-21
set PATH=%JAVA_HOME%\bin;%PATH%

# Verify installation
java -version
javac -version
```

#### Linux/macOS

```bash
# Using SDKMAN (recommended)
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install java 21.0.1-open

# Or using package manager
# Ubuntu/Debian
sudo apt update
sudo apt install openjdk-21-jdk

# macOS with Homebrew
brew install openjdk@21

# Verify installation
java -version
javac -version
```

### 2. Maven Installation

#### Windows

```bash
# Download Maven from https://maven.apache.org/download.cgi
# Extract to C:\apache-maven-3.9.6
# Set environment variables
set MAVEN_HOME=C:\apache-maven-3.9.6
set PATH=%MAVEN_HOME%\bin;%PATH%

# Verify installation
mvn -version
```

#### Linux/macOS

```bash
# Using SDKMAN
sdk install maven

# Or using package manager
# Ubuntu/Debian
sudo apt install maven

# macOS with Homebrew
brew install maven

# Verify installation
mvn -version
```

### 3. IDE Setup

#### IntelliJ IDEA

1. Install **IntelliJ IDEA Community** or **Ultimate**
2. Install plugins:
   - **Cucumber for Java**
   - **TestNG**
   - **Maven Helper**
3. Import project as Maven project
4. Configure Java SDK to version 21

#### Eclipse

1. Install **Eclipse IDE for Java Developers**
2. Install plugins:
   - **Cucumber Eclipse Plugin**
   - **TestNG for Eclipse**
   - **Maven Integration for Eclipse**
3. Import project as Maven project

#### VS Code

1. Install **VS Code**
2. Install extensions:
   - **Extension Pack for Java**
   - **Cucumber (Gherkin) Full Support**
   - **TestNG for Java**
3. Open project folder

## 🔧 Project Configuration

### 1. Clone and Setup Project

```bash
# Clone the repository
git clone <repository-url>
cd api-test-automation

# Verify project structure
ls -la
```

### 2. Maven Configuration

#### pom.xml Key Dependencies

```xml
<dependencies>
    <!-- Cucumber Dependencies -->
    <dependency>
        <groupId>io.cucumber</groupId>
        <artifactId>cucumber-java</artifactId>
        <version>7.23.0</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>io.cucumber</groupId>
        <artifactId>cucumber-testng</artifactId>
        <version>7.23.0</version>
        <scope>test</scope>
    </dependency>

    <!-- TestNG Dependency -->
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.10.2</version>
        <scope>test</scope>
    </dependency>

    <!-- RestAssured Dependency -->
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>rest-assured</artifactId>
        <version>5.5.6</version>
        <scope>test</scope>
    </dependency>

    <!-- Jackson Dependencies for JSON -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.16.1</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

### 3. API Server Configuration

#### Local Development

```java
// For local development
private static final String BASE_URI = "http://localhost:3000";
```

#### WSL to Windows Host

```java
// For WSL environment accessing Windows host
private static final String BASE_URI = "http://192.168.176.1:3000";
```

#### Finding Windows Host IP from WSL

```bash
# Method 1: Get default gateway
ip route show | grep default | awk '{print $3}'

# Method 2: Check nameserver
cat /etc/resolv.conf | grep nameserver | awk '{print $2}'

# Method 3: Use hostname
hostname -I
```

#### Docker Environment

```java
// For Docker environment
private static final String BASE_URI = "http://host.docker.internal:3000";
```

### 4. Environment-Specific Configuration

#### Using System Properties

```bash
# Set base URI via system property
mvn test -Dbase.uri=http://your-api-server:3000
```

#### Using Environment Variables

```bash
# Set environment variable
export API_BASE_URI=http://your-api-server:3000
mvn test
```

#### Configuration Class

```java
public class TestConfig {
    private static final String BASE_URI = getBaseUri();

    private static String getBaseUri() {
        String baseUri = System.getProperty("base.uri");
        if (baseUri != null && !baseUri.isEmpty()) {
            return baseUri;
        }

        String envUri = System.getenv("API_BASE_URI");
        if (envUri != null && !envUri.isEmpty()) {
            return envUri;
        }

        // Default configuration
        return "http://localhost:3000";
    }
}
```

## 🧪 Test Execution Setup

### 1. Verify Setup

```bash
# Clean and compile
mvn clean compile test-compile

# Run a simple test
mvn test -Dtest=TestNGAuthenticationTests#testValidAuthentication
```

### 2. Test API Connectivity

```bash
# Test API server connectivity
curl -X POST http://localhost:3000/auth \
  -H "Content-Type: application/json" \
  -d '{"user_name":"user","password":"password"}'
```

### 3. Verify Reports Generation

```bash
# Run tests and check reports
mvn test
ls -la target/cucumber-reports/
ls -la target/surefire-reports/
```

## 🔐 Authentication Setup

### 1. API Authentication

#### Basic Authentication

```java
request = RestAssured.given()
    .auth().basic("username", "password")
    .header("Content-Type", "application/json");
```

#### Bearer Token Authentication

```java
request = RestAssured.given()
    .header("Authorization", "Bearer " + authToken)
    .header("Content-Type", "application/json");
```

#### API Key Authentication

```java
request = RestAssured.given()
    .header("X-API-Key", apiKey)
    .header("Content-Type", "application/json");
```

### 2. Test Data Management

#### Hardcoded Test Data

```java
private static final Map<String, String> VALID_CREDENTIALS = Map.of(
    "user_name", "user",
    "password", "password"
);
```

#### External Test Data

```java
// Load from properties file
Properties props = new Properties();
props.load(new FileInputStream("src/test/resources/test-data.properties"));
String username = props.getProperty("valid.username");
```

#### Dynamic Test Data

```java
// Generate test data dynamically
String timestamp = String.valueOf(System.currentTimeMillis());
String email = "test" + timestamp + "@example.com";
```

## 📊 Reporting Configuration

### 1. Cucumber Reports

#### HTML Report Configuration

```java
@CucumberOptions(
    plugin = {
        "html:target/cucumber-reports/cucumber-pretty.html",
        "json:target/cucumber-reports/cucumber.json"
    }
)
```

#### Custom Report Configuration

```java
@CucumberOptions(
    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber-pretty.html",
        "json:target/cucumber-reports/cucumber.json",
        "junit:target/cucumber-reports/cucumber.xml",
        "timeline:target/cucumber-reports/timeline",
        "usage:target/cucumber-reports/usage.json",
        "rerun:target/cucumber-reports/rerun.txt"
    }
)
```

### 2. TestNG Reports

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
    </configuration>
</plugin>
```

## 🐳 Docker Setup (Optional)

### 1. Dockerfile for Test Environment

```dockerfile
FROM openjdk:21-jdk-slim

# Install Maven
RUN apt-get update && apt-get install -y maven

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Run tests
CMD ["mvn", "test"]
```

### 2. Docker Compose for Full Stack

```yaml
version: "3.8"
services:
  api-server:
    image: your-api-server:latest
    ports:
      - "3000:3000"
    environment:
      - NODE_ENV=test

  test-runner:
    build: .
    depends_on:
      - api-server
    environment:
      - API_BASE_URI=http://api-server:3000
    volumes:
      - ./target:/app/target
```

### 3. Running Tests in Docker

```bash
# Build and run tests
docker-compose up --build test-runner

# Run specific test profile
docker-compose run test-runner mvn test -Pcucumber-only
```

## 🔧 IDE-Specific Configuration

### 1. IntelliJ IDEA

#### Run Configurations

1. **Cucumber Tests**:

   - Main class: `org.vm.test.TestRunner`
   - VM options: `-Dbase.uri=http://localhost:3000`

2. **TestNG Tests**:
   - Main class: `org.testng.TestNG`
   - Suite file: `src/test/resources/testng.xml`

#### Code Style

```xml
<!-- .idea/codeStyles/Project.xml -->
<component name="ProjectCodeStyleConfiguration">
  <code_scheme name="Project" version="173">
    <JavaCodeStyleSettings>
      <option name="IMPORT_LAYOUT_TABLE">
        <value>
          <option name="name" value="java" />
          <option name="value" value="java.*" />
          <option name="name" value="javax" />
          <option name="value" value="javax.*" />
          <option name="name" value="org" />
          <option name="value" value="org.*" />
          <option name="name" value="com" />
          <option name="value" value="com.*" />
        </value>
      </option>
    </JavaCodeStyleSettings>
  </code_scheme>
</component>
```

### 2. VS Code

#### Settings.json

```json
{
  "java.configuration.updateBuildConfiguration": "automatic",
  "java.test.config": {
    "workingDirectory": "${workspaceFolder}"
  },
  "cucumber.features": "src/test/resources/features",
  "cucumber.glue": "org.vm.test.stepdefs"
}
```

## 🚨 Troubleshooting

### Common Setup Issues

#### 1. Java Version Mismatch

```
Error: A JNI error has occurred, please check your installation
```

**Solution**: Ensure Java 21 is installed and JAVA_HOME is set correctly.

#### 2. Maven Dependency Issues

```
Could not resolve dependencies
```

**Solution**:

```bash
mvn clean install -U
```

#### 3. API Connection Issues

```
Connection refused
```

**Solution**:

- Verify API server is running
- Check firewall settings
- Verify correct IP address and port

#### 4. WSL Network Issues

```
Cannot connect to Windows host
```

**Solution**:

```bash
# Find Windows host IP
ip route show | grep default | awk '{print $3}'

# Test connectivity
ping <windows-host-ip>
```

### Debug Commands

```bash
# Check Java version
java -version

# Check Maven version
mvn -version

# Check project compilation
mvn clean compile

# Run with debug output
mvn test -X

# Check test dependencies
mvn dependency:tree
```

## 📚 Additional Resources

- [Java Installation Guide](https://docs.oracle.com/en/java/javase/21/install/)
- [Maven Installation Guide](https://maven.apache.org/install.html)
- [IntelliJ IDEA Setup](https://www.jetbrains.com/help/idea/getting-started.html)
- [VS Code Java Setup](https://code.visualstudio.com/docs/java/java-tutorial)
- [Docker Installation](https://docs.docker.com/get-docker/)

## 🤝 Getting Help

If you encounter issues during setup:

1. Check the troubleshooting section above
2. Verify all prerequisites are installed correctly
3. Check the project's issue tracker
4. Review the framework-specific documentation:
   - [Cucumber Setup](CUCUMBER.md#troubleshooting)
   - [TestNG Setup](TESTNG.md#troubleshooting)
