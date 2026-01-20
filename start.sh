#!/bin/bash

# AlphaWork Startup Script

echo "==================================="
echo "  AlphaWork - Initialization"
echo "==================================="

# Check if running with Docker Compose
if [ "$1" == "docker" ]; then
    echo "Starting with Docker Compose..."
    cd infra
    docker-compose up -d
    echo "Services started!"
    echo "Backend:  http://localhost:8080"
    echo "Frontend: http://localhost:4200"
    echo "Database: localhost:5432"
else
    echo "Starting development servers..."
    
    # Start PostgreSQL in background (if using Docker)
    echo "Starting PostgreSQL..."
    docker run --name alphawork-db \
      -e POSTGRES_DB=alphawork \
      -e POSTGRES_USER=alphawork \
      -e POSTGRES_PASSWORD=alphawork \
      -p 5432:5432 \
      -d postgres:15 > /dev/null 2>&1
    
    sleep 2
    
    # Start backend
    echo "Starting backend server..."
    cd alphawork/backend
    mvn spring-boot:run > /tmp/backend.log 2>&1 &
    BACKEND_PID=$!
    echo "Backend PID: $BACKEND_PID"
    
    # Start frontend
    echo "Starting frontend server..."
    cd ../../frontend
    npm start > /tmp/frontend.log 2>&1 &
    FRONTEND_PID=$!
    echo "Frontend PID: $FRONTEND_PID"
    
    echo ""
    echo "==================================="
    echo "  All Services Started!"
    echo "==================================="
    echo "Backend:  http://localhost:8080"
    echo "Frontend: http://localhost:4200"
    echo "API Docs: http://localhost:8080/swagger-ui.html"
    echo ""
    echo "Logs:"
    echo "Backend:  tail -f /tmp/backend.log"
    echo "Frontend: tail -f /tmp/frontend.log"
    echo ""
    echo "To stop services:"
    echo "  Backend:  kill $BACKEND_PID"
    echo "  Frontend: kill $FRONTEND_PID"
    echo "  Database: docker stop alphawork-db"
    echo "==================================="
fi
