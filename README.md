# Kumo — OTA Update System (Backend)

> A cloud-based OTA firmware update platform backend, providing device connection management, campaign dispatching, real-time status reporting, and reporting features.

---

## Table of Contents

- [Project Overview](#project-overview)
- [System Architecture](#system-architecture)
- [Wireframe](#wireframe)
- [ER Model](#er-model)
- [WebSocket Connection Flow](#websocket-connection-flow)
- [Folder Structure](#folder-structure)
- [Tech Stack](#tech-stack)
- [Related Frontend Projects](#related-frontend-projects)
- [Installation & Setup](#installation--setup)
- [Swagger API Docs](#swagger-api-docs)
- [Core Technologies](#core-technologies)

---

## Project Overview

Kumo is an OTA (Over-The-Air) firmware update management platform consisting of three repositories:

| Repo | Description |
|------|-------------|
| `kumo_backend` (this repo) | Spring Boot backend providing REST API and WebSocket services |
| `kumo_frontend` | Admin frontend for managing campaigns, viewing device status and reports |
| `kumo_demo_device` | Device-side frontend simulator, simulating IoT devices reporting update status via WebSocket |

**Core Features:**
- Device connection and firmware version reporting (WebSocket / STOMP)
- OTA Campaign creation, management and dispatching
- Device connection log queries
- 7-day received device count report by brand
- JWT authentication protecting REST APIs
- Redis caching for daily received device count per brand

---

## System Architecture

![System Architecture](docs/%E6%88%AA%E5%9C%96%202026-04-18%2018.35.42.png)

---

## Wireframe

> https://miro.com/app/board/uXjVJ-xg_CM=/

---

## ER Model

![ER Model](docs/%E6%88%AA%E5%9C%96%202026-04-18%2018.30.56.png)

**Table Descriptions:**

| Table | Description |
|-------|-------------|
| `devices` | Device basic info (SN, brand, model) |
| `connect_logs` | Device connection logs and status per session |
| `campaigns` | OTA update task configuration |
| `status_ref` | Device status reference table (received / succeeded / failed ...) |
| `download_by_ref` | Download method reference table |

---

## WebSocket Connection Flow

> https://mermaid.ai/app/projects/dd4a5778-54e5-4f18-a629-403442f9b1fb/diagrams/5cbbb6cb-1b69-4bea-83c8-c8bda499f62a/version/v0.1/edit

---

## Folder Structure

```
kumo/
├── src/
│   ├── main/
│   │   ├── java/tw/idv/rainbow/
│   │   │   ├── KumoApplication.java          # Application entry point
│   │   │   ├── common/
│   │   │   │   ├── ApiResult.java            # Unified API response format
│   │   │   │   └── JsonConverter.java        # JSON conversion utility
│   │   │   ├── config/
│   │   │   │   ├── SecurityConfig.java       # Spring Security configuration
│   │   │   │   ├── SwaggerConfig.java        # OpenAPI / Swagger configuration
│   │   │   │   ├── WebSocketConfig.java      # STOMP WebSocket configuration
│   │   │   │   └── SpringMvcConfig.java      # MVC configuration
│   │   │   ├── security/
│   │   │   │   ├── JwtUtil.java              # JWT generation and validation
│   │   │   │   └── JwtFilter.java            # JWT request filter
│   │   │   ├── web/
│   │   │   │   ├── controller/               # REST API Controllers
│   │   │   │   │   ├── LoginController.java
│   │   │   │   │   ├── DeviceController.java
│   │   │   │   │   ├── CampaignController.java
│   │   │   │   │   ├── ReportController.java
│   │   │   │   │   └── FileController.java
│   │   │   │   ├── dto/                      # Data Transfer Objects
│   │   │   │   ├── entity/                   # JPA Entities
│   │   │   │   ├── repository/               # Spring Data JPA Repositories
│   │   │   │   └── service/                  # Business logic layer
│   │   │   │       └── impl/
│   │   │   └── websocket/
│   │   │       └── ConnectController.java    # WebSocket message handler
│   │   └── resources/
│   │       └── application.properties        # Application configuration
│   └── test/
├── uploads/                                  # Uploaded firmware files directory
├── pom.xml
└── README.md
```

---

## Tech Stack

| Technology | Version |
|------------|---------|
| Java | 17 |
| Spring Boot | 4.0.5 |
| Spring Security | (managed by Spring Boot) |
| Spring Data JPA | (managed by Spring Boot) |
| Spring WebSocket | (managed by Spring Boot) |
| Spring Data Redis (Lettuce) | (managed by Spring Boot) |
| MySQL Connector/J | (managed by Spring Boot) |
| JJWT | 0.11.5 |
| SpringDoc OpenAPI | 2.5.0 |
| JNanoID | 2.0.0 |
| Lombok | (managed by Spring Boot) |
| Maven | 4.0.0 |

---

## Related Frontend Projects

| Name | Repo | Description |
|------|------|-------------|
| `kumo_frontend` | https://github.com/169628/kumo_frontend | Admin UI for Campaign CRUD, device list, and report dashboard |
| `kumo_demo_device` | https://github.com/169628/kumo_demo_device | Simulates IoT devices connecting via WebSocket and reporting update status |

---

## Installation & Setup

### Prerequisites

- Java 17+
- Maven 3.8+
- Docker & Docker Compose

---

### Step 1 — Start the Databases (MySQL + Redis)

Create `docker-compose.yml` (if it doesn't exist):

```yaml
version: '3.8'
services:
  mysql:
    image: mysql:8.0
    container_name: kumo-mysql
    environment:
      MYSQL_ROOT_PASSWORD: 123456
      MYSQL_DATABASE: KUMO
    ports:
      - "3307:3306"
    volumes:
      - kumo-mysql-data:/var/lib/mysql

  redis:
    image: redis:7.0
    container_name: kumo-redis
    command: redis-server --requirepass mypassword
    ports:
      - "6379:6379"

volumes:
  kumo-mysql-data:
```

Start the containers:

```bash
docker compose up -d
```

Verify containers are running:

```bash
docker ps
```

---

### Step 2 — Create Database Tables

Connect to the MySQL container and run the following SQL:

```bash
docker exec -it kumo-mysql mysql -uroot -p123456 KUMO
```

```sql
-- Device status reference table
CREATE TABLE status_ref (
    status_id INT AUTO_INCREMENT PRIMARY KEY,
    status    VARCHAR(50) NOT NULL
);

-- Download method reference table
CREATE TABLE download_by_ref (
    download_by_id SMALLINT AUTO_INCREMENT PRIMARY KEY,
    content        VARCHAR(100) NOT NULL
);

-- Device table
CREATE TABLE devices (
    device_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    sn            VARCHAR(100)  NOT NULL,
    brand         VARCHAR(50)   NOT NULL,
    model         VARCHAR(100)  NOT NULL,
    first_connect TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_sn (sn)
);

-- Connection log table
CREATE TABLE connect_logs (
    id_for_jpa  INT AUTO_INCREMENT PRIMARY KEY,
    session_id  VARCHAR(100),
    device_id   BIGINT        NOT NULL,
    status_id   INT           NOT NULL,
    sv          VARCHAR(50),
    reported_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (device_id)  REFERENCES devices(device_id),
    FOREIGN KEY (status_id)  REFERENCES status_ref(status_id)
);

-- OTA Campaign table
CREATE TABLE campaigns (
    no              INT AUTO_INCREMENT PRIMARY KEY,
    campaign_id     BIGINT GENERATED ALWAYS AS (no * 1000) STORED,
    brand           VARCHAR(50),
    model           VARCHAR(100),
    sv              VARCHAR(50),
    tv              VARCHAR(50),
    file            VARCHAR(255),
    file_size       INT,
    is_test_mode    BOOLEAN DEFAULT FALSE,
    test_list       JSON,
    download_by_id  SMALLINT,
    is_enabled      BOOLEAN DEFAULT TRUE,
    create_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_deleted      BOOLEAN DEFAULT FALSE,
    file_path       VARCHAR(500),
    FOREIGN KEY (download_by_id) REFERENCES download_by_ref(download_by_id)
);

-- Seed reference data
INSERT INTO `status_ref` (`status_id`, `status`) VALUES
    (0, 'timeout'),
    (1, 'received'),
    (2, 'cancel'),
    (3, 'downloading'),
    (4, 'downloaded'),
    (5, 'reboot'),
    (6, 'succeeded'),
    (7, 'failed');

INSERT INTO `download_by_ref` (`download_by_id`, `content`) VALUES
    (1, 'wifi'),
    (2, 'user');
```

---

### Step 3 — Clone the Project

```bash
git clone https://github.com/169628/kumo_backend.git
cd kumo
```

---

### Step 4 — Verify Configuration

Confirm `src/main/resources/application.properties` matches your Docker settings:

```properties
spring.datasource.url=jdbc:mysql://localhost:3307/KUMO
spring.datasource.username=root
spring.datasource.password=123456

spring.data.redis.host=localhost
spring.data.redis.port=6379
spring.data.redis.password=mypassword
```

---

### Step 5 — Build and Start the Backend

```bash
# Using Maven Wrapper
./mvnw spring-boot:run

# Or package first, then run
./mvnw clean package -DskipTests
java -jar target/kumo-*.jar
```

The service listens on `http://localhost:8080` by default.

---

### Step 6 — Verify the Service

```bash
# Test login to obtain a JWT
curl -X POST http://localhost:8080/kumo/api/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin"}'
```

---

## Swagger API Docs

Once the service is running, open the API docs in your browser:

```
http://localhost:8080/kumo/api/swagger-ui.html
```

OpenAPI JSON Schema:

```
http://localhost:8080/kumo/api/v3/api-docs
```

> Most APIs require JWT authentication. Call `/login` first to obtain a token, then click **Authorize** in the top-right of Swagger UI and enter `Bearer <token>`.

---

## API Overview

| Method | Path | Description | Requires JWT |
|--------|------|-------------|--------------|
| POST | `/login` | Login and obtain JWT Token | No |
| GET | `/device` | Get all devices with latest status | Yes |
| GET | `/device/{id}` | Get connection logs for a specific device | Yes |
| GET | `/campaign` | Get all campaigns | Yes |
| GET | `/campaign/{id}` | Get a specific campaign | Yes |
| POST | `/campaign` | Create a campaign (with firmware upload) | Yes |
| PUT | `/campaign/{id}` | Update a campaign | Yes |
| DELETE | `/campaign/{id}` | Delete a campaign | Yes |
| PUT | `/campaign/enable/{id}` | Toggle campaign enabled status | Yes |
| GET | `/report/received` | 7-day received device count report by brand | Yes |
| GET | `/file/{fileName}` | Download firmware file | No |
| WS | `/endpoint` | WebSocket connection endpoint (STOMP) | No |

---

## Core Technologies

### JWT (JSON Web Token)

- Signed using **HMAC SHA-256 (HS256)** algorithm
- Token expiry: **24 hours**
- Clients must include the following header in every REST API request:
  ```
  Authorization: Bearer <token>
  ```
- The following paths are public endpoints that **do not** require JWT:
  - `POST /login`
  - `GET /file/*`
  - `WS /endpoint/**`
  - `/swagger-ui/**`, `/v3/api-docs/**`

---

### WebSocket (STOMP over SockJS)

- Uses the **STOMP** protocol with **SockJS** fallback support
- Devices connect to `/endpoint` and subscribe to `/msg/{sn}` to receive responses
- Devices send messages to `/connect/device/{sn}`
- The admin frontend can subscribe to `/msg/{sn}` to receive real-time device status updates
- WebSocket connections do **not** require JWT authentication

---

### Redis

- Uses **Lettuce** connection pool
- Purpose: Cache daily received device SN sets per brand
- Key format: `{brand}:{yyyy-MM-dd}`
- TTL: **30 days**
- Used by the report API to calculate 7-day received device counts per brand

---

### Default Account

| Username | Password |
|----------|----------|
| `admin` | `admin` |
