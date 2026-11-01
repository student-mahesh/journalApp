# Journal App — Spring Boot Backend

A backend application built with **Java, Spring Boot, Spring Data MongoDB, and Spring Security**.

Journal App demonstrates backend development concepts including REST API development, MongoDB persistence, JWT-based authentication, role-based authorization, secure password handling, application configuration, logging, and automated testing.

The project focuses on building a structured and secure backend using a layered architecture.

## Features

- RESTful backend application using Spring Boot.
- MongoDB integration using Spring Data MongoDB.
- User and journal-entry data persistence.
- User registration and login.
- JWT-based authentication.
- Password hashing using BCrypt.
- Role-based authorization with `USER` and `ADMIN` roles.
- Protected endpoints using Spring Security.
- MongoDB document references using `@DBRef`, where configured.
- Transaction management for operations requiring coordinated data changes.
- YAML-based application configuration.
- Environment-specific configuration using Spring Profiles.
- Application logging using SLF4J.
- Automated testing using JUnit and Mockito.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java | Programming language |
| Spring Boot | Backend application framework |
| Spring Web | REST API development |
| Spring Data MongoDB | Database access |
| MongoDB | NoSQL database |
| Spring Security | Authentication and authorization |
| JWT | Stateless authentication |
| BCrypt | Password hashing |
| Maven | Dependency and build management |
| YAML | Application configuration |
| SLF4J | Application logging |
| JUnit | Automated testing |
| Mockito | Dependency mocking |

## Architecture

The application follows a layered backend architecture to separate API handling, business logic, and database operations.

```text
             Client
        (Postman / Frontend)
                 |
                 v
          REST Controller
                 |
                 v
              Service
           (Business Logic)
                 |
                 v
            Repository
                 |
                 v
              MongoDB


       Spring Security + JWT
                 |
                 v
       Authentication and
          Authorization
```

### Application Layers

- **Controller Layer:** Handles incoming HTTP requests and returns API responses.
- **Service Layer:** Contains business logic and coordinates application operations.
- **Repository Layer:** Communicates with MongoDB through Spring Data MongoDB.
- **Model Layer:** Represents user and journal-entry documents.
- **Security Layer:** Handles authentication, JWT validation, password hashing, and authorization.
- **Configuration Layer:** Manages application, database, and security configuration.

## Authentication and Authorization

The application uses Spring Security and JWT for securing protected API endpoints.

### Authentication Flow

1. A user registers with the application.
2. The password is hashed using BCrypt before being stored.
3. The user logs in using their credentials.
4. After successful authentication, a JWT is issued.
5. The client sends the token with subsequent protected requests.
6. Spring Security validates the token and applies the configured authorization rules.

Example authorization header:

```http
Authorization: Bearer <your-jwt-token>
```

### User Roles

| Role | Description |
|---|---|
| USER | Access to endpoints permitted for normal users |
| ADMIN | Access to endpoints permitted for administrators |

Access permissions are controlled by the application's security configuration.

## Database

The application uses **MongoDB** as its database.

Spring Data MongoDB provides repository support for database operations. MongoDB documents represent application data, with document references using `@DBRef` where configured.

Database connection details and credentials should be supplied through external configuration or environment variables.

## Configuration

Application configuration is maintained using YAML files.

Configuration includes:

- MongoDB connection settings.
- Application and server settings.
- JWT-related configuration.
- Spring Security settings.
- Environment-specific properties.

Spring Profiles can be used to maintain separate configurations for development and other environments.

Sensitive credentials and signing secrets should never be committed to the repository.

## Logging

SLF4J is used for application logging to help trace application execution, investigate errors, and monitor important backend events.

Sensitive information such as passwords, JWT tokens, and database credentials should not be included in logs.

## Testing

The project uses:

- **JUnit:** For writing and executing automated tests.
- **Mockito:** For mocking dependencies and isolating unit tests.

Run tests using Maven:

**Windows**
```bash
mvnw.cmd test
```

**macOS / Linux**
```bash
./mvnw test
```

## Getting Started

### Prerequisites

- Java JDK compatible with the project's configured Java version.
- MongoDB.
- Git.
- Maven or the included Maven Wrapper.

### 1. Clone the repository

```bash
git clone https://github.com/student-mahesh/journalApp.git
cd journalApp
```

### 2. Configure MongoDB

Configure the MongoDB connection URI and database name in the appropriate YAML configuration or environment variables.

### 3. Configure application secrets

Provide the JWT signing secret and other required sensitive configuration through environment variables or a secure configuration mechanism.

Do not commit production credentials or secrets.

### 4. Run the application

**Windows**
```bash
mvnw.cmd spring-boot:run
```

**macOS / Linux**
```bash
./mvnw spring-boot:run
```

The application starts using the active Spring profile and configured server port.

## API Testing

The backend APIs can be tested using Postman or another HTTP client.

For protected endpoints:

1. Register a test user.
2. Log in using valid credentials.
3. Obtain the JWT.
4. Add the JWT as a Bearer token.
5. Access endpoints permitted for the authenticated user's role.

> API endpoint paths, request examples, response examples, and HTTP status codes will be documented here after verifying the implemented controllers.

## Future Improvements

Potential improvements for future development:

- OpenAPI / Swagger API documentation.
- Docker-based application setup.
- GitHub Actions CI pipeline.
- Centralized exception handling.
- Consistent API response structures.
- Expanded integration and security testing.
- Frontend integration.

## Author

**Mahesh Savkar**

- GitHub: [student-mahesh](https://github.com/student-mahesh)
- LinkedIn: [mahesh-savkar](https://www.linkedin.com/in/mahesh-savkar-300377294/)

---

*This repository currently focuses on backend implementation. Features and API documentation should be updated as the project evolves.*
