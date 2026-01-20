# Setup Guide - AlphaWork Development Environment

## Full Project Setup Instructions

### Step 1: Prerequisites Installation

#### On Windows:
```powershell
# Install Java 21
# Download from: https://jdk.java.net/21/
# Or use Chocolatey:
choco install openjdk

# Install Maven
choco install maven

# Install Node.js LTS
choco install nodejs

# Install PostgreSQL
choco install postgresql
# During installation, remember the password
```

#### On macOS:
```bash
# Using Homebrew
brew install java21
brew install maven
brew install node
brew install postgresql

# Start PostgreSQL
brew services start postgresql
```

#### On Linux (Ubuntu/Debian):
```bash
# Java 21
sudo apt-get install openjdk-21-jdk

# Maven
sudo apt-get install maven

# Node.js
curl -fsSL https://deb.nodesource.com/setup_20.x | sudo -E bash -
sudo apt-get install -y nodejs

# PostgreSQL
sudo apt-get install postgresql postgresql-contrib
sudo systemctl start postgresql
```

### Step 2: Clone & Setup Database

```bash
# Navigate to projects directory
cd /path/to/projects

# Create PostgreSQL database
psql -U postgres
CREATE DATABASE alphawork;
CREATE USER alphawork WITH PASSWORD 'alphawork';
ALTER ROLE alphawork SET client_encoding TO 'utf8';
ALTER ROLE alphawork SET default_transaction_isolation TO 'read committed';
ALTER ROLE alphawork SET default_transaction_deferrable TO on;
ALTER ROLE alphawork SET default_transaction_read_committed TO off;
GRANT ALL PRIVILEGES ON DATABASE alphawork TO alphawork;
\q
```

### Step 3: Backend Setup

```bash
cd alphawork/backend

# Download dependencies
mvn clean install

# Verify compilation
mvn compile

# Run tests
mvn test

# Start the backend
mvn spring-boot:run
```

**Backend will be available at:** `http://localhost:8080`
**API Docs:** `http://localhost:8080/swagger-ui.html`

### Step 4: Frontend Setup

```bash
cd frontend

# Install dependencies (takes a few minutes)
npm install

# Start development server
npm start
```

**Frontend will be available at:** `http://localhost:4200`

### Step 5: Verify Installation

#### Test Backend:
```bash
curl http://localhost:8080/api/health
# Expected response: {"status":"UP","service":"AlphaWork Backend","version":"1.0.0"}
```

#### Test Frontend:
Visit `http://localhost:4200` in your browser

### Step 6: Create Test User (Optional)

Connect to database and run:
```sql
-- Insert test user
INSERT INTO users (id, email, password, first_name, last_name, role, active, created_at, updated_at)
VALUES (
  'test-user-1',
  'admin@alphawork.com',
  '$2a$10$...', -- bcrypt hash of 'Admin123'
  'Admin',
  'User',
  'ORG_HEAD',
  true,
  NOW(),
  NOW()
);
```

## Docker Setup (Alternative)

### Quick Start with Docker Compose

```bash
cd infra

# Build and start all services
docker-compose up -d

# Verify services
docker-compose ps

# View logs
docker-compose logs -f backend
docker-compose logs -f frontend

# Stop services
docker-compose down
```

### Access Services:
- Backend: `http://localhost:8080`
- Frontend: `http://localhost:4200`
- Database: `localhost:5432`

## Development Workflow

### 1. Creating a New Backend Endpoint

Example: Adding a `GET /api/users/{id}` endpoint

**Step 1: Entity (already exists)**
```java
// domain/entity/User.java - already created
```

**Step 2: Repository**
```java
// repository/UserRepository.java
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findById(String id);
}
```

**Step 3: Service**
```java
// service/UserService.java
@Service
public class UserService {
    public UserDTO getUser(String id) {
        return userRepository.findById(id)
            .map(this::mapToDTO)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
```

**Step 4: Controller**
```java
// controller/UserController.java
@GetMapping("/{id}")
public ResponseEntity<UserDTO> getUser(@PathVariable String id) {
    return ResponseEntity.ok(userService.getUser(id));
}
```

**Step 5: Database Migration (if needed)**
```sql
-- src/main/resources/db/migration/V2__Add_user_index.sql
CREATE INDEX idx_users_id ON users(id);
```

### 2. Creating a New Frontend Component

```bash
ng generate component components/board/kanban-board
```

**File structure:**
```
kanban-board/
├── kanban-board.component.ts
├── kanban-board.component.html
├── kanban-board.component.scss
└── kanban-board.component.spec.ts
```

### 3. Running Tests

**Backend:**
```bash
cd alphawork/backend
mvn test                                # All tests
mvn test -Dtest=AuthServiceTest        # Specific test
mvn test jacoco:report                 # With coverage
```

**Frontend:**
```bash
cd frontend
ng test                                # All tests
ng test --include='**/auth.service.spec.ts'  # Specific test
ng test --code-coverage               # With coverage
```

## Troubleshooting Common Issues

### Port Already in Use

```bash
# Find process using port 8080
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# macOS/Linux
lsof -i :8080
kill -9 <PID>
```

### PostgreSQL Connection Failed

```bash
# Verify PostgreSQL is running
psql -U postgres

# Create database if missing
createdb alphawork

# Reset password
ALTER USER alphawork WITH PASSWORD 'alphawork';
```

### Npm Install Fails

```bash
# Clear npm cache
npm cache clean --force

# Delete node_modules
rm -rf node_modules package-lock.json

# Reinstall
npm install
```

### Maven Dependency Issues

```bash
# Clear Maven cache
rm -rf ~/.m2/repository

# Rebuild
mvn clean install
```

### Java Version Mismatch

```bash
# Check Java version
java -version

# Set JAVA_HOME (Windows)
set JAVA_HOME=C:\Program Files\Java\jdk-21

# Set JAVA_HOME (macOS/Linux)
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
```

## IDE Setup

### IntelliJ IDEA

1. Open project root: `File → Open → Select projects folder`
2. Configure Java SDK: `File → Project Structure → SDKs → + → JDK 21`
3. Enable annotation processing: `Settings → Build → Compiler → Annotation Processors → Enable`
4. Install Angular extension: `Plugins → Search "Angular" → Install`

### Visual Studio Code

1. Install Extensions:
   - Extension Pack for Java (Microsoft)
   - Spring Boot Extension Pack (VMware)
   - Angular Language Service (Angular)
   - ES7+ React/Redux/React-Native snippets

2. Configure Workspace:
   - Create `.vscode/settings.json`
   - Set Java home path
   - Configure formatter (Prettier, ESLint)

## Git Workflow

```bash
# Clone repository
git clone <repo-url> alphawork

# Create feature branch
git checkout -b feature/new-feature

# Make changes and commit
git add .
git commit -m "feat: Add new feature"

# Push to remote
git push origin feature/new-feature

# Create Pull Request on GitHub
# Wait for review and merge
```

## Environment Files

Create `.env` file in project root:

```bash
# Database
DB_HOST=localhost
DB_PORT=5432
DB_NAME=alphawork
DB_USER=alphawork
DB_PASSWORD=alphawork

# Backend
BACKEND_PORT=8080
JWT_SECRET=your-secret-key-here
JWT_EXPIRATION=86400000

# Frontend
FRONTEND_PORT=4200
BACKEND_URL=http://localhost:8080
WS_URL=ws://localhost:8080
```

## Performance Profiling

### Backend JVM Profiling
```bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xmx512m -Xms256m"
```

### Frontend Bundle Analysis
```bash
ng build --stats-json
webpack-bundle-analyzer dist/frontend/stats.json
```

## Next Steps

1. **Familiarize with the codebase**
   - Read backend README.md
   - Read frontend README.md
   - Review API documentation at /swagger-ui.html

2. **Create test user**
   - Use /api/auth/login endpoint
   - Create sample projects and issues

3. **Explore features**
   - Create Kanban board
   - Create Scrum project with sprints
   - Test real-time updates with WebSocket

4. **Development**
   - Implement new features
   - Write tests
   - Submit PRs

## Useful Commands Reference

```bash
# Backend
mvn clean install          # Build
mvn spring-boot:run       # Run dev server
mvn test                  # Run tests
mvn package               # Create JAR

# Frontend
npm install               # Install dependencies
npm start                 # Run dev server
npm test                  # Run tests
npm run build            # Build for production

# Database
psql -U alphawork alphawork  # Connect to database
\dt                          # List tables
\d table_name               # Describe table

# Docker
docker-compose up -d      # Start services
docker-compose down       # Stop services
docker-compose logs -f    # View logs
docker ps                 # List containers
```

## Need Help?

- Check README files in each directory
- Review API documentation: http://localhost:8080/swagger-ui.html
- Check GitHub Issues
- Review code comments
- Ask in team Slack/Discord

---

**Happy Coding! 🚀**
