package controllers;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.User;

public class UserControllers {
    private Connection connection;

    public UserControllers(Connection connection) {
        this.connection = connection;
    }

    private String wrapQuotesString(String str) {
        return "\"" + str + "\"";
    }

    private User findUserByEmail(String email) {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = this.connection.createStatement();
            String queryString = "SELECT * FROM users WHERE email= " + wrapQuotesString(email);

            resultSet = statement.executeQuery(queryString);

            resultSet.next();
            User user = new User(resultSet.getInt("user_id"), resultSet.getString("full_name"),
                    resultSet.getString("email"), resultSet.getString("gender"), resultSet.getString("mobile_number"),
                    resultSet.getString("password"));

            return user;
        } catch (SQLException exception) {
            System.out.println("Something went wrong : ");
            return null;
        }
    }

    public boolean login(String email, String password) {
        User user = findUserByEmail(email);

        if (user == null) {
            System.out.println("Email not found, try again!");
            return false;
        }
        if (user.getPassword().equals(password)) {
            System.out.println("Welcome " + user.getFullName());
            return true;
        } else {
            System.out.println("Wrong password!!! ");
            return false;
        }
    }

    public boolean createUser(String fullName, String email, String mobileNumber, String gender, String password) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String query = "INSERT INTO users(full_name, email, mobile_number, gender, password) VALUES" +
                    "(" +
                    wrapQuotesString(fullName) + ", " +
                    wrapQuotesString(email) + ", " +
                    wrapQuotesString(mobileNumber) + ", " +
                    wrapQuotesString(gender) + ", " +
                    wrapQuotesString(password) +
                    ")";

            statement.execute(query);
            return true;
        } catch (SQLException exception) {
            System.out.println("failed to insert the data into the table " + exception);
            return false;
        } finally {
            try {
                statement.close();
            } catch (SQLException exception) {
                System.out.println("failed to close the statement " + exception.getMessage());
            }
        }
    }

    public ArrayList<User> getAllUsers() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM users");

            ArrayList<User> users = new ArrayList<User>();

            while (resultSet.next()) {
                User user = new User(resultSet.getInt("user_id"), resultSet.getString("full_name"),
                        resultSet.getString("email"), resultSet.getString("mobile_number"),
                        resultSet.getString("gender"), resultSet.getString("password"));
                users.add(user);
            }
            return users;
        } catch (SQLException exception) {
            System.out.println("failed to get the users data " + exception);
            return null;
        } finally {
            try {
                resultSet.close();
            } catch (SQLException exception) {
                System.out.println("failed to close the result set " + exception.getMessage());
            }
            try {
                statement.close();
            } catch (SQLException exception) {
                System.out.println("failed to close the statement " + exception.getMessage());
            }
        }
    }

    public User getUserById(int userId) {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = this.connection.createStatement();
            String queryString = "SELECT * FROM users WHERE user_id = " + userId;
            resultSet = statement.executeQuery(queryString);
            resultSet.next();
            User user = new User(resultSet.getInt("user_id"), resultSet.getString("full_name"),
                    resultSet.getString("email"), resultSet.getString("mobile_number"), resultSet.getString("gender"),
                    resultSet.getString("password"));

            return user;
        } catch (SQLException exception) {
            System.out.println("failed to get the data");
            return null;
        }
    }

    public boolean deleteUserById(int userId) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String queryString = "DELETE FROM users WHERE user_id = " + userId;

            statement.execute(queryString);
            return true;
        } catch (SQLException exception) {
            System.out.println("failed to delete the data : " + exception.getMessage());
            return false;
        } finally {
            try {
                statement.close();
            } catch (SQLException exception) {
                System.out.println("failed to close the statement " + exception.getMessage());
            }
        }
    }

    public boolean updateFullName(int userId, String newFullName) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String queryString = "UPDATE users SET full_name=" + wrapQuotesString(newFullName) + " WHERE user_id = "
                    + userId;

            statement.execute(queryString);
            return true;
        } catch (SQLException exception) {
            System.out.println("failed to delete the data : " + exception.getMessage());
            return false;
        } finally {
            try {
                statement.close();
            } catch (SQLException exception) {
                System.out.println("failed to close the statement " + exception.getMessage());
            }
        }
    }

    public boolean updateEmail(int userId, String newEmail) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String queryString = "UPDATE users SET email=" + wrapQuotesString(newEmail) + " WHERE user_id = "
                    + userId;

            statement.execute(queryString);
            return true;
        } catch (SQLException exception) {
            System.out.println("failed to delete the data : " + exception.getMessage());
            return false;
        } finally {
            try {
                statement.close();
            } catch (SQLException exception) {
                System.out.println("failed to close the statement " + exception.getMessage());
            }
        }
    }

    public boolean updateMobileNumber(int userId, String newMobileNumber) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String queryString = "UPDATE users SET mobile_number=" + wrapQuotesString(newMobileNumber)
                    + " WHERE user_id = "
                    + userId;

            statement.execute(queryString);
            return true;
        } catch (SQLException exception) {
            System.out.println("failed to delete the data : " + exception.getMessage());
            return false;
        } finally {
            try {
                statement.close();
            } catch (SQLException exception) {
                System.out.println("failed to close the statement " + exception.getMessage());
            }
        }
    }

    public boolean updateGender(int userId, String newGender) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String queryString = "UPDATE users SET gender=" + wrapQuotesString(newGender) + " WHERE user_id = "
                    + userId;

            statement.execute(queryString);
            return true;
        } catch (SQLException exception) {
            System.out.println("failed to delete the data : " + exception.getMessage());
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
