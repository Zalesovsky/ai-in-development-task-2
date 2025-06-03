# AI in Development Task 2

## Overview
This project is a Spring Boot application that implements a user management system with JWT authentication.

## Documentation
Detailed documentation is available in the [docs](./docs) directory:

- [Getting Started](./docs/getting-started.md) - Setup and quick start guide
- [Architecture](./docs/architecture.md) - System architecture and components
- [API Reference](./docs/api-reference.md) - API endpoints and usage
- [Security](./docs/security.md) - Security implementation and configuration
- [Database](./docs/database.md) - Database schema and management
- [Development Guide](./docs/development.md) - Development workflow and guidelines

## Features
- User registration and authentication
- JWT-based security
- RESTful API
- PostgreSQL database
- Docker support

## Prerequisites
- Java 17
- PostgreSQL 14
- Maven 3.8
- Docker (optional)

## Quick Start
1. Clone the repository
2. Configure `application.yml`
3. Run the application:
```bash
mvn spring-boot:run
```

## API Endpoints
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - User login
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user

## Security
- JWT authentication
- Password encryption
- Rate limiting
- Security headers

## Database
- PostgreSQL
- Flyway migrations
- Connection pooling

## Development
See [Development Guide](./docs/development.md) for:
- Project setup
- Code style
- Testing
- Deployment

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
