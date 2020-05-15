package com.sysoiev;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class App {
//todo: method search by id
    private Scanner scanner;

    public App() throws IOException, SQLException {
        dropTable();
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

    public void run() throws IOException, SQLException {
        boolean go = true;
        while (go) {
            scanner = new Scanner(System.in);
            System.out.println("\nChoose option, please :");
            System.out.println("Enter number : ");
            System.out.println("1. Insert rows ");
            System.out.println("2. Update row ");
            System.out.println("3. Delete row ");
            System.out.println("4. Show all rows ");
            System.out.println("5. End ");
            int number = scanner.nextInt();
            switch (number) {
                case 1:
                    insertPreparedStatement();
                    break;
                case 2:
                    updateData();
                    break;
                case 3:
                    deleteData();
                    break;
                case 4:
                    selectData();
                    break;
                case 5:
                    go = false;
                    break;
                default:
                    System.out.println("Wrong number");
                    System.out.println("Enter number from 1 to 5, please");
            }
        }
    }

    public static void main(String[] args) throws IOException, SQLException {
        App app = new App();
        app.run();
    }

    public Connection getConnection() throws IOException, SQLException {

        Properties properties = new Properties();
        try (InputStream inputStream = App.class.getResourceAsStream("/database.properties")) {
            properties.load(inputStream);
        }
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        return DriverManager.getConnection(url, username, password);
    }

    public void insertData() throws SQLException, IOException {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate("INSERT products(ProductName, Price) VALUES ('iPhone X', 76000)," +
                    "('Galaxy S9', 45000), ('Nokia 9', 36000)");
            System.out.printf("Added %d rows", rows);
        }
    }

    public void dropTable() throws IOException, SQLException {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE products");
        }
    }

    public void updateData() throws IOException, SQLException {
        scanner = new Scanner(System.in);
        System.out.println("enter new product name : ");
        String newName = scanner.nextLine();
        System.out.println("enter new product price : ");
        int price = scanner.nextInt();
        System.out.println("enter product id : ");
        int id = scanner.nextInt();
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE products SET ProductName=?,Price = ?   WHERE Id = " + id);
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, price);
            int rows = preparedStatement.executeUpdate();
            System.out.printf("\nUpdated %d rows", rows);
        }
    }

    public void deleteData() throws IOException, SQLException {
        scanner = new Scanner(System.in);
        System.out.println("input product name : ");
        String name = scanner.nextLine();
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM products WHERE ProductName = ?");
            preparedStatement.setString(1, name);
            int rows = preparedStatement.executeUpdate();
            System.out.printf("\n%d row(s) deleted", rows);
        }
    }

    public void selectData() throws IOException, SQLException {
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

    public void insertPreparedStatement() throws IOException, SQLException {
        scanner = new Scanner(System.in);
        System.out.println("Input product name: ");
        String name = scanner.nextLine();
        System.out.println("Input product price: ");
        int price = scanner.nextInt();
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO products (ProductName, Price) Values (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, price);
            int rows = preparedStatement.executeUpdate();
            System.out.printf("%d rows added", rows);
        }
    }
}