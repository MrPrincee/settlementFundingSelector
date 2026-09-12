# Settlement Funding Selector

A Spring Boot REST API for selecting correspondent settlement instructions based on the available settlement balance.

The application selects the combination of instructions that maximizes the total expected fee without exceeding the available settlement balance.

## Technologies

- Java 17
- Spring Boot 4.1.1
- Spring Data JPA
- PostgreSQL 17
- Flyway
- Docker Compose
- Maven
- JUnit 5
- Mockito

## How It Works

Each funding request contains:

- available settlement balance
- candidate settlement instructions
- instruction amount
- expected fee
- instruction reference

The application uses a 0/1 knapsack-style algorithm.

Each instruction can either be fully selected or not selected. Partial funding is not allowed.

The goal is to maximize the total expected fee while keeping the total settlement amount within the available balance.

Every request and all candidate instructions are stored in PostgreSQL for audit purposes. Each instruction is stored with a `selected` flag.

## Database

The application uses PostgreSQL.

Start the database with:

```bash
docker compose up -d
```

Database configuration:

```text
Database: settlement_db
Username: settlement_user
Password: settlement_password
Port: 5432
```

Flyway manages the database schema.

The project contains migrations for:

- settlement request and instruction tables
- database indexes

The following indexes are used:

- `settlement_instructions(request_id)` for retrieving instructions belonging to a settlement request
- `settlement_requests(created_at desc)` for newest-first pagination

## Running the Application

Make sure PostgreSQL is running:

```bash
docker compose up -d
```

Run the application:

```bash
./mvnw spring-boot:run
```

On Windows PowerShell:

```powershell
.\mvnw spring-boot:run
```

The API runs on:

```text
http://localhost:8080
```

## API Endpoints

### Fund Settlement Instructions

```http
POST /api/v1/settlement/fund
```

Example request:

```json
{
  "availableSettlementBalance": 20000,
  "candidateInstructions": [
    {
      "instructionReference": "INS-2001",
      "instructionAmount": 7000,
      "expectedFee": 150
    },
    {
      "instructionReference": "INS-2002",
      "instructionAmount": 9000,
      "expectedFee": 210
    },
    {
      "instructionReference": "INS-2003",
      "instructionAmount": 4000,
      "expectedFee": 90
    },
    {
      "instructionReference": "INS-2004",
      "instructionAmount": 6000,
      "expectedFee": 130
    }
  ]
}
```

For this request, the selected instructions consume `20000` and produce a total expected fee of `450`.

A successful request with selected instructions returns HTTP `201 Created`.

If no instruction can be selected, the API returns HTTP `200 OK` with an empty selected instruction list and zero totals.

### Get Settlement Request

```http
GET /api/v1/settlement/{requestId}
```

Returns a previously processed settlement request.

If the request does not exist, the API returns HTTP `404 Not Found`.

### List Settlement Requests

```http
GET /api/v1/settlement?page=0&size=10
```

Returns settlement requests using pagination, ordered from newest to oldest.

`page` must be greater than or equal to `0` and `size` must be greater than `0`.

## Validation

Invalid request data returns HTTP `400 Bad Request`.

Examples include:

- negative settlement balance
- negative instruction amount
- blank instruction reference
- missing required fields
- monetary values with more than two decimal places
- invalid pagination parameters

## Tests

The project contains:

- funding algorithm unit tests
- service unit tests
- Spring Boot context test
- PostgreSQL integration test

Make sure the PostgreSQL Docker container is running before running the complete test suite.

Run tests with:

```powershell
.\mvnw test
```

## Build

Create the executable JAR with:

```powershell
.\mvnw clean package
```

The generated JAR will be available in the `target` directory.