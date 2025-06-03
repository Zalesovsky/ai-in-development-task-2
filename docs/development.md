# Development Guide

## Development Environment Setup

### Required Tools
- JDK 17
- Maven 3.8+
- PostgreSQL 14+
- Docker & Docker Compose
- IDE (IntelliJ IDEA recommended)

### IDE Configuration
1. Install Lombok plugin
2. Enable annotation processing
3. Configure code style:
   - Use 4 spaces for indentation
   - Use UTF-8 encoding
   - Enable auto-imports

## Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/example/
│   │       ├── controller/
│   │       ├── service/
│   │       ├── repository/
│   │       ├── model/
│   │       ├── dto/
│   │       ├── security/
│   │       └── config/
│   └── resources/
│       ├── application.yml
│       └── db/migration/
└── test/
    └── java/
        └── com/example/
```

## Development Workflow

### 1. Branch Strategy
- `main` - production branch
- `develop` - development branch
- `feature/*` - feature branches
- `bugfix/*` - bug fix branches

### 2. Commit Guidelines
```
<type>(<scope>): <subject>

<body>

<footer>
```

Types:
- feat: new feature
- fix: bug fix
- docs: documentation
- style: formatting
- refactor: code restructuring
- test: adding tests
- chore: maintenance

### 3. Code Review Process
1. Create pull request
2. Self-review
3. Request review
4. Address feedback
5. Merge after approval

## Testing

### Unit Tests
```java
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    
    @MockBean
    private UserRepository userRepository;
    
    @Test
    void whenValidUser_thenSaveSucceeds() {
        // test implementation
    }
}
```

### Integration Tests
```java
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void whenValidInput_thenReturns200() throws Exception {
        // test implementation
    }
}
```

## Code Quality

### Static Analysis
- SonarQube
- Checkstyle
- PMD

### Code Coverage
- JaCoCo
- Minimum coverage: 80%

## Performance Testing
- JMeter for load testing
- JProfiler for profiling
- Metrics collection

## Documentation
- Keep README up to date
- Document API changes
- Update database schema
- Maintain changelog

## Deployment
1. Build:
```bash
mvn clean package
```

2. Run:
```bash
java -jar target/application.jar
```

3. Docker:
```bash
docker build -t app .
docker run -p 8080:8080 app
```

## Troubleshooting
- Check logs in `logs/application.log`
- Monitor application metrics
- Review database performance
- Check security logs 