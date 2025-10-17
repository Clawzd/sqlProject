package com.example.sqlproject;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public Connection connect() {
        Connection connection = null;
        try {
            String url = "jdbc:mysql://localhost:3306/horse_racing";
            String user = "root";
            String password = "";

            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
