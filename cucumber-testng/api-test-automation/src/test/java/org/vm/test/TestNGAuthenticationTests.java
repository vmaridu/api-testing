package org.vm.test;

import java.util.Map;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestNGAuthenticationTests {

    private static final String BASE_URI = "http://192.168.176.1:3000";
    private static final String AUTH_ENDPOINT = "/auth";

    private RequestSpecification request;
    private Response response;
    private Map<String, String> validCredentials;
    private Map<String, String> invalidCredentials;

    @BeforeClass
    public void setUpClass() {
        System.out.println("=== TESTNG SETUP: Before Class ===");
        System.out.println("Initializing TestNG Authentication Tests");
        System.out.println("Base URI: " + BASE_URI);

        // Configure RestAssured logging globally
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.config = RestAssured.config().logConfig(
                RestAssured.config().getLogConfig().enableLoggingOfRequestAndResponseIfValidationFails());

        // Initialize credentials
        validCredentials = new HashMap<>();
        validCredentials.put("user_name", "user");
        validCredentials.put("password", "password");

        invalidCredentials = new HashMap<>();
        invalidCredentials.put("user_name", "user");
        invalidCredentials.put("password", "wrongpassword");

        System.out.println("Valid credentials: " + validCredentials);
        System.out.println("Invalid credentials: " + invalidCredentials);
        System.out.println("=================================");
    }

    @BeforeTest
    public void setUpTest() {
        System.out.println("=== TESTNG SETUP: Before Test ===");
        System.out.println("Setting up test environment");
        RestAssured.baseURI = BASE_URI;
        System.out.println("RestAssured base URI set to: " + BASE_URI);
        System.out.println("================================");
    }

    @BeforeMethod
    public void setUpMethod() {
        System.out.println("--- TESTNG SETUP: Before Method ---");
        System.out.println("Preparing request specification");
        request = RestAssured.given()
                .log().all()
                .header("Content-Type", "application/json");
        System.out.println("Request specification prepared");
        System.out.println("-------------------------------");
    }

    @Test(description = "Test valid authentication with correct credentials", priority = 1)
    public void testValidAuthentication() {
        System.out.println("=== TEST: Valid Authentication ===");
        System.out.println("Testing authentication with valid credentials");
        System.out.println("Request Body: " + validCredentials);

        // Make the API request
        response = request.body(validCredentials).post(AUTH_ENDPOINT);

        // Log response details
        System.out.println("=== REST ASSURED RESPONSE ===");
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Headers: " + response.getHeaders());
        System.out.println("Response Body: " + response.getBody().asString());
        System.out.println("Response Time: " + response.getTime() + " ms");
        System.out.println("=============================");

        // Assertions
        System.out.println("=== VALIDATION: HTTP 200 with tokenId ===");
        System.out.println("Expected Status: 200, Actual: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), 200, "Expected HTTP 200 status code");

        Map<String, Object> responseBody = response.getBody().as(Map.class);
        System.out.println("Response Body: " + responseBody);
        System.out.println("Token found: " + responseBody.get("token"));

        Assert.assertNotNull(responseBody.get("token"), "Token should not be null");
        System.out.println("✅ Validation passed: HTTP 200 with tokenId");
        System.out.println("==========================================");
    }

    @Test(description = "Test invalid authentication with incorrect credentials", priority = 2)
    public void testInvalidAuthentication() {
        System.out.println("=== TEST: Invalid Authentication ===");
        System.out.println("Testing authentication with invalid credentials");
        System.out.println("Request Body: " + invalidCredentials);

        // Make the API request
        response = request.body(invalidCredentials).post(AUTH_ENDPOINT);

        // Log response details
        System.out.println("=== REST ASSURED RESPONSE ===");
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Headers: " + response.getHeaders());
        System.out.println("Response Body: " + response.getBody().asString());
        System.out.println("Response Time: " + response.getTime() + " ms");
        System.out.println("=============================");

        // Assertions
        System.out.println("=== VALIDATION: HTTP 401 with error message ===");
        System.out.println("Expected Status: 401, Actual: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), 401, "Expected HTTP 401 status code");

        Map<String, Object> responseBody = response.getBody().as(Map.class);
        System.out.println("Response Body: " + responseBody);
        System.out.println("Error message found: " + responseBody.get("message"));

        Assert.assertNotNull(responseBody.get("message"), "Error message should not be null");
        System.out.println("✅ Validation passed: HTTP 401 with error message");
        System.out.println("===============================================");
    }

    @Test(description = "Test authentication with empty credentials", priority = 3)
    public void testEmptyCredentials() {
        System.out.println("=== TEST: Empty Credentials ===");
        System.out.println("Testing authentication with empty credentials");

        Map<String, String> emptyCredentials = new HashMap<>();
        emptyCredentials.put("user_name", "");
        emptyCredentials.put("password", "");

        System.out.println("Request Body: " + emptyCredentials);

        // Make the API request
        response = request.body(emptyCredentials).post(AUTH_ENDPOINT);

        // Log response details
        System.out.println("=== REST ASSURED RESPONSE ===");
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Headers: " + response.getHeaders());
        System.out.println("Response Body: " + response.getBody().asString());
        System.out.println("Response Time: " + response.getTime() + " ms");
        System.out.println("=============================");

        // Assertions - expecting 400 or 401 for empty credentials
        System.out.println("=== VALIDATION: HTTP 400/401 with error message ===");
        System.out.println("Expected Status: 400 or 401, Actual: " + response.getStatusCode());

        Assert.assertTrue(response.getStatusCode() == 400 || response.getStatusCode() == 401,
                "Expected HTTP 400 or 401 status code for empty credentials");

        Map<String, Object> responseBody = response.getBody().as(Map.class);
        System.out.println("Response Body: " + responseBody);

        // Check if there's an error message
        if (responseBody.get("message") != null) {
            System.out.println("Error message found: " + responseBody.get("message"));
        }

        System.out.println("✅ Validation passed: HTTP 400/401 for empty credentials");
        System.out.println("=====================================================");
    }

    @AfterMethod
    public void tearDownMethod() {
        System.out.println("--- TESTNG TEARDOWN: After Method ---");
        System.out.println("Cleaning up after test method");
        request = null;
        response = null;
        System.out.println("Cleanup completed");
        System.out.println("----------------------------------");
    }

    @AfterTest
    public void tearDownTest() {
        System.out.println("=== TESTNG TEARDOWN: After Test ===");
        System.out.println("Cleaning up test environment");
        RestAssured.reset();
        System.out.println("RestAssured reset completed");
        System.out.println("================================");
    }

    @AfterClass
    public void tearDownClass() {
        System.out.println("=== TESTNG TEARDOWN: After Class ===");
        System.out.println("Final cleanup after all tests");
        validCredentials = null;
        invalidCredentials = null;
        System.out.println("All resources cleaned up");
        System.out.println("TestNG Authentication Tests completed");
        System.out.println("====================================");
    }
}
