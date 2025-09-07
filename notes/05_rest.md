# REST API Tutorial

## Table of Contents

1. [REST Fundamentals](#rest-fundamentals)
2. [API Endpoints Overview](#api-endpoints-overview)
3. [Authentication API](#authentication-api)
4. [User Management API](#user-management-api)
5. [Request and Response Examples](#request-and-response-examples)
6. [Test Cases](#test-cases)
7. [REST Testing Tools](#rest-testing-tools)
8. [Best Practices](#best-practices)

## REST Fundamentals

**REST (Representational State Transfer)** is an architectural style for designing networked applications. It uses HTTP methods to perform operations on resources identified by URLs.

### Key REST Principles:

- **Stateless**: Each request contains all necessary information
- **Client-Server**: Separation of concerns between client and server
- **Cacheable**: Responses can be cached for performance
- **Uniform Interface**: Consistent interaction patterns
- **Resource-based**: Everything is a resource with a unique identifier
- **HTTP Methods**: Use standard HTTP verbs (GET, POST, PUT, DELETE, PATCH)

### REST vs SOAP:

| Aspect             | REST                        | SOAP                     |
| ------------------ | --------------------------- | ------------------------ |
| **Architecture**   | Architectural style         | Protocol                 |
| **Data Format**    | JSON, XML, HTML, plain text | XML only                 |
| **Transport**      | HTTP only                   | HTTP, SMTP, TCP, etc.    |
| **Standards**      | No official standards       | W3C standards            |
| **Caching**        | HTTP caching supported      | Not supported            |
| **Performance**    | Faster (lighter payload)    | Slower (XML overhead)    |
| **Error Handling** | HTTP status codes           | Built-in fault mechanism |
| **Learning Curve** | Easier                      | Steeper                  |

## API Endpoints Overview

Based on the provided configuration, this REST API includes:

### Base Configuration:

- **Host**: `localhost:3000` (Mockoon server)
- **Content-Type**: `application/json`
- **CORS**: Enabled with wildcard origin
- **Authentication**: Bearer token (JWT)

### Available Endpoints:

1. **Authentication Endpoint**

   - `POST /auth` - User authentication

2. **User Management Endpoint**
   - `GET /users` - List all users
   - `POST /users` - Create new user
   - `GET /users/{id}` - Get specific user
   - `PUT /users/{id}` - Update user
   - `DELETE /users/{id}` - Delete user

## Authentication API

### Endpoint: `POST /auth`

**Purpose**: Authenticate user and receive JWT token

#### Request Headers:

```http
Content-Type: application/json
```

#### Request Body:

```json
{
  "user_name": "user",
  "password": "password"
}
```

#### Success Response (200 OK):

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30"
}
```

#### Error Response (401 Unauthorized):

```http
HTTP/1.1 401 Unauthorized
Content-Type: application/json

{
  "message": "user_name and password incorrect"
}
```

## User Management API

### Endpoint: `GET /users`

**Purpose**: Retrieve all users

#### Request Headers:

```http
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30
```

#### Success Response (200 OK):

```http
HTTP/1.1 200 OK
Content-Type: application/json

[]
```

#### Error Response (401 Unauthorized):

```http
HTTP/1.1 401 Unauthorized
Content-Type: application/json

{
  "message": "Invalid Bearer Token"
}
```

### Endpoint: `POST /users`

**Purpose**: Create a new user

#### Request Headers:

```http
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30
```

#### Request Body:

```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "age": 30,
  "department": "Engineering"
}
```

#### Success Response (201 Created):

```http
HTTP/1.1 201 Created
Content-Type: application/json
Location: /users/1

{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "age": 30,
  "department": "Engineering",
  "createdAt": "2024-01-15T10:30:00Z"
}
```

### Endpoint: `GET /users/{id}`

**Purpose**: Retrieve a specific user by ID

#### Request Headers:

```http
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30
```

#### Success Response (200 OK):

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "age": 30,
  "department": "Engineering",
  "createdAt": "2024-01-15T10:30:00Z"
}
```

#### Error Response (404 Not Found):

```http
HTTP/1.1 404 Not Found
Content-Type: application/json

{
  "message": "User not found"
}
```

### Endpoint: `PUT /users/{id}`

**Purpose**: Update an entire user record

#### Request Headers:

```http
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30
```

#### Request Body:

```json
{
  "id": 1,
  "name": "John Updated",
  "email": "john.updated@example.com",
  "age": 31,
  "department": "Product Management"
}
```

#### Success Response (200 OK):

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": 1,
  "name": "John Updated",
  "email": "john.updated@example.com",
  "age": 31,
  "department": "Product Management",
  "updatedAt": "2024-01-15T11:00:00Z"
}
```

### Endpoint: `PATCH /users/{id}`

**Purpose**: Partially update a user record

#### Request Headers:

```http
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30
```

#### Request Body:

```json
{
  "age": 32
}
```

#### Success Response (200 OK):

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": 1,
  "name": "John Updated",
  "email": "john.updated@example.com",
  "age": 32,
  "department": "Product Management",
  "updatedAt": "2024-01-15T11:15:00Z"
}
```

### Endpoint: `DELETE /users/{id}`

**Purpose**: Delete a user record

#### Request Headers:

```http
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30
```

#### Success Response (204 No Content):

```http
HTTP/1.1 204 No Content
```

## Request and Response Examples

### Complete Authentication Flow

#### Step 1: Authentication Request

```http
POST http://localhost:3000/auth HTTP/1.1
Content-Type: application/json

{
  "user_name": "user",
  "password": "password"
}
```

#### Step 2: Authentication Response

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30"
}
```

#### Step 3: Create User Request

```http
POST http://localhost:3000/users HTTP/1.1
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30

{
  "name": "Jane Smith",
  "email": "jane.smith@example.com",
  "age": 28,
  "department": "Marketing"
}
```

#### Step 4: Create User Response

```http
HTTP/1.1 201 Created
Content-Type: application/json
Location: /users/1

{
  "id": 1,
  "name": "Jane Smith",
  "email": "jane.smith@example.com",
  "age": 28,
  "department": "Marketing",
  "createdAt": "2024-01-15T10:30:00Z"
}
```

## Test Cases

### Authentication Test Cases

#### 🔐 Test Case 1: Valid Authentication

**Endpoint**: `POST /auth`

**Request**:

```http
POST /auth HTTP/1.1
Content-Type: application/json

{
  "user_name": "user",
  "password": "password"
}
```

**Expected Response**:

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30"
}
```

**Assertions** ✅:

- ✅ Response status is 200
- ✅ Response contains token field
- ✅ Token is valid JWT format

#### ❌ Test Case 2: Invalid Credentials

**Endpoint**: `POST /auth`

**Request**:

```http
POST /auth HTTP/1.1
Content-Type: application/json

{
  "user_name": "invalid_user",
  "password": "wrong_password"
}
```

**Expected Response**:

```http
HTTP/1.1 401 Unauthorized
Content-Type: application/json

{
  "message": "user_name and password incorrect"
}
```

**Assertions** ✅:

- ✅ Response status is 401
- ✅ Response contains error message
- ✅ No token in response

#### ⚠️ Test Case 3: Missing Credentials

**Endpoint**: `POST /auth`

**Request**:

```http
POST /auth HTTP/1.1
Content-Type: application/json

{
  "user_name": "user"
}
```

**Expected Response**:

```http
HTTP/1.1 400 Bad Request
Content-Type: application/json

{
  "message": "Missing required field: password"
}
```

**Assertions** ✅:

- ✅ Response status is 400
- ✅ Response contains validation error

### User Management Test Cases

#### 👤 Test Case 4: Create User Success

**Endpoint**: `POST /users`

**Request**:

```http
POST /users HTTP/1.1
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30

{
  "name": "Test User",
  "email": "test@example.com",
  "age": 25,
  "department": "Testing"
}
```

**Expected Response**:

```http
HTTP/1.1 201 Created
Content-Type: application/json
Location: /users/1

{
  "id": 1,
  "name": "Test User",
  "email": "test@example.com",
  "age": 25,
  "department": "Testing"
}
```

**Assertions** ✅:

- ✅ Response status is 201
- ✅ Location header contains user ID
- ✅ Response body contains created user data
- ✅ User ID is generated

#### 🚫 Test Case 5: Create User Without Authentication

**Endpoint**: `POST /users`

**Request**:

```http
POST /users HTTP/1.1
Content-Type: application/json

{
  "name": "Test User",
  "email": "test@example.com",
  "age": 25,
  "department": "Testing"
}
```

**Expected Response**:

```http
HTTP/1.1 401 Unauthorized
Content-Type: application/json

{
  "message": "Invalid Bearer Token"
}
```

**Assertions** ✅:

- ✅ Response status is 401
- ✅ Response contains authentication error

#### 🔍 Test Case 6: Get User Success

**Endpoint**: `GET /users/1`

**Request**:

```http
GET /users/1 HTTP/1.1
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30
```

**Expected Response**:

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": 1,
  "name": "Test User",
  "email": "test@example.com",
  "age": 25,
  "department": "Testing"
}
```

**Assertions** ✅:

- ✅ Response status is 200
- ✅ Response contains user data
- ✅ User ID matches requested ID

#### 🔍❌ Test Case 7: Get User Not Found

**Endpoint**: `GET /users/999`

**Request**:

```http
GET /users/999 HTTP/1.1
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30
```

**Expected Response**:

```http
HTTP/1.1 404 Not Found
Content-Type: application/json

{
  "message": "User not found"
}
```

**Assertions** ✅:

- ✅ Response status is 404
- ✅ Response contains not found message

#### ✏️ Test Case 8: Update User Success

**Endpoint**: `PUT /users/1`

**Request**:

```http
PUT /users/1 HTTP/1.1
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30

{
  "id": 1,
  "name": "Updated User",
  "email": "updated@example.com",
  "age": 26,
  "department": "Updated Department"
}
```

**Expected Response**:

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": 1,
  "name": "Updated User",
  "email": "updated@example.com",
  "age": 26,
  "department": "Updated Department"
}
```

**Assertions** ✅:

- ✅ Response status is 200
- ✅ Response contains updated user data
- ✅ All fields are updated correctly

#### 🗑️ Test Case 9: Delete User Success

**Endpoint**: `DELETE /users/1`

**Request**:

```http
DELETE /users/1 HTTP/1.1
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30
```

**Expected Response**:

```http
HTTP/1.1 204 No Content
```

**Assertions** ✅:

- ✅ Response status is 204
- ✅ Response body is empty

### Integration Test Cases

#### 🔄 Test Case 10: Complete User Lifecycle

**Description**: Test complete user creation, retrieval, update, and deletion

**Step 1: 🔐 Authenticate**

```http
POST /auth HTTP/1.1
Content-Type: application/json

{
  "user_name": "user",
  "password": "password"
}
```

**Expected**: `200 OK` with JWT token

---

**Step 2: 👤 Create User**

```http
POST /users HTTP/1.1
Content-Type: application/json
Authorization: Bearer {{token}}

{
  "name": "Lifecycle Test User",
  "email": "lifecycle@example.com",
  "age": 30,
  "department": "Testing"
}
```

**Expected**: `201 Created` with user data

---

**Step 3: 🔍 Get User**

```http
GET /users/{{userId}} HTTP/1.1
Authorization: Bearer {{token}}
```

**Expected**: `200 OK` with user data

---

**Step 4: ✏️ Update User**

```http
PUT /users/{{userId}} HTTP/1.1
Content-Type: application/json
Authorization: Bearer {{token}}

{
  "id": "{{userId}}",
  "name": "Updated Lifecycle User",
  "email": "updated.lifecycle@example.com",
  "age": 31,
  "department": "Updated Testing"
}
```

**Expected**: `200 OK` with updated user data

---

**Step 5: 🗑️ Delete User**

```http
DELETE /users/{{userId}} HTTP/1.1
Authorization: Bearer {{token}}
```

**Expected**: `204 No Content`

**Overall Assertions** ✅:

- ✅ All steps execute successfully
- ✅ User data persists between operations
- ✅ Authentication works throughout lifecycle
- ✅ User is completely removed after deletion

## REST Testing Tools

### 1. Postman

**Comprehensive API testing platform** with REST support.

#### Features:

- **Request Builder**: Visual request creation
- **Collections**: Organize API requests
- **Environment Variables**: Dynamic data management
- **Tests**: JavaScript-based assertions
- **Mock Servers**: API mocking capabilities
- **Documentation**: Auto-generated API docs

#### Postman Test Example:

```javascript
// Postman Test Script
pm.test("Status code is 200", function () {
  pm.response.to.have.status(200);
});

pm.test("Response has token", function () {
  var jsonData = pm.response.json();
  pm.expect(jsonData.token).to.exist;
});

pm.test("Token is valid JWT", function () {
  var jsonData = pm.response.json();
  pm.expect(jsonData.token).to.match(
    /^[A-Za-z0-9-_]+\.[A-Za-z0-9-_]+\.[A-Za-z0-9-_]+$/
  );
});
```

### 2. cURL

**Command-line tool** for REST API testing.

#### cURL Examples:

```bash
# Authentication
curl -X POST http://localhost:3000/auth \
  -H "Content-Type: application/json" \
  -d '{"user_name": "user", "password": "password"}'

# Create User
curl -X POST http://localhost:3000/users \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30" \
  -d '{"name": "Test User", "email": "test@example.com", "age": 25, "department": "Testing"}'

# Get User
curl -X GET http://localhost:3000/users/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30"
```

### 3. REST Client Extensions

#### VS Code REST Client:

```http
### Authentication
POST http://localhost:3000/auth
Content-Type: application/json

{
  "user_name": "user",
  "password": "password"
}

### Create User
POST http://localhost:3000/users
Content-Type: application/json
Authorization: Bearer {{authToken}}

{
  "name": "Test User",
  "email": "test@example.com",
  "age": 25,
  "department": "Testing"
}
```

### 4. Programming Language Libraries

#### JavaScript (Axios):

```javascript
// Authentication
const authResponse = await axios.post("http://localhost:3000/auth", {
  user_name: "user",
  password: "password",
});

const token = authResponse.data.token;

// Create User
const userResponse = await axios.post(
  "http://localhost:3000/users",
  {
    name: "Test User",
    email: "test@example.com",
    age: 25,
    department: "Testing",
  },
  {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }
);
```

#### Python (Requests):

```python
import requests

# Authentication
auth_response = requests.post('http://localhost:3000/auth', json={
    'user_name': 'user',
    'password': 'password'
})

token = auth_response.json()['token']

# Create User
user_response = requests.post('http://localhost:3000/users', json={
    'name': 'Test User',
    'email': 'test@example.com',
    'age': 25,
    'department': 'Testing'
}, headers={
    'Authorization': f'Bearer {token}'
})
```

## Best Practices

### 1. HTTP Status Codes

```http
# Use appropriate status codes
GET /users/123     → 200 OK (found) or 404 Not Found
POST /users        → 201 Created
PUT /users/123     → 200 OK (updated) or 404 Not Found
DELETE /users/123  → 204 No Content
PATCH /users/123   → 200 OK (updated)
```

### 2. Request/Response Headers

```http
# Standard headers
Content-Type: application/json
Authorization: Bearer <token>
Accept: application/json
Location: /users/123 (for created resources)
```

### 3. Error Handling

```json
{
  "error": {
    "code": "VALIDATION_ERROR",
    "message": "Invalid input data",
    "details": [
      {
        "field": "email",
        "message": "Invalid email format"
      }
    ],
    "timestamp": "2024-01-15T10:30:00Z",
    "requestId": "req-123-456"
  }
}
```

### 4. Authentication

```http
# Bearer token authentication
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

# Handle token expiration
HTTP/1.1 401 Unauthorized
WWW-Authenticate: Bearer error="invalid_token", error_description="Token expired"
```

### 5. Resource Design

```http
# RESTful resource URLs
GET    /users           # List users
GET    /users/123       # Get user
POST   /users           # Create user
PUT    /users/123       # Update user
DELETE /users/123       # Delete user

# Nested resources
GET    /users/123/orders        # User's orders
POST   /users/123/orders        # Create order for user
```

---

## Summary

REST APIs provide a simple, flexible approach to building web services:

- **Resource-based**: Everything is a resource with a unique URL
- **HTTP Methods**: Use standard verbs for operations
- **Stateless**: Each request is independent
- **JSON/XML**: Flexible data formats
- **HTTP Status Codes**: Standard error handling
- **Authentication**: Bearer tokens, API keys, OAuth

Key takeaways:

- REST uses HTTP methods to perform operations on resources
- Authentication is typically handled via Bearer tokens
- Proper HTTP status codes provide clear feedback
- Testing tools like Postman and cURL support comprehensive REST testing
- Best practices include consistent error handling and resource design

Understanding REST fundamentals is essential for modern API development and testing.
