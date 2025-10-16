package com.example.sqlproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/horse_racing";
        String user = "root";
        String password = "";

        try {

            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected successfully to horse_racing");




            conn.close();
            System.out.println("Connection closed successfully.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
