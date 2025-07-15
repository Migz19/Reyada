## Reyada CRM

A simple **Java Spring Boot** application with a **MySQL** database and a basic HTML/CSS/JavaScript frontend.

### Features

- **User Authentication** (Register, Login, Logout)
- **Profile**: Display logged-in user's name and email
- **CRM Integration**:
  - Fetch contacts from Bitrix24 CRM and save to MySQL
  - Add new contacts to MySQL and Bitrix24

### Endpoints

1. **POST** `/auth/register`

   - **Request (JSON)**: `{ "username": "user", "email": "user@example.com", "password": "pass123" }`
   - **Responses**:
     - `200 OK` – User registered
     - `409 Conflict` – Email already exists
     - `400 Bad Request` – Invalid input

2. **POST** `/auth/login`

   - **Request (JSON)**: `{ "email": "user@example.com", "password": "pass123" }`
   - **Responses**:
     - `200 OK` – Returns `{ id, name, email }`
     - `401 Unauthorized` – Wrong credentials

3. **GET** `/crm/contacts`

   - **Description**: Fetches contacts from Bitrix24 CRM, saves them to the MySQL database, and returns the list of saved contacts.
   - **Responses**:
     - `200 OK` – Returns list of contact objects
     - `500 Internal Server Error` – Fetch or save failure

4. **POST** `/crm/addContact`

   - **Request (JSON)**: `{ "name": "Contact Name", "email": "contact@example.com", "phone": "1234567890" }`
   - **Description**: Adds a new contact to the MySQL database and also pushes it to Bitrix24 CRM.
   - **Responses**:
     - `200 OK` – Contact added
     - `400 Bad Request` – Invalid input data

### Frontend

- **auth.html**: Registration and login forms
- **profile.html**: Displays user profile and provides an interface to fetch CRM contacts and add new contacts
- **contacts_dashboard.html**: Contacts dashboard to add a new contact or fetch all contacts from Bitrix24 and MySQL

### Setup

1. **Database**: Create a MySQL database (e.g., `myapp`).
2. **Configuration**: Update `application.properties` with your MySQL and Bitrix24 API credentials.
3. **Run**:
   ```bash
   mvn spring-boot:run
   ```
4. **Access**:
   - `http://localhost:8080/auth.html`
   - `http://localhost:8080/profile.html`

> **Note**: Passwords are hashed with **BCrypt** before storage.

