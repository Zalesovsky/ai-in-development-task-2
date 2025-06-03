# Getting Started

## Prerequisites
- Java 17 or higher
- PostgreSQL 14 or higher
- Maven 3.8 or higher
- Docker and Docker Compose (optional)

## Project Setup

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/ai-in-development-task-2.git
cd ai-in-development-task-2
```

### 2. Database Setup
```bash
# Using Docker
docker-compose up -d

# Or manually create database
createdb ai_in_development
```

### 3. Configuration
Create `application.yml` in `src/main/resources`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/ai_in_development
    username: your_username
    password: your_password
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: true

jwt:
  secret: your_jwt_secret_key
  expiration: 86400000 # 24 hours
```

### 4. Build and Run
```bash
# Build
mvn clean install

# Run
mvn spring-boot:run
```

## Quick Start
1. Start the application
2. Register a new user:
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"password123"}'
```
3. Login to get JWT token:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"password123"}'
```

## Next Steps
- Review [API Reference](./api-reference.md) for available endpoints
- Check [Security](./security.md) for authentication details
- See [Development Guide](./development.md) for contribution guidelines 