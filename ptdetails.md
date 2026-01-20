🚀 FULL PROJECT PROMPT — AlphaWork
Project Name:

AlphaWork — Real-Time Agile Work Console

Project Vision

Build a modern, enterprise-grade, web-based Work Console that enables organizations to manage projects, teams, tasks, and sprints using Scrum and Kanban methodologies, with:

Real-time collaboration

Strong role-based security

Full auditability

Scalable architecture

Rich user experience

The system should function similarly to a lightweight Jira + Trello hybrid, but customized for internal organizational workflows.

Core Objectives

AlphaWork must:

Support Scrum + Kanban boards

Enable hierarchical roles (Org-Head → Team Lead → User)

Provide secure authentication via JWT

Deliver real-time updates via WebSockets

Store data reliably in PostgreSQL

Maintain a complete audit trail

Be built with Angular + Spring Boot

Be container-ready with Docker

Target Users

Org-Head (Admin)

Team Lead (Manager)

Regular User (Contributor)

Key Features
1. Authentication & Security

JWT-based login

Secure password hashing (bcrypt)

Role-Based Access Control (RBAC)

HTTPS + CORS enabled

API rate limiting

2. Project & Board Management

Users must be able to:

Create Projects

Choose Board Type: Scrum or Kanban

Define project duration, start date, end date

Assign teams to projects

3. Issue Types

Each work item can be:

TASK

STORY

BUG

Each issue must support:

Title

Description

Assignee

Status

Estimate (hours)

Time spent

Attachments / notes

4. Workflow States

Default workflow:

TODO → DOING → DONE


Configurable per project:

BLOCKED

REVIEW

QA

5. Sprint & Backlog (Scrum)

Scrum projects must support:

Backlog view

Sprint planning

Sprint goal

Sprint start date

Sprint end date

Drag-and-drop issue assignment into sprints

6. Kanban Board

Kanban projects must support:

3 columns: TODO / DOING / DONE

Drag-and-drop movement

Real-time updates for all users

7. Real-Time Collaboration

When any user updates an issue:

Board updates instantly for all users

Activity stream logs the action

WebSockets push updates live

8. Time Tracking

Users can:

Log time spent per task

Add notes to time logs

View total time spent vs estimate

9. Audit Trail

System must log:

Who changed what

When the change happened

Before vs after state

Affected entity (Project, Task, User, etc.)

10. Role Permissions (RBAC)
Action	Org-Head	Team Lead	User
Create Project	✅	❌	❌
Create Board	✅	❌	❌
Create Sprint	✅	✅	❌
Assign Tasks	✅	✅	❌
Move Task	✅	✅	✅ (own only)
View Audit Logs	✅	⚠️ Team only	❌
Technology Stack
Frontend

Angular 17+

TypeScript

Angular Material + Tailwind

RxJS

WebSockets (STOMP client)

Drag & Drop via Angular CDK

Backend

Java 21

Spring Boot 3.5.x

Spring Security + JWT

Spring Data JPA

WebSockets (STOMP)

Hibernate Validator

Database

PostgreSQL 15 (Docker)

Flyway for migrations

DevOps

Docker + Docker Compose

Kubernetes (optional later)

GitHub Actions CI/CD

Prometheus + Grafana

High-Level Architecture
[ Angular Frontend ]
        |
   HTTPS + WS
        |
[ Spring Boot Backend ]
        |
   JPA / Hibernate
        |
[ PostgreSQL (Docker) ]

API Endpoints (Core)
Auth
POST /api/auth/login

Projects
POST /api/projects
GET  /api/projects
GET  /api/projects/{id}

Boards
POST /api/projects/{id}/boards
GET  /api/projects/{id}/boards

Sprints
POST /api/boards/{id}/sprints
GET  /api/boards/{id}/sprints

Issues
POST  /api/issues
PATCH /api/issues/{id}/status
POST  /api/issues/{id}/timelog

Deployment Requirements

Backend runs on port 8080

Frontend runs on port 4200

PostgreSQL runs in Docker on port 5432

Docker Compose should manage services

Success Criteria

The project is successful when:

Backend health = {"status":"UP"}

JWT login works

Projects can be created

Kanban board updates in real time

Sprint planning works

Audit logs record all changes

System runs in Docker

Optional Enhancements (Future Phase)

Email notifications for deadlines

CSV/Excel exports

Mobile-responsive UI

AI-based sprint planning

Jira import/export

Deliverables

Fully working Angular + Spring Boot app

Dockerized PostgreSQL

JWT authentication

Scrum + Kanban boards

WebSocket real-time updates

Swagger/OpenAPI documentation

RBAC implementation

Audit logging