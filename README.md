# DevPulse 🚀

DevPulse is a **developer project showcase platform** built with Spring Boot 3. It provides a robust REST API backend for managing developer accounts, project ideas, implementation submissions, voting, and trending algorithms.

## ✨ Features

- **User Authentication:** Secure JWT-based registration and login system with BCrypt password hashing.
- **Idea Management:** Full CRUD operations for project ideas. Only creators can modify or delete their ideas.
- **Implementations:** Developers can submit their solutions (GitHub repositories) for specific ideas.
- **GitHub Integration:** Automatically fetches and schedules updates for repository data like star count, primary language, and latest commits using the GitHub API.
- **Voting & Trending:** Users can upvote implementations. A gamified trending algorithm backed by Redis caching highlights the top solutions.
- **Admin Controls:** Centralized management for admins to perform cascading deletions of users, ideas, and implementations.
- **Exception Handling:** Global exception handling returning clean, consistent JSON responses for validation errors, unauthorized access, and missing resources.

## 🛠️ Tech Stack

- **Framework:** Spring Boot 3.2.5
- **Language:** Java 21
- **Database:** PostgreSQL
- **ORM:** Spring Data JPA / Hibernate
- **Security:** Spring Security 6 & JJWT (JSON Web Token)
- **Caching:** Redis
- **Validation:** Jakarta Bean Validation
- **Utilities:** Lombok, Dotenv, Maven

## 📋 Prerequisites

To run this project locally, you will need:
- Java 21 or higher
- Maven
- PostgreSQL database
- Redis server
- GitHub Personal Access Token (for rate-limit bypass on repository data fetch)

## 🚀 Getting Started

1. **Clone or Download the Repository**

2. **Setup the Database & Environment Variables**
   Create a `.env` file in the root directory (same level as `pom.xml`) and configure your credentials:
   
   ```properties
   DB_URL=jdbc:postgresql://localhost:5432/devpulse
   DB_USERNAME=your_postgres_username
   DB_PASSWORD=your_postgres_password
   JWT_TOKEN=your_base64_encoded_secret_key_for_jwt
   GITHUB_TOKEN=your_github_personal_access_token
   ```

3. **Build the Application**
   ```bash
   mvn clean install
   ```

4. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```
   The server will start on `http://localhost:8080`.

## 🔌 API Endpoints Summary

### User Module
- `POST /api/users/register` - Register a new user (`Name`, `Email`, `Password`)
- `POST /api/users/login` - Authenticate and receive a JWT
- `GET /api/users/me` - Get current authenticated user details

### Idea Module
- `POST /api/ideas/create` - Create a new idea
- `GET /api/ideas/all` - Get all ideas (Paginated)
- `GET /api/ideas/{id}` - Get idea by ID
- `PUT /api/ideas/{id}` - Update idea (Owner only)
- `PATCH /api/ideas/{id}` - Partially update idea (Owner only)
- `DELETE /api/ideas/{id}` - Delete idea (Owner only)

### Implementation Module
- `POST /api/ideas/{ideaId}/implementations` - Submit a GitHub repository as a solution
- `GET /api/ideas/{ideaId}/implementations` - Get all implementations for an idea
- `DELETE /api/implementations/{id}` - Delete implementation (Owner only)

### Voting & Trending
- `POST /api/implementations/{id}/vote` - Upvote or toggle a vote for an implementation
- `GET /api/trending` - Get trending implementations based on vote count (Redis cached)

### Admin Module
Protected endpoints (`ROLE_ADMIN` required) for managing the platform:
- `GET /api/admin/users`
- `DELETE /api/admin/users/{id}` (Cascading delete)
- `DELETE /api/admin/ideas/{id}`
- `DELETE /api/admin/implementations/{id}`

## 🏗️ Architecture Design

DevPulse uses a **Modular Monolithic Architecture**. The code is separated into distinct packages (`user`, `idea`, `implementation`, `config`, `github`, `vote`, `trending`, `admin`), making it highly cohesive and easy to extract into microservices in the future.

- **Data Flow:** `Controller` -> `Service` -> `Repository` -> `Database`
- **Security:** CSRF is disabled (stateless REST API). Every protected endpoint validates identity via a `JwtFilter` executing before standard Spring Security filters.
- **Data Transfer:** Strict use of Request and Response DTOs prevents exposing database entities to the client layer. 

---
*Built with ❤️ for Developers*
