package controllers;

import java.util.ArrayList;

import utils.Utils;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.User;

@SuppressWarnings("unused")
public class UserControllers {
    private final Connection connection;
    static int currentUserId;

    public UserControllers(Connection connection) {
        this.connection = connection;
        this.createUserTable();
    }

    private void createUserTable() {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS users(" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "fullname VARCHAR(255) NOT NULL," +
                    "email VARCHAR(255) NOT NULL UNIQUE," +
                    "mobile_number VARCHAR(10) NOT NULL UNIQUE," +
                    "gender ENUM(\"male\", \"female\", \"other\") NOT NULL," +
                    "password VARCHAR(24) NOT NULL," +
                    "user_type ENUM(\"staff\", \"admin\", \"user\") DEFAULT \"user\"," +
                    "created_at DATETIME DEFAULT NOW()" +
                    ")";
            statement.execute(query);
        } catch (SQLException exception) {
            System.out.println("failed to create users table " + exception);
        } finally {
            Utils.closeStatement(statement);
        }
    }

    private User createUserFromResultSet(ResultSet resultSet) {
        try {
            return new User(resultSet.getInt("id"), resultSet.getString("fullname"),
                    resultSet.getString("email"), resultSet.getString("gender"), resultSet.getString("mobile_number"),
                    resultSet.getString("password"), resultSet.getString("user_type"), resultSet.getDate("created_at"));
        } catch (SQLException exception) {
            System.out.println("something went wrong" + exception);
            return null;
        }

    }

    private User findUserByEmail(String email) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            String queryString = "SELECT * FROM users WHERE email= " + Utils.wrapQuotesString(email);
            resultSet = statement.executeQuery(queryString);

            resultSet.next();
            return createUserFromResultSet(resultSet);
        } catch (SQLException exception) {
            System.out.println("Something went wrong");
            return null;
        } finally {
            Utils.closeResultSet(resultSet);
            Utils.closeStatement(statement);
        }
    }

    public boolean login(String email, String password) {
        User user = findUserByEmail(email);

        if (user == null) {
            System.out.println("Email not found, try again!");
            return false;
        }
        if (user.getPassword().equals(password)) {
            System.out.println("Welcome " + user.getFullname());
            currentUserId = user.getId();
            return true;
        } else {
            System.out.println("Wrong password!!! ");
            return false;
        }
    }

    public boolean updateFullname(String newFullname) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String queryString = "UPDATE users SET fullname=" + Utils.wrapQuotesString(newFullname)
                    + " WHERE id = "
                    + currentUserId;
            statement.execute(queryString);
            return true;
        } catch (SQLException exception) {
            System.out.println("failed to delete the data : " + exception.getMessage());
            return false;
        } finally {
            Utils.closeStatement(statement);
        }
    }

    public boolean updateEmail(String newEmail) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String queryString = "UPDATE users SET email = " + Utils.wrapQuotesString(newEmail) + " WHERE id = "
                    + currentUserId;

            statement.execute(queryString);
            return true;
        } catch (SQLException exception) {
            System.out.println("failed to delete the data : " + exception.getMessage());
            return false;
        } finally {
            Utils.closeStatement(statement);
        }
    }

    public boolean updateMobileNumber(String newMobileNumber) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String queryString = "UPDATE users SET mobile_number = " + Utils.wrapQuotesString(newMobileNumber)
                    + " WHERE id = "
                    + currentUserId;

            statement.execute(queryString);
            return true;
        } catch (SQLException exception) {
            System.out.println("failed to delete the data : " + exception.getMessage());
            return false;
        } finally {
            Utils.closeStatement(statement);
        }
    }

    public boolean updateGender(String newGender) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String queryString = "UPDATE users SET gender=" + Utils.wrapQuotesString(newGender) + " WHERE id = "
                    + currentUserId;

            statement.execute(queryString);
            return true;
        } catch (SQLException exception) {
            System.out.println("failed to delete the data : " + exception.getMessage());
            return false;
        } finally {
            Utils.closeStatement(statement);
        }
    }

    public boolean changePassword(String currentPassword, String newPassword) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            String queryString = "SELECT * FROM users WHERE id = " + currentUserId;
            resultSet = statement.executeQuery(queryString);

            resultSet.next();
            if (resultSet.getString("password").equals(currentPassword)) {
                try {
                    queryString = "UPDATE users SET password=" + Utils.wrapQuotesString(newPassword)
                            + " WHERE id = "
                            + currentUserId;

                    statement.execute(queryString);
                    return true;
                } catch (SQLException exception) {
                    System.out.println("something went wrong");
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException exception) {
            System.out.println("Something went wrong");
            return false;
        } finally {
            Utils.closeResultSet(resultSet);
            Utils.closeStatement(statement);
        }
    }

    // 🔑 only admin access this deleteUserById function
    public boolean createUser(String fullname, String email, String mobileNumber, String gender, String password) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String query = "INSERT INTO users(fullname, email, mobile_number, gender, password) VALUES" +
                    "(" +
                    Utils.wrapQuotesString(fullname) + ", " +
                    Utils.wrapQuotesString(email) + ", " +
                    Utils.wrapQuotesString(mobileNumber) + ", " +
                    Utils.wrapQuotesString(gender) + ", " +
                    Utils.wrapQuotesString(password) +
                    ")";

            statement.execute(query);
            return true;
        } catch (SQLException exception) {
            System.out.println("failed to insert the data into the table " + exception);
            return false;
        } finally {
            Utils.closeStatement(statement);
        }
    }

    public ArrayList<User> getAllUsers() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM users");

            ArrayList<User> users = new ArrayList<>();

            while (resultSet.next()) {
                users.add(createUserFromResultSet(resultSet));
            }
            return users;
        } catch (SQLException exception) {
            System.out.println("failed to get the users data " + exception);
            return null;
        } finally {
            Utils.closeResultSet(resultSet);
            Utils.closeStatement(statement);
        }
    }

    public User searchUserById(int id) {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = this.connection.createStatement();
            String queryString = "SELECT * FROM users WHERE id = " + id;
            resultSet = statement.executeQuery(queryString);
            resultSet.next();
            return createUserFromResultSet(resultSet);
        } catch (SQLException exception) {
            System.out.println("failed to get the data");
            return null;
        } finally {
            Utils.closeResultSet(resultSet);
            Utils.closeStatement(statement);
        }
    }

    public boolean deleteUserById(int id) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String queryString = "DELETE FROM users WHERE id = " + id;
            statement.execute(queryString);
            return true;
        } catch (SQLException exception) {
            System.out.println("failed to delete the data : " + exception.getMessage());
            return false;
        } finally {
            Utils.closeStatement(statement);
        }
    }

    public User myAccountDetails() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            String queryString = "SELECT * FROM users WHERE id = " + currentUserId;
            resultSet = statement.executeQuery(queryString);

            resultSet.next();
            return createUserFromResultSet(resultSet);
        } catch (SQLException exception) {
            System.out.println("Something went wrong");
            return null;
        } finally {
            Utils.closeResultSet(resultSet);
            Utils.closeStatement(statement);
        }
    }
}
