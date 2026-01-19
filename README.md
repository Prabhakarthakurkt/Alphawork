# AlphaWork - Full Stack Project

## Quick Start

### Prerequisites
- Java 21+
- Node.js 20+ LTS
- PostgreSQL 15+
- Docker & Docker Compose

### Development (Without Docker)

#### 1. Start PostgreSQL
```bash
# Mac/Linux
brew install postgresql
createdb alphawork

# Windows/Docker
docker run --name alphawork-db \
  -e POSTGRES_DB=alphawork \
  -e POSTGRES_USER=alphawork \
  -e POSTGRES_PASSWORD=alphawork \
  -p 5432:5432 \
  -d postgres:15
```

#### 2. Start Backend
```bash
cd alphawork/backend
mvn clean install
mvn spring-boot:run
# Backend: http://localhost:8080
# Swagger: http://localhost:8080/swagger-ui.html
```

#### 3. Start Frontend
```bash
cd frontend
npm install
npm start
# Frontend: http://localhost:4200
```

### Production (With Docker)

```bash
cd infra
docker-compose up -d
```

Services:
- Backend: http://localhost:8080
- Frontend: http://localhost:4200
- Database: localhost:5432
- Nginx: http://localhost:80

## Project Architecture

```
AlphaWork/
├── alphawork/
│   └── backend/                    # Spring Boot Backend
│       ├── src/main/java/          # Java source code
│       ├── src/main/resources/
│       │   └── db/migration/       # Flyway SQL scripts
│       ├── pom.xml                 # Maven configuration
│       ├── Dockerfile
│       └── README.md
├── frontend/                       # Angular Frontend
│       ├── src/app/
│       │   ├── services/           # API services
│       │   ├── interceptors/       # HTTP interceptors
│       │   └── components/         # Angular components
│       ├── package.json
│       ├── Dockerfile
│       └── README.md
├── infra/                          # Infrastructure
│       ├── docker-compose.yml
│       └── nginx.conf
└── README.md
```

## Database Schema

### Core Entities
- **User**: System users with roles
- **Organization**: Top-level organizational unit
- **Team**: Groups of users within organizations
- **Project**: Work items grouped by project
- **Board**: Scrum or Kanban board
- **Sprint**: Scrum sprint management
- **Issue**: Work items (Task, Story, Bug)
- **TimeLog**: Time tracking per issue
- **Note**: Comments on issues
- **AuditLog**: Complete change history

### Relationships
```
User
├── Organizations (M:M)
├── Teams (M:M)
├── Issues (Assigned)
├── TimeLogs (Author)
└── Notes (Author)

Organization
├── Users (M:M)
├── Teams (1:M)
├── Projects (1:M)
└── AuditLogs (1:M)

Project
├── Teams (M:M)
├── Boards (1:M)
├── Issues (1:M)
└── AuditLogs (1:M)

Board
├── Project (M:1)
├── Sprints (1:M)
└── Issues (1:M)

Sprint
├── Board (M:1)
└── Issues (1:M)

Issue
├── Project (M:1)
├── Board (M:1)
├── Sprint (0:1)
├── Assignee User (M:1)
├── TimeLogs (1:M)
└── Notes (1:M)
```

## API Documentation

### Base URL
- Development: `http://localhost:8080/api`
- Production: `https://api.alphawork.com/api`

### Authentication

Login endpoint:
```bash
POST /auth/login
{
  "email": "user@example.com",
  "password": "password123"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": "uuid",
    "email": "user@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "ORG_HEAD"
  }
}
```

Include token in requests:
```
Authorization: Bearer <token>
```

### Project Endpoints

```bash
# Create project
POST /projects
{
  "name": "Alpha Project",
  "description": "...",
  "startDate": "2024-01-17",
  "endDate": "2024-03-17"
}

# List projects
GET /projects

# Get project
GET /projects/{id}

# Update project
PUT /projects/{id}

# Delete project
DELETE /projects/{id}
```

### Issue Endpoints

```bash
# Create issue
POST /issues
{
  "title": "Fix login bug",
  "description": "...",
  "type": "BUG",
  "status": "TODO",
  "estimateHours": 4,
  "projectId": "uuid",
  "boardId": "uuid"
}

# Get board issues
GET /issues/board/{boardId}

# Update issue status
PATCH /issues/{id}/status?status=DOING

# Update issue
PUT /issues/{id}

# Delete issue
DELETE /issues/{id}
```

### WebSocket Events

Connect: `ws://localhost:8080/ws/alphawork`

Events:
- `ISSUE_UPDATED` - Issue status or content changed
- `ISSUE_MOVED` - Issue moved between columns/sprints
- `BOARD_UPDATED` - Board configuration changed
- `SPRINT_STARTED` - Sprint started
- `SPRINT_ENDED` - Sprint completed
- `USER_JOINED` - User joined board
- `USER_LEFT` - User left board

## Roles & Permissions

### ORG_HEAD (Administrator)
- ✅ Create/edit/delete projects
- ✅ Create/edit/delete boards
- ✅ Create/edit/delete sprints
- ✅ Create/edit/delete teams
- ✅ Manage users and roles
- ✅ Assign tasks to anyone
- ✅ View all audit logs
- ✅ Assign tasks to self

### TEAM_LEAD (Manager)
- ✅ Create/edit/delete sprints (own team)
- ✅ Assign tasks (own team)
- ✅ Move tasks
- ✅ View team audit logs
- ✅ Assign tasks to self
- ❌ Create projects
- ❌ Delete projects
- ❌ View org-wide audit logs

### USER (Contributor)
- ✅ Move own tasks
- ✅ Log time on tasks
- ✅ Add notes/comments
- ✅ View assigned issues
- ✅ Update task status
- ❌ Assign tasks
- ❌ Create sprints
- ❌ View audit logs

## Features

### 1. Authentication & Security
- JWT token-based auth
- BCrypt password hashing
- RBAC enforcement
- CORS configuration
- HTTPS support
- Rate limiting ready

### 2. Project Management
- Create projects with dates
- Board type selection (Scrum/Kanban)
- Team assignment
- Project status tracking
- Archive/restore capability

### 3. Issue Management
- 3 issue types: Task, Story, Bug
- 6 issue statuses: TODO, DOING, DONE, BLOCKED, REVIEW, QA
- Assignee assignment
- Time estimation (hours)
- Time tracking
- Attachments support
- Comments/notes system

### 4. Kanban Board
- 3 columns (TODO, DOING, DONE)
- Drag-and-drop interface
- Real-time sync
- Configurable workflows
- Issue ordering per column

### 5. Scrum Board
- Sprint planning view
- Backlog management
- Sprint goals
- Velocity tracking
- Sprint reports
- Burndown charts (ready)

### 6. Real-Time Collaboration
- WebSocket live updates
- Activity feed
- Presence awareness
- Collaborative editing
- Change notifications

### 7. Time Tracking
- Log time per issue
- View time logs
- Time vs estimate
- Sprint velocity
- Team capacity planning

### 8. Audit Trail
- Complete change history
- Before/after state
- Timestamp tracking
- User attribution
- Entity change tracking

## Development Workflow

### Adding a New Feature

1. **Backend**
   - Add entity in `domain/entity/`
   - Create repository in `repository/`
   - Add service methods in `service/`
   - Create DTO in `dto/`
   - Implement controller endpoint in `controller/`
   - Add database migration in `db/migration/`

2. **Frontend**
   - Create service methods in `services/`
   - Build component in `components/`
   - Add routing if needed in `app.routes.ts`
   - Integrate with existing components

3. **Testing**
   - Add unit tests for service
   - Add component tests
   - Test API integration
   - Test WebSocket updates

## Deployment

### Environment Variables

**Backend:**
```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/alphawork
SPRING_DATASOURCE_USERNAME=alphawork
SPRING_DATASOURCE_PASSWORD=alphawork
JWT_SECRET=your-secret-key-here
JWT_EXPIRATION=86400000
```

**Frontend:**
```bash
BACKEND_URL=https://api.alphawork.com
WS_URL=wss://api.alphawork.com
```

### Docker Compose

```bash
# Development
docker-compose -f infra/docker-compose.yml up

# Production
docker-compose -f infra/docker-compose.yml -f infra/docker-compose.prod.yml up -d
```

### Kubernetes (Optional)

Create deployments for:
- PostgreSQL StatefulSet
- Backend Deployment
- Frontend Deployment
- Nginx Ingress Controller

## Monitoring & Logging

### Backend Health
```bash
GET /api/health
```

### Logs
- Docker: `docker logs alphawork-backend`
- File: Check `logs/` directory

### Metrics (Spring Boot Actuator)
```
GET /actuator/health
GET /actuator/metrics
```

## Testing

### Backend
```bash
cd alphawork/backend
mvn test
mvn test -Dtest=AuthServiceTest
mvn test jacoco:report
```

### Frontend
```bash
cd frontend
ng test
ng test --code-coverage
```

## Troubleshooting

### Database Connection Issues
- Verify PostgreSQL is running
- Check credentials in `application.properties`
- Ensure database `alphawork` exists

### Port Conflicts
```bash
# Change backend port
export SERVER_PORT=8081

# Change frontend port
ng serve --port 4300
```

### WebSocket Connection Failed
- Check CORS configuration
- Verify WebSocket endpoint is correct
- Check browser console for errors

### JWT Token Expired
- Login again to get new token
- Default expiration: 24 hours
- Token stored in localStorage

## Performance Tips

1. **Database**: Use indices on frequently queried columns
2. **API**: Implement pagination for large datasets
3. **Frontend**: Use lazy loading for routes
4. **WebSocket**: Implement message batching
5. **Caching**: Add Spring Cache for frequently accessed data

## Security Best Practices

1. **Secrets**: Never commit JWT secret, use environment variables
2. **HTTPS**: Always use in production
3. **CORS**: Whitelist only trusted origins
4. **CSRF**: Enabled by default in Spring Security
5. **SQL Injection**: Prevented via JPA parameterized queries
6. **XSS**: Angular sanitizes by default

## Scaling Considerations

1. **Database**: Consider read replicas for large installations
2. **Backend**: Horizontal scaling via load balancer
3. **Frontend**: Static file caching via CDN
4. **WebSocket**: Use message broker (Redis) for multi-instance
5. **Monitoring**: Implement Prometheus + Grafana

## License

MIT License - Free for commercial and personal use

## Support & Contributing

- Report issues in GitHub Issues
- Submit PRs for improvements
- Check CONTRIBUTING.md for guidelines

## Team

- Backend Lead: [Your Name]
- Frontend Lead: [Your Name]
- DevOps: [Your Name]

## Roadmap

- [ ] Email notifications
- [ ] CSV/Excel export
- [ ] Mobile app (React Native)
- [ ] AI-powered sprint planning
- [ ] Jira/Azure DevOps integration
- [ ] Advanced analytics/reporting
- [ ] Custom workflow automation
- [ ] Plugin system

---

**Version**: 1.0.0  
**Last Updated**: January 17, 2024  
**Status**: Production Ready
