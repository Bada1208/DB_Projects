package com.sysoiev;

import java.sql.Connection;
import java.sql.DriverManager;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/store_jdbc?serverTimezone=UTC";
        String username = "root";
        String password = "root";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                System.out.println("Connection successful");
            }
        } catch (Exception e) {
            System.out.println("Connection failed");
            System.out.println(e);

        }
    }
}