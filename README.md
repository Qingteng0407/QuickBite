# QuickBite

QuickBite is a work-in-progress food ordering backend built with Spring Boot. It provides separate APIs for restaurant administrators and customers, with MySQL persistence, Redis caching, JWT authentication, and object-storage-based image uploads.

This repository contains **Cancat Take Out (能猫外卖)**, a learning project extended from the Sky Take Out course while studying practical Java backend development.

## Current Features

### Admin

- Employee login and account management
- Category management
- Dish creation, pagination, editing, deletion, and status management
- Dish flavour management
- Automatic population of audit fields with Spring AOP
- Image upload to Alibaba Cloud OSS
- Shop business status management with Redis
- Automatic cache invalidation after dish changes
- Automatic set meal suspension when a related dish is suspended

### Customer

- WeChat user login
- Shop business status query
- Category browsing
- Dish and flavour browsing
- Set meal browsing
- Redis-backed dish list caching

### Planned

- Shopping cart
- Address book
- Order placement and payment flow
- Order status management
- WebSocket notifications
- Dashboard statistics and reports

## Tech Stack

- Java 18
- Spring Boot 2.7.3
- Spring MVC
- Spring Data Redis
- MyBatis
- MySQL
- Druid
- PageHelper
- JWT
- Spring AOP
- Knife4j / Swagger
- Alibaba Cloud OSS
- Maven
- Docker (local Redis)

## Project Structure

```text
QuickBite
├── sky-common   # Shared constants, utilities, context and result objects
├── sky-pojo     # Entities, DTOs and view objects
└── sky-server   # Controllers, services, mappers and application configuration
```

The main request flow is:

```text
Controller -> Service -> Mapper -> MySQL
                         |
                         -> Redis cache
```

## Getting Started

### Prerequisites

- JDK 18
- Maven 3.8+
- MySQL 8+
- Redis 6+

### Configuration

The application expects the following local services:

- MySQL: `localhost:3306`
- Database: `sky_take_out`
- Redis: `localhost:6379`
- Redis database: `10`

Configure your own database password, OSS credentials, and WeChat credentials before starting the application.

> Never commit real passwords, access keys, or application secrets. Use environment variables or a local configuration file excluded by Git.

### Run

```bash
mvn -pl sky-server -am spring-boot:run
```

The backend starts on:

```text
http://localhost:8080
```

## Build

```bash
mvn -pl sky-server -am -DskipTests compile
```

## Project Status

The project is under active development. The current focus is completing the customer ordering workflow and improving test coverage, documentation, and deployment configuration.

## Acknowledgements

- [Sky Take Out (苍穹外卖)](https://www.bilibili.com/video/BV1TP411v7v6) by 黑马程序员
