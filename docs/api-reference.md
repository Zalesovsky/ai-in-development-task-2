# API Reference

## Authentication Endpoints

### Register User
```http
POST /api/auth/register
Content-Type: application/json

{
    "username": "string",
    "password": "string"
}
```

**Response**
```json
{
    "data": {
        "id": "number",
        "username": "string"
    },
    "message": "User registered successfully",
    "status": "SUCCESS"
}
```

### Login
```http
POST /api/auth/login
Content-Type: application/json

{
    "username": "string",
    "password": "string"
}
```

**Response**
```json
{
    "data": {
        "token": "string",
        "type": "Bearer"
    },
    "message": "Login successful",
    "status": "SUCCESS"
}
```

## User Endpoints

### Get User by ID
```http
GET /api/users/{id}
Authorization: Bearer {token}
```

**Response**
```json
{
    "data": {
        "id": "number",
        "username": "string"
    },
    "message": "User retrieved successfully",
    "status": "SUCCESS"
}
```

### Update User
```http
PUT /api/users/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
    "username": "string",
    "password": "string"
}
```

**Response**
```json
{
    "data": {
        "id": "number",
        "username": "string"
    },
    "message": "User updated successfully",
    "status": "SUCCESS"
}
```

## Error Responses

### 400 Bad Request
```json
{
    "message": "Invalid request parameters",
    "status": "ERROR"
}
```

### 401 Unauthorized
```json
{
    "message": "Invalid or expired token",
    "status": "ERROR"
}
```

### 404 Not Found
```json
{
    "message": "Resource not found",
    "status": "ERROR"
}
```

## Authentication
All protected endpoints require a valid JWT token in the Authorization header:
```http
Authorization: Bearer {token}
```

## Rate Limiting
- 100 requests per minute per IP
- 1000 requests per hour per user

## Response Format
All responses follow this structure:
```json
{
    "data": {}, // Response data (optional)
    "message": "string", // Status message
    "status": "SUCCESS|ERROR" // Response status
}
``` 