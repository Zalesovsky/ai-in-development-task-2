# Test Rules

## General Requirements
- Minimum code coverage: 70% for core package
- All tests must be in the appropriate package structure matching the source code
- Use meaningful test names that describe the test scenario
- Follow the AAA pattern (Arrange, Act, Assert)

## Test Categories

### 1. Service Layer Tests
- Must use `@ExtendWith(MockitoExtension.class)`
- Mock all dependencies using `@Mock`
- Inject mocks using `@InjectMocks`
- Test both positive and negative scenarios
- Verify mock interactions when necessary
- Example structure:
```java
@ExtendWith(MockitoExtension.class)
class ServiceTest {
    @Mock
    private Repository repository;
    
    @InjectMocks
    private Service service;
    
    @Test
    void shouldDoSomething() {
        // Arrange
        // Act
        // Assert
    }
}
```

### 2. Controller Layer Tests
- Use `@WebMvcTest` for controller testing
- Mock service layer dependencies
- Test all HTTP methods (GET, POST, PUT, DELETE)
- Test response status codes and content
- Use `MockMvc` for HTTP request simulation
- Example structure:
```java
@WebMvcTest(Controller.class)
class ControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private Service service;
    
    @Test
    void shouldHandleRequest() {
        // Arrange
        // Act
        // Assert
    }
}
```

### 3. Integration Tests
- Extend `AbstractIntegrationTest` or `AbstractAuthenticatedIntegrationTest`
- Use `@AutoConfigureMockMvc`
- Test complete request flow
- Use H2 in-memory database
- Test with real dependencies
- Example structure:
```java
@AutoConfigureMockMvc
class IntegrationTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void shouldProcessCompleteFlow() {
        // Arrange
        // Act
        // Assert
    }
}
```

### 4. Security Tests
- Use `TestSecurityConfig` for security configuration
- Test authentication and authorization
- Test JWT token handling
- Test protected endpoints
- Example structure:
```java
@WebMvcTest
@Import(TestSecurityConfig.class)
class SecurityTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void shouldHandleAuthentication() {
        // Arrange
        // Act
        // Assert
    }
}
```

## Test Naming Conventions
- Use descriptive names that explain the test scenario
- Follow the pattern: `should[ExpectedBehavior]_when[Condition]`
- Examples:
  - `shouldReturnUser_whenUserExists`
  - `shouldThrowException_whenUserNotFound`
  - `shouldCreateNewUser_whenValidDataProvided`

## Assertions
- Use specific assertions from JUnit Jupiter
- Use Mockito for verifying mock interactions
- Use appropriate assertion methods:
  - `assertEquals` for exact matches
  - `assertNotNull` for null checks
  - `assertThrows` for exception testing
  - `verify` for mock interaction verification

## Test Data
- Use test data builders or factory methods
- Keep test data close to the test
- Use meaningful test data values
- Consider using `@BeforeEach` for common test data setup

## Code Coverage Requirements
- Minimum 70% line coverage for core package
- Focus on business logic coverage
- Cover both success and failure scenarios
- Include edge cases in test coverage

## Best Practices
- Keep tests independent
- Don't share state between tests
- Clean up resources after tests
- Use appropriate test annotations
- Follow the single responsibility principle for test methods
- Document complex test scenarios with comments 