# MALSHAN VEHICLE RENT SHOP MANAGEMENT SYSTEM

![Java](https://img.shields.io/badge/Java-17-blue)
![Maven](https://img.shields.io/badge/Maven-3.8.0-orange)
![License](https://img.shields.io/badge/License-Open%20Source-green)

---

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Setup Instructions](#setup-instructions)
- [Database](#database)
- [Reports & Validations](#reports--validations)
- [Notes & Recommendations](#notes--recommendations)
- [License](#license)

---

## Overview
**Malshan Vehicle Rent Shop Management System** is a **Java-based MVC application** designed to manage vehicle rentals efficiently.  
It supports:

- Customers, Drivers, Vehicles, Employees  
- Payments, Maintenance, Insurance  
- Report generation  
- Regex-based input validations  

This project is **portfolio-ready** and simulates a **real-world rental shop workflow**.
<img width="1366" height="768" alt="Screenshot (5)" src="https://github.com/user-attachments/assets/5d17d4a1-f8ff-4ba3-a682-27ef2f94924f" />
## Login Page

Users can log in with **User ID** and **Password**.  
- Includes a **“Show Password” toggle** to view or hide the password.  
- Validates input and displays an **error message** for incorrect credentials.  
- Successful login opens the **Main Menu** to access all modules: Customers, Vehicles, Drivers, Employees, Payments, Maintenance, Insurance, and Reports.

<img width="1366" height="768" alt="Screenshot (6)" src="https://github.com/user-attachments/assets/1008bf9c-d1c6-448a-9f61-c3373efe06d6" />

## User Dashboard

After login, users access the **User Dashboard**, which provides quick access to the system’s main modules with **three main buttons**:

1. **Customer & Rental Management**  
   - Manage Customers, Vehicle Rentals, and Customer Payments.  
   - Add, update, view, or delete customer and rental information.  

2. **Vehicle & Driver Management**  
   - Manage Vehicles and Drivers.  
   - Add new vehicles, assign drivers, track availability, and view details.  

3. **Employee & Reports Management**  
   - Manage Employees, Employee Salaries, Maintenance, Insurance, and generate reports.  
   - Access salary details, maintenance history, insurance info, and create reports.

  <img width="1366" height="768" alt="Screenshot (10)" src="https://github.com/user-attachments/assets/e7451723-0f06-4321-8862-db3675998052" />

<img width="1366" height="768" alt="Screenshot (12)" src="https://github.com/user-attachments/assets/d9ef2c29-1131-4b99-b3b7-976c0bd24f9d" />
<img width="1366" height="768" alt="Screenshot (13)" src="https://github.com/user-attachments/assets/b456e1bf-87d1-4174-be21-a69bffdcfb6e" />


## Report View
- Select report type from menu.  
- View summary of data in a **table format**.  
- Option to **export reports** (CSV, PDF) for professional use.  
- Works with **all modules**: Customers, Vehicles, Drivers, Employees, Maintenance, Insurance.
<img width="1366" height="768" alt="Screenshot (14)" src="https://github.com/user-attachments/assets/58a23ffd-e23d-487f-8e97-0b4ade828c6f" />
<img width="1366" height="768" alt="Screenshot (15)" src="https://github.com/user-attachments/assets/9f3e4dd3-7233-4176-b3f0-706fa193ab68" />



---

## Features
- **User Management**: Login and user credentials  
- **Vehicle Management**: CRUD operations for vehicles  
- **Driver Management**: Manage drivers, licenses, medical, status  
- **Customer Management**: Register and track customers  
- **Customer Payments**: Record rental payments  
- **Employee Management**: CRUD for employees and salaries  
- **Maintenance & Insurance**: Track maintenance history and insurance policies  
- **Reports**: Generate rental, payment, and salary reports  
- **Input Validation**: Regex validation for NIC, email, contact, license numbers

---

## Technologies Used
- Java (Core Java)  
- MySQL (Database)  
- Maven (Build & Dependency Management)  
- MVC Architecture (Model-View-Controller)  
- Singleton Pattern (DB connection & controllers)  
- Regex Validation for user input

---

## Project Structure
```text
MALSHAN-VEHICLE-RENT-SHOP-MANAGEMENT-SYSTEM/
│
├── src/
│   └── main/
│       ├── java/
│       │   ├── controller/   # Business logic
│       │   ├── dao/          # Database access classes
│       │   ├── dto/          # Data Transfer Objects
│       │   └── view/         # Console/UI interaction
│       └── resources/        # DB config / properties
│
├── sql/
│   ├── schema.sql            # Database table creation
│   └── sample_data.sql       # Sample data inserts
│
├── reports/                  # Generated reports (CSV or PDF)
├── pom.xml
├── README.md
└── .gitignore
````

---

## Setup Instructions

1. **Clone the repository**

```bash
git clone https://github.com/namal1230/MALSHAN-VEHICLE-RENT-SHOP-MANAGEMENT-SYSTEM.git
```

2. **Setup MySQL Database**

* Create a database: `carrentalshop`
* Run `/sql/schema.sql` to create tables
* Run `/sql/sample_data.sql` to insert sample data

3. **Configure Database Connection**

* Edit `DBConnection.java` with your MySQL credentials

4. **Build and Run**

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.malshan.Main" # Replace with your main class
```

---

## Database

* Tables include: `users`, `customers`, `vehicles`, `drivers`, `employees`, `employee_salaries`, `customer_payments`, `maintenance`, `insurance`
* **Foreign keys** ensure data consistency
* Sample data included to test the system

---

## Reports & Validations

* Generate reports for **rentals, payments, and employee salaries**
* **Regex validations** for input:

  * NIC: `[0-9]{9}[Vv]`
  * Email: `^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$`
  * Contact: `0[0-9]{9}`
  * License: `[A-Z0-9]{6,15}`

---

## Notes & Recommendations

* **Passwords:** Hash passwords instead of storing plain text
* **Dates:** Use proper `DATE` types for rental and hire dates
* **UI:** Consider upgrading from console to **JavaFX**
* **Exception Handling:** Improve DB error handling
* **Reports:** Can extend to **PDF/Excel exports** for professional use

---

## License

This project is **open for learning and portfolio purposes**.
Feel free to fork, modify, and use it for educational or personal projects.

