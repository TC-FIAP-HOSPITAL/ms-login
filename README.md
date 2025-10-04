# Pós Tech FIAP - Tech Challenge

## Architecture and Java Development

### MS-Login Microservice

---

## Overview

The **Tech Challenge** is a hands-on project from FIAP’s postgraduate course in Java Architecture and Development, aiming to create a restaurant management system, developed in phases.

> **Main goal:**
> This microservice handles userDomain authentication and management for the MS-Login system, allowing secure access to the platform for restaurants and customers.

---

## Microservice Objective

Develop a robust and complete login microservice using **Spring Boot**, focused on userDomain management. The implemented operations include:

- User creation
- User data updating
- User deletion
- Login validation

The microservice is Docker-ready, uses Docker Compose for orchestration, and is integrated with a relational database (PostgreSQL or H2).

---

## Features

### User Management

- **Registration fields:**

  - User:
    - Name (`String`)
    - Email (`String`)
    - Username (`String`)
    - Password (`String`)
    - Role (`Enum`)
    - Last update date (`Date`)
    - Address (`Address`)
      - Street (`String`)
      - Number (`String`)
      - Complement (`String`)
      - City (`String`)
      - State (`String`)

- **Password change**: Users can change their passwordEncoder.
- **Login validation**: Checks userDomain credentials.
- **User deletion**: Remove registered userDomains.

### User Roles

- Restaurant Owner
- Customer
- Admin

---

## Architecture

The backend is developed in **Spring Boot**, follows **SOLID** principles, and uses **Hexagonal Architecture** and **Clean Architecture**.
_Business rules are isolated from external dependencies._

## Modular Design

> Communication between modules is done via interfaces, so each module can be developed and tested independently.

## Key Modules

- <b>infrastructure/config:</b>

  - <b>swagger/SwaggerConfig</b> - API documentation configuration (Swagger/OpenAPI)
  - <b>init/AdminUserInitializer</b> - Initializes admin userDomain(s) at startup

- <b>entrypoint/controllers:</b>

  - <b>AuthController</b> - Handles POST /login (JWT authentication)
  - <b>UserController</b> - Handles GET, POST, PUT, DELETE /userDomains endpoints for userDomain management with access control

- <b>domain/model:</b>

  - <b>User</b> - Main userDomain domain model/entity
  - <b>Role</b> - User roleEnum enum (ADMIN, CUSTOMER, etc)
  - <b>Address</b> - Value object/entity for userDomain addresses
  - <b>UserRepository</b> - Repository interface for userDomain persistence

- <b>entrypoint/controllers/dto:</b>

  - <b>UserDtoRequest</b> - Used for creating/updating a userDomain (client → server)
  - <b>UserDtoResponse</b> - Used in userDomain API responses (server → client)
  - <b>AddressDto</b> - Used as part of the address payload

- <b>entrypoint/controllers/handler:</b>

  - <b>ApiError</b> - Standard API error response body
  - <b>GlobalExceptionHandler</b> - Handles and maps exceptions to HTTP responses

- <b>application/usecase/userDomain/exceptions:</b>

  - <b>UserAlreadyExistsException, UserNotFoundException, UserHasException</b> - Specific domain exceptions for userDomain operations

- <b>entrypoint/controllers/mappers:</b>

  - <b>UserMapper</b> - Maps between User/UserDto and entity/domain
  - <b>AddressMapper</b> - Maps between Address/AddressDto and entity/domain

- <b>infrastructure/dataproviders/database/entities:</b>

  - <b>JpaUserEntity, JpaAddressEntity</b> - JPA entity mappings for persistence

- <b>application/gateways:</b>

  - <b>JpaUserRepository</b> - Spring Data repository interface
  - <b>PasswordEncoder</b> - Password encoding interface

- <b>infrastructure/dataproviders/database/implementations:</b>

  - <b>UserRepositoryImpl</b> - Implementation for domain persistence

- <b>infrastructure/password:</b>

  - <b>BcryptPasswordEncoderImpl</b> - Password hashing/encoding implementation

- <b>infrastructure/config/security:</b>

  - <b>SecurityConfig</b> - Overall security configuration (roles, HTTP security)
  - <b>JwtAuthenticationFilter</b> - Parses JWT from requests
  - <b>JwtUtil</b> - Signs, validates, and extracts claims from JWTs
  - <b>MyUserDetails</b> - Custom userDomain principal for Spring Security
  - <b>MyUserDetailsService</b> - Loads userDomains for authentication
  - <b>SecurityExceptionHandlerConfig</b> - Security-specific exception handling
  - <b>SecurityUtil</b> - Helper methods for current authentication

- <b>application/usecase/userDomain:</b>
  - <b>CreateUserUsecase, GetUsersUsecase, etc.</b> - Use case interfaces
  - <b>implementation/CreateUserUsecaseImpl, etc.</b> - Use case implementations

---

## 🛠️ Technology Stack

- **Java 21**
- **Spring Boot** (core app)
- **Spring Data JPA** (data access)
- **Spring Security** (auth/authz)
- **H2** / **PostgreSQL** (databases)
- **Docker** / **Docker Compose**
- **MapStruct** (object mapping)
- **Swagger** (API docs)
- **Mockito** (mocking)
- **JUnit 5** (tests)
- **Postman** (manual API testing)

---

## 🔧 Configurations

- **Default Admin User:**

  - `login`: `admin`
  - `passwordEncoder`: Value from `admin.passwordEncoder` in `application.properties`

- **JWT Secret:**

  - Value from `jwt.secret` in `application.properties`

- **Database:**
  - Env`SPRING_PROFILES_ACTIVE=postgres` to use PostgreSQL
  - Env`SPRING_PROFILES_ACTIVE=h2` to use in memory H2

---

## 🚀 Running the Project

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/ms-login.git
```

### 2. Go to the Project Directory

```bash
cd ms-login
```

### 3. Start the Login Microservice

```bash
docker-compose -f docker-compose-local.yaml up -d
```

or

```
docker-compose -f docker-compose-postgres.yaml up -d
```

### 4. Import Collection

```bash
Import Postman Collection from /postman/Postman.json
```

---

## 📁 Project Structure

```plaintext
ms-login/
├── Dockerfile
├── docker-compose-local.yaml
├── docker-compose-postgres.yaml
├── docs/
│   └── ..
├── postman/
│   └── Postman.json
├── README.md
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/fiap/ms/login/
    │   │       ├── application/
    │   │       │   ├── gateways/
    │   │       │   │   ├── JpaUserRepository.java
    │   │       │   │   └── PasswordEncoder.java
    │   │       │   └── usecase/
    │   │       │       └── userDomain/
    │   │       │           ├── CreateUserUsecase.java
    │   │       │           ├── DeleteUserUsecase.java
    │   │       │           ├── GetUserByIdUsecase.java
    │   │       │           ├── GetUsersUsecase.java
    │   │       │           ├── UpdateUserUsecase.java
    │   │       │           ├── exceptions/
    │   │       │           │   ├── UserAlreadyExistsException.java
    │   │       │           │   └── UserNotFoundException.java
    │   │       │           └── implementation/
    │   │       │               ├── CreateUserUsecaseImpl.java
    │   │       │               ├── DeleteUserUsecaseImpl.java
    │   │       │               ├── GetUserByIdUsecaseImpl.java
    │   │       │               ├── GetUsersUsecaseImpl.java
    │   │       │               └── UpdateUserUsecaseImpl.java
    │   │       ├── domain/
    │   │       │   └── model/
    │   │       │       ├── Address.java
    │   │       │       ├── Role.java
    │   │       │       ├── User.java
    │   │       │       └── UserRepository.java
    │   │       ├── entrypoint/
    │   │       │   └── controllers/
    │   │       │       ├── AuthController.java
    │   │       │       ├── UserController.java
    │   │       │       ├── dto/
    │   │       │       │   ├── AddressDto.java
    │   │       │       │   ├── UserDtoRequest.java
    │   │       │       │   └── UserDtoResponse.java
    │   │       │       ├── handler/
    │   │       │       │   ├── ApiError.java
    │   │       │       │   └── GlobalExceptionHandler.java
    │   │       │       └── mappers/
    │   │       │           ├── AddressMapper.java
    │   │       │           └── UserMapper.java
    │   │       ├── infrastructure/
    │   │       │   ├── config/
    │   │       │   │   ├── init/
    │   │       │   │   │   └── AdminUserInitializer.java
    │   │       │   │   ├── security/
    │   │       │   │   │   ├── JwtAuthenticationFilter.java
    │   │       │   │   │   ├── JwtUtil.java
    │   │       │   │   │   ├── MyUserDetails.java
    │   │       │   │   │   ├── MyUserDetailsService.java
    │   │       │   │   │   ├── SecurityConfig.java
    │   │       │   │   │   ├── SecurityExceptionHandlerConfig.java
    │   │       │   │   │   └── SecurityUtil.java
    │   │       │   │   └── swagger/
    │   │       │   │       └── SwaggerConfig.java
    │   │       │   ├── dataproviders/
    │   │       │   │   └── database/
    │   │       │   │       ├── entities/
    │   │       │   │       │   ├── JpaAddressEntity.java
    │   │       │   │       │   └── JpaUserEntity.java
    │   │       │   │       └── implementations/
    │   │       │   │           └── UserRepositoryImpl.java
    │   │       │   └── password/
    │   │       │       └── BcryptPasswordEncoderImpl.java
    │   │       └── MsLoginApplication.java
    │   └── resources/
    │       ├── application.properties
    │       ├── application-h2.properties
    │       ├── application-postgres.properties
    │       ├── static/
    │       └── templates/
    └── test/
        └── java/com/fiap/ms/login/
            ├── application/
            │   └── usecase/
            │       └── userDomain/
            │           ├── exceptions/
            │           │   └── UserExceptionsTest.java
            │           └── implementation/
            │               ├── CreateUserUsecaseImplTest.java
            │               ├── DeleteUserUsecaseImplTest.java
            │               ├── GetUserByIdUsecaseImplTest.java
            │               ├── GetUsersUsecaseImplTest.java
            │               └── UpdateUserUsecaseImplTest.java
            ├── domain/
            │   └── model/
            │       ├── RoleTest.java
            │       └── UserTest.java
            ├── entrypoint/
            │   └── controllers/
            │       ├── UserControllerTest.java
            │       ├── handler/
            │       │   └── GlobalExceptionHandlerTest.java
            │       └── mappers/
            │           └── UserMapperTest.java
            ├── infrastructure/
            │   ├── config/
            │   │   └── security/
            │   │       ├── MyUserDetailsServiceTest.java
            │   │       └── SecurityUtilTest.java
            │   ├── dataproviders/
            │   │   └── database/
            │   │       ├── entities/
            │   │       │   └── JpaUserEntityTest.java
            │   │       └── implementations/
            │   │           └── UserRepositoryImplTest.java
            │   ├── http/
            │   │   └── HttpClientTest.java
            │   └── password/
            │       └── BcryptPasswordEncoderImplTest.java
            └── MsLoginApplicationTests.java
```

---

## 🧪 Testing & Quality Assurance

This project maintains high code quality standards through comprehensive testing:

### Test Coverage

- **Current coverage**: 82% (JaCoCo report in `target/site/jacoco/index.html`)

### Test Organization

- **Clean Architecture Testing**: Tests are organized following the same clean architecture principles as the main code
- **Isolated Unit Tests**: Each component is tested in isolation using mocking frameworks
- **Comprehensive Coverage**: Tests cover both happy path and error scenarios
- **Authentication Testing**: Security aspects are thoroughly tested including JWT token handling

### Running Tests

```bash
# Run all tests (uses in-memory H2 profile)
mvn test -Dspring.profiles.active=h2

# Generate coverage report
mvn jacoco:report

# View coverage report
open target/site/jacoco/index.html
```

### Quality Tools

- **JaCoCo**: Code coverage analysis and reporting
- **Mockito**: Mocking framework for unit tests
- **JUnit 5**: Testing framework with comprehensive assertion library
- **Spring Boot Test**: Integration testing support

---

## Endpoints

- Postman collection: `/postman/Postman.json`
- Application: [http://localhost:8080](http://localhost:8080)
- Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- OpenAPI Docs: [http://localhost:8080/api-docs](http://localhost:8080/api-docs)

### User Controller

#### GET /userDomains

- **Summary:** Get all userDomains. <b>Administrators only.</b>
- **Description:** Returns a <b>paginated list</b> of all userDomains.
- **Operation ID:** `getUsers`
- **Tags:** `userDomain-controller`
- **Query Parameters:**
  - `page` (optional, integer) - Page index, default 0.
  - `size` (optional, integer) - Page size, default 10.
- **Responses:**
  - `200 OK`: Users retrieved successfully. Returns an array of <b>UserDtoResponse</b> objects.

#### PUT /userDomains

- **Summary:** Update userDomain content. <b>Only admin can change other userDomains. User can change itself, except for roleEnum.</b>
- **Description:** Updates userDomain data.
- **Operation ID:** `updateUser`
- **Tags:** `userDomain-controller`
- **Request Body:** `application/json` with schema <b>UserDtoRequest</b>
- **Responses:**
  - `200 OK`: User updated successfully. Returns a <b>UserDtoResponse</b> object.

#### POST /userDomains

- **Summary:** Create a new userDomain.<b> Anyone can create a CUSTOMER userDomain. Admin can create any roleEnum.</b>
- **Description:** Creates a new userDomain.
- **Operation ID:** `createUser`
- **Tags:** `userDomain-controller`
- **Request Body:** `application/json` with schema <b>UserDtoRequest</b>
- **Responses:**
  - `201 Created`: User created successfully. Returns a <b>UserDtoResponse</b> object.

#### GET /userDomains/{user_id}

- **Summary:** Get userDomain by ID. <b>User can only get their own data. Admin can get any userDomain.</b>
- **Description:** Returns a userDomain by their <b>UUID</b>.
- **Operation ID:** `getUser`
- **Tags:** `userDomain-controller`
- **Parameters:**
  - `user_id` (path, required): <b>string (UUID)</b>
- **Responses:**
  - `200 OK`: User retrieved successfully. Returns a <b>UserDtoResponse</b> object.

#### DELETE /userDomains/{user_id}

- **Summary:** Deletes a userDomain. <b>Only admin can delete other userDomains. User can delete themselves.</b>
- **Description:** Soft deletes a userDomain (by unique identifier).
- **Operation ID:** `deleteUser`
- **Tags:** `userDomain-controller`
- **Parameters:**
  - `user_id` (path, required): <b>string (UUID)</b>
- **Responses:**
  - `204 No Content`: User deleted successfully.

### Auth Controller

#### POST /login

- **Summary:** Logs in a userDomain.
- **Operation ID:** `login`
- **Tags:** `auth-controller`
- **Request Body:** `application/json` with schema <b>LoginRequestDTO</b>
- **Responses:**
  - `200 OK`: Returns a <b>LoginResponse</b> object.

## Schemas

### UserDtoRequest

```json
{
  "type": "object",
  "properties": {
    "id": {
      "type": "string",
      "format": "uuid"
    },
    "name": {
      "type": "string"
    },
    "email": {
      "type": "string"
    },
    "username": {
      "type": "string"
    },
    "password": {
      "type": "string"
    },
    "roleEnum": {
      "type": "string"
    },
    "address": {
      "$ref": "#/components/schemas/AddressDto"
    }
  }
}
```

### UserDtoResponse

```
{
  "type": "object",
  "properties": {
    "id": {
      "type": "string",
      "format": "uuid"
    },
    "name": {
      "type": "string"
    },
    "email": {
      "type": "string"
    },
    "username": {
      "type": "string"
    },
    "roleEnum": {
      "type": "string"
    },
    "createdAt": {
      "type": "string",
      "format": "date-time"
    },
    "modifiedAt": {
      "type": "string",
      "format": "date-time"
    },
    "address": {
      "$ref": "#/components/schemas/AddressDto"
    }
  }
}
```

### AddressDto

```json
{
  "type": "object",
  "properties": {
    "id": { "type": "string", "format": "uuid" },
    "street": { "type": "string" },
    "number": { "type": "string" },
    "complement": { "type": "string" },
    "city": { "type": "string" },
    "state": { "type": "string" }
  }
}
```

### LoginRequestDTO

```json
{
  "type": "object",
  "properties": {
    "username": { "type": "string" },
    "password": { "type": "string" }
  }
}
```

### LoginResponse

```json
{
  "type": "object",
  "properties": {
    "token": { "type": "string" },
    "username": { "type": "string" },
    "userId": { "type": "string" },
    "expiresAt": { "type": "string" }
  }
}
```

# 🥡 <b>MS-Login - Postman API Collection Documentation</b>

This API supports typical <b>userDomain management operations</b> (login, create userDomain, update, delete, etc.) for two roles: <code>ADMIN</code> and <code>USER</code>. The list below explains how each call works, what you have to provide, and what you'll get back.

---

## 🤖 SECTION 1: AS ADMIN

### 1. [ADMIN] Login

- Endpoint: POST /login
- Description: Authenticates as admin userDomain, returning a JWT token in the response.
- Request Body Example:
  {
  "username": "admin",
  "password": "Password1@"
  }
- Usage: Use this call first. The token from the response will be stored in a Postman variable <code>jwt_admin</code> to be used in the following admin requests.
- Response:
  {
  "token": "(JWT_HERE)",
  "username": "admin",
  "expiresAt": "...",
  "userId": "(UUID)"
  }

### 2. [ADMIN] Create Customer

- Endpoint: POST /userDomains
- Auth: Bearer token {{jwt_admin}}
- Description: Admin creates a new CUSTOMER userDomain.
- Request Body Example:
  {
  "name": "John Doe",
  "email": "john@doe.com.br",
  "username": "johnny",
  "password": "Password1@",
  "roleEnum": "CUSTOMER",
  "address": {
  "street": "Baker Street",
  "number": "221B",
  "complement": "",
  "city": "London",
  "state": "BL"
  }
  }
- Result: Returns the created userDomain (with id and address).

### 3. [ADMIN] Create Restaurant Owner

- Endpoint: POST /userDomains
- Auth: Bearer token {{jwt_admin}}
- Description: Admin creates a new RESTAURANT_OWNER userDomain.
- Request: Like above, but roleEnum is RESTAURANT_OWNER.
- Result: Returns the created restaurant owner userDomain.

### 4. [ADMIN] Create Admin

- Endpoint: POST /userDomains
- Auth: Bearer token {{jwt_admin}}
- Description: Admin creates another admin.
- Request: Like above, but roleEnum is ADMIN.
- Result: Returns new admin.

### 5. [ADMIN] Get All Users

- Endpoint: GET /userDomains?page=0&size=10
- Auth: Bearer token {{jwt_admin}}
- Description: Admin retrieves a paginated list of all userDomains.
- Params:
  page: Zero-based page index (default 0);
  size: Page size (default 10).
- Result: Array of userDomain info (paginated).

### 6. [ADMIN] Get User From Id

- Endpoint: GET /userDomains/:id
- Auth: Bearer token {{jwt_admin}}
- Description: Admin retrieves a userDomain by specific UUID id.
- Path: Replace :id with userDomain's UUID.
- Result: The userDomain object for matching id.

### 7. [USER] Update User

- Endpoint: PUT /userDomains
- Auth: Bearer token {{jwt_admin}}
- Description: Admin updates an existing userDomain.
- Request Body Example:
  {
  "id": "4fd90a00-27ea-4c05-afd6-ba3e9d5cc33e",
  "name": "John DoeX",
  "email": "john@doe.com.br",
  "username": "johnny",
  "password": "Password1@",
  "roleEnum": "CUSTOMER",
  "address": {
  "street": "Baker Street",
  "number": "221B",
  "complement": "",
  "city": "London",
  "state": "BL"
  }
  }
- Result: Returns the updated userDomain info.

### 8. [USER] Delete User

- Endpoint: DELETE /userDomains/:id
- Auth: Bearer token {{jwt_admin}}
- Description: Admin deletes a userDomain by UUID.
- Body: Often not needed for DELETE (UUID is in path).
- Result: 204 No Content if successful.

---

## 👨🏻‍💼 SECTION 2: AS USER

### 1. [USER] Create User

- Endpoint: POST /userDomains
- Auth: None
- Description: Anyone can register a new CUSTOMER.
- Request Body Example:
  {
  "name": "Jane",
  "email": "jane@uol.com.br",
  "username": "jane",
  "password": "Password1@",
  "roleEnum": "CUSTOMER",
  "address": {
  "street": "Baker Street",
  "number": "221C",
  "complement": "Apt 2",
  "city": "London",
  "state": "BL"
  }
  }
- Result: Returns the created userDomain object.

### 2. [USER] Login

- Endpoint: POST /login
- Auth: None
- Description: Logs in as a regular userDomain, returns a JWT as response.
- Request:
  {
  "username": "jane",
  "password": "Password1@"
  }
- Result: Response with JWT token (stored as jwt_user).

### 3. [USER] Get All Users (ERROR)

- Endpoint: GET /userDomains
- Auth: Bearer token {{jwt_user}}
- Description: Not allowed for regular userDomains. Will return an error (403 Forbidden).

### 4. [USER] Get User From Id

- Endpoint: GET /userDomains/:id
- Auth: Bearer token {{jwt_user}}
- Description: Gets a userDomain's own info (OK only if UUID matches the logged-in userDomain; 403 for others).
- Result: Returns userDomain info.

### 5. [USER] Update User

- Endpoint: PUT /userDomains
- Auth: Bearer token {{jwt_user}}
- Description: User updates their own info.
- Request: Must include id and all relevant userDomain information.
- Result: Returns updated userDomain info.

### 6. [USER] Delete User

- Endpoint: DELETE /userDomains/:id
- Auth: Bearer token {{jwt_user}}
- Description: User deletes themselves.
- Result: 204 No Content if successful.

---

## ⚠️ SECTION 3: ERROR (Generic)

- Any call with ERROR in the name simulates unauthorized/invalid usage.
- Expect 401/403/409, depending on the scenario.

---

## 🔡 DATA FORMAT SUMMARY

- Create/Update User: All userDomain info + nested address.
- Login: Username and password.
- Token: Returned on login, used as Bearer in Authorization header for other requests.

---

## 🔀 TYPICAL FLOW

### Admin:

1. Login as admin (/login)
2. Use jwt_admin to create userDomains, get all userDomains, update, delete.

### User:

1. Register (/userDomains)
2. Login (/login) → get jwt_user
3. Get/update/delete own account using token

---

## 💬 COMMON RESPONSE BODIES

- Success:
  User object: id, name, email, username, roleEnum, address:{...}
- Login success:
  token, username, expiresAt, userId
- Error:
  status, error, message, path

---

## ❗ Tips

- Always use the correct Bearer token (jwt_admin or jwt_user) when calling protected endpoints.
- User endpoints (except registration and login) require JWT.
- Admin can do everything; userDomain can only manage themselves.
- For update or delete, always use correct id in the path and body.

## 💡 TODO

### 📖 Documentation

- Document **JWT structure** (header, claims, signature).

### 🧪 Testing & Quality

- Include a **code coverage badge/instructions**.
- Add **sample integration tests** (especially for authentication/userDomain management).
