package com.acc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Database details
    private static final String URL =
            "jdbc:mysql://localhost:3306/zensor?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Tushar#mysql";

    // Private constructor (prevents object creation)
    private DBConnection() {}

    // Get database connection
    public static Connection getDBConnection()
            throws ClassNotFoundException, SQLException {

        // Load MySQL JDBC Driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Return connection
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
