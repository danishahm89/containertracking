# Container Tracking Service

A small Spring Boot REST microservice for registering shipping containers and tracking their status.

## Features

- Register a container (status starts as `NOT_STARTED`)
- Look up a container by its tracking id
- Update a container status
- Created and last-updated timestamps filled automatically with Spring Data JPA auditing
- Unit tests for the controller layer with MockMvc and Mockito

## Tech stack

- Java 17
- Spring Boot 3.3.11 (Web, Data JPA, Actuator)
- H2 in-memory database (local development)
- Lombok
- JUnit 4, Mockito, Spring MockMvc
- Maven

## API

Base path: `/containers` (default port `9090`)

| Method | Path | Description | Success response |
|---|---|---|---|
| POST | `/containers` | Create a container | `201 Created` with the saved container |
| GET | `/containers/{containerId}` | Get a container by tracking id | `200 OK`, or `404 Not Found` |
| PUT | `/containers/{containerId}/status/{status}` | Update the container status | `200 OK` |

### Container fields

| Field | Type | Notes |
|---|---|---|
| `trackingId` | int | Generated id |
| `containerName` | string | |
| `containerType` | string | |
| `status` | string | Set to `NOT_STARTED` on creation |
| `createdAt` | timestamp | Set automatically |
| `updatedAt` | timestamp | Set automatically |

## Run locally

Requirements: JDK 17 and Maven.

```bash
mvn spring-boot:run
```

Example calls:

```bash
# Create a container
curl -X POST http://localhost:9090/containers \
  -H "Content-Type: application/json" \
  -d '{"containerName":"MSKU1234567","containerType":"20FT"}'

# Get it
curl http://localhost:9090/containers/1

# Update its status
curl -X PUT http://localhost:9090/containers/1/status/LOADED
```

The H2 console is available at http://localhost:9090/h2-console (JDBC URL: `jdbc:h2:mem:testdb`, user: `sa`, empty password).

## Run the tests

```bash
mvn test
```

## Project structure

```
src/main/java/com/service/containertracking
├── ContainertrackingApplication.java
├── controller/ContainerController.java
├── service/ContainerService.java
├── repository/ContainerRepo.java
└── entity/Container.java
src/test/java/com/service/containertracking/controller
└── ContainerControllerTest.java
```

## Possible improvements

- Return `404 Not Found` when updating a container that does not exist
- Validate allowed status values (for example with an enum)
- Add a list endpoint with pagination
- Use constructor injection instead of field injection
- Move the tests to JUnit 5
- Replace the in-memory H2 database with PostgreSQL for deployment
