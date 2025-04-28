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
