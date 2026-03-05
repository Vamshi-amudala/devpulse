<div align="center">

<img src="https://capsule-render.vercel.app/api?type=waving&color=gradient&customColorList=6,11,20&height=200&section=header&text=DevPulse&fontSize=80&fontColor=fff&animation=twinkling&fontAlignY=35&desc=Developer%20Project%20Showcase%20Platform&descAlignY=60&descSize=18" width="100%"/>

<br/>

[![Java](https://img.shields.io/badge/Java_21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot_3.2.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)](https://redis.io/)
[![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)](https://jwt.io/)
[![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)](https://maven.apache.org/)

<br/>

> **A robust REST API backend for managing developer accounts, project ideas, implementations, voting, and trending algorithms — built with Spring Boot 3 & Java 21.**

<br/>

<img src="https://readme-typing-svg.demolab.com?font=Fira+Code&weight=600&size=18&pause=1000&color=63B3ED&center=true&vCenter=true&multiline=true&repeat=true&width=600&height=60&lines=JWT+Auth+%E2%80%A2+GitHub+Integration+%E2%80%A2+Redis+Caching;Idea+Management+%E2%80%A2+Voting+%E2%80%A2+Trending+Algorithm" alt="Typing SVG" />

</div>

---

## ✨ Features

<table>
<tr>
<td width="50%">

### 🔐 JWT Authentication
Secure registration & login with **BCrypt** password hashing. Stateless JWT tokens validated on every protected request.

### 💡 Idea Management
Full **CRUD** operations for project ideas with pagination. Only creators can modify or delete their own content.

### ⚙️ Implementations
Developers submit **GitHub repositories** as solutions to ideas. Multiple implementations supported per idea.

</td>
<td width="50%">

### 🐙 GitHub Integration
Auto-fetches **star counts**, primary language, and latest commits. Background scheduler keeps data fresh.

### 🔥 Voting & Trending
Toggle upvotes on implementations. A **gamified trending algorithm** backed by Redis caching surfaces the best work.

### 🛡️ Admin Controls
Role-protected endpoints for **cascading deletions** of users, ideas, and implementations with clean error handling.

</td>
</tr>
</table>

---

## 🛠️ Tech Stack

<div align="center">

| Layer | Technology | Purpose |
|---|---|---|
| **Framework** | ![Spring Boot](https://img.shields.io/badge/Spring_Boot_3-6DB33F?style=flat&logo=springboot&logoColor=white) | Core application framework |
| **Language** | ![Java](https://img.shields.io/badge/Java_21-ED8B00?style=flat&logo=openjdk&logoColor=white) | Runtime & language features |
| **Database** | ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=flat&logo=postgresql&logoColor=white) | Primary data store |
| **ORM** | ![Hibernate](https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=flat&logo=spring&logoColor=white) | Entity management / Hibernate |
| **Security** | ![Spring Security](https://img.shields.io/badge/Spring_Security_6-6DB33F?style=flat&logo=springsecurity&logoColor=white) | Auth, JJWT token handling |
| **Caching** | ![Redis](https://img.shields.io/badge/Redis-DC382D?style=flat&logo=redis&logoColor=white) | Trending algorithm cache |
| **Validation** | ![Jakarta](https://img.shields.io/badge/Jakarta_Validation-007396?style=flat&logo=java&logoColor=white) | Bean constraint validation |
| **Build** | ![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat&logo=apachemaven&logoColor=white) | Dependency & build management |
| **Utilities** | ![Lombok](https://img.shields.io/badge/Lombok-pink?style=flat) · ![Dotenv](https://img.shields.io/badge/Dotenv-ECD53F?style=flat&logo=dotenv&logoColor=black) | Boilerplate & env config |

</div>

---

## 🏗️ Architecture

```
DevPulse — Modular Monolithic Architecture
──────────────────────────────────────────
  📡 Controller  →  ⚙️ Service  →  🗄️ Repository  →  🐘 PostgreSQL
                                                    ↕
                                               ⚡ Redis Cache

Packages:  user · idea · implementation · vote · trending · github · admin · config

Security:  Every request → JwtFilter → Spring Security filters → Protected endpoint
Data:      Request DTOs → Service layer → Entity → Response DTOs (no entity leakage)
```

> **Modular by design.** Each package is self-contained and can be extracted into a microservice independently.

---

## 📋 Prerequisites

Before running DevPulse locally, ensure you have the following installed:

| Requirement | Version | Notes |
|---|---|---|
| ☕ **Java** | 21+ | LTS release required |
| 🏗️ **Maven** | Latest | Build & dependency management |
| 🐘 **PostgreSQL** | Any recent | Create a `devpulse` database |
| ⚡ **Redis** | Any recent | Runs on default port `6379` |
| 🐙 **GitHub PAT** | — | Bypasses GitHub API rate limits |

---

## 🚀 Getting Started

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/your-username/devpulse.git
cd devpulse
```

### 2️⃣ Configure Environment Variables

Create a `.env` file in the **root directory** (same level as `pom.xml`):

```properties
# ── Database ───────────────────────────────
DB_URL=jdbc:postgresql://localhost:5432/devpulse
DB_USERNAME=your_postgres_username
DB_PASSWORD=your_postgres_password

# ── Security ───────────────────────────────
JWT_TOKEN=your_base64_encoded_secret_key_for_jwt

# ── GitHub Integration ─────────────────────
GITHUB_TOKEN=your_github_personal_access_token
```

### 3️⃣ Build the Application

```bash
mvn clean install
```

### 4️⃣ Run the Server

```bash
mvn spring-boot:run
```

> 🟢 Server starts at **`http://localhost:8080`**

---

## 🔌 API Reference

<details>
<summary><b>👤 User Module</b></summary>

<br/>

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| `POST` | `/api/users/register` | Register a new user | ❌ Public |
| `POST` | `/api/users/login` | Authenticate → receive JWT | ❌ Public |
| `GET` | `/api/users/me` | Get current user details | ✅ JWT |

</details>

<details>
<summary><b>💡 Idea Module</b></summary>

<br/>

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| `POST` | `/api/ideas/create` | Create a new idea | ✅ JWT |
| `GET` | `/api/ideas/all` | List all ideas (paginated) | ✅ JWT |
| `GET` | `/api/ideas/{id}` | Get idea by ID | ✅ JWT |
| `PUT` | `/api/ideas/{id}` | Full update (owner only) | ✅ JWT |
| `PATCH` | `/api/ideas/{id}` | Partial update (owner only) | ✅ JWT |
| `DELETE` | `/api/ideas/{id}` | Delete idea (owner only) | ✅ JWT |

</details>

<details>
<summary><b>⚙️ Implementation Module</b></summary>

<br/>

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| `POST` | `/api/ideas/{ideaId}/implementations` | Submit a GitHub repo as solution | ✅ JWT |
| `GET` | `/api/ideas/{ideaId}/implementations` | List all implementations for idea | ✅ JWT |
| `DELETE` | `/api/implementations/{id}` | Delete implementation (owner only) | ✅ JWT |

</details>

<details>
<summary><b>🔥 Voting & Trending</b></summary>

<br/>

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| `POST` | `/api/implementations/{id}/vote` | Upvote / toggle vote | ✅ JWT |
| `GET` | `/api/trending` | Get trending implementations (cached) | ✅ JWT |

</details>

<details>
<summary><b>🛡️ Admin Module — <code>ROLE_ADMIN</code> required</b></summary>

<br/>

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/admin/users` | List all platform users |
| `DELETE` | `/api/admin/users/{id}` | Cascading delete user + all their content |
| `DELETE` | `/api/admin/ideas/{id}` | Delete any idea |
| `DELETE` | `/api/admin/implementations/{id}` | Delete any implementation |

</details>

---

## 🔒 Security Model

```
Public Routes          →  /api/users/register  ·  /api/users/login
Protected Routes       →  All others require:  Authorization: Bearer <JWT>
Admin Routes           →  /api/admin/**  require ROLE_ADMIN
```

- **CSRF** is disabled — stateless REST API
- Every request passes through a custom `JwtFilter` before Spring Security filters
- Passwords hashed with **BCrypt**
- Clean, consistent JSON error responses for `401`, `403`, `404`, and validation failures

---

<div align="center">

<img src="https://capsule-render.vercel.app/api?type=waving&color=gradient&customColorList=6,11,20&height=120&section=footer&animation=twinkling" width="100%"/>

**Built with ❤️ for Developers**

*Spring Boot 3.2.5 · Java 21 · PostgreSQL · Redis · GitHub API*

</div>