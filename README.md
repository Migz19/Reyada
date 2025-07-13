# Reyada CRM

## Authentication Feature

A simple authentication module built with **Java Spring Boot** and **MySQL**, with a basic HTML/CSS/JavaScript frontend.

## Features

- **Register**: Create new user accounts.
- **Login**: Authenticate users and store session in localStorage.
- **Logout**: Clear session and redirect to login.
- **Profile**: Display logged-in user's name and email.
- **Input Validation**: Check email format and password length on the client and server.

## Endpoints

1. **POST `/auth/register`**
   - **Body (JSON)**: `{ "username": "user", "email": "user@example.com", "password": "pass123" }`
   - **Responses**:
     - `200 OK` – User registered
     - `409 Conflict` – Email exists
     - `400 Bad Request` – Invalid input

2. **POST `/auth/login`**
   - **Body (JSON)**: `{ "email": "user@example.com", "password": "pass123" }`
   - **Responses**:
     - `200 OK` – Returns `{ id, name, email }`
     - `401 Unauthorized` – Wrong credentials

3. **GET `profile.html`**
   - Loads user data from `localStorage`
   - Redirects to login if not authenticated

4. **Logout** (client-side)
   - Clears `localStorage` and redirects to `auth.html`

## Setup

1. **Database**: Create MySQL database (e.g., `myapp`).
2. **Config**: Update `application.properties` with DB credentials.
3. **Run**: `mvn spring-boot:run`
4. **Access UI**:
   - `http://localhost:8080/auth.html` — login/register
   - `http://localhost:8080/profile.html` — user profile

---

> **Note**: Passwords are hashed with **BCrypt** before storage.

