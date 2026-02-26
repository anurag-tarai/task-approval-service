# Task Approval Service

A secure backend service for managing tasks with an approval workflow.

## Tech Stack

- Java 17
- Spring Boot
- Spring Security (JWT)
- JPA / Hibernate
- MySQL
- Maven
- Docker
- SpringDoc OpenAPI (Swagger)

---

## Features

- JWT-based authentication
- Role-based access control (USER / ADMIN)
- Task approval workflow
- Business rule enforcement
- Basic analytics endpoints
- Health check endpoint
- Dockerized setup
- Swagger API documentation

---

## Running the Application

### Option 1: Run with Docker (Recommended)

1. Clone the repository:

```bash
git clone <repository-url>
cd <project-folder>
```

2. Build and start containers:

```bash
docker compose up --build
```

3. Stop containers:

```bash
docker compose down -v
```

Application runs at:

```
http://localhost:8080
```

---

### Option 2: Run Locally

1. Clone the repository:

```bash
git clone https://github.com/anurag-tarai/task-approval-service.git
cd task-approval-service
```

2. Set the following environment variables:

```
DATASOURCE_USERNAME=your_mysql_username
DATASOURCE_PASSWORD=your_mysql_password
SECURITY_JWT_SECRET=your_very_secure_32+_character_secret_key
SECURITY_JWT_EXPIRATION=86400000
```

3. Ensure MySQL is running locally.

4. Application configuration (`application.properties`):

```properties
spring.application.name=Task Approval Service

spring.datasource.url=jdbc:mysql://localhost:3306/vivatech_tasks?createDatabaseIfNotExist=true
spring.datasource.username=${DATASOURCE_USERNAME}
spring.datasource.password=${DATASOURCE_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT Configuration
security.jwt.secret=${SECURITY_JWT_SECRET}
security.jwt.expiration=${SECURITY_JWT_EXPIRATION}
```

5. Build and run:

```bash
mvn clean install
mvn spring-boot:run
```

Application runs at:

```
http://localhost:8080
```

---

## Roles

### USER
- Create tasks
- View their own tasks

### ADMIN
- View all tasks
- Approve tasks
- Reject tasks

---

## Task Entity

- id
- taskNumber (e.g., TASK-0001)
- title
- description
- status (CREATED → APPROVED / REJECTED)
- createdBy
- createdAt
- updatedAt

---

## Authentication APIs

| Method | Endpoint |
|--------|----------|
| POST | /api/v1/auth/register |
| POST | /api/v1/auth/login |

---

## Task APIs

| Method | Endpoint | Access |
|--------|----------|--------|
| POST | /api/v1/tasks | USER only |
| GET | /api/v1/tasks | USER (own), ADMIN (all) |
| PUT | /api/v1/tasks/{id}/approve | ADMIN only |
| PUT | /api/v1/tasks/{id}/reject | ADMIN only |

---

## Analytics APIs

| Method | Endpoint |
|--------|----------|
| GET | /api/v1/analytics/tasks-by-status |
| GET | /api/v1/analytics/daily-task-count |

---

## Health Check

| Method | Endpoint |
|--------|----------|
| GET | /health-check |

---

## Business Rules

- USER cannot approve or reject tasks
- ADMIN cannot create tasks
- Approved tasks cannot be rejected
- Rejected tasks cannot be approved
- Role enforcement handled using Spring Security

---

## Swagger API Documentation

Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

OpenAPI docs:

```
http://localhost:8080/v3/api-docs
```

---

## Security

- Authentication via JWT
- Passwords encrypted using BCrypt
- Role-based authorization using Spring Security