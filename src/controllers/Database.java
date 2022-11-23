package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private final String database_name = "hotel_management_system";
    private final String connection_url = "jdbc:mysql://localhost:3306/" + database_name;
    private final String user = "root";
    private final String password = "rohan1234";
    private Connection connection;

    public Connection connect() {
        try {
            this.connection = DriverManager.getConnection(connection_url, user, password);
            return this.connection;
        } catch (SQLException exception) {
            System.out.println("failed to connected to the datatase " + exception.getMessage());
            return null;
        }
    }

    public void close() {
        try {
            if (this.connection != null) {
                this.connection.close();
            }
        } catch (SQLException exception) {
            System.out.println("failed to close the connection to the database " + exception.getMessage());
        }
    }

    public boolean createUserTable() {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String query = "CREATE TABLE users(" +
                    "user_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "full_name VARCHAR(255) NOT NULL, " +
                    "email VARCHAR(255) NOT NULL UNIQUE, " +
                    "mobile_number VARCHAR(10) NOT NULL UNIQUE, " +
                    "gender VARCHAR(24) NOT NULL, " +
                    "CHECK (gender = 'male' OR gender = 'female' OR gender = 'other')" +
                    ")";
            statement.execute(query);
            return true;
        } catch (SQLException exception) {
            System.out.println("failed to create table " + exception.getMessage());
            return false;
        } finally {
            try {
                statement.close();
            } catch (SQLException exception) {
                System.out.println("failed to close the statement " + exception.getMessage());
            }
        }
    }
}
