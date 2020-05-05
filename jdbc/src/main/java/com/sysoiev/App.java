package com.sysoiev;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class App {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            String createTable = "CREATE TABLE IF NOT EXISTS products (Id INT PRIMARY KEY AUTO_INCREMENT, ProductName VARCHAR(20), Price INT)";
            try (Connection connection = getConnection()) {
                Statement statement = connection.createStatement();
                // создание таблицы
                statement.executeUpdate(createTable);
                System.out.println("Table has been created");
                int rows = statement.executeUpdate("INSERT products(ProductName, Price) VALUES ('iPhone X', 76000)," +
                        "('Galaxy S9', 45000), ('Nokia 9', 36000)");
                System.out.printf("Added %d rows", rows);
            }
        } catch (Exception e) {
            System.out.println("Connection failed...");

            System.out.println(e);
        }
    }

    public static Connection getConnection() throws IOException, SQLException {

        Properties properties = new Properties();
        try (InputStream inputStream = App.class.getResourceAsStream("/database.properties")) {
            properties.load(inputStream);
        }
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        return DriverManager.getConnection(url, username, password);
    }
}