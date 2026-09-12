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

## Project Structure

The application is separated into the following layers:

- `controller` - REST API endpoints
- `service` - business logic and transaction handling
- `repository` - database access using Spring Data JPA
- `algorithm` - funding selection logic
- `entity` - database entities
- `dto` - API request and response models
- `exception` - API error handling

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

The algorithm stores the best funding option for each reachable settlement amount. Its practical complexity depends on the number of distinct reachable amounts. In the worst case, the number of possible states can grow significantly with the number of candidate instructions.

Every request and all candidate instructions are stored in PostgreSQL for audit purposes. Each instruction is stored with a `selected` flag.

## Database

The application uses PostgreSQL 17 running in Docker.

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

Flyway manages the database schema and migrations.

The database contains two main tables:

- `settlement_requests` stores the request ID, available balance, total settlement consumed, total expected fee and creation time.
- `settlement_instructions` stores every candidate instruction, its amount, expected fee, reference, related request and whether it was selected.

The following indexes are used:

- `settlement_instructions(request_id)` improves retrieval of instructions belonging to a settlement request.
- `settlement_requests(created_at desc)` supports newest-first pagination.

## Running the Application

Make sure PostgreSQL is running:

```bash
docker compose up -d
```

Run the application with Maven:

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

### Running the Packaged JAR

Build the application:

```powershell
.\mvnw clean package
```

Run the generated executable JAR:

```powershell
java -jar target\settlementFundingSelector-0.0.1-SNAPSHOT.jar
```

Make sure the PostgreSQL container is running before starting the JAR.

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

## cURL Examples

### Fund Settlement Instructions

```bash
curl -X POST http://localhost:8080/api/v1/settlement/fund \
  -H "Content-Type: application/json" \
  -d '{
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
  }'
```

### Get Settlement Request

Replace `{requestId}` with the UUID returned by the funding endpoint.

```bash
curl http://localhost:8080/api/v1/settlement/{requestId}
```

### List Settlement Requests

```bash
curl "http://localhost:8080/api/v1/settlement?page=0&size=10"
```

## Validation

Invalid request data returns HTTP `400 Bad Request`.

Examples include:

- negative settlement balance
- negative instruction amount
- blank instruction reference
- missing required fields
- monetary values with more than two decimal places
- invalid pagination parameters

A settlement balance of zero and an empty candidate instruction list are allowed. If no instruction is selected, the API returns HTTP `200 OK` with zero totals.

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

The complete test suite currently contains 6 tests.

## Build

Create the executable JAR with:

```powershell
.\mvnw clean package
```

The generated JAR is available at:

```text
target/settlementFundingSelector-0.0.1-SNAPSHOT.jar
```

Run it with:

```powershell
java -jar target\settlementFundingSelector-0.0.1-SNAPSHOT.jar
```