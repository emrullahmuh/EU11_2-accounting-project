# FinTracker Application

The `FinTracker Application` is a Maven-based Spring Boot application, leveraging version `2.7.6` of Spring Boot and Java `11`. It's designed to provide accounting functionalities and integrates with tools and platforms like PostgreSQL, Docker, and AWS.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java 11
- Maven
- Docker
- Access to PostgreSQL (locally or through a service)


## Setup & Running the Service

Depending on your operating system, follow the appropriate steps to get the service up and running:

### For Windows Users:

1. Navigate to the project root directory in your terminal.
2. Run the following command:
   ```bash docker-compose -f docker-compose-win.yaml up --remove-orphans```

### For Mac Users:

1. Navigate to the project root directory in your terminal.
2. Run the following command:
   ```bash docker compose up --remove-orphans -f docker-compose-mac.yaml ```



---

# Java and Spring Boot Coding Standards

## 1. Code Formatting

- **Indentation:**
    - Use 4 spaces for indentation, not tabs.
- **Braces:**
    - Always use braces, even for single statement blocks.
- **Line Length:**
    - Keep lines to a maximum of 120 characters.
- **Whitespace:**
    - Avoid trailing whitespaces.

## 2. Naming Conventions

- **Classes:**
    - Start with an uppercase and use CamelCase.
    - *Example:* `UserService`
- **Branching Strategy:**
    - use jira ticket names.
    - *Example:* `EU11-34`
- ** Commit Messages:**
    - EU11-34: Add company entity and dto
- **Methods:**
    - Start with a lowercase and use camelCase.
    - *Example:* `getUserData()`
- **Variables:**
    - Use meaningful names and avoid single-letter names (except for loop indexes).
    - *Example:* `userList`, `accountStatus`
- **Constants:**
    - Use uppercase with underscores.
    - *Example:* `MAX_RETRY_COUNT`

## 3. Comments

- Write meaningful comments and avoid obvious comments.
- Use Javadoc style comments for classes and methods.
- Comment any code that might appear non-trivial or has business implications.

## 4. Spring Boot Specifics

- **Annotations:**
    - Place Spring Boot's annotations in this order: `@SpringBootApplication`, `@RestController`, `@RequestMapping`, etc.
- **Property Injection:**
    - Use constructor injection over field injection for better testability.
- **Exception Handling:**
    - Use `@ControllerAdvice` and `@ExceptionHandler` to handle exceptions globally.

## 5. General Guidelines

- **Single Responsibility Principle:**
    - A class should have only one reason to change.
- **Avoid Magic Numbers:**
    - Instead of hardcoding numbers, use named constants.
- **Null Safety:**
    - Always check for `null` before using an object or provide default values using `Optional`.
- **Logging:**
    - Use appropriate logging levels (INFO, DEBUG, ERROR) and always log exceptions.
- **Unit Testing:**
    - Always write unit tests for your service layers. Aim for a high code coverage.

## 6. Dependencies

- Keep your dependencies up to date.
- Avoid using deprecated libraries or methods.
- Use Maven or Gradle's `scope` to ensure runtime-only libraries don't get bundled at compile-time.

## 7. Database

- **Naming:**
    - Use snake_case for table and column names.
- **Indexes:**
    - Always index foreign keys and frequently searched fields.
- **Lazy Loading:**
    - Be cautious when using lazy loading with Hibernate to avoid N+1 problems.

---
