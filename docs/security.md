# Security

## Authentication

### JWT Authentication
The application uses JSON Web Tokens (JWT) for authentication. The token contains:
- User ID
- Username
- Expiration time
- Issued at time

### Token Structure
```json
{
    "sub": "user_id",
    "username": "username",
    "iat": "issued_at_timestamp",
    "exp": "expiration_timestamp"
}
```

## Security Configuration

### Password Security
- Passwords are hashed using BCrypt
- Minimum password length: 8 characters
- Password must contain at least:
  - One uppercase letter
  - One lowercase letter
  - One number
  - One special character

### JWT Configuration
```yaml
jwt:
  secret: ${JWT_SECRET}
  expiration: 86400000 # 24 hours
```

### Security Headers
The application includes the following security headers:
- `X-Content-Type-Options: nosniff`
- `X-Frame-Options: DENY`
- `X-XSS-Protection: 1; mode=block`
- `Strict-Transport-Security: max-age=31536000; includeSubDomains`

## Protected Endpoints
All endpoints except the following require authentication:
- `POST /api/auth/register`
- `POST /api/auth/login`

## Rate Limiting
To prevent brute force attacks:
- 5 failed login attempts per minute per IP
- 100 requests per minute per IP
- 1000 requests per hour per user

## Best Practices
1. Always use HTTPS in production
2. Store JWT secret in environment variables
3. Implement token refresh mechanism
4. Log security events
5. Regular security audits
6. Keep dependencies updated

## Security Headers Implementation
```java
@Configuration
public class SecurityHeadersConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        return http
            .headers(headers -> headers
                .xssProtection(xss -> xss.disable())
                .contentSecurityPolicy(csp -> csp
                    .policyDirectives("default-src 'self'"))
            )
            .build();
    }
}
```

## Error Handling
Security-related errors return appropriate HTTP status codes:
- 401: Unauthorized (invalid/missing token)
- 403: Forbidden (insufficient permissions)
- 429: Too Many Requests (rate limit exceeded) 