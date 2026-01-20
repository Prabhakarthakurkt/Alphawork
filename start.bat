@echo off
REM AlphaWork Startup Script for Windows

echo ===================================
echo   AlphaWork - Initialization
echo ===================================

if "%1"=="docker" (
    echo Starting with Docker Compose...
    cd infra
    docker-compose up -d
    echo Services started!
    echo Backend:  http://localhost:8080
    echo Frontend: http://localhost:4200
    echo Database: localhost:5432
) else (
    echo Starting development servers...
    
    REM Start PostgreSQL
    echo Starting PostgreSQL...
    docker run --name alphawork-db ^
      -e POSTGRES_DB=alphawork ^
      -e POSTGRES_USER=alphawork ^
      -e POSTGRES_PASSWORD=alphawork ^
      -p 5432:5432 ^
      -d postgres:15 >nul 2>&1
    
    timeout /t 2 /nobreak
    
    REM Start backend in new window
    echo Starting backend server...
    start "AlphaWork Backend" cmd /k "cd alphawork\backend && mvn spring-boot:run"
    
    REM Start frontend in new window
    echo Starting frontend server...
    start "AlphaWork Frontend" cmd /k "cd frontend && npm start"
    
    echo.
    echo ===================================
    echo   All Services Starting!
    echo ===================================
    echo Backend:  http://localhost:8080
    echo Frontend: http://localhost:4200
    echo API Docs: http://localhost:8080/swagger-ui.html
    echo ===================================
)
