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
