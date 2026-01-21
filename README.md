# ATM Management System with GUI (Java)

## 📌 Project Description
The **ATM Management System** is a Java-based desktop application that simulates real-world ATM operations.  
It uses **Java Swing** for GUI, **MySQL + JDBC** for database connectivity, and follows proper **OOP principles**.

---

## 🎯 Features
- Secure login using **Customer Number & PIN**
- Create and delete bank accounts
- Check **Current** and **Saving** balances
- Deposit and withdraw money
- GUI-based application (Java Swing)
- JDBC-based database connectivity
- Runtime transaction tracking using **Collection Framework**
- Robust **Exception Handling**

---

## 🛠 Technologies Used
- **Language:** Java
- **GUI:** Java Swing
- **Database:** MySQL
- **Connectivity:** JDBC (MySQL Connector/J)
- **IDE:** Eclipse / IntelliJ IDEA

---

## 📂 Project Structure
```
ATM_WITH_GUI
│
├── ATM.java               # Application entry point
├── ATM_GUI.java           # GUI handling & events
├── OptionMenu.java        # Business logic & loops
├── Account.java           # Account model (Encapsulation)
├── AccountDAO.java        # Database access (DAO)
├── DBConnection.java      # JDBC connection utility
└── mysql-connector.jar    # MySQL JDBC Driver
```

---

## 🧠 Concepts Implemented

### 🔹 Object-Oriented Programming (OOP)
- **Encapsulation:** Private variables with getters/setters in `Account`
- **Abstraction:** Database logic hidden in `AccountDAO`
- **Inheritance:** `OptionMenu` extends `Account`

### 🔹 Exception Handling
- `SQLException` handled for DB errors
- `ClassNotFoundException` handled for JDBC driver
- Input-related exceptions handled to prevent crashes

### 🔹 Loops
- Loops used to continuously run ATM menu
- GUI follows event-driven architecture (internal event loop)

---

## 🗄 Database Design
**Table: accounts**
| Column | Type |
|------|------|
| customer_no | INT (Primary Key) |
| pin | INT |
| current_balance | DOUBLE | DEFAULT 0 |
| saving_balance | DOUBLE | DEFAULT 0 |

---

## ▶ How to Run the Project
1. Install **Java JDK** and **MySQL**
2. Create database and `accounts` table
3. Add `mysql-connector.jar` to project build path
4. Update DB credentials in `DBConnection.java`
5. Run `ATM.java`

---

## 🎓 Learning Outcomes
- Java Swing GUI development
- JDBC & MySQL integration
- Practical OOP implementation
- Real-world desktop application design

---

This project is for **educational purposes only**.
