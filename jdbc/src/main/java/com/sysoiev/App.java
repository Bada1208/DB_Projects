package com.sysoiev;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class App {

    public static void main(String[] args) throws IOException, SQLException {
        dropTable();
        tableCreation();
        insertData();
        updateData();
       // deleteData();
        System.out.println();
        selectData();
    }

    public static void tableCreation() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            String createTable = "CREATE TABLE IF NOT EXISTS products (Id INT PRIMARY KEY AUTO_INCREMENT, ProductName VARCHAR(20), Price INT)";
            try (Connection connection = getConnection()) {
                Statement statement = connection.createStatement();
                // создание таблицы
                statement.executeUpdate(createTable);
                System.out.println("Table has been created");
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

    public static void insertData() throws SQLException, IOException {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate("INSERT products(ProductName, Price) VALUES ('iPhone X', 76000)," +
                    "('Galaxy S9', 45000), ('Nokia 9', 36000)");
            System.out.printf("Added %d rows", rows);
        }
    }

    public static void dropTable() throws IOException, SQLException {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE products");
        }
    }

    public static void updateData() throws IOException, SQLException {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate("UPDATE products SET Price = Price - 5000");
            System.out.printf("\nUpdated %d rows", rows);
        }
    }

    public static void deleteData() throws IOException, SQLException {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate("DELETE FROM products WHERE Id = 3");
            System.out.printf("\n%d row(s) deleted", rows);
        }
    }

    public static void selectData() throws IOException, SQLException {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM products");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int price = resultSet.getInt(3);
                System.out.printf("%d. %s - %d \n", id, name, price);
            }
        }
    }
}