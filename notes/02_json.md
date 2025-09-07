# JSON Tutorial for API Testing

## Table of Contents

1. [JSON Basics](#json-basics)
2. [JSON Syntax and Structure](#json-syntax-and-structure)
3. [JSON Data Types](#json-data-types)
4. [JSON Validation](#json-validation)
5. [JSON in API Context](#json-in-api-context)
6. [Practical Examples](#practical-examples)
7. [JSON vs XML](#json-vs-xml)

## JSON Basics

**JSON (JavaScript Object Notation)** is a lightweight, text-based data interchange format that's easy for humans to read and write, and easy for machines to parse and generate. It's the de facto standard for REST APIs and modern web applications.

### Key Characteristics:

- **Lightweight**: Minimal syntax overhead
- **Language independent**: Works with any programming language
- **Human readable**: Easy to understand and debug
- **Self-describing**: Structure is clear from the data
- **Fast parsing**: Efficient for both parsing and generation

### Why JSON for API Testing?

- REST APIs primarily use JSON
- Modern web applications standard format
- Mobile applications prefer JSON
- Microservices communication
- Real-time data exchange (WebSockets, etc.)

## JSON Syntax and Structure

### Basic JSON Document Structure

```json
{
  "key": "value",
  "number": 42,
  "boolean": true,
  "array": [1, 2, 3],
  "object": {
    "nested": "value"
  }
}
```

### JSON Rules:

1. **Data is in name/value pairs**: `"name": "value"`
2. **Data is separated by commas**: `"key1": "value1", "key2": "value2"`
3. **Objects are enclosed in curly braces**: `{}`
4. **Arrays are enclosed in square brackets**: `[]`
5. **Strings must be in double quotes**: `"string"`
6. **No trailing commas**: Not allowed after last element
7. **No comments**: JSON doesn't support comments

### Common JSON Mistakes:

```json
// WRONG: Single quotes instead of double quotes
{
  'name': 'John',
  'age': 30
}

// WRONG: Trailing comma
{
  "name": "John",
  "age": 30,
}

// WRONG: Unquoted keys
{
  name: "John",
  age: 30
}

// WRONG: Comments (not supported)
{
  "name": "John", // This is invalid
  "age": 30
}

// CORRECT
{
  "name": "John",
  "age": 30
}
```

## JSON Data Types

### 1. String

```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "description": "A person with a name and email"
}
```

### 2. Number

```json
{
  "age": 30,
  "height": 175.5,
  "temperature": -10,
  "population": 1000000
}
```

### 3. Boolean

```json
{
  "isActive": true,
  "isDeleted": false,
  "hasPermission": true
}
```

### 4. Null

```json
{
  "middleName": null,
  "lastLogin": null,
  "optionalField": null
}
```

### 5. Object

```json
{
  "user": {
    "id": 123,
    "name": "John Doe",
    "address": {
      "street": "123 Main St",
      "city": "New York",
      "country": "USA"
    }
  }
}
```

### 6. Array

```json
{
  "fruits": ["apple", "banana", "orange"],
  "numbers": [1, 2, 3, 4, 5],
  "users": [
    { "id": 1, "name": "John" },
    { "id": 2, "name": "Jane" }
  ]
}
```

### 7. Mixed Types

```json
{
  "id": 123,
  "name": "John Doe",
  "isActive": true,
  "tags": ["developer", "api", "testing"],
  "profile": {
    "age": 30,
    "skills": ["JavaScript", "Python", "API Testing"]
  },
  "lastLogin": null
}
```

## JSON Validation

### JSON Schema Example

```json
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "type": "object",
  "properties": {
    "id": {
      "type": "integer",
      "minimum": 1
    },
    "name": {
      "type": "string",
      "minLength": 1,
      "maxLength": 100
    },
    "email": {
      "type": "string",
      "format": "email"
    },
    "age": {
      "type": "integer",
      "minimum": 0,
      "maximum": 150
    },
    "isActive": {
      "type": "boolean"
    },
    "tags": {
      "type": "array",
      "items": {
        "type": "string"
      }
    }
  },
  "required": ["id", "name", "email"],
  "additionalProperties": false
}
```

### Validation Rules:

- **Type validation**: Ensure correct data types
- **Required fields**: Check mandatory properties
- **Format validation**: Email, date, URI formats
- **Range validation**: Min/max values for numbers
- **Length validation**: String and array lengths
- **Pattern matching**: Regular expressions for strings

### Validation Tools:

- **Online validators**: JSONLint.com, JSONSchema.org
- **IDE plugins**: JSON tools in VS Code, IntelliJ
- **Command line**: jq, jsonlint
- **Libraries**: ajv (JavaScript), jsonschema (Python)

## JSON in API Context

### Error Response Format

```json
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
    ],
    "timestamp": "2024-01-15T10:30:00Z",
    "requestId": "req-123-456"
  }
}
```

## Practical Examples

### Example 1: E-commerce Order

```json
{
  "orderId": "ORD-2024-001",
  "date": "2024-01-15T10:30:00Z",
  "customer": {
    "id": 12345,
    "name": "John Doe",
    "email": "john@example.com",
    "address": {
      "street": "123 Main St",
      "city": "New York",
      "state": "NY",
      "zipCode": "10001",
      "country": "USA"
    }
  },
  "items": [
    {
      "productId": "PROD-001",
      "name": "Laptop",
      "quantity": 1,
      "price": 999.99,
      "category": "Electronics"
    },
    {
      "productId": "PROD-002",
      "name": "Mouse",
      "quantity": 2,
      "price": 25.5,
      "category": "Accessories"
    }
  ],
  "totals": {
    "subtotal": 1050.99,
    "tax": 84.08,
    "shipping": 15.0,
    "total": 1150.07
  },
  "payment": {
    "method": "credit_card",
    "cardLast4": "1234",
    "cardType": "Visa"
  },
  "status": "pending"
}
```

### Example 2: API Configuration

```json
{
  "api": {
    "version": "v1",
    "baseUrl": "https://api.example.com",
    "timeout": 30000,
    "retryPolicy": {
      "maxAttempts": 3,
      "delay": 1000,
      "backoffMultiplier": 2
    }
  },
  "endpoints": {
    "users": {
      "url": "/users",
      "methods": ["GET", "POST", "PUT", "DELETE"],
      "authentication": "bearer_token"
    },
    "orders": {
      "url": "/orders",
      "methods": ["GET", "POST"],
      "authentication": "bearer_token"
    }
  },
  "authentication": {
    "type": "bearer_token",
    "tokenEndpoint": "https://auth.example.com/token",
    "clientId": "your-client-id",
    "clientSecret": "your-client-secret"
  },
  "logging": {
    "level": "info",
    "format": "json",
    "output": "console"
  }
}
```

### Example 3: Test Data Structure

```json
{
  "testSuite": {
    "name": "User Management API Tests",
    "version": "1.0.0",
    "description": "Comprehensive tests for user CRUD operations"
  },
  "testCases": [
    {
      "id": "TC001",
      "name": "Create Valid User",
      "description": "Test creating a user with valid data",
      "method": "POST",
      "endpoint": "/api/users",
      "headers": {
        "Content-Type": "application/json",
        "Authorization": "Bearer {{token}}"
      },
      "body": {
        "name": "Test User",
        "email": "test@example.com",
        "age": 25
      },
      "expectedStatus": 201,
      "expectedResponse": {
        "id": "{{userId}}",
        "name": "Test User",
        "email": "test@example.com",
        "age": 25,
        "isActive": true
      }
    },
    {
      "id": "TC002",
      "name": "Get User by ID",
      "description": "Test retrieving a user by ID",
      "method": "GET",
      "endpoint": "/api/users/{{userId}}",
      "headers": {
        "Authorization": "Bearer {{token}}"
      },
      "expectedStatus": 200,
      "expectedResponse": {
        "id": "{{userId}}",
        "name": "Test User",
        "email": "test@example.com"
      }
    }
  ],
  "environment": {
    "baseUrl": "https://api.example.com",
    "token": "{{$randomToken}}"
  }
}
```

## JSON vs XML

| Feature         | JSON               | XML                |
| --------------- | ------------------ | ------------------ |
| **Syntax**      | Concise, key-value | Verbose, tag-based |
| **Readability** | Machine friendly   | Human readable     |
| **Size**        | Smaller file size  | Larger file size   |
| **Parsing**     | Simpler            | More complex       |
| **Schema**      | JSON Schema        | DTD, XSD           |
| **Comments**    | Not supported      | Supported          |
| **Namespaces**  | Limited support    | Built-in support   |
| **API Usage**   | REST, GraphQL      | SOAP, some REST    |

### When to Use JSON:

- REST APIs
- Mobile applications
- Real-time data exchange
- Modern web applications
- Microservices
- Configuration files
- NoSQL databases

### When to Use XML:

- SOAP web services
- Enterprise systems
- Document markup
- Legacy system integration
- Complex data structures with metadata

## Best Practices for JSON in API Testing

### 1. Structure and Organization

```json
{
  "status": "success",
  "data": {
    "users": [
      { "id": 1, "name": "John" },
      { "id": 2, "name": "Jane" }
    ]
  },
  "meta": {
    "total": 2,
    "page": 1,
    "limit": 10
  }
}
```

### 2. Consistent Naming Conventions

```json
{
  "userProfile": {
    "firstName": "John",
    "lastName": "Doe",
    "emailAddress": "john@example.com",
    "phoneNumber": "+1234567890",
    "createdAt": "2024-01-15T10:30:00Z"
  }
}
```

### 3. Error Handling

```json
{
  "success": false,
  "error": {
    "code": "VALIDATION_ERROR",
    "message": "Invalid input data",
    "details": [
      {
        "field": "email",
        "message": "Invalid email format",
        "value": "invalid-email"
      }
    ],
    "timestamp": "2024-01-15T10:30:00Z",
    "requestId": "req-123-456"
  }
}
```

### 4. Testing Considerations

- **Validate JSON structure** before sending requests
- **Check data types** match expected schema
- **Verify required fields** are present
- **Test edge cases** (null values, empty arrays, etc.)
- **Validate against schema** when available
- **Test malformed JSON** handling

### 5. Performance Optimization

```json
{
  "users": [
    {
      "id": 1,
      "name": "John",
      "email": "john@example.com"
    }
  ],
  "pagination": {
    "page": 1,
    "limit": 20,
    "total": 100,
    "hasNext": true
  }
}
```

## Tools for JSON Testing

### 1. Postman

- Built-in JSON support
- Syntax highlighting and validation
- JSON Schema validation
- Environment variables
- Pre-request and test scripts

### 2. REST Client Extensions

- **VS Code**: REST Client extension
- **IntelliJ**: HTTP Client
- **Insomnia**: API testing tool
- **Thunder Client**: Lightweight REST client

### 3. JSON Editors and Validators

- **VS Code**: JSON extension
- **Online**: JSONLint.com, JSONFormatter.org
- **Browser**: Built-in JSON.parse() validation
- **Command line**: jq, jsonlint

### 4. Command Line Tools

```bash
# Validate JSON
echo '{"name": "John"}' | jq .

# Format JSON
jq . input.json > formatted.json

# Extract values
jq '.users[0].name' response.json

# Filter data
jq '.users[] | select(.age > 25)' users.json
```

### 5. Testing Libraries

```javascript
// JavaScript/Node.js
const Ajv = require('ajv');
const ajv = new Ajv();
const validate = ajv.compile(schema);
const valid = validate(data);

// Python
import jsonschema
jsonschema.validate(data, schema)

// Java
import com.fasterxml.jackson.databind.ObjectMapper;
ObjectMapper mapper = new ObjectMapper();
mapper.readValue(jsonString, MyClass.class);
```

## Advanced JSON Concepts

### 1. JSONPath

```json
{
  "store": {
    "book": [
      {
        "category": "reference",
        "author": "Nigel Rees",
        "title": "Sayings of the Century",
        "price": 8.95
      },
      {
        "category": "fiction",
        "author": "Evelyn Waugh",
        "title": "Sword of Honour",
        "price": 12.99
      }
    ]
  }
}
```

JSONPath expressions:

- `$.store.book[*].author` - All authors
- `$.store.book[?(@.price < 10)]` - Books under $10
- `$.store.book[0].title` - First book title

### 2. JSON Merge Patch

```json
// Original
{
  "name": "John",
  "age": 30,
  "address": {
    "city": "New York",
    "country": "USA"
  }
}

// Patch
{
  "age": 31,
  "address": {
    "city": "Boston"
  }
}

// Result
{
  "name": "John",
  "age": 31,
  "address": {
    "city": "Boston",
    "country": "USA"
  }
}
```

### 3. JSON Pointer

```json
{
  "users": [
    { "id": 1, "name": "John" },
    { "id": 2, "name": "Jane" }
  ]
}
```

JSON Pointer expressions:

- `/users/0/name` → "John"
- `/users/1/id` → 2
- `/users` → [{"id": 1, "name": "John"}, {"id": 2, "name": "Jane"}]

## Exercises

### Exercise 1: Create a User Profile JSON

Create a JSON document for a user profile with the following fields:

- Personal info (name, email, phone, date of birth)
- Address information (street, city, state, zip, country)
- Preferences (language, timezone, notifications)
- Social media links (optional array)

### Exercise 2: REST API Request/Response

Create a complete REST API interaction for creating a product:

- Request body with product details
- Expected response structure
- Error response for validation failures

### Exercise 3: Test Data Structure

Design a JSON structure for API test cases that includes:

- Test case metadata
- Request configuration
- Expected response
- Environment variables
- Assertion rules

### Exercise 4: JSON Schema

Create a JSON Schema for a blog post that validates:

- Required fields (title, content, author)
- Optional fields (tags, publishDate, featured)
- Data type constraints
- String length limits

---

## Summary

JSON is the modern standard for API data exchange, offering simplicity, efficiency, and broad language support. Understanding JSON structure, validation, and best practices is essential for effective API testing in today's web development landscape.

Key takeaways:

- JSON provides lightweight, human-readable data format
- Essential for REST API and GraphQL testing
- Requires proper syntax and validation
- JSON Schema enables robust validation
- Tools like Postman and jq support JSON testing
- Performance considerations for large datasets
