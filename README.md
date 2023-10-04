## Features

- **User Management**: Add, update, and delete users in the organizational structure.
- **Hierarchy**: Users are organized hierarchically, allowing for nested teams and departments.
- **Search**: Search for users by name within a specified branch of the organization.
- **Export Data**: Export organizational data to JSON format.

## Endpoints

- **POST /users/add**: Add a new user to the organization.
- **GET /users/findByName**: Find users by name within a specified branch of the organization.
- **GET /users/{userId}**: Get user details by ID.
- **PUT /users/{userId}**: Update user details by ID.
- **DELETE /users/{userId}**: Delete a user by ID.
- **POST /users/dumpStructure**: Dump the organizational structure to a JSON file.

## Usage

1. Start the Spring Boot application.
2. Use the provided endpoints to manage and query the organizational structure.
3. Swagger documentation is available for easy testing and understanding of API endpoints.

## Getting Started

1. Clone this repository.
2. Build the project using Maven.
3. Configure your database settings in `application.properties`.
4. Run the application.

## Dependencies

- Java Spring Boot
- Spring Data JPA for database interactions
- Springdoc for Swagger documentation
- PostgreSQL for data storage

## Author

Oleksandr Kalabin
