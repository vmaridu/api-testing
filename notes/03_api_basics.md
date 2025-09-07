# API Basics Tutorial

## Table of Contents

1. [API Fundamentals](#api-fundamentals)
2. [HTTP Status Codes](#http-status-codes)
3. [HTTP Methods (Verbs)](#http-methods-verbs)
4. [API vs Endpoints](#api-vs-endpoints)
5. [API Authentication Methods](#api-authentication-methods)
6. [Request and Response Structure](#request-and-response-structure)
7. [Best Practices](#best-practices)
8. [Common API Patterns](#common-api-patterns)

## API Fundamentals

**API (Application Programming Interface)** is a set of protocols, routines, and tools for building software applications. It defines how different software components should interact with each other.

### Key Concepts:

- **Interface**: Contract between client and server
- **Stateless**: Each request contains all necessary information
- **Client-Server**: Separation of concerns
- **Cacheable**: Responses can be cached for performance
- **Uniform Interface**: Consistent interaction patterns

### Why APIs Matter:

- **Integration**: Connect different systems and services
- **Scalability**: Build modular, distributed applications
- **Reusability**: Share functionality across applications
- **Platform Independence**: Work across different technologies

## HTTP Status Codes

HTTP status codes are three-digit numbers that indicate the result of an HTTP request. They provide information about whether the request was successful, failed, or needs additional action.

### 1xx Informational Responses

| Code | Name                | Description                                  | Example Use Case        |
| ---- | ------------------- | -------------------------------------------- | ----------------------- |
| 100  | Continue            | Request received, continue with request body | Large file uploads      |
| 101  | Switching Protocols | Server switching protocols as requested      | WebSocket upgrades      |
| 102  | Processing          | Server received and is processing request    | Long-running operations |

### 2xx Success Responses

| Code | Name            | Description                             | Example Use Case         |
| ---- | --------------- | --------------------------------------- | ------------------------ |
| 200  | OK              | Request successful                      | GET, PUT, PATCH requests |
| 201  | Created         | Resource created successfully           | POST requests            |
| 202  | Accepted        | Request accepted for processing         | Async operations         |
| 204  | No Content      | Request successful, no content returned | DELETE requests          |
| 206  | Partial Content | Partial content returned                | Range requests           |

#### Examples:

```http
# 200 OK - Successful GET request
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": 123,
  "name": "John Doe",
  "email": "john@example.com"
}
```

```http
# 201 Created - Successful POST request
HTTP/1.1 201 Created
Content-Type: application/json
Location: /api/users/123

{
  "id": 123,
  "name": "John Doe",
  "email": "john@example.com",
  "createdAt": "2024-01-15T10:30:00Z"
}
```

```http
# 204 No Content - Successful DELETE request
HTTP/1.1 204 No Content
```

### 3xx Redirection Responses

| Code | Name               | Description                                 | Example Use Case    |
| ---- | ------------------ | ------------------------------------------- | ------------------- |
| 301  | Moved Permanently  | Resource permanently moved                  | URL changes         |
| 302  | Found              | Resource temporarily moved                  | Temporary redirects |
| 304  | Not Modified       | Resource not modified since last request    | Caching             |
| 307  | Temporary Redirect | Request should be repeated with same method | Temporary redirects |
| 308  | Permanent Redirect | Request should be repeated with same method | Permanent redirects |

#### Examples:

```http
# 301 Moved Permanently
HTTP/1.1 301 Moved Permanently
Location: https://new-api.example.com/users

# 304 Not Modified
HTTP/1.1 304 Not Modified
ETag: "abc123"
Last-Modified: Wed, 15 Jan 2024 10:30:00 GMT
```

### 4xx Client Error Responses

| Code | Name                 | Description                            | Example Use Case          |
| ---- | -------------------- | -------------------------------------- | ------------------------- |
| 400  | Bad Request          | Invalid request syntax                 | Malformed JSON            |
| 401  | Unauthorized         | Authentication required                | Missing/invalid token     |
| 403  | Forbidden            | Server refuses to authorize            | Insufficient permissions  |
| 404  | Not Found            | Resource not found                     | Invalid endpoint          |
| 405  | Method Not Allowed   | HTTP method not allowed                | POST to GET-only endpoint |
| 409  | Conflict             | Request conflicts with current state   | Duplicate resource        |
| 422  | Unprocessable Entity | Well-formed but semantically incorrect | Validation errors         |
| 429  | Too Many Requests    | Rate limit exceeded                    | API throttling            |

#### Examples:

```http
# 400 Bad Request
HTTP/1.1 400 Bad Request
Content-Type: application/json

{
  "error": {
    "code": "INVALID_REQUEST",
    "message": "Request body is malformed",
    "details": "Expected JSON object"
  }
}
```

```http
# 401 Unauthorized
HTTP/1.1 401 Unauthorized
Content-Type: application/json
WWW-Authenticate: Bearer

{
  "error": {
    "code": "UNAUTHORIZED",
    "message": "Authentication required"
  }
}
```

```http
# 404 Not Found
HTTP/1.1 404 Not Found
Content-Type: application/json

{
  "error": {
    "code": "NOT_FOUND",
    "message": "User with ID 999 not found"
  }
}
```

```http
# 422 Unprocessable Entity
HTTP/1.1 422 Unprocessable Entity
Content-Type: application/json

{
  "error": {
    "code": "VALIDATION_ERROR",
    "message": "Invalid input data",
    "details": [
      {
        "field": "email",
        "message": "Invalid email format"
      },
      {
        "field": "age",
        "message": "Age must be between 0 and 150"
      }
    ]
  }
}
```

### 5xx Server Error Responses

| Code | Name                  | Description                           | Example Use Case          |
| ---- | --------------------- | ------------------------------------- | ------------------------- |
| 500  | Internal Server Error | Generic server error                  | Unexpected server failure |
| 501  | Not Implemented       | Server doesn't support functionality  | Unsupported features      |
| 502  | Bad Gateway           | Invalid response from upstream server | Proxy/gateway issues      |
| 503  | Service Unavailable   | Server temporarily unavailable        | Maintenance mode          |
| 504  | Gateway Timeout       | Upstream server timeout               | Network issues            |
| 507  | Insufficient Storage  | Server cannot store representation    | Storage full              |

#### Examples:

```http
# 500 Internal Server Error
HTTP/1.1 500 Internal Server Error
Content-Type: application/json

{
  "error": {
    "code": "INTERNAL_ERROR",
    "message": "An unexpected error occurred",
    "requestId": "req-123-456"
  }
}
```

```http
# 503 Service Unavailable
HTTP/1.1 503 Service Unavailable
Content-Type: application/json
Retry-After: 300

{
  "error": {
    "code": "SERVICE_UNAVAILABLE",
    "message": "Service temporarily unavailable for maintenance"
  }
}
```

## HTTP Methods (Verbs)

HTTP methods define the action to be performed on a resource. They indicate the intended operation for a given resource.

### Core HTTP Methods

#### GET

- **Purpose**: Retrieve data from server
- **Idempotent**: Yes
- **Safe**: Yes (no side effects)
- **Request Body**: Usually none
- **Response**: Data representation

```http
GET /api/users/123 HTTP/1.1
Host: api.example.com
Authorization: Bearer your-token

# Response
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": 123,
  "name": "John Doe",
  "email": "john@example.com"
}
```

#### POST

- **Purpose**: Create new resource or submit data
- **Idempotent**: No
- **Safe**: No
- **Request Body**: Yes (data to create)
- **Response**: Created resource or result

```http
POST /api/users HTTP/1.1
Host: api.example.com
Content-Type: application/json
Authorization: Bearer your-token

{
  "name": "Jane Smith",
  "email": "jane@example.com",
  "age": 28
}

# Response
HTTP/1.1 201 Created
Location: /api/users/124
Content-Type: application/json

{
  "id": 124,
  "name": "Jane Smith",
  "email": "jane@example.com",
  "age": 28,
  "createdAt": "2024-01-15T10:30:00Z"
}
```

#### PUT

- **Purpose**: Update entire resource (replace)
- **Idempotent**: Yes
- **Safe**: No
- **Request Body**: Yes (complete resource)
- **Response**: Updated resource

```http
PUT /api/users/123 HTTP/1.1
Host: api.example.com
Content-Type: application/json
Authorization: Bearer your-token

{
  "id": 123,
  "name": "John Updated",
  "email": "john.updated@example.com",
  "age": 31
}

# Response
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": 123,
  "name": "John Updated",
  "email": "john.updated@example.com",
  "age": 31,
  "updatedAt": "2024-01-15T11:00:00Z"
}
```

#### PATCH

- **Purpose**: Partial update of resource
- **Idempotent**: Yes
- **Safe**: No
- **Request Body**: Yes (partial data)
- **Response**: Updated resource

```http
PATCH /api/users/123 HTTP/1.1
Host: api.example.com
Content-Type: application/json
Authorization: Bearer your-token

{
  "age": 32
}

# Response
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": 123,
  "name": "John Updated",
  "email": "john.updated@example.com",
  "age": 32,
  "updatedAt": "2024-01-15T11:15:00Z"
}
```

#### DELETE

- **Purpose**: Remove resource
- **Idempotent**: Yes
- **Safe**: No
- **Request Body**: Usually none
- **Response**: Success confirmation or deleted resource

```http
DELETE /api/users/123 HTTP/1.1
Host: api.example.com
Authorization: Bearer your-token

# Response
HTTP/1.1 204 No Content
```

### Additional HTTP Methods

#### HEAD

- **Purpose**: Get headers without body
- **Use Case**: Check if resource exists, get metadata

```http
HEAD /api/users/123 HTTP/1.1
Host: api.example.com

# Response
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 156
Last-Modified: Wed, 15 Jan 2024 10:30:00 GMT
ETag: "abc123"
```

#### OPTIONS

- **Purpose**: Get allowed methods for resource
- **Use Case**: CORS preflight requests

```http
OPTIONS /api/users HTTP/1.1
Host: api.example.com
Origin: https://example.com
Access-Control-Request-Method: POST

# Response
HTTP/1.1 200 OK
Allow: GET, POST, OPTIONS
Access-Control-Allow-Methods: GET, POST, OPTIONS
Access-Control-Allow-Headers: Content-Type, Authorization
```

## API vs Endpoints

Understanding the difference between APIs and endpoints is crucial for effective API testing and development.

### API (Application Programming Interface)

**API** is the entire interface or contract that defines how different software components interact.

#### Characteristics:

- **Complete Interface**: Includes all available operations
- **Contract**: Defines rules, protocols, and data formats
- **Abstraction**: Hides implementation details
- **Documentation**: Comprehensive specification

#### Example API:

```
User Management API
├── Authentication endpoints
├── User CRUD operations
├── User profile management
├── User preferences
└── User analytics
```

### Endpoint

**Endpoint** is a specific URL or URI that represents a particular resource or operation within an API.

#### Characteristics:

- **Specific URL**: Unique address for a resource
- **Single Operation**: Handles one type of request
- **Resource Focused**: Represents a specific data entity
- **HTTP Method**: Associated with specific HTTP verbs

#### Example Endpoints:

```
GET    /api/users           - List all users
GET    /api/users/123       - Get specific user
POST   /api/users           - Create new user
PUT    /api/users/123       - Update entire user
PATCH  /api/users/123       - Partial user update
DELETE /api/users/123       - Delete user
```

### API vs Endpoint Comparison

| Aspect            | API                     | Endpoint                           |
| ----------------- | ----------------------- | ---------------------------------- |
| **Scope**         | Complete interface      | Single resource/operation          |
| **Granularity**   | High-level              | Low-level                          |
| **URL**           | Base URL (e.g., `/api`) | Full path (e.g., `/api/users/123`) |
| **Documentation** | API specification       | Individual operation               |
| **Testing**       | End-to-end scenarios    | Individual requests                |
| **Versioning**    | API version             | Endpoint version                   |

### Real-world Example

#### API: E-commerce Platform API

```
Base URL: https://api.shop.com/v1
```

#### Endpoints within the API:

```
# Product Management
GET    /api/v1/products              - List products
GET    /api/v1/products/123          - Get product details
POST   /api/v1/products              - Create product
PUT    /api/v1/products/123          - Update product
DELETE /api/v1/products/123          - Delete product

# Order Management
GET    /api/v1/orders                - List orders
GET    /api/v1/orders/456            - Get order details
POST   /api/v1/orders                - Create order
PATCH  /api/v1/orders/456            - Update order status

# User Management
GET    /api/v1/users                 - List users
GET    /api/v1/users/789             - Get user profile
POST   /api/v1/users                 - Register user
PUT    /api/v1/users/789             - Update user profile
```

## API Authentication Methods

Authentication is the process of verifying the identity of a client making API requests. Different methods provide various levels of security and complexity.

### 1. API Key Authentication

**Simple key-based authentication** where clients include a unique key in requests.

#### How it works:

1. Client requests API key from provider
2. Key is included in requests (header, query parameter, or body)
3. Server validates key and grants access

#### Implementation Examples:

```http
# Header-based API Key
GET /api/users HTTP/1.1
Host: api.example.com
X-API-Key: your-api-key-here

# Query Parameter API Key
GET /api/users?api_key=your-api-key-here HTTP/1.1
Host: api.example.com

# Authorization Header (Common Pattern)
GET /api/users HTTP/1.1
Host: api.example.com
Authorization: ApiKey your-api-key-here
```

#### Pros and Cons:

- ✅ **Simple**: Easy to implement and use
- ✅ **Fast**: No complex validation
- ❌ **Security**: Keys can be easily compromised
- ❌ **No Expiration**: Keys typically don't expire automatically

### 2. Bearer Token Authentication

**Token-based authentication** where clients include a bearer token in the Authorization header.

#### How it works:

1. Client authenticates with credentials
2. Server returns a bearer token
3. Client includes token in subsequent requests
4. Server validates token and grants access

#### Implementation Examples:

```http
# Bearer Token in Authorization Header
GET /api/users HTTP/1.1
Host: api.example.com
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

# Token Request
POST /api/auth/token HTTP/1.1
Host: api.example.com
Content-Type: application/json

{
  "username": "john@example.com",
  "password": "securepassword"
}

# Token Response
HTTP/1.1 200 OK
Content-Type: application/json

{
  "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "token_type": "Bearer",
  "expires_in": 3600,
  "refresh_token": "def50200..."
}
```

#### Pros and Cons:

- ✅ **Secure**: Tokens can expire and be revoked
- ✅ **Stateless**: No server-side session storage
- ✅ **Scalable**: Works well in distributed systems
- ❌ **Complexity**: Requires token management

### 3. Basic Authentication

**Username/password authentication** encoded in Base64 format.

#### How it works:

1. Client combines username and password
2. Encodes credentials in Base64
3. Includes in Authorization header
4. Server decodes and validates credentials

#### Implementation Examples:

```http
# Basic Authentication
GET /api/users HTTP/1.1
Host: api.example.com
Authorization: Basic am9obkBleGFtcGxlLmNvbTpzZWN1cmVwYXNzd29yZA==

# Credentials: john@example.com:securepassword
# Base64 Encoded: am9obkBleGFtcGxlLmNvbTpzZWN1cmVwYXNzd29yZA==
```

#### Pros and Cons:

- ✅ **Simple**: Easy to implement
- ✅ **Standard**: Widely supported
- ❌ **Security**: Credentials sent with every request
- ❌ **No Expiration**: Credentials don't expire

### 4. OAuth 2.0

**Authorization framework** that enables applications to obtain limited access to user accounts.

#### OAuth 2.0 Flow:

```http
# 1. Authorization Request
GET /oauth/authorize?client_id=123&redirect_uri=https://app.com/callback&response_type=code&scope=read HTTP/1.1
Host: api.example.com

# 2. Authorization Code Response
HTTP/1.1 302 Found
Location: https://app.com/callback?code=AUTH_CODE_123

# 3. Token Exchange
POST /oauth/token HTTP/1.1
Host: api.example.com
Content-Type: application/x-www-form-urlencoded

grant_type=authorization_code&code=AUTH_CODE_123&client_id=123&client_secret=secret&redirect_uri=https://app.com/callback

# 4. Access Token Response
HTTP/1.1 200 OK
Content-Type: application/json

{
  "access_token": "ACCESS_TOKEN_123",
  "token_type": "Bearer",
  "expires_in": 3600,
  "refresh_token": "REFRESH_TOKEN_123",
  "scope": "read"
}

# 5. API Request with Token
GET /api/users HTTP/1.1
Host: api.example.com
Authorization: Bearer ACCESS_TOKEN_123
```

#### OAuth 2.0 Grant Types:

- **Authorization Code**: For web applications
- **Client Credentials**: For server-to-server
- **Password**: For trusted applications
- **Implicit**: For single-page applications

### 5. JWT (JSON Web Token)

**Self-contained token** that includes user information and permissions.

#### JWT Structure:

```
Header.Payload.Signature
```

#### Example JWT:

```json
// Header
{
  "alg": "HS256",
  "typ": "JWT"
}

// Payload
{
  "sub": "1234567890",
  "name": "John Doe",
  "iat": 1516239022,
  "exp": 1516242622,
  "roles": ["user", "admin"]
}

// Complete JWT
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyLCJleHAiOjE1MTYyNDI2MjIsInJvbGVzIjpbInVzZXIiLCJhZG1pbiJdfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

#### Implementation:

```http
# JWT Token Request
POST /api/auth/login HTTP/1.1
Host: api.example.com
Content-Type: application/json

{
  "username": "john@example.com",
  "password": "securepassword"
}

# JWT Token Response
HTTP/1.1 200 OK
Content-Type: application/json

{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "expires_in": 3600
}

# API Request with JWT
GET /api/users HTTP/1.1
Host: api.example.com
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### 6. HMAC (Hash-based Message Authentication Code)

**Cryptographic authentication** using shared secret and request signature.

#### How it works:

1. Client and server share a secret key
2. Client creates signature using request data and secret
3. Server validates signature using same algorithm
4. Access granted if signatures match

#### Implementation Example:

```http
# HMAC Request
GET /api/users HTTP/1.1
Host: api.example.com
Authorization: HMAC-SHA256 Credential=ACCESS_KEY_ID, SignedHeaders=host;x-date, Signature=calculated_signature
X-Date: 20240115T103000Z

# Signature Calculation (Client-side)
string_to_sign = "GET\n/api/users\n\nhost:api.example.com\nx-date:20240115T103000Z"
signature = HMAC-SHA256(secret_key, string_to_sign)
```

### Authentication Method Comparison

| Method           | Security | Complexity | Stateless | Expiration | Use Case           |
| ---------------- | -------- | ---------- | --------- | ---------- | ------------------ |
| **API Key**      | Low      | Low        | Yes       | No         | Simple APIs        |
| **Bearer Token** | Medium   | Medium     | Yes       | Yes        | REST APIs          |
| **Basic Auth**   | Low      | Low        | No        | No         | Internal APIs      |
| **OAuth 2.0**    | High     | High       | Yes       | Yes        | Third-party access |
| **JWT**          | High     | Medium     | Yes       | Yes        | Modern web apps    |
| **HMAC**         | High     | High       | Yes       | No         | Financial APIs     |

## Request and Response Structure

Understanding the structure of HTTP requests and responses is essential for API testing.

### HTTP Request Structure

```
Method Request-URI HTTP-Version
Header1: Value1
Header2: Value2

Request Body (if any)
```

#### Complete Request Example:

```http
POST /api/users HTTP/1.1
Host: api.example.com
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
User-Agent: MyApp/1.0
Accept: application/json
Content-Length: 156

{
  "name": "John Doe",
  "email": "john@example.com",
  "age": 30
}
```

### HTTP Response Structure

```
HTTP-Version Status-Code Reason-Phrase
Header1: Value1
Header2: Value2

Response Body (if any)
```

#### Complete Response Example:

```http
HTTP/1.1 201 Created
Content-Type: application/json
Location: /api/users/123
X-RateLimit-Limit: 1000
X-RateLimit-Remaining: 999
X-RateLimit-Reset: 1642248000
Date: Mon, 15 Jan 2024 10:30:00 GMT
Content-Length: 234

{
  "id": 123,
  "name": "John Doe",
  "email": "john@example.com",
  "age": 30,
  "createdAt": "2024-01-15T10:30:00Z",
  "updatedAt": "2024-01-15T10:30:00Z"
}
```

### Common HTTP Headers

#### Request Headers:

- **Authorization**: Authentication credentials
- **Content-Type**: Media type of request body
- **Accept**: Media types client can handle
- **User-Agent**: Client application information
- **X-API-Key**: API key for authentication

#### Response Headers:

- **Content-Type**: Media type of response body
- **Location**: URL of created resource
- **Cache-Control**: Caching directives
- **X-RateLimit-\***: Rate limiting information
- **Set-Cookie**: Cookie to set in client

## Best Practices

### 1. HTTP Status Code Usage

```http
# Use appropriate status codes
GET /api/users/123     → 200 OK (found) or 404 Not Found
POST /api/users        → 201 Created
PUT /api/users/123     → 200 OK (updated) or 404 Not Found
DELETE /api/users/123  → 204 No Content
PATCH /api/users/123   → 200 OK (updated)
```

### 2. Consistent Error Responses

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

### 3. Proper HTTP Method Usage

```http
# Use GET for data retrieval
GET /api/users
GET /api/users/123

# Use POST for creation
POST /api/users
POST /api/orders

# Use PUT for complete updates
PUT /api/users/123

# Use PATCH for partial updates
PATCH /api/users/123

# Use DELETE for removal
DELETE /api/users/123
```

### 4. Authentication Best Practices

```http
# Use HTTPS for all authentication
# Include proper headers
Authorization: Bearer token-here
Content-Type: application/json

# Handle token expiration
HTTP/1.1 401 Unauthorized
WWW-Authenticate: Bearer error="invalid_token", error_description="Token expired"
```

## Common API Patterns

### 1. RESTful API Design

```
# Resource-based URLs
GET    /api/users           # List users
GET    /api/users/123       # Get user
POST   /api/users           # Create user
PUT    /api/users/123       # Update user
DELETE /api/users/123       # Delete user

# Nested resources
GET    /api/users/123/orders        # User's orders
POST   /api/users/123/orders        # Create order for user
GET    /api/users/123/orders/456    # Specific order
```

### 2. Pagination

```json
{
  "data": [
    { "id": 1, "name": "User 1" },
    { "id": 2, "name": "User 2" }
  ],
  "pagination": {
    "page": 1,
    "limit": 10,
    "total": 100,
    "totalPages": 10,
    "hasNext": true,
    "hasPrev": false
  }
}
```

### 3. Filtering and Sorting

```http
# Query parameters for filtering
GET /api/users?status=active&role=admin&sort=name&order=asc

# Response
{
  "data": [...],
  "filters": {
    "status": "active",
    "role": "admin"
  },
  "sort": {
    "field": "name",
    "order": "asc"
  }
}
```

### 4. Versioning

```http
# URL versioning
GET /api/v1/users
GET /api/v2/users

# Header versioning
GET /api/users HTTP/1.1
API-Version: v1

# Query parameter versioning
GET /api/users?version=v1
```

---

## Summary

Understanding API basics is fundamental to effective API testing. Key concepts include:

- **HTTP Status Codes**: Proper use for different scenarios
- **HTTP Methods**: Correct verbs for different operations
- **API vs Endpoints**: Understanding scope and granularity
- **Authentication**: Various methods and their use cases
- **Request/Response Structure**: Proper formatting and headers
- **Best Practices**: Industry standards and patterns

Mastering these fundamentals provides a solid foundation for comprehensive API testing and development.
