# 🚀 MultithreadTasks

A Spring Boot backend that demonstrates task orchestration, asynchronous execution, progress tracking, cancellation, file generation, and scheduled cleanup.

Built as a **quick-reference project for job applications** to showcase practical backend engineering with Java.

## ✨ What This App Does

- Creates and manages polymorphic tasks through a REST API
- Supports two task types:
  - `project`: generates a downloadable ZIP artifact
  - `cron`: runs a counter asynchronously from `x` to `y` (1 step/second)
- Exposes progress for running counter tasks
- Allows cancellation of running counter tasks
- Periodically deletes stale, non-executed tasks via scheduler
- Persists data with JPA + H2

## 🧱 Tech Stack

- **Language:** Java 11
- **Framework:** Spring Boot 2.2.5
- **Modules:** Spring Web, Spring Data JPA, Spring Scheduling, Spring Async
- **Database:** H2 (in-memory/file dev usage)
- **Build Tool:** Maven
- **Testing:** JUnit + Spring Boot Test + Mockito
- **Utilities:** Apache Commons IO

## 🏗️ Architecture Snapshot

- `Task` is an abstract base entity with Jackson polymorphic deserialization
- `CronTask` and `ProjectGenerationTask` extend `Task`
- `TaskController` exposes unified task endpoints under `/api/tasks`
- Specialized services handle task-type-specific behavior
- `Cleanup` scheduler removes expired, not-executed tasks

## 🔐 Authentication Header

All non-`OPTIONS` API requests require:

- Header: `App-Auth`
- Value: `totally_secret`

## 📡 API Quick Reference

Base path: `/api/tasks`

- `GET /` list tasks
- `POST /` create task
- `GET /{taskId}` get task by id
- `PUT /{taskId}` update task
- `DELETE /{taskId}` delete task
- `POST /{taskId}/execute` execute task
- `GET /{taskId}/result` download result (project tasks)
- `GET /{taskId}/progress` check cron task progress
- `POST /{taskId}/stop` stop running cron task

### Example: Create `cron` Task

```json
{
  "type": "cron",
  "name": "Counter Demo",
  "x": 1,
  "y": 10
}
```

### Example: Create `project` Task

```json
{
  "type": "project",
  "name": "Zip Demo"
}
```

## ⚙️ Run Locally

```bash
mvn clean install
mvn spring-boot:run
```

Default app URL: `http://localhost:8080`

## 🧪 Test

```bash
mvn test
```

## 📁 Useful Project Paths

- `src/main/java/com/bartzilla/controllers` - REST controllers
- `src/main/java/com/bartzilla/services` - business logic
- `src/main/java/com/bartzilla/model` - JPA entities/repositories
- `src/main/java/com/bartzilla/schedule` - scheduled cleanup/counter logic
- `src/main/resources/application.properties` - app configuration
