# Database

## Overview
The application uses PostgreSQL as its primary database. The database schema is managed using Flyway migrations.

## Schema

### Users Table
```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

## Migrations
Migrations are located in `src/main/resources/db/migration`:
- `V1__create_users_table.sql`
- `V2__add_user_roles.sql`

## Database Configuration
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/ai_in_development
    username: ${DB_USER}
    password: ${DB_PASS}
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: true
    properties:
      hibernate:
        format_sql: true
```

## Connection Pool
The application uses HikariCP for connection pooling:
```yaml
spring:
  datasource:
    hikari:
      maximum-pool-size: 10
      minimum-idle: 5
      idle-timeout: 300000
      connection-timeout: 20000
```

## Indexes
- Primary Key: `users.id`
- Unique Index: `users.username`

## Backup and Recovery
1. Regular backups:
```bash
pg_dump -U username -d ai_in_development > backup.sql
```

2. Restore from backup:
```bash
psql -U username -d ai_in_development < backup.sql
```

## Performance Optimization
1. Connection pooling
2. Prepared statements
3. Proper indexing
4. Regular VACUUM
5. Query optimization

## Monitoring
- Connection pool metrics
- Query performance
- Table statistics
- Index usage

## Best Practices
1. Use migrations for schema changes
2. Regular backups
3. Monitor performance
4. Optimize queries
5. Maintain indexes
6. Regular maintenance 