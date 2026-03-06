---

# Zomato Backend System – Spring Boot

Java | Spring Boot | PostgreSQL | PostGIS | JWT | System Design

A backend system that simulates the core functionality of an online food delivery platform similar to **Zomato**.

---

# System Design Summary

This project demonstrates the backend architecture of a food delivery platform similar to Zomato.

Key architectural characteristics:

* Backend services implemented using **Spring Boot**
* Domain-based services for **customers, restaurants, orders, and delivery executives**
* **JWT-based authentication and role-based authorization**
* **PostgreSQL database with PostGIS extension** for geospatial queries
* Complete **order lifecycle management** from order placement to delivery

The system models the full food ordering workflow including restaurant discovery, order creation, delivery assignment, and payment handling.

---

# Application Overview

The platform enables customers to discover nearby restaurants, place food orders, and track deliveries.

Core capabilities include:

* restaurant discovery
* menu browsing
* order placement
* delivery executive assignment
* order lifecycle management
* payment handling
* ratings for restaurants and delivery executives
* geospatial queries to find nearby restaurants and delivery executives

The backend is organized into domain services responsible for customers, restaurants, orders, delivery operations, and authentication.

---

# Features

### Customer

* find nearby restaurants
* view restaurant menus
* create order requests
* place orders
* cancel orders
* track order status
* rate restaurants
* rate delivery executives
* manage delivery addresses
* add funds to wallet

---

### Restaurant

* manage menu items
* view incoming orders
* accept or reject cancellations

---

### Delivery Executive

* accept delivery assignments
* confirm order pickup
* confirm order delivery
* update delivery status
* withdraw wallet balance
* rate customers

---

### Admin

* onboard restaurants
* onboard delivery executives
* ban restaurants
* ban delivery executives
* view system orders

---

# Tech Stack

### Backend

* Java
* Spring Boot
* Spring Data JPA
* Spring Security
* REST APIs

### Authentication

* JWT Token Authentication
* Role-based Authorization

### Database

* PostgreSQL
* **PostGIS extension (for geospatial queries)**

### Tools

* Maven
* Swagger UI

---

# System Architecture

The backend follows a **layered architecture** with domain services.

```text
Controller Layer
        ↓
Service Layer
        ↓
Repository Layer
        ↓
PostgreSQL + PostGIS
```

Major domain services:

* **UserService** – authentication and user management
* **CustomerService** – customer operations
* **RestaurantService** – restaurant and menu management
* **OrderRequestService** – temporary order creation
* **OrderService** – order lifecycle management
* **DeliveryExecutiveService** – delivery operations
* **Wallet** – payment handling
* **Rating** – rating and feedback

---

# Architecture Diagram

The following diagram illustrates the service interactions during the food ordering workflow.

![Zomato Architecture](zomato-system-architecture.png)

---

# Food Ordering Workflow

### Step 1 — Customer discovers nearby restaurants

Customers search for restaurants close to their location.

```text
GET /restaurants/nearby
```

RestaurantService uses **PostGIS geospatial queries** to find nearby restaurants based on latitude and longitude.

---

### Step 2 — Customer views restaurant menu

Customers view menu items of a selected restaurant.

```text
GET /restaurants/{restaurantId}/menu
```

RestaurantService returns available menu items.

---

### Step 3 — Customer builds order request

Customers add or remove menu items to create an order request.

```text
POST /orderRequest/addMenuItem
DELETE /orderRequest/removeMenuItem
```

OrderRequestService maintains the temporary order.

---

### Step 4 — Customer places order

```text
POST /orders/placeOrder
```

The system performs:

* order validation
* price calculation
* payment processing
* order creation

---

### Step 5 — Assign delivery executive

OrderService finds the nearest available delivery executive using **PostGIS location queries**.

```text
findNearestDeliveryExecutive()
```

The order is then assigned for delivery.

---

### Step 6 — Delivery lifecycle

Delivery executive performs the following operations:

```text
acceptOrderDelivery
confirmOrderPickup
confirmOrderDelivery
```

The order status is updated accordingly.

---

# Order Lifecycle

Orders progress through the following states:

```text
CREATED
ACCEPTED
PREPARING
OUT_FOR_DELIVERY
DELIVERED
CANCELLED
```

---

# Authentication & Authorization

The system uses **JWT-based authentication**.

Authentication endpoints:

```text
POST /signup
POST /login
```

After login:

* server issues a **JWT token**
* token must be included in request headers

Example:

```text
Authorization: Bearer <JWT_TOKEN>
```

Role-based access control ensures:

* customers access customer APIs
* restaurants access restaurant APIs
* delivery executives access delivery APIs
* admins access admin APIs

---

# Core Entities

Main domain entities:

```text
User
Customer
Restaurant
MenuItem
Order
OrderRequest
DeliveryExecutive
Wallet
Address
Rating
```

Relationships:

```text
Customer → Order
Restaurant → MenuItem
Order → DeliveryExecutive
Customer → Address
```

Geospatial fields stored using **PostGIS** enable efficient queries such as:

* nearest restaurants
* nearest delivery executives
* delivery distance calculations

---

# Design Patterns Used

The system uses several object-oriented design patterns:

* Builder Pattern
* Factory Pattern
* Singleton Pattern

These patterns help maintain modular architecture and clean service design.

---

# Additional Components

Other infrastructure features include:

* Swagger API documentation
* GlobalExceptionHandler
* GlobalResponseHandler
* DEV and PROD profiles

---

# Running the Project

Clone the repository

```text
git clone https://github.com/lokesh2yss/Spring-Boot-Zomato-App
```

Navigate to the project directory

```text
cd Spring-Boot-Zomato-App
```

Run the application

```text
mvn spring-boot:run
```

Ensure PostgreSQL is running and configured in `application.properties`.

---

# Future Improvements

Possible enhancements:

* Redis caching for restaurant search
* Kafka event-driven architecture
* real-time delivery tracking
* microservice architecture
* Docker deployment
* distributed order assignment

---

# Author

Lokesh Kumar
Senior Backend Engineer
Java | Spring Boot | Distributed Systems

LeetCode
[https://leetcode.com/u/lokeshtalks/](https://leetcode.com/u/lokeshtalks/)

---

