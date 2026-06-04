TASK MANAGER API

Semplice REST API per la gestione delle task con Spring Boot. Il progetto è stato sviluppato con un esercizio di backend per esercitarmi sui principi di Java OOP, Rest Design,
JPA/Hibernate.

TECNLOGIE UTILIZZATE
  - Java 17
  - Spring Boot 4
  - Spring Web
  - Spring Data JPA (Hibernate)
  - MySql
  - Maven
  - Jakarta Validation


CARATTERISTICHE

  - Operazioni CRUD
  - DTO pattern (Create/Update/Response)
  - Input Validation con Jakarta
  - Global Exception Handling
  - Pagination support
  - Separazione tra Controller, Service e Repository

SCHEMA DATABASE

CREATE DATABASE TaskManager;
Use TaskManager;

CREATE TABLE Task(
    taskID INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    status ENUM('TODO','IN_PROGRESS','DONE','CANCELLED') NOT NULL,
    priority ENUM('LOW','MEDIUM','HIGH','URGENT')NOT NULL DEFAULT 'MEDIUM',
    duedate DATE,
    createdDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL
);



API ENDPOINTS

   - GET: "/api/task"  Get all tasks
   - GET: "/api/task/search/{title}"  Get all tasks with a certain title
   - POST: "api/task"   Create a new task
   - PUT: "/api/task/{id}"  Update an existing task
   - DELETE "/api/task/delete/{id}"   Delete a task



AUTORE 
Sviluppato da Nicola Cirelli
