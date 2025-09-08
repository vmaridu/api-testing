# Postman Tutorial

## Table of Contents

1. [Postman Basics](#postman-basics)
2. [JavaScript for Postman](#javascript-for-postman)
3. [Variables and Data Types](#variables-and-data-types)
4. [Functions and Methods](#functions-and-methods)
5. [Objects and JSON Handling](#objects-and-json-handling)
6. [Postman-Specific JavaScript](#postman-specific-javascript)
7. [Automation Scripts](#automation-scripts)
8. [Testing and Assertions](#testing-and-assertions)

## Postman Basics

### Core Concepts:

- **Collections**: Organize related API requests
- **Folders**: Group requests within collections
- **Requests**: Individual API calls (GET, POST, PUT, DELETE)
- **Scripts**:
  - Pre-request Scripts: Run before sending request
  - Post-response Scripts: Run after receiving response
- **Variables**:
  - Global: Available across all requests
  - Environment: Available in specific environment
  - Local: Available only in current request
- **Environments**: Set of variables for different environments
- **Postman Console**: Debug and log output

## JavaScript for Postman

JavaScript is the scripting language used in Postman for automation, testing, and dynamic data handling.

### Why JavaScript in Postman?

- **Dynamic Requests**: Generate dynamic request data
- **Response Validation**: Test response data and status codes
- **Data Extraction**: Extract data from responses for subsequent requests
- **Environment Management**: Set and manage environment variables
- **Test Automation**: Write comprehensive test suites

## Variables and Data Types

### 1. Variable Declaration

```javascript
// Using let (block-scoped, can be reassigned)
let userName = "john_doe";
let userId = 123;

// Using const (block-scoped, cannot be reassigned)
const apiBaseUrl = "https://api.example.com";
const maxRetries = 3;

// Using var (function-scoped, avoid in modern JavaScript)
var oldStyle = "not recommended";
```

### 2. Data Types

```javascript
// String
let name = "John Doe";
let email = "john@example.com";
let template = `Hello ${name}`; // Template literal

// Number
let age = 30;
let price = 99.99;
let count = 0;

// Boolean
let isActive = true;
let isDeleted = false;

// Array
let users = ["john", "jane", "bob"];
let numbers = [1, 2, 3, 4, 5];
let mixed = ["text", 123, true];

// Object
let user = {
  id: 1,
  name: "John Doe",
  email: "john@example.com",
  active: true,
};

// Null and Undefined
let emptyValue = null;
let notDefined = undefined;
```

### 3. Postman Variables

```javascript
// Set variables
pm.environment.set("baseUrl", "https://api.example.com");
pm.globals.set("authToken", "abc123");
pm.variables.set("userId", 123);

// Get variables
let baseUrl = pm.environment.get("baseUrl");
let token = pm.globals.get("authToken");
let id = pm.variables.get("userId");

// Unset variables
pm.environment.unset("tempVar");
pm.globals.unset("oldToken");
```

## Functions and Methods

### 1. Function Declaration

```javascript
// Function declaration
function calculateAge(birthYear) {
  return new Date().getFullYear() - birthYear;
}

// Function expression
const greetUser = function (name) {
  return `Hello, ${name}!`;
};

// Arrow function (ES6)
const addNumbers = (a, b) => a + b;

// Arrow function with block
const processUser = (user) => {
  user.processed = true;
  user.timestamp = new Date().toISOString();
  return user;
};
```

### 2. Built-in Methods

```javascript
// String methods
let name = "John Doe";
console.log(name.length); // 8
console.log(name.toUpperCase()); // "JOHN DOE"
console.log(name.toLowerCase()); // "john doe"
console.log(name.split(" ")); // ["John", "Doe"]
console.log(name.includes("John")); // true

// Array methods
let users = ["john", "jane", "bob"];
console.log(users.length); // 3
console.log(users.push("alice")); // 4 (returns new length)
console.log(users.pop()); // "alice" (removes and returns last element)
console.log(users.join(", ")); // "john, jane, bob"

// Array iteration
users.forEach((user) => console.log(user));
let upperUsers = users.map((user) => user.toUpperCase());
let filteredUsers = users.filter((user) => user.startsWith("j"));

// Number methods
let num = 123.456;
console.log(num.toFixed(2)); // "123.46"
console.log(Math.round(num)); // 123
console.log(Math.random()); // Random number 0-1
```

### 3. Date and Time

```javascript
// Current date/time
let now = new Date();
console.log(now.toISOString()); // "2024-01-15T10:30:00.000Z"
console.log(now.getTime()); // Timestamp in milliseconds

// Specific date
let specificDate = new Date("2024-01-15");
let timestamp = new Date(1642248000000);

// Date formatting
let formatted = now.toLocaleDateString(); // "1/15/2024"
let timeString = now.toLocaleTimeString(); // "10:30:00 AM"

// Date arithmetic
let tomorrow = new Date(now.getTime() + 24 * 60 * 60 * 1000);
let yesterday = new Date(now.getTime() - 24 * 60 * 60 * 1000);
```

## Objects and JSON Handling

### 1. Object Creation and Manipulation

```javascript
// Object creation
let user = {
  id: 1,
  name: "John Doe",
  email: "john@example.com",
  profile: {
    age: 30,
    department: "Engineering",
  },
  isActive: true,
};

// Accessing properties
console.log(user.name); // "John Doe"
console.log(user["email"]); // "john@example.com"
console.log(user.profile.age); // 30

// Modifying properties
user.name = "John Updated";
user["email"] = "john.updated@example.com";
user.profile.department = "Product";

// Adding new properties
user.role = "admin";
user.lastLogin = new Date().toISOString();

// Deleting properties
delete user.isActive;
```

### 2. JSON Handling

```javascript
// Converting object to JSON string
let userObject = {
  id: 1,
  name: "John Doe",
  email: "john@example.com",
};

let jsonString = JSON.stringify(userObject);
console.log(jsonString); // '{"id":1,"name":"John Doe","email":"john@example.com"}'

// Converting JSON string to object
let jsonData = '{"id":1,"name":"John Doe","email":"john@example.com"}';
let parsedObject = JSON.parse(jsonData);
console.log(parsedObject.name); // "John Doe"

// Pretty printing JSON
let prettyJson = JSON.stringify(userObject, null, 2);
console.log(prettyJson);
```

### 3. Object Methods

```javascript
// Object.keys() - Get all property names
let user = { id: 1, name: "John", email: "john@example.com" };
let keys = Object.keys(user); // ["id", "name", "email"]

// Object.values() - Get all property values
let values = Object.values(user); // [1, "John", "john@example.com"]

// Object.entries() - Get key-value pairs
let entries = Object.entries(user); // [["id", 1], ["name", "John"], ["email", "john@example.com"]]

// Object.assign() - Copy properties
let newUser = Object.assign({}, user, { role: "admin" });

// Spread operator (ES6) - Modern way to copy/merge
let updatedUser = {
  ...user,
  role: "admin",
  lastLogin: new Date().toISOString(),
};
```

## Postman-Specific JavaScript

### 1. pm Object

```javascript
// pm object is the main Postman object
console.log("Postman version:", pm.info.version);

// Request information
console.log("Request URL:", pm.request.url.toString());
console.log("Request method:", pm.request.method);
console.log("Request headers:", pm.request.headers);

// Response information
console.log("Response status:", pm.response.code);
console.log("Response time:", pm.response.responseTime);
console.log("Response headers:", pm.response.headers);
```

### 2. Request and Response Handling

```javascript
// Pre-request script - Modify request before sending
pm.request.headers.add({
  key: "Authorization",
  value: "Bearer " + pm.environment.get("authToken"),
});

// Set dynamic request body
let requestBody = {
  name: "Test User",
  email: "test@example.com",
  timestamp: new Date().toISOString(),
};
pm.request.body.raw = JSON.stringify(requestBody);

// Post-response script - Handle response
let responseJson = pm.response.json();
console.log("Response data:", responseJson);

// Extract data from response
if (responseJson.token) {
  pm.environment.set("authToken", responseJson.token);
}

if (responseJson.user && responseJson.user.id) {
  pm.environment.set("userId", responseJson.user.id);
}
```

### 3. Environment and Global Variables

```javascript
// Environment variables
pm.environment.set("baseUrl", "https://api.example.com");
pm.environment.set("apiVersion", "v1");
pm.environment.set("timeout", 30000);

// Global variables
pm.globals.set("sharedToken", "global-token-123");
pm.globals.set("maxRetries", 3);

// Local variables (request-scoped)
pm.variables.set("requestId", Math.random().toString(36).substr(2, 9));

// Getting variables with defaults
let baseUrl = pm.environment.get("baseUrl") || "https://localhost:3000";
let timeout = parseInt(pm.globals.get("timeout")) || 5000;

// Check if variable exists
if (pm.environment.has("authToken")) {
  console.log("Auth token is set");
} else {
  console.log("Auth token is missing");
}
```

## Automation Scripts

### 1. Pre-request Scripts

```javascript
// Generate dynamic data
let randomId = Math.floor(Math.random() * 1000) + 1;
let timestamp = new Date().toISOString();
let uniqueEmail = `test${Date.now()}@example.com`;

// Set variables for this request
pm.variables.set("randomId", randomId);
pm.variables.set("timestamp", timestamp);
pm.variables.set("uniqueEmail", uniqueEmail);

// Add authentication header
let token = pm.environment.get("authToken");
if (token) {
  pm.request.headers.add({
    key: "Authorization",
    value: `Bearer ${token}`,
  });
}

// Set request body with dynamic data
let requestData = {
  name: `Test User ${randomId}`,
  email: uniqueEmail,
  age: Math.floor(Math.random() * 50) + 18,
  department: "Testing",
  createdAt: timestamp,
};

pm.request.body.raw = JSON.stringify(requestData);
```

### 2. Post-response Scripts

```javascript
// Log response information
console.log("Status Code:", pm.response.code);
console.log("Response Time:", pm.response.responseTime + "ms");
console.log("Response Size:", pm.response.responseSize + " bytes");

// Parse response
let responseJson = pm.response.json();

// Extract and store data
if (pm.response.code === 200 || pm.response.code === 201) {
  if (responseJson.token) {
    pm.environment.set("authToken", responseJson.token);
    console.log("Auth token updated");
  }

  if (responseJson.user && responseJson.user.id) {
    pm.environment.set("userId", responseJson.user.id);
    console.log("User ID stored:", responseJson.user.id);
  }

  if (responseJson.id) {
    pm.environment.set("lastCreatedId", responseJson.id);
    console.log("Created resource ID:", responseJson.id);
  }
}

// Handle pagination
if (responseJson.pagination) {
  pm.environment.set("nextPage", responseJson.pagination.nextPage);
  pm.environment.set("totalPages", responseJson.pagination.totalPages);
}
```

## Testing and Assertions

### 1. Basic Assertions

```javascript
// Status code assertions
pm.test("Status code is 200", function () {
  pm.response.to.have.status(200);
});

pm.test("Status code is successful", function () {
  pm.expect(pm.response.code).to.be.oneOf([200, 201, 202]);
});

// Response time assertions
pm.test("Response time is less than 1000ms", function () {
  pm.expect(pm.response.responseTime).to.be.below(1000);
});

// Header assertions
pm.test("Content-Type is application/json", function () {
  pm.expect(pm.response.headers.get("Content-Type")).to.include(
    "application/json"
  );
});
```

### 2. JSON Response Testing

```javascript
// Parse response JSON
let responseJson = pm.response.json();

// Basic property assertions
pm.test("Response has required fields", function () {
  pm.expect(responseJson).to.have.property("id");
  pm.expect(responseJson).to.have.property("name");
  pm.expect(responseJson).to.have.property("email");
});

// Data type assertions
pm.test("ID is a number", function () {
  pm.expect(responseJson.id).to.be.a("number");
});

pm.test("Name is a string", function () {
  pm.expect(responseJson.name).to.be.a("string");
});

// Value assertions
pm.test("Name is not empty", function () {
  pm.expect(responseJson.name).to.not.be.empty;
});

pm.test("Email format is valid", function () {
  pm.expect(responseJson.email).to.match(/^[^\s@]+@[^\s@]+\.[^\s@]+$/);
});
```

### 3. Array Response Testing

```javascript
// Test array response
let users = pm.response.json();

pm.test("Response is an array", function () {
  pm.expect(users).to.be.an("array");
});

pm.test("Array is not empty", function () {
  pm.expect(users).to.not.be.empty;
});

pm.test("Each user has required properties", function () {
  users.forEach((user) => {
    pm.expect(user).to.have.property("id");
    pm.expect(user).to.have.property("name");
    pm.expect(user).to.have.property("email");
  });
});
```

---

## Summary

JavaScript in Postman enables powerful API testing automation:

- **Variables**: Dynamic data handling with environment, global, and local variables
- **Functions**: Reusable code for common operations
- **Objects**: JSON data manipulation and validation
- **Automation**: Pre-request and post-response scripts for dynamic behavior
- **Testing**: Comprehensive assertions and validation

Key takeaways:

- JavaScript is essential for Postman automation
- Variables enable dynamic request/response handling
- Functions provide code reusability
- Objects and JSON are core to API testing
- Assertions validate response data and behavior

Mastering JavaScript basics is crucial for effective Postman API testing automation.
