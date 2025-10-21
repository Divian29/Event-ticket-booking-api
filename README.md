# Event Ticket Booking System

A Spring Boot application that provides a RESTful API for managing event ticket bookings with concurrency handling, waiting lists, and database persistence.

---

## Features

- Initialize events with a total number of tickets.
- Book tickets concurrently for users.
- Automatic waiting list handling when events are sold out.
- Cancel bookings and automatically assign tickets to waiting list users.
- View event status, including available tickets and waiting list count.
- Concurrency-safe booking and cancellation operations.
- Persist orders and events in a relational database.

---

## Tech Stack

- Java 17
- Spring Boot 3.5.6
- Spring Data JPA
- PostgreSQL
- Flyway (Database migration)
- Bucket4j (Rate limiting)
- JUnit 5 & Mockito (Unit and integration testing)
- Lombok

---

## Setup & Running Instructions

1. **Clone the repository:**

   ```bash
   git clone https://github.com/Divian29/Event-ticket-booking-api.git
   cd event-ticket-booking-system
 ===============================  
Run Flyway migrations:

Flyway will automatically create tables on application start if configured properly.
================================
Build and run the application:
   mvn clean install
   mvn spring-boot:run
=======================
API Documentation
=====================
  a) Initialize Event

Endpoint: POST /api/events/initialize

Request Body: {
"eventName": "Music Concert",
"totalTickets": 100
}

b)Book Ticket

Endpoint: POST /api/events/book

Request Body:
{
"eventId": 1,
"userId": 5
}


========================
Design Choices
=======================
Concurrency: ReentrantLock ensures thread-safety during booking/cancellation.

Optimistic Locking: @Version in Event entity prevents race conditions when multiple updates occur.

DTOs: Separate request and response classes for clarity and maintainability.

Waiting List: Managed in the database for persistent ordering.

Service Layer: Handles business logic; controller only exposes endpoints.

Error Handling: Global exception handler provides meaningful error messages.


6. Testing

Unit Tests: Cover service layer with Mockito.

Logging & Rate Limiting

Logging implemented using Slf4j for tracking operations.

Rate limiting implemented using Bucket4j to prevent abuse of endpoints.






From Olivia  Chidiogo Ikejiuba