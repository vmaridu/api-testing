package org.vm.test.stepdefs;

import java.util.Map;

import org.testng.Assert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class AuthenticationTests {

    private static final String BASE_URI = "http://192.168.176.1:3000";
    private static final String AUTH_ENDPOINT = "/auth";
    private static final Map<String, String> VALID_CREDENTIALS = Map.of("user_name", "user", "password", "password");
    private static final Map<String, String> INVALID_CREDENTIALS = Map.of("user_name", "user", "password",
            "wrongpassword");

    private RequestSpecification request;
    private Response response;
    private Map<String, String> credentials;

    static {
        // Configure RestAssured logging globally
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.config = RestAssured.config().logConfig(
                RestAssured.config().getLogConfig().enableLoggingOfRequestAndResponseIfValidationFails());
    }

    @Given("The user have valid credentials")
    public void theUserHaveValidCredentials() {
        this.credentials = VALID_CREDENTIALS;
        System.out.println("=== SETUP: Valid Credentials ===");
        System.out.println("Using credentials: " + credentials);
        System.out.println("================================");
    }

    @Given("The user have invalid credentials")
    public void theUserHaveInvalidCredentials() {
        this.credentials = INVALID_CREDENTIALS;
        System.out.println("=== SETUP: Invalid Credentials ===");
        System.out.println("Using credentials: " + credentials);
        System.out.println("=================================");
    }

    @When("User passed user-name and password in api request")
    public void userPassedUserNameAndPasswordInApiRequest() {
        RestAssured.baseURI = BASE_URI;

        // Enhanced logging for Cucumber reports
        System.out.println("=== API REQUEST DETAILS ===");
        System.out.println("Base URI: " + BASE_URI);
        System.out.println("Endpoint: " + AUTH_ENDPOINT);
        System.out.println("HTTP Method: POST");
        System.out.println("Content-Type: application/json");
        System.out.println("Request Body (JSON): " + credentials);
        System.out.println("===========================");

        request = RestAssured.given()
                .log().all() // Log all request details
                .header("Content-Type", "application/json")
                .body(credentials);

        response = request.post(AUTH_ENDPOINT);

        // Enhanced response logging for Cucumber reports
        System.out.println("=== API RESPONSE DETAILS ===");
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Status Line: " + response.getStatusLine());
        System.out.println("Response Time: " + response.getTime() + " ms");
        System.out.println("Response Headers:");
        response.getHeaders().forEach(header -> System.out.println("  " + header.getName() + ": " + header.getValue()));
        System.out.println("Response Body (JSON): " + response.getBody().asString());
        System.out.println("Response Size: " + response.getBody().asString().length() + " characters");
        System.out.println("============================");
    }

    @Then("the response should have http 200 with json response containing token")
    public void theResponseShouldHaveHttp200WithJsonResponseContainingToken() {
        System.out.println("=== VALIDATION: HTTP 200 with token ===");
        System.out.println("Expected Status: 200, Actual: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), 200, "Expected HTTP 200 status code");

        Map<String, Object> responseBody = response.getBody().as(Map.class);
        System.out.println("Full Response Body (JSON): " + responseBody);
        System.out.println("Token found: " + responseBody.get("token"));
        System.out.println("Token type: "
                + (responseBody.get("token") != null ? responseBody.get("token").getClass().getSimpleName() : "null"));

        Assert.assertNotNull(responseBody.get("token"), "Token should not be null");
        System.out.println("✅ Validation PASSED: HTTP 200 with valid token");
        System.out.println("=============================================");
    }

    @Then("the response should have http 401 with json response containing error message")
    public void theResponseShouldHaveHttp401WithJsonResponseContainingErrorMessage() {
        System.out.println("=== VALIDATION: HTTP 401 with error message ===");
        System.out.println("Expected Status: 401, Actual: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), 401, "Expected HTTP 401 status code");

        Map<String, Object> responseBody = response.getBody().as(Map.class);
        System.out.println("Full Response Body (JSON): " + responseBody);
        System.out.println("Error message found: " + responseBody.get("message"));
        System.out.println("Error message type: "
                + (responseBody.get("message") != null ? responseBody.get("message").getClass().getSimpleName()
                        : "null"));

        Assert.assertNotNull(responseBody.get("message"), "Error message should not be null");
        System.out.println("✅ Validation PASSED: HTTP 401 with error message");
        System.out.println("================================================");
    }

}
