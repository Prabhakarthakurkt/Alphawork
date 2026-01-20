# AlphaWork - Quick Reference Guide

## 🚀 Start Here

### 5-Minute Docker Setup
```bash
cd projects/alphawork/infra
docker-compose up -d
```

Access:
- 🌐 Frontend: http://localhost:4200
- 🔧 Backend: http://localhost:8080
- 📚 API Docs: http://localhost:8080/swagger-ui.html
- 🗄️ Database: localhost:5432

### Local Development (15 Minutes)
```bash
# Terminal 1: Backend
cd projects/alphawork/backend
mvn clean install
mvn spring-boot:run

# Terminal 2: Frontend
cd projects/frontend
npm install
npm start
```

---

## 📁 Project Map

```
projects/
├── alphawork/backend/          Spring Boot 3.5.9
│   ├── src/main/java/          Java code (30+ files)
│   ├── pom.xml                 Dependencies
│   └── README.md               Backend guide
├── frontend/                   Angular 21
│   ├── src/app/                TypeScript code
│   ├── package.json            Dependencies
│   └── README.md               Frontend guide
├── README.md                   Main guide (START HERE)
├── SETUP_GUIDE.md              Installation steps
├── PROJECT_SUMMARY.md          Complete overview
└── IMPLEMENTATION_CHECKLIST.md What's done
```

---

## 🔑 Key Credentials (Development)

**Database:**
- Host: localhost:5432
- Name: alphawork
- User: alphawork
- Password: alphawork

**JWT:**
- Secret: `alphawork-secret-key-for-jwt-token-generation-must-be-long-enough-for-hs256`
- Expiration: 24 hours

**Test User:** (After creating in DB)
- Email: admin@alphawork.com
- Password: Admin123

---

## 📡 API Quick Reference

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"password"}'
```

### Create Project
```bash
curl -X POST http://localhost:8080/api/projects \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -H "organizationId: <org-id>" \
  -d '{
    "name":"My Project",
    "description":"...",
    "startDate":"2024-01-17",
    "endDate":"2024-03-17"
  }'
```

### Create Issue
```bash
curl -X POST http://localhost:8080/api/issues \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "title":"Fix login bug",
    "type":"BUG",
    "status":"TODO",
    "projectId":"<id>",
    "boardId":"<id>"
  }'
```

### Update Issue Status
```bash
curl -X PATCH "http://localhost:8080/api/issues/<id>/status?status=DOING" \
  -H "Authorization: Bearer <token>"
```

---

## 🏗️ Architecture Overview

```
┌─────────────────────────────────┐
│     Angular Frontend (4200)      │
├─────────────────────────────────┤
│  Services, Components, Guards    │
├─────────────────────────────────┤
│   HTTP + WebSocket Clients       │
└────────────┬────────────────────┘
             │
     ┌───────┴───────┐
     │               │
     ▼ HTTP          ▼ WS
┌─────────────────────────────────┐
│   Spring Boot Backend (8080)     │
├─────────────────────────────────┤
│  Controllers → Services → Repos  │
├─────────────────────────────────┤
│  JPA Entities, JWT, RBAC         │
└────────────┬────────────────────┘
             │
             ▼
┌─────────────────────────────────┐
│   PostgreSQL (5432)             │
├─────────────────────────────────┤
│  10 Tables, Relationships, Index │
└─────────────────────────────────┘
```

---

## 📚 Documentation Map

| Document | For | Time | Link |
|----------|-----|------|------|
| README.md | Overview | 5 min | Start here |
| SETUP_GUIDE.md | Installation | 15 min | Setup |
| PROJECT_SUMMARY.md | Details | 20 min | Deep dive |
| Backend README | Dev backend | 20 min | Java specific |
| Frontend README | Dev frontend | 20 min | Angular specific |
| API Docs | API usage | 10 min | /swagger-ui.html |
| Checklist | Progress | 5 min | What's done |

---

## 🔨 Common Commands

### Backend
```bash
mvn clean install          # Build
mvn spring-boot:run       # Run dev server
mvn test                  # Run tests
mvn package               # Create JAR
mvn clean                 # Clean build
```

### Frontend
```bash
npm install               # Install deps
npm start                 # Run dev server
ng build                  # Production build
ng test                   # Run tests
npm run lint             # Check code quality
```

### Docker
```bash
docker-compose up -d      # Start all services
docker-compose down       # Stop all services
docker-compose logs -f    # View logs
docker ps                 # List containers
docker exec -it <name> bash  # Shell access
```

### Database
```bash
psql -U alphawork alphawork     # Connect
\dt                             # List tables
\d table_name                   # Describe table
SELECT * FROM users;            # Query
\q                              # Quit
```

---

## 🧪 Testing

### Backend Tests
```bash
cd alphawork/backend

# Run all
mvn test

# Run specific
mvn test -Dtest=AuthServiceTest

# Coverage
mvn test jacoco:report
```

### Frontend Tests
```bash
cd frontend

# Run all
ng test

# Coverage
ng test --code-coverage
```

---

## 🔐 Security Essentials

### Roles & Permissions

| Role | Can Create | Can Assign | Can View Audit |
|------|-----------|-----------|----------------|
| ORG_HEAD | Projects, Boards, Sprints | Anyone | All |
| TEAM_LEAD | Sprints | Own team | Team |
| USER | Nothing | Nothing | None |

### JWT Usage
```
Header: Authorization: Bearer <token>
Token format: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Expiration: 24 hours (configurable)
```

### Password Hashing
```java
// BCrypt with salt
$2a$10$...
```

---

## 📊 Database Tables Quick View

| Table | Purpose | Key Fields |
|-------|---------|-----------|
| users | System users | id, email, role |
| organizations | Org units | id, name |
| teams | User groups | id, name, lead_id |
| projects | Work projects | id, name, status |
| boards | Scrum/Kanban | id, type |
| sprints | Sprint planning | id, status, dates |
| issues | Work items | id, status, type |
| time_logs | Time tracking | id, hours_spent |
| notes | Comments | id, content |
| audit_logs | Change history | id, action |

---

## 🐛 Troubleshooting

### Port In Use
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <pid> /F

# Mac/Linux
lsof -i :8080
kill -9 <pid>
```

### Database Issues
```bash
# Test connection
psql -U alphawork -d alphawork -c "SELECT 1"

# Reset
dropdb alphawork
createdb alphawork
```

### Maven Issues
```bash
# Clear cache
rm -rf ~/.m2/repository

# Rebuild
mvn clean install -U
```

### NPM Issues
```bash
# Clear cache
npm cache clean --force

# Reinstall
rm -rf node_modules package-lock.json
npm install
```

---

## 🎓 Learning Paths

### New to Spring Boot?
1. Understand Controllers (REST endpoints)
2. Study Services (business logic)
3. Learn Repositories (database access)
4. Review Security (JWT, RBAC)
5. Explore WebSocket (real-time)

### New to Angular?
1. Components (building blocks)
2. Services (data management)
3. Routing (navigation)
4. Observables (async patterns)
5. Interceptors (HTTP customization)

### New to Docker?
1. Understand images (blueprints)
2. Learn containers (running instances)
3. Study docker-compose (multi-service)
4. Configure volumes (data persistence)
5. Set up networking

---

## 🚢 Deployment

### Production Checklist
- [ ] Change JWT_SECRET to strong random string
- [ ] Set proper database credentials
- [ ] Enable HTTPS (Let's Encrypt)
- [ ] Configure backup strategy
- [ ] Set up monitoring/logging
- [ ] Load test the system
- [ ] Create disaster recovery plan
- [ ] Document deployment steps

### Docker Production
```bash
docker-compose \
  -f docker-compose.yml \
  -f docker-compose.prod.yml \
  up -d
```

### Kubernetes (Ready)
- Create YAML manifests
- Set resource limits
- Configure auto-scaling
- Setup health checks
- Configure ingress

---

## 📞 Getting Help

### Check These First
1. Look in relevant README.md
2. Review PROJECT_SUMMARY.md
3. Check IMPLEMENTATION_CHECKLIST.md
4. Search code comments
5. Review test files for examples

### Common Questions

**Q: How do I create a new API endpoint?**
A: See "Adding a Feature" in SETUP_GUIDE.md

**Q: Where's the API documentation?**
A: http://localhost:8080/swagger-ui.html (after startup)

**Q: How do I run tests?**
A: See "Testing" section in respective README.md

**Q: What's the default login?**
A: Create a user in DB or review SETUP_GUIDE.md

**Q: Can I use with different database?**
A: Yes, change SPRING_DATASOURCE_URL in application.properties

---

## 🎯 Typical Development Workflow

### 1. Feature Request
```
User: "Add ability to export issues as CSV"
```

### 2. Backend Implementation
```
1. Add method to IssueService
2. Create endpoint in IssueController
3. Test with curl/Postman
4. Document in README
```

### 3. Frontend Implementation
```
1. Add button in component
2. Call service method
3. Handle response
4. Test in browser
```

### 4. Testing
```
1. Write unit tests
2. Run integration tests
3. Manual testing in UI
4. Verify audit logs
```

### 5. Deployment
```
1. Commit to git
2. Build Docker image
3. Push to registry
4. Deploy to production
5. Verify health checks
```

---

## 📈 Performance Tips

### Backend
- Use pagination for large datasets
- Add proper indices (already done)
- Cache frequently accessed data
- Use select projections
- Monitor query performance

### Frontend
- Lazy load routes
- Unsubscribe from observables
- Use trackBy in *ngFor
- OnPush change detection
- Tree shake unused code

### Database
- Regular VACUUM and ANALYZE
- Monitor slow queries
- Backup regularly
- Archive old audit logs
- Monitor connection pool

---

## 🔄 Version Control

### Typical Git Workflow
```bash
# Create branch
git checkout -b feature/new-feature

# Make changes
git add .
git commit -m "feat: Add new feature"

# Push branch
git push origin feature/new-feature

# Create PR on GitHub
# Get review
# Merge to main
```

### Commit Message Format
```
feat: Add new feature
fix: Fix bug
docs: Update documentation
test: Add tests
refactor: Improve code
chore: Maintenance
```

---

## 🎉 Quick Wins (Easy to Add)

1. **Email Notifications** - Setup mail service
2. **CSV Export** - Add export endpoint
3. **Advanced Search** - Filter queries
4. **Themes** - CSS variables
5. **Keyboard Shortcuts** - Hotkeys service
6. **Dark Mode** - Theme toggle
7. **Timezone Support** - Date formatting
8. **Avatar Upload** - File handling

---

## 📱 Mobile Responsive (Ready)

The frontend is ready for mobile responsiveness:
- Angular Material responsive
- Tailwind mobile-first
- Flexbox layouts
- Touch-friendly buttons
- Viewport configuration

Implement by adding media queries and mobile components.

---

## 🤝 Contributing

### Steps
1. Fork repository
2. Create feature branch
3. Make changes
4. Write tests
5. Create pull request
6. Get code review
7. Merge to main

### Code Style
- Follow existing patterns
- Use meaningful names
- Add comments for complex logic
- Keep functions small
- Test your changes

---

## 📞 Support Contacts

- **Documentation**: See README files
- **Issues**: GitHub Issues
- **Email**: support@alphawork.com
- **Slack**: #alphawork channel

---

## ⭐ Star Features

- ✨ JWT Authentication
- 🚀 Real-time WebSocket
- 📊 Complete Audit Trail
- 🔐 RBAC with 3 roles
- 💾 PostgreSQL with migrations
- 🐳 Docker ready
- 📱 Responsive design
- 📚 Full documentation

---

## 🎯 Next Steps

1. **Run Docker Setup** (5 min)
   ```bash
   cd projects/alphawork/infra
   docker-compose up -d
   ```

2. **Access Services** (1 min)
   - Frontend: http://localhost:4200
   - API: http://localhost:8080/swagger-ui.html

3. **Read Documentation** (15 min)
   - Start with README.md
   - Review PROJECT_SUMMARY.md

4. **Explore Code** (20 min)
   - Backend structure
   - Frontend services
   - Database schema

5. **Start Development** (30 min)
   - Set up IDE
   - Make first change
   - Run tests

---

**Happy Coding!** 🚀

For detailed help, see the appropriate README.md or SETUP_GUIDE.md

---

**Quick Links**
- [Main README](README.md)
- [Setup Guide](SETUP_GUIDE.md)
- [Project Summary](PROJECT_SUMMARY.md)
- [Backend README](alphawork/backend/README.md)
- [Frontend README](frontend/README.md)
- [Checklist](IMPLEMENTATION_CHECKLIST.md)
