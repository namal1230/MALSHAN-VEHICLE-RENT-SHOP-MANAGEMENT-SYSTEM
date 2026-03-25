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

```
Do you want me to do that next?
```
