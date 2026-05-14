# Academy System

> **Stack:** Java 17 · Spring Boot 4.0.5 · PostgreSQL · Spring Security + JWT
> **Version:** 1.0 · May 2026

---

## Table of Contents

1. [Project Overview](#1-project-overview)
2. [System Features](#2-system-features)
3. [System Architecture & Tech Stack](#3-system-architecture--tech-stack)
4. [Security](#4-security)
5. [System Flow Design](#5-system-flow-design)

---

## 1. Project Overview

### 1.1 Introduction

The **Academy System** is a robust, backend educational management platform built on the Spring Boot framework. It provides a comprehensive REST API for managing a structured academic hierarchy — **Courses → Sections → Lectures → Resources** — and a multi-tier user system covering Admins, Authors, and Students. Access to all resources is secured via stateless JWT authentication and role-based authorization.

The system is designed as a pure backend service; all interaction happens through HTTP/JSON REST endpoints, making it suitable as the backbone for any frontend (web, mobile) or third-party integration.

### 1.2 Purpose & Scope

The platform serves three primary user roles:

| Role | Purpose |
|------|---------|
| **Admin** | Platform management — oversees all users, courses, and content. |
| **Author** | Content creation — creates and manages courses, sections, lectures, and resources. |
| **Student** | Content consumption — enrolls in courses and accesses educational material. |

### 1.3 Key Highlights

- Hierarchical content model: Course → Section → Lecture → Resource (Video / Text / File)
- Polymorphic resource system using a single-table inheritance strategy with a `resource_type` discriminator
- Stateless JWT-based authentication with BCrypt password hashing (strength 12)
- Automatic audit trail on every entity (`createdAt`, `lastModifiedAt`) via JPA lifecycle callbacks
- Username-prefix convention (`admin_`, `author_`, `student_`) enables efficient multi-table user dispatch
- Full DTO pattern — internal entities are never exposed directly to API consumers
- Bean Validation on all DTOs with strict regex and constraint rules

### 1.4 Technology Snapshot

| Layer | Technology | Version |
|-------|-----------|---------|
| Language | Java | 17 |
| Framework | Spring Boot | 4.0.5 |
| Security | Spring Security + JJWT | Latest |
| ORM / Persistence | Spring Data JPA (Hibernate) | — |
| Database | PostgreSQL | — |
| Boilerplate Reduction | Lombok | — |
| Validation | Jakarta Bean Validation | — |
| Build Tool | Apache Maven | — |

---

## 2. System Features

### 2.1 User Management

- **Three distinct user roles**: Admin, Author, Student — each with its own entity, repository, service, controller, and DTO.
- **Unified authentication**: All user types share a common `User` base class that implements Spring Security's `UserDetails`, enabling a single login flow regardless of role.
- **Username-prefix convention**: Usernames are automatically prefixed (`admin_`, `author_`, `student_`) on creation via `@PrePersist` lifecycle callbacks, enabling the `UserService` dispatcher to route lookups to the correct sub-repository without runtime role checks on every query.
- **Author profiles**: Authors have an additional `biography` field stored with their account.
- **Student levels**: Students carry a `level` field (positive integer) representing their academic standing.
- **Full CRUD for all user types** via protected REST endpoints.

### 2.2 Course Management

- Create, read, update, and delete **Courses**.
- Each course has a `title` (4–100 characters), an optional `description` (up to 1000 characters), and must be associated with at least one Author.
- Many-to-many associations: a course can have multiple **Authors** (instructors) and multiple **Students** (enrollees).
- Course content is organized into ordered **Sections**.

### 2.3 Section Management

- Sections act as logical groupings within a course (e.g., "Module 1 — Introduction").
- Each section carries a `sectionOrder` (positive integer) to define its sequence within the course.
- Section names are restricted to alphabetic characters and spaces.
- Full CRUD operations are available via the `/sections` endpoint.

### 2.4 Lecture Management

- Lectures represent individual teaching units within a Section.
- Each lecture is linked to exactly one parent **Section** and exactly one **Resource** (its content).
- Lecture names are restricted to alphabetic characters.
- Full CRUD operations available via `/lectures`.

### 2.5 Polymorphic Resource System

- Resources are the actual educational content attached to lectures.
- Three content types are supported, differentiated by a `resource_type` discriminator field:
  - **Video** (`V`) — e.g., a URL pointing to a video file or stream.
  - **Text** (`T`) — e.g., a URL pointing to a text/HTML document.
  - **File** (`F`) — e.g., a URL pointing to a downloadable file (PDF, ZIP, etc.).
- All resource types are stored in a **single database table** (Single Table Inheritance), reducing join complexity.
- Resources share common fields: `name`, `size` (max 500 MB), and `url` (must be a valid HTTPS URL).

### 2.6 Audit Infrastructure

- Every entity in the system extends `BaseEntity`, which automatically sets `createdAt` on `@PrePersist` and updates `lastModifiedAt` on `@PostUpdate`.
- All corresponding DTOs extend `BaseEntityDto`, which mirrors these audit fields for API responses.
- This provides a full, automatic audit trail with no manual timestamp management needed.

### 2.7 Input Validation

- All incoming request bodies are validated at the controller layer using `@Valid` + Jakarta Bean Validation annotations.
- Validation errors return structured error responses rather than stack traces.
- Field-level constraints include regex patterns, min/max lengths, positive-integer checks, and URL format validation.

---

## 3. System Architecture & Tech Stack

### 3.1 Architectural Style

The Academy System follows a classic **N-Tier Layered Architecture** within a single Spring Boot application. The layers are strictly separated to decouple concerns:

```
HTTP Client
    │
    ▼
┌─────────────────────────────────────────┐
│         Web / Controller Layer          │  REST controllers — handle HTTP, call services
├─────────────────────────────────────────┤
│           Service Layer                 │  Business logic, DTO ↔ Entity conversions
├─────────────────────────────────────────┤
│        Repository / DAO Layer           │  Spring Data JPA interfaces → SQL queries
├─────────────────────────────────────────┤
│         Domain Model Layer              │  JPA Entities + DTOs
├─────────────────────────────────────────┤
│            PostgreSQL                   │  Persistent storage
└─────────────────────────────────────────┘
```

Additionally, a **Security Filter Chain** sits in front of the Controller layer, intercepting every request to validate JWT tokens before anything reaches business logic.

### 3.2 Package Structure

The codebase lives under `com.mo7s.academySystem` and is organized by domain module:

```
com.mo7s.academySystem/
├── AcademySystem.java          # Spring Boot entry point (@SpringBootApplication)
├── baseEntity/
│   ├── BaseEntity.java         # Shared JPA base with id, createdAt, lastModifiedAt
│   └── BaseEntityDto.java      # Shared DTO base mirroring audit fields
├── config/
│   ├── SecurityConfig.java     # Spring Security filter chain configuration
│   ├── JwtService.java         # JWT generation, validation, claim extraction
│   └── JwtAuthenticationFilter.java  # OncePerRequestFilter — validates Bearer tokens
├── user/
│   ├── User.java               # @MappedSuperclass — base user entity implementing UserDetails
│   ├── Role.java               # Enum: ADMIN, AUTHOR, STUDENT
│   ├── UserService.java        # Dispatcher — routes by username prefix
│   ├── MyUserDetailsService.java  # Spring Security UserDetailsService implementation
│   ├── UserDTO.java            # Base user DTO
│   ├── auth/                   # AuthenticationController, AuthenticationService,
│   │                           #   RegisterRequest, AuthenticationRequest, AuthenticationResponse
│   ├── admin/                  # Admin entity, AdminDto, AdminController, AdminService
│   ├── author/                 # Author entity, AuthorDto, AuthorController, AuthorService
│   └── student/                # Student entity, StudentDto, StudentController, StudentService
├── course/                     # Course entity, CourseDto, CourseController, CourseService
├── section/                    # Section entity, SectionDto, SectionController, SectionService
├── lecture/                    # Lecture entity, LectureDto, LectureController, LectureService
└── resource/
    ├── Resource.java           # Base @Entity with SINGLE_TABLE inheritance + discriminator
    ├── ResourceDto.java        # Base DTO with fromEntity/toEntity polymorphic factory methods
    ├── ResourceController.java
    ├── ResourceService.java
    ├── video/                  # Video entity, VideoDto
    ├── text/                   # Text entity, TextDto
    └── file/                   # File entity, FileDto
```

### 3.3 Domain Model & Entity Relationships

The content model is strictly hierarchical:

```
Course (1) ──── (*) Section (1) ──── (*) Lecture (1) ──── (1) Resource
   │                                                              │
   ├── ManyToMany → Author                              ┌────────┼────────┐
   └── ManyToMany → Student                           Video    Text     File
```

**Key JPA relationships:**

| Relationship | Entities | Type | Notes |
|---|---|---|---|
| Course → Section | `Course` has `sections` | `@OneToMany` | Cascade — sections owned by course |
| Section → Course | `Section` has `course` | `@ManyToOne` | Back-reference |
| Section → Lecture | `Section` has `lectures` | `@OneToMany` | Sections contain lectures |
| Lecture → Section | `Lecture` has `section` | `@ManyToOne` | Back-reference |
| Lecture → Resource | `Lecture` has `resource` | `@OneToOne` | Each lecture has exactly one resource |
| Course → Author | `Course` has `authors` | `@ManyToMany` | A course can have multiple instructors |
| Course → Student | `Course` has `students` | `@ManyToMany` | Enrollment relationship |
| Resource (base) | `Video`, `Text`, `File` | `SINGLE_TABLE` Inheritance | `resource_type` column: V / T / F |

### 3.4 DTO Pattern

The system strictly enforces a DTO (Data Transfer Object) pattern:

- **Entities** map to database tables and contain JPA annotations; they are never serialized to API responses.
- **DTOs** are the API contract — what clients send and receive.
- Each DTO provides:
  - A constructor `XxxDto(XxxEntity entity)` — converts from persistence to API response.
  - A static method `XxxDto.toEntity(XxxDto dto)` — converts from API request to persistence.

### 3.5 Tech Stack Detail

**Spring Boot 4.0.5 / Java 17**
Spring Boot provides auto-configuration, an embedded Tomcat server, and rich integrations. Java 17 LTS offers modern language features and improved performance.

**PostgreSQL**
The primary relational data store. Hibernate manages schema auto-update (`spring.jpa.hibernate.ddl-auto=update`). The single-table inheritance for resources means all resource rows live in one `resource` table distinguished by the `resource_type` column.

**Spring Security + JJWT**
Authentication is stateless. JWT tokens signed with HS256 are issued at login and validated on every subsequent request by a custom `OncePerRequestFilter`. No server-side session is maintained.

**Spring Data JPA (Hibernate)**
Repository interfaces extending `JpaRepository` handle all CRUD operations, with Hibernate generating SQL automatically. Each user subtype (`Admin`, `Author`, `Student`) has its own `JpaRepository`.

**Lombok**
Eliminates boilerplate getter/setter/constructor code via compile-time annotation processing.

**Jakarta Bean Validation**
All DTOs are annotated with constraints (`@NotBlank`, `@Size`, `@Pattern`, `@Positive`, `@Max`, `@URL`, etc.). Controllers use `@Valid` to trigger validation before any service logic runs.

---


## 4. Security

### 4.1 Overview

The Academy System uses **Spring Security** with a **stateless JWT-based** authentication model. No server-side sessions are created or maintained. Every protected request must carry a valid signed token.

### 4.2 Security Configuration (`SecurityConfig`)

The `SecurityConfig` class defines the `SecurityFilterChain` bean with the following rules:

- **CSRF:** Disabled — appropriate for a stateless REST API.
- **Session Management:** `STATELESS` — Spring Security will not create or use an HTTP session.
- **Public routes:** All paths matching `/v1/auth/**` are permitted without authentication.
- **All other routes:** Require a valid JWT (`authenticated()`).
- **Filter ordering:** `JwtAuthenticationFilter` is inserted **before** `UsernamePasswordAuthenticationFilter` in the filter chain.

### 4.3 JWT Service (`JwtService`)

The `JwtService` class manages the complete JWT lifecycle:

| Responsibility | Detail |
|---|---|
| **Token generation** | Creates a signed JWT on successful login. Claims contain the username (subject). |
| **Signing algorithm** | HS256 (HMAC with SHA-256) |
| **Token validity** | **24 hours** |
| **Claim extraction** | `extractUsername(token)` reads the subject claim. `extractExpiration(token)` checks expiry. |
| **Token validation** | Checks signature, expiry, and that the extracted username matches the `UserDetails` loaded from the DB. |

### 4.4 JWT Authentication Filter (`JwtAuthenticationFilter`)

`JwtAuthenticationFilter` extends `OncePerRequestFilter` and runs on every request:

1. Reads the `Authorization` header.
2. Checks that it starts with `Bearer `.
3. Extracts the token string.
4. Calls `JwtService.extractUsername(token)` to get the username.
5. Calls `MyUserDetailsService.loadUserByUsername(username)` to fetch the full `UserDetails`.
6. Calls `JwtService.isTokenValid(token, userDetails)` to verify the token is not expired and belongs to this user.
7. If valid, creates a `UsernamePasswordAuthenticationToken` and sets it in the `SecurityContextHolder`, marking the request as authenticated.
8. If invalid or missing, the request proceeds unauthenticated — Spring Security will then reject it with `401 Unauthorized` for protected routes.

### 4.5 Password Security

- Passwords are **never stored in plain text**.
- The `AuthenticationService` uses `BCryptPasswordEncoder` with **strength 12** to hash passwords before persistence.
- `DaoAuthenticationProvider` is configured with this encoder and the `MyUserDetailsService` for credential verification during login.

### 4.6 User Details & Role Dispatch

- `MyUserDetailsService` implements Spring Security's `UserDetailsService`.
- It calls `UserService.getUserByUserName(username)`, which parses the username prefix to dispatch to the correct sub-service (`AdminService`, `AuthorService`, or `StudentService`).
- The `User` base class implements `getAuthorities()` by converting the user's `Role` enum into a `SimpleGrantedAuthority`, enabling Spring Security to enforce role-based access rules if needed.

### 4.7 Input Validation as a Security Measure

- All DTO fields are validated with Jakarta Bean Validation before reaching any service logic.
- Regex constraints on usernames (`^admin_[a-zA-Z0-9]+$`, etc.) prevent injection of unexpected characters.
- URL fields on resources must be valid HTTPS URLs, blocking malformed or non-HTTPS links.

### 4.8 Security Summary Table

| Feature | Implementation |
|---|---|
| Authentication model | Stateless JWT (Bearer token) |
| Token signing algorithm | HS256 |
| Token expiry | 24 hours |
| Password hashing | BCrypt, strength 12 |
| Authentication provider | `DaoAuthenticationProvider` |
| Session policy | `STATELESS` |
| CSRF protection | Disabled (stateless API) |
| Public endpoints | `/v1/auth/**` only |
| All other endpoints | Require valid JWT |

---

## 5. System Flow Design

### 5.1 User Registration Flow

```
Client
  │
  ├─ POST /v1/auth/register { firstName, lastName, userName, password, role }
  │
  ▼
AuthenticationController
  │
  └─ authenticationService.register(request)
       │
       ├─ BCryptPasswordEncoder.encode(password)
       │
       ├─ Dispatch by role:
       │    ADMIN   → AdminService.save(admin)
       │    AUTHOR  → AuthorService.save(author)
       │    STUDENT → StudentService.save(student)
       │
       │  (Each sub-service @PrePersist applies the username prefix)
       │
       ├─ JwtService.generateToken(savedUser)
       │
       └─ Return AuthenticationResponse { token, userId, role }
```

### 5.2 Authentication (Login) Flow

```
Client
  │
  ├─ POST /v1/auth/authenticate { userName, password }
  │
  ▼
AuthenticationController
  │
  └─ authenticationService.authenticate(request)
       │
       ├─ DaoAuthenticationProvider.authenticate(
       │      UsernamePasswordAuthenticationToken(userName, password))
       │       │
       │       ├─ MyUserDetailsService.loadUserByUsername(userName)
       │       │       │
       │       │       └─ UserService.getUserByUserName(userName)
       │       │               │
       │       │               ├─ Prefix "admin_"   → AdminService.findByUserName()
       │       │               ├─ Prefix "author_"  → AuthorService.findByUserName()
       │       │               └─ Prefix "student_" → StudentService.findByUserName()
       │       │
       │       └─ BCryptPasswordEncoder.matches(rawPassword, storedHash)
       │
       ├─ JwtService.generateToken(user) → signed JWT (HS256, 24h expiry)
       │
       └─ Return AuthenticationResponse { token, userId, role }
```

### 5.3 Authenticated Request Flow (Protected Endpoints)

```
Client
  │
  ├─ GET /courses  (Authorization: Bearer <jwt>)
  │
  ▼
JwtAuthenticationFilter (OncePerRequestFilter)
  │
  ├─ Extract "Bearer <token>" from Authorization header
  ├─ JwtService.extractUsername(token) → "author_jane_smith"
  ├─ MyUserDetailsService.loadUserByUsername("author_jane_smith")
  ├─ JwtService.isTokenValid(token, userDetails)
  │       ├─ Check signature (HS256)
  │       └─ Check expiry (< 24h)
  │
  ├─ [Valid] → Set UsernamePasswordAuthenticationToken in SecurityContextHolder
  │
  ▼
SecurityFilterChain
  │
  └─ Route to CourseController.getAllCourses()
       │
       └─ CourseService.findAll()
            │
            └─ CourseRepository.findAll() → PostgreSQL
                 │
                 └─ List<Course> → List<CourseDto> → 200 OK JSON response
```
