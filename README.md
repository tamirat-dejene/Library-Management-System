# Library Management System

## Introduction

The Library Management System is a Java-based application designed to manage the operations of a library. It includes functionalities such as adding and removing books, managing user accounts, and keeping track of book borrowings and returns.

## Contributors

| Tamirat Dejenie | Tadiyos Dejene | Tebarek Shemsu | Yohannes Tigistu |
| :--- | :--- | :--- | :--- |

## Features

- **Add Book:** Add new books to the library inventory.
- **Remove Book:** Remove books from the library inventory.
- **User Management and Authentication:** Manage user accounts, including registration and authentication.
- **Borrow Book:** Allow users to borrow books from the library.
- **Return Book:** Enable users to return borrowed books.
- **Employee and Admin Registration:** Provide


## Prerequisites
Before you begin, ensure you have the following installed on your system:

- Java Development Kit (JDK) 17 or higher
- Apache Maven 3.6.3 or higher
- MySQL Server

## Dependencies
The project relies on the following dependencies:

- JavaFX Controls (version 21.0.1)
- JavaFX FXML (version 21.0.1)
- MySQL Connector/J (version 8.2.0)

These dependencies are managed via Maven and are specified in the `pom.xml` file.

## Installation
To set up and build the project, follow these steps:

- **Ensure you have Java Development Kit (JDK) 11**:
  - Download from [Oracle's website](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).
  - Alternatively, if using VS Code, install the **Extension Pack For Java** extension.

- **Install Apache Maven**:
  - Download from [Maven's official website](https://maven.apache.org/install.html).

Once you have these tools, Maven will automatically download all the project dependencies specified in the `pom.xml` file and build the project when you run the appropriate Maven commands.

### Clone the Repository
First, clone the repository to your local machine using the following command:
```sh
git clone https://github.com/tamirat-dejene/Library-Management-System.git
```
Navigate to the project directory:
```sh
cd library-management-system
```

Or alternatively if you use vs code just open the cloned repository inside the VS code.

### Configure the Database
Create a new database for the library management system. You can do this using the MySQL command line or MySQL Workbench.
```sql
CREATE DATABASE IF NOT EXISTS Library_Management_System;
USE Library_Management_System;

CREATE TABLE Author (
    Idnumber VARCHAR(30) PRIMARY KEY,
    Fullname VARCHAR(30) NOT NULL,
    Email VARCHAR(30),
    Country VARCHAR(30),
    Biography VARCHAR(300)
);

CREATE TABLE Book (
    Bookid VARCHAR(30) PRIMARY KEY,
    Title VARCHAR(60) NOT NULL,
    AuthorIdnumber VARCHAR(30),
    Publicationyear INT NOT NULL,
    Edition INT,
    Genre VARCHAR(100),
    Lang VARCHAR(50),
    Synopsis VARCHAR(400),
    Price DOUBLE,
    FOREIGN KEY (AuthorIdnumber) REFERENCES Author(Idnumber)
);

CREATE TABLE USER (
    Fullname VARCHAR(50) NOT NULL,
    Idnumber VARCHAR(30) PRIMARY KEY,
    Email VARCHAR(50) NOT NULL,
    Type VARCHAR(50)
);

CREATE TABLE Worker (
    Fullname VARCHAR(100) NOT NULL,
    IdNumber VARCHAR(50) PRIMARY KEY,
    Position VARCHAR(30),
    Email VARCHAR(50)
);    

CREATE TABLE Borrow_transaction (
    borrowId VARCHAR(30) PRIMARY KEY,
    Bookid VARCHAR(30),
    UserID VARCHAR(50),
    Rent_date DATE,
    Due_date DATE,
    FOREIGN KEY (Bookid) REFERENCES Book(Bookid),
    FOREIGN KEY (UserID) REFERENCES USER(Idnumber)
);

CREATE TABLE Systemadmin (
    Idnumber VARCHAR(50) PRIMARY KEY,
    Username VARCHAR(50) NOT NULL,
    Password VARCHAR(50) NOT NULL,
    FOREIGN KEY (Idnumber) REFERENCES Worker(Idnumber)
);
```

After the database is created succesfully, update the database configuration in the com.lms.backend.Library class. Ensure it includes the correct database URL, username, and password.

### Build the Project
Use Maven to build the project. Run the following command in the project directory by opening the external terminal or inside the vscode:
```sh
mvn clean install
```
This will download the required dependencies and compile the project.

### Running the Application
To run the application, use the following Maven command:
```sh
mvn javafx:run
```
This will start the JavaFX application.

## Project Structure
The project follows a modular structure. Below is an overview of the main modules and their purposes:

- **module-info.java**: Contains module declarations and dependencies.
- **src/main/java**: Contains the Java source files withing the following two packages.
  -   com.lms.backend
  -   com.lms.LibraryManagementSystem
- **src/main/resources**: Contains the FXML files and other resources.

## Contributing
If you wish to contribute to the project, please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Commit your changes (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Open a pull request.
