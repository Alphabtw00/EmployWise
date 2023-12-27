Project Name: Employwise Spring Boot Project

---
## Description  

Employwise is a Spring Boot-based application integrated with MongoDB, designed to manage employee data efficiently through RESTful APIs, 
enabling operations like adding, retrieving, updating, and deleting employees, along with retrieving hierarchical information like employee managers at various levels.

---
## Project Setup

### Requirements
- Java Development Kit (JDK) 8 or higher
- Maven
- MongoDB

### Installation Steps
1. Clone the repository: `git clone <repository_url>`
2. Navigate to the project directory: `cd employwise-spring-boot`
3. Build the project: `mvn clean install`
4. Run the application: `java -jar target/employwise-0.0.1-SNAPSHOT.jar`

---

## Database Setup

### MongoDB Configuration
-
- Ensure MongoDB is installed and running.
- Create a databse
- Configuration details:
    - Host: localhost
    - Port: 27017
    - Database Name: alpha (replace with your databse name)
    - Auto-create collections: true

---

## API Documentation

### Base URL when running locally
- `http://localhost:8080/employ`

### Routes

#### 1. Add Employee
- Endpoint: `POST /employ`
- Input JSON Structure:
    ```json
    {
        "employeeName": "String",
        "phoneNumber": "String",
        "email": "String",
        "reportsTo": "String",
        "profileImage": "String"
    }
    ```
- Description: Adds a new employee to the database.

#### 2. Get All Employees
- Endpoint: `GET /employ`
- Query Parameters:
    - `pageNumber` (default: 0)
    - `pageSize` (default: 10)
    - `sort` (default: "name")
- Description: Retrieves a paginated list of employees.

#### 3. Delete Employee
- Endpoint: `DELETE /employ/{employeeId}`
- Description: Deletes the employee with the specified ID.

#### 4. Update Employee
- Endpoint: `PUT /employ/{employeeId}`
- Input JSON Structure:
    ```json
    {
        "employeeName": "String",
        "phoneNumber": "String",
        "email": "String",
        "reportsTo": "String",
        "profileImage": "String"
    }
    ```
- Description: Updates the employee information with the specified ID.

#### 5. Get Nth Level Manager
- Endpoint: `GET /employ/{employeeId}/manager/{level}`
- Description: Retrieves the Nth level manager of the specified employee.

---

## Additional Notes

- Replace `{employeeId}` and `{level}` with appropriate values when making requests.

---

