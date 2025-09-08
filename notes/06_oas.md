# OpenAPI Specification (OAS) Tutorial

## Table of Contents

1. [OpenAPI Fundamentals](#openapi-fundamentals)
2. [Complete OpenAPI Specification](#complete-openapi-specification)
3. [Authentication Endpoint](#authentication-endpoint)
4. [User Management Endpoints](#user-management-endpoints)
5. [Data Models and Schemas](#data-models-and-schemas)
6. [Security Definitions](#security-definitions)
7. [OpenAPI Tools and Validation](#openapi-tools-and-validation)
8. [Best Practices](#best-practices)

## OpenAPI Fundamentals

**OpenAPI Specification (OAS)** is a standard, language-agnostic interface description for REST APIs. It allows both humans and computers to discover and understand the capabilities of a service without access to source code, documentation, or network traffic inspection.

### Key Benefits:

- **API Documentation**: Auto-generated, interactive documentation
- **Code Generation**: Generate client SDKs and server stubs
- **Testing**: Automated API testing and validation
- **Design-First**: Design APIs before implementation
- **Tooling**: Extensive ecosystem of tools and libraries

### OpenAPI Structure:

```yaml
openapi: 3.0.0
info:
  title: API Title
  version: 1.0.0
servers:
  - url: http://localhost:3000
paths:
  /endpoint:
    get:
      responses:
        "200":
          description: Success
components:
  schemas:
    Model:
      type: object
```

## Complete OpenAPI Specification

Based on the Mockoon configuration, here's the complete OpenAPI specification:

```yaml
openapi: 3.0.3
info:
  title: React Lab API
  description: |
    A REST API for user management with JWT authentication.
    This API provides endpoints for user authentication and CRUD operations on user resources.
  version: 1.0.0
  contact:
    name: API Support
    email: support@example.com
  license:
    name: MIT
    url: https://opensource.org/licenses/MIT

servers:
  - url: http://localhost:3000
    description: Development server
  - url: https://api.example.com
    description: Production server

paths:
  /auth:
    post:
      tags:
        - Authentication
      summary: Authenticate user
      description: |
        Authenticate a user with username and password credentials.
        Returns a JWT token for subsequent API requests.
      operationId: authenticateUser
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: "#/components/schemas/AuthRequest"
            examples:
              valid_credentials:
                summary: Valid credentials
                value:
                  user_name: "user"
                  password: "password"
              invalid_credentials:
                summary: Invalid credentials
                value:
                  user_name: "invalid_user"
                  password: "wrong_password"
      responses:
        "200":
          description: Authentication successful
          content:
            application/json:
              schema:
                $ref: "#/components/schemas/AuthResponse"
              examples:
                success:
                  summary: Successful authentication
                  value:
                    token: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30"
        "401":
          description: Authentication failed
          content:
            application/json:
              schema:
                $ref: "#/components/schemas/ErrorResponse"
              examples:
                invalid_credentials:
                  summary: Invalid credentials
                  value:
                    message: "user_name and password incorrect"
        "400":
          description: Bad request - missing or invalid input
          content:
            application/json:
              schema:
                $ref: "#/components/schemas/ErrorResponse"
              examples:
                missing_password:
                  summary: Missing password
                  value:
                    message: "Missing required field: password"

  /users:
    get:
      tags:
        - Users
      summary: List all users
      description: Retrieve a list of all users in the system
      operationId: listUsers
      security:
        - bearerAuth: []
      responses:
        "200":
          description: List of users retrieved successfully
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: "#/components/schemas/User"
              examples:
                empty_list:
                  summary: Empty user list
                  value: []
                users_list:
                  summary: List with users
                  value:
                    - id: 1
                      name: "John Doe"
                      email: "john@example.com"
                      age: 30
                      department: "Engineering"
                      createdAt: "2024-01-15T10:30:00Z"
        "401":
          description: Unauthorized - invalid or missing token
          content:
            application/json:
              schema:
                $ref: "#/components/schemas/ErrorResponse"
              examples:
                invalid_token:
                  summary: Invalid token
                  value:
                    message: "Invalid Bearer Token"

    post:
      tags:
        - Users
      summary: Create a new user
      description: Create a new user in the system
      operationId: createUser
      security:
        - bearerAuth: []
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: "#/components/schemas/CreateUserRequest"
            examples:
              new_user:
                summary: New user data
                value:
                  name: "Jane Smith"
                  email: "jane@example.com"
                  age: 28
                  department: "Marketing"
      responses:
        "201":
          description: User created successfully
          headers:
            Location:
              description: URL of the created user
              schema:
                type: string
                example: "/users/1"
          content:
            application/json:
              schema:
                $ref: "#/components/schemas/User"
              examples:
                created_user:
                  summary: Created user
                  value:
                    id: 1
                    name: "Jane Smith"
                    email: "jane@example.com"
                    age: 28
                    department: "Marketing"
                    createdAt: "2024-01-15T10:30:00Z"
        "401":
          description: Unauthorized - invalid or missing token
          content:
            application/json:
              schema:
                $ref: "#/components/schemas/ErrorResponse"
              examples:
                invalid_token:
                  summary: Invalid token
                  value:
                    message: "Invalid Bearer Token"
        "400":
          description: Bad request - invalid user data
          content:
            application/json:
              schema:
                $ref: "#/components/schemas/ErrorResponse"
              examples:
                validation_error:
                  summary: Validation error
                  value:
                    message: "Invalid input data"
                    details:
                      - field: "email"
                        message: "Invalid email format"

  /users/{userId}:
    get:
      tags:
        - Users
      summary: Get user by ID
      description: Retrieve a specific user by their ID
      operationId: getUserById
      security:
        - bearerAuth: []
      parameters:
        - name: userId
          in: path
          required: true
          description: Unique identifier of the user
          schema:
            type: integer
            format: int64
            example: 1
      responses:
        "200":
          description: User retrieved successfully
          content:
            application/json:
              schema:
                $ref: "#/components/schemas/User"
              examples:
                user_data:
                  summary: User data
                  value:
                    id: 1
                    name: "John Doe"
                    email: "john@example.com"
                    age: 30
                    department: "Engineering"
                    createdAt: "2024-01-15T10:30:00Z"
        "401":
          description: Unauthorized - invalid or missing token
          content:
            application/json:
              schema:
                $ref: "#/components/schemas/ErrorResponse"
        "404":
          description: User not found
          content:
            application/json:
              schema:
                $ref: "#/components/schemas/ErrorResponse"
              examples:
                not_found:
                  summary: User not found
                  value:
                    message: "User not found"

    put:
      tags:
        - Users
      summary: Update user
      description: Update an existing user with complete data
      operationId: updateUser
      security:
        - bearerAuth: []
      parameters:
        - name: userId
          in: path
          required: true
          description: Unique identifier of the user
          schema:
            type: integer
            format: int64
            example: 1
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: "#/components/schemas/UpdateUserRequest"
            examples:
              update_data:
                summary: Updated user data
                value:
                  id: 1
                  name: "John Updated"
                  email: "john.updated@example.com"
                  age: 31
                  department: "Product Management"
      responses:
        "200":
          description: User updated successfully
          content:
            application/json:
              schema:
                $ref: "#/components/schemas/User"
              examples:
                updated_user:
                  summary: Updated user
                  value:
                    id: 1
                    name: "John Updated"
                    email: "john.updated@example.com"
                    age: 31
                    department: "Product Management"
                    updatedAt: "2024-01-15T11:00:00Z"
        "401":
          description: Unauthorized - invalid or missing token
          content:
            application/json:
              schema:
                $ref: "#/components/schemas/ErrorResponse"
        "404":
          description: User not found
          content:
            application/json:
              schema:
                $ref: "#/components/schemas/ErrorResponse"

    patch:
      tags:
        - Users
      summary: Partially update user
      description: Update specific fields of an existing user
      operationId: patchUser
      security:
        - bearerAuth: []
      parameters:
        - name: userId
          in: path
          required: true
          description: Unique identifier of the user
          schema:
            type: integer
            format: int64
            example: 1
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: "#/components/schemas/PatchUserRequest"
            examples:
              partial_update:
                summary: Partial update
                value:
                  age: 32
      responses:
        "200":
          description: User updated successfully
          content:
            application/json:
              schema:
                $ref: "#/components/schemas/User"
        "401":
          description: Unauthorized - invalid or missing token
          content:
            application/json:
              schema:
                $ref: "#/components/schemas/ErrorResponse"
        "404":
          description: User not found
          content:
            application/json:
              schema:
                $ref: "#/components/schemas/ErrorResponse"

    delete:
      tags:
        - Users
      summary: Delete user
      description: Delete a user from the system
      operationId: deleteUser
      security:
        - bearerAuth: []
      parameters:
        - name: userId
          in: path
          required: true
          description: Unique identifier of the user
          schema:
            type: integer
            format: int64
            example: 1
      responses:
        "204":
          description: User deleted successfully
        "401":
          description: Unauthorized - invalid or missing token
          content:
            application/json:
              schema:
                $ref: "#/components/schemas/ErrorResponse"
        "404":
          description: User not found
          content:
            application/json:
              schema:
                $ref: "#/components/schemas/ErrorResponse"

components:
  securitySchemes:
    bearerAuth:
      type: http
      scheme: bearer
      bearerFormat: JWT
      description: |
        JWT token obtained from the /auth endpoint.
        Include the token in the Authorization header as: `Bearer <token>`

  schemas:
    AuthRequest:
      type: object
      required:
        - user_name
        - password
      properties:
        user_name:
          type: string
          description: Username for authentication
          example: "user"
        password:
          type: string
          description: Password for authentication
          example: "password"
          format: password

    AuthResponse:
      type: object
      required:
        - token
      properties:
        token:
          type: string
          description: JWT token for API authentication
          example: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30"

    User:
      type: object
      required:
        - id
        - name
        - email
      properties:
        id:
          type: integer
          format: int64
          description: Unique identifier for the user
          example: 1
        name:
          type: string
          description: Full name of the user
          example: "John Doe"
        email:
          type: string
          format: email
          description: Email address of the user
          example: "john@example.com"
        age:
          type: integer
          minimum: 0
          maximum: 150
          description: Age of the user
          example: 30
        department:
          type: string
          description: Department where the user works
          example: "Engineering"
        createdAt:
          type: string
          format: date-time
          description: Timestamp when the user was created
          example: "2024-01-15T10:30:00Z"
        updatedAt:
          type: string
          format: date-time
          description: Timestamp when the user was last updated
          example: "2024-01-15T11:00:00Z"

    CreateUserRequest:
      type: object
      required:
        - name
        - email
      properties:
        name:
          type: string
          description: Full name of the user
          example: "Jane Smith"
        email:
          type: string
          format: email
          description: Email address of the user
          example: "jane@example.com"
        age:
          type: integer
          minimum: 0
          maximum: 150
          description: Age of the user
          example: 28
        department:
          type: string
          description: Department where the user works
          example: "Marketing"

    UpdateUserRequest:
      type: object
      required:
        - id
        - name
        - email
      properties:
        id:
          type: integer
          format: int64
          description: Unique identifier for the user
          example: 1
        name:
          type: string
          description: Full name of the user
          example: "John Updated"
        email:
          type: string
          format: email
          description: Email address of the user
          example: "john.updated@example.com"
        age:
          type: integer
          minimum: 0
          maximum: 150
          description: Age of the user
          example: 31
        department:
          type: string
          description: Department where the user works
          example: "Product Management"

    PatchUserRequest:
      type: object
      properties:
        name:
          type: string
          description: Full name of the user
          example: "John Updated"
        email:
          type: string
          format: email
          description: Email address of the user
          example: "john.updated@example.com"
        age:
          type: integer
          minimum: 0
          maximum: 150
          description: Age of the user
          example: 32
        department:
          type: string
          description: Department where the user works
          example: "Product Management"

    ErrorResponse:
      type: object
      required:
        - message
      properties:
        message:
          type: string
          description: Error message describing what went wrong
          example: "Invalid Bearer Token"
        details:
          type: array
          description: Additional error details (for validation errors)
          items:
            type: object
            properties:
              field:
                type: string
                description: Field that caused the error
                example: "email"
              message:
                type: string
                description: Specific error message for the field
                example: "Invalid email format"
        timestamp:
          type: string
          format: date-time
          description: Timestamp when the error occurred
          example: "2024-01-15T10:30:00Z"
        requestId:
          type: string
          description: Unique identifier for the request
          example: "req-123-456"

  responses:
    UnauthorizedError:
      description: Authentication information is missing or invalid
      content:
        application/json:
          schema:
            $ref: "#/components/schemas/ErrorResponse"
          examples:
            invalid_token:
              summary: Invalid token
              value:
                message: "Invalid Bearer Token"
            missing_token:
              summary: Missing token
              value:
                message: "Authorization header is required"

    NotFoundError:
      description: The specified resource was not found
      content:
        application/json:
          schema:
            $ref: "#/components/schemas/ErrorResponse"
          examples:
            user_not_found:
              summary: User not found
              value:
                message: "User not found"

    ValidationError:
      description: The request data is invalid
      content:
        application/json:
          schema:
            $ref: "#/components/schemas/ErrorResponse"
          examples:
            validation_failed:
              summary: Validation failed
              value:
                message: "Invalid input data"
                details:
                  - field: "email"
                    message: "Invalid email format"
                  - field: "age"
                    message: "Age must be between 0 and 150"

tags:
  - name: Authentication
    description: User authentication operations
  - name: Users
    description: User management operations
```

## Authentication Endpoint

The `/auth` endpoint handles user authentication and returns a JWT token:

### Key Features:

- **POST Method**: Accepts username and password
- **JWT Response**: Returns a Bearer token for API access
- **Error Handling**: Proper error responses for invalid credentials
- **Validation**: Required fields validation

### Request Schema:

```yaml
AuthRequest:
  type: object
  required:
    - user_name
    - password
  properties:
    user_name:
      type: string
      example: "user"
    password:
      type: string
      format: password
      example: "password"
```

### Response Schemas:

```yaml
# Success Response (200)
AuthResponse:
  type: object
  properties:
    token:
      type: string
      example: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

# Error Response (401)
ErrorResponse:
  type: object
  properties:
    message:
      type: string
      example: "user_name and password incorrect"
```

## User Management Endpoints

The `/users` endpoint provides full CRUD operations:

### CRUD Operations:

1. **GET /users** - List all users
2. **POST /users** - Create new user
3. **GET /users/{userId}** - Get specific user
4. **PUT /users/{userId}** - Update entire user
5. **PATCH /users/{userId}** - Partial update
6. **DELETE /users/{userId}** - Delete user

### User Schema:

```yaml
User:
  type: object
  required:
    - id
    - name
    - email
  properties:
    id:
      type: integer
      format: int64
      example: 1
    name:
      type: string
      example: "John Doe"
    email:
      type: string
      format: email
      example: "john@example.com"
    age:
      type: integer
      minimum: 0
      maximum: 150
      example: 30
    department:
      type: string
      example: "Engineering"
    createdAt:
      type: string
      format: date-time
      example: "2024-01-15T10:30:00Z"
    updatedAt:
      type: string
      format: date-time
      example: "2024-01-15T11:00:00Z"
```

## Data Models and Schemas

### Request Schemas:

#### CreateUserRequest:

```yaml
CreateUserRequest:
  type: object
  required:
    - name
    - email
  properties:
    name:
      type: string
      example: "Jane Smith"
    email:
      type: string
      format: email
      example: "jane@example.com"
    age:
      type: integer
      minimum: 0
      maximum: 150
      example: 28
    department:
      type: string
      example: "Marketing"
```

#### UpdateUserRequest:

```yaml
UpdateUserRequest:
  type: object
  required:
    - id
    - name
    - email
  properties:
    id:
      type: integer
      format: int64
      example: 1
    name:
      type: string
      example: "John Updated"
    email:
      type: string
      format: email
      example: "john.updated@example.com"
    age:
      type: integer
      minimum: 0
      maximum: 150
      example: 31
    department:
      type: string
      example: "Product Management"
```

#### PatchUserRequest:

```yaml
PatchUserRequest:
  type: object
  properties:
    name:
      type: string
      example: "John Updated"
    email:
      type: string
      format: email
      example: "john.updated@example.com"
    age:
      type: integer
      minimum: 0
      maximum: 150
      example: 32
    department:
      type: string
      example: "Product Management"
```

### Error Schema:

```yaml
ErrorResponse:
  type: object
  required:
    - message
  properties:
    message:
      type: string
      example: "Invalid Bearer Token"
    details:
      type: array
      items:
        type: object
        properties:
          field:
            type: string
            example: "email"
          message:
            type: string
            example: "Invalid email format"
    timestamp:
      type: string
      format: date-time
      example: "2024-01-15T10:30:00Z"
    requestId:
      type: string
      example: "req-123-456"
```

## Security Definitions

### Bearer Token Authentication:

```yaml
securitySchemes:
  bearerAuth:
    type: http
    scheme: bearer
    bearerFormat: JWT
    description: |
      JWT token obtained from the /auth endpoint.
      Include the token in the Authorization header as: `Bearer <token>`
```

### Security Requirements:

```yaml
security:
  - bearerAuth: [] # Applied to protected endpoints
```

### CORS Configuration:

Based on the Mockoon config, the API supports:

- **Access-Control-Allow-Origin**: `*`
- **Access-Control-Allow-Methods**: `GET,POST,PUT,PATCH,DELETE,HEAD,OPTIONS`
- **Access-Control-Allow-Headers**: `Content-Type, Origin, Accept, Authorization, Content-Length, X-Requested-With`

## OpenAPI Tools and Validation

### 1. Swagger UI

**Interactive API documentation** generated from OpenAPI spec.

#### Features:

- **Interactive Testing**: Test endpoints directly from documentation
- **Schema Validation**: Visual representation of request/response schemas
- **Authentication**: Built-in authentication testing
- **Examples**: Request/response examples

#### Usage:

```bash
# Install Swagger UI
npm install -g swagger-ui-serve

# Serve the OpenAPI spec
swagger-ui-serve openapi.yaml
```

### 2. OpenAPI Generator

**Code generation** from OpenAPI specifications.

#### Generate Client SDKs:

```bash
# Install OpenAPI Generator
npm install -g @openapitools/openapi-generator-cli

# Generate JavaScript client
openapi-generator-cli generate -i openapi.yaml -g javascript -o ./client

# Generate Python client
openapi-generator-cli generate -i openapi.yaml -g python -o ./client

# Generate Java client
openapi-generator-cli generate -i openapi.yaml -g java -o ./client
```

### 3. Postman Import

**Import OpenAPI spec** into Postman for testing.

#### Steps:

1. Open Postman
2. Click "Import"
3. Select "Link" tab
4. Enter OpenAPI spec URL or upload file
5. Postman automatically creates collection with all endpoints

### 4. API Validation Tools

#### Spectral (Linting):

```bash
# Install Spectral
npm install -g @stoplight/spectral-cli

# Lint OpenAPI spec
spectral lint openapi.yaml
```

#### OpenAPI Validator:

```bash
# Install validator
npm install -g swagger-parser

# Validate spec
swagger-parser validate openapi.yaml
```

## Best Practices

### 1. Specification Structure

```yaml
# Good: Clear organization
openapi: 3.0.3
info:
  title: "API Name"
  version: "1.0.0"
  description: "Clear API description"

servers:
  - url: "https://api.example.com"
    description: "Production server"

paths:
  /endpoint:
    get:
      summary: "Clear endpoint summary"
      description: "Detailed description"
      responses:
        "200":
          description: "Success response"
```

### 2. Schema Design

```yaml
# Good: Comprehensive schema
User:
  type: object
  required:
    - id
    - name
    - email
  properties:
    id:
      type: integer
      format: int64
      description: "Unique identifier"
      example: 1
    name:
      type: string
      description: "Full name"
      example: "John Doe"
    email:
      type: string
      format: email
      description: "Email address"
      example: "john@example.com"
```

### 3. Error Handling

```yaml
# Good: Consistent error responses
responses:
  "400":
    description: "Bad Request"
    content:
      application/json:
        schema:
          $ref: "#/components/schemas/ErrorResponse"
  "401":
    description: "Unauthorized"
    content:
      application/json:
        schema:
          $ref: "#/components/schemas/ErrorResponse"
  "404":
    description: "Not Found"
    content:
      application/json:
        schema:
          $ref: "#/components/schemas/ErrorResponse"
```

### 4. Security

```yaml
# Good: Clear security definitions
components:
  securitySchemes:
    bearerAuth:
      type: http
      scheme: bearer
      bearerFormat: JWT
      description: "JWT token authentication"

# Apply security to endpoints
security:
  - bearerAuth: []
```

### 5. Documentation

```yaml
# Good: Comprehensive documentation
paths:
  /users:
    post:
      tags:
        - Users
      summary: "Create a new user"
      description: |
        Creates a new user in the system with the provided information.
        Returns the created user data including the generated ID.
      operationId: "createUser"
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: "#/components/schemas/CreateUserRequest"
            examples:
              new_user:
                summary: "New user example"
                value:
                  name: "Jane Smith"
                  email: "jane@example.com"
                  age: 28
                  department: "Marketing"
```

---

## Summary

OpenAPI Specification provides a standardized way to describe REST APIs:

- **Complete Documentation**: Auto-generated, interactive API docs
- **Code Generation**: Generate client SDKs and server stubs
- **Validation**: Ensure API consistency and correctness
- **Tooling**: Extensive ecosystem of tools and libraries
- **Design-First**: Design APIs before implementation

Key takeaways:

- OpenAPI 3.0.3 is the current standard
- Comprehensive schemas improve API usability
- Security definitions enable proper authentication
- Examples and descriptions enhance documentation
- Validation tools ensure specification quality

Understanding OpenAPI is essential for modern API development and testing workflows.
