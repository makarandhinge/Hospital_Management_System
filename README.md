
---

# Hospital Management System

A simple Java-based Hospital Management System following OOP, JDBC, and custom exception handling.

---

## Features
- Patient, Doctor, and Appointment management
- Menu-driven console interface
- JDBC-based MySQL database interaction
- Custom exception handling

---

## Directory Structure
```
Sample/
├── docker-compose.yml
├── mysql-init/
│   └── init.sql
├── entity/
├── dao/
├── exception/
├── util/
├── main/
├── db.properties
├── pom.xml
├── README.md
└── .gitignore
```

---

## Setting up MySQL (with and without Docker)

You can set up the MySQL database required for this project using either Docker (recommended for simplicity) or by installing MySQL directly on your system.

### Using Docker (Recommended)

1. Ensure Docker and Docker Compose are installed on your machine.
2. From the `Sample/` directory, run:
   ```bash
   docker-compose up -d
   ```
   - This will start a MySQL 8.0 container and initialize the schema using the scripts in `mysql-init/`.
3. To connect to the running MySQL instance:
   ```bash
   docker exec -it hospital-mysql mysql -uhospitaluser -phospitalpass hospitaldb
   ```

### Without Docker (Manual Installation)

1. **Install MySQL Server:**
   - On Ubuntu/Debian:
     ```bash
     sudo apt update
     sudo apt install mysql-server
     ```
   - On macOS (with Homebrew):
     ```bash
     brew install mysql
     ```
   - On Windows: Download and install from [MySQL Downloads](https://dev.mysql.com/downloads/installer/).

2. **Start MySQL Service:**
   - On Linux/macOS:
     ```bash
     sudo service mysql start
     ```
   - On Windows: Start the MySQL service from the Services app or the MySQL installer.

3. **Create Database and User:**
   ```sql
   CREATE DATABASE hospitaldb;
   CREATE USER 'hospitaluser'@'localhost' IDENTIFIED BY 'hospitalpass';
   GRANT ALL PRIVILEGES ON hospitaldb.* TO 'hospitaluser'@'localhost';
   FLUSH PRIVILEGES;
   ```

4. **Initialize Schema:**
   - Run the schema SQL script provided in `mysql-init/init.sql`:
     ```bash
     mysql -uhospitaluser -phospitalpass hospitaldb < mysql-init/init.sql
     ```

---

## Quick Start Instructions

### 1. Start MySQL Database with Docker

From the `Sample/` directory, run:
```bash
docker-compose up -d
```
- This will start a MySQL 8.0 container and initialize the schema.

### 2. Insert Dummy Data (Required for Testing)

Open a MySQL shell in the running container:
```bash
docker exec -it hospital-mysql mysql -uhospitaluser -phospitalpass hospitaldb
```
Paste the following SQL to insert a patient and a doctor:
```sql
INSERT INTO Patient (firstName, lastName, dateOfBirth, gender, contactNumber, address)
VALUES ('John', 'Doe', '1990-01-01', 'Male', '1234567890', '123 Main St');

INSERT INTO Doctor (firstName, lastName, specialization, contactNumber)
VALUES ('Alice', 'Smith', 'General', '0987654321');
```

### 3. Build and Run the Java Application

```bash
mvn clean compile exec:java -Dexec.mainClass=main.MainModule
```
- The menu will appear in your terminal.

---

## Sample Test Prompts

### Schedule an Appointment
```
Enter choice: 4
Enter details:
Patient ID: 1
Doctor ID: 1
Appointment Date (YYYY-MM-DD): 2025-11-24
Description: General Checkup
```

### Get Appointments for Patient
```
Enter choice: 2
Enter Patient ID: 1
```

### Exit
```
Enter choice: 0
```

---

## Troubleshooting
- **Foreign Key Constraint Error:** Make sure the Patient and Doctor IDs exist in the database.
- **Date Error:** Use a valid date (e.g., `2025-11-24`).
- **JDBC Driver Error:** Ensure Maven downloads dependencies, or run:
  ```bash
  mvn dependency:resolve
  ```

---

## Database Schema (for reference)

```sql
CREATE TABLE Patient (
    patientId INT PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    dateOfBirth DATE,
    gender VARCHAR(10),
    contactNumber VARCHAR(20),
    address VARCHAR(255)
);

CREATE TABLE Doctor (
    doctorId INT PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    specialization VARCHAR(100),
    contactNumber VARCHAR(20)
);

CREATE TABLE Appointment (
    appointmentId INT PRIMARY KEY AUTO_INCREMENT,
    patientId INT,
    doctorId INT,
    appointmentDate DATE,
    description VARCHAR(255),
    FOREIGN KEY (patientId) REFERENCES Patient(patientId),
    FOREIGN KEY (doctorId) REFERENCES Doctor(doctorId)
);
```

---

## Authors
- Makarand Milind Hinge

