# AlphaWork Backend - Build Instructions

## Prerequisites

- Java 21 or higher
- Maven 3.9+
- PostgreSQL 15+
- Docker & Docker Compose (for containerized deployment)

## Project Structure

```
alphawork/backend/
├── src/
│   ├── main/java/com/alphawork/backend/
│   │   ├── AlphaWorkApplication.java
│   │   ├── controller/       # REST API Controllers
│   │   ├── service/          # Business Logic
│   │   ├── domain/entity/    # JPA Entities
│   │   ├── repository/       # Data Access Layer
│   │   ├── config/           # Spring Configuration
│   │   ├── security/         # JWT & Security
│   │   └── dto/              # Data Transfer Objects
│   ├── resources/
│   │   ├── application.properties
│   │   └── db/migration/     # Flyway SQL Migrations
│   └── test/java/           # Unit Tests
├── pom.xml
├── Dockerfile
└── README.md
```

## Local Development Setup

### 1. Start PostgreSQL

```bash
# Using Docker
docker run --name alphawork-db \
  -e POSTGRES_DB=alphawork \
  -e POSTGRES_USER=alphawork \
  -e POSTGRES_PASSWORD=alphawork \
  -p 5432:5432 \
  -d postgres:15
```

### 2. Build the Backend

```bash
cd alphawork/backend
mvn clean install
```

### 3. Run the Backend

```bash
mvn spring-boot:run
```

The backend will start on http://localhost:8080

### 4. Access API Documentation

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## Key Technologies

| Layer | Technology | Version |
|-------|-----------|---------|
| Framework | Spring Boot | 3.5.9 |
| Language | Java | 21 |
| Database | PostgreSQL | 15 |
| Migrations | Flyway | Latest |
| Authentication | JWT (JJWT) | 0.12.3 |
| WebSocket | Spring WebSocket + STOMP | Built-in |
| Documentation | SpringDoc OpenAPI | 2.0.2 |
| ORM | Spring Data JPA + Hibernate | Built-in |

## API Endpoints

### Authentication
- `POST /api/auth/login` - Login with email/password
- `GET /api/health` - Health check

### Projects
- `POST /api/projects` - Create project (ORG_HEAD only)
- `GET /api/projects` - List projects
- `GET /api/projects/{id}` - Get project details
- `PUT /api/projects/{id}` - Update project
- `DELETE /api/projects/{id}` - Delete project

### Issues
- `POST /api/issues` - Create issue
- `GET /api/issues/board/{boardId}` - Get issues by board
- `GET /api/issues/sprint/{sprintId}` - Get issues by sprint
- `GET /api/issues/{id}` - Get issue details
- `PUT /api/issues/{id}` - Update issue
- `PATCH /api/issues/{id}/status` - Update issue status
- `DELETE /api/issues/{id}` - Delete issue

## Docker Deployment

### Build Docker Image

```bash
docker build -t alphawork-backend:1.0.0 .
```

### Run with Docker Compose

From the `infra/` directory:

```bash
docker-compose up -d
```

This will start:
- PostgreSQL on port 5432
- Backend on port 8080
- Frontend on port 4200
- Nginx reverse proxy on ports 80/443

## Database Migrations

Flyway automatically runs SQL migrations from `src/main/resources/db/migration/`

Migration files follow the naming convention: `V{version}__{description}.sql`

Current migrations:
- `V1__Initial_Schema.sql` - Creates all tables and indices

To add a new migration:
1. Create file `V{number}__{description}.sql` in `db/migration/`
2. Restart the application - Flyway will execute it automatically

## Security

### JWT Token

Tokens are issued on successful login and must be included in requests:

```
Authorization: Bearer <token>
```

### Default Configuration

- Token expiration: 24 hours
- Password encoding: BCrypt
- CORS: Enabled for localhost:4200, localhost:3000, localhost
- HTTPS: Enforced in production

### Role-Based Access Control (RBAC)

| Role | Permissions |
|------|-----------|
| ORG_HEAD | Create projects, boards, sprints; manage users; view audit logs |
| TEAM_LEAD | Create sprints, assign tasks; view team audit logs |
| USER | Move own tasks, log time, view boards |

## WebSocket

Real-time updates use STOMP protocol over WebSocket:

- Endpoint: `ws://localhost:8080/ws/alphawork`
- Message topics:
  - `/topic/board/{boardId}` - Board updates
  - `/topic/issue/{issueId}` - Issue updates
  - `/topic/sprint/{sprintId}` - Sprint updates

## Testing

```bash
# Run all tests
mvn test

# Run specific test
mvn test -Dtest=AuthServiceTest

# Generate test coverage
mvn test jacoco:report
```

## Troubleshooting

### Database Connection Error

```
Check:
- PostgreSQL is running on localhost:5432
- Credentials match (alphawork/alphawork)
- Database 'alphawork' exists
```

### Port Already in Use

```bash
# Kill process on port 8080
lsof -ti:8080 | xargs kill -9
```

### JWT Token Invalid

- Verify token has not expired (24 hours)
- Check Authorization header format: `Bearer <token>`
- Ensure JWT secret matches configuration

## Performance Tuning

1. **Database Indices**: All critical columns are indexed
2. **Connection Pooling**: HikariCP (default in Spring Boot)
3. **Query Optimization**: Use projections for large datasets
4. **Caching**: Can be added via Spring Cache abstraction

## Monitoring

Access health and metrics:

```
GET /actuator/health - Health status
GET /actuator/metrics - Application metrics
```

## Production Deployment

1. Update `application.properties` with production database credentials
2. Set environment variable: `JWT_SECRET=<strong-secret>`
3. Build Docker image: `docker build -t alphawork-backend:1.0.0 .`
4. Push to registry and deploy using orchestration tool (K8s, ECS, etc.)

## License

MIT License

## Support

For issues and questions, create an issue in the project repository.
