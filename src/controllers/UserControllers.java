package controllers;

import java.util.ArrayList;

import utils.Utils;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.User;

public class UserControllers {
    private final Connection connection;
    private final Utils utils = new Utils();
    // when user is successfully logged in their unique user id will be stored
    // in currentUserId
    private int currentUserId;

    public UserControllers(Connection connection) {
        this.connection = connection;
        // if this application is running for the first time, there might be a chance
        // that users table isn't exits in database then it will be created
        this.createUserTable();
    }

    // following 3 function are private because it will be used only in this class
    private boolean createUserTable() {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS users(" +
                    "user_id INT AUTO_INCREMENT PRIMARY KEY," +
                    "full_name VARCHAR(255) NOT NULL," +
                    "email VARCHAR(255) NOT NULL UNIQUE," +
                    "mobile_number VARCHAR(10) NOT NULL UNIQUE," +
                    "gender ENUM(\"male\", \"female\", \"other\") NOT NULL," +
                    "password VARCHAR(24) NOT NULL," +
                    "user_type ENUM(\"staff\", \"admin\") DEFAULT \"staff\"," +
                    "created_at DATETIME DEFAULT NOW()" +
                    ")";
            statement.execute(query);
            return true;
        } catch (SQLException exception) {
            System.out.println("failed to create users table " + exception);
            return false;
        } finally {
            try {
                statement.close();
            } catch (SQLException exception) {
                System.out.println("failed to close statment " + exception.getMessage());
            }
        }
    }

    private User createUserFromResultSet(ResultSet resultSet) {
        try {
            User user = new User(resultSet.getInt("user_id"), resultSet.getString("full_name"),
                    resultSet.getString("email"), resultSet.getString("gender"), resultSet.getString("mobile_number"),
                    resultSet.getString("password"), resultSet.getString("user_type"), resultSet.getDate("created_at"));

            return user;
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
            String queryString = "SELECT * FROM users WHERE email= " + utils.wrapQuotesString(email);
            resultSet = statement.executeQuery(queryString);

            resultSet.next();
            return createUserFromResultSet(resultSet);
        } catch (SQLException exception) {
            System.out.println("Something went wrong");
            return null;
        } finally {
            try {
                statement.close();
            } catch (SQLException exception) {
                System.out.println("Something went wrong");
            }
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
            this.currentUserId = user.getUserId();
            return true;
        } else {
            System.out.println("Wrong password!!! ");
            return false;
        }
    }

    // 🐸 every user can only change their own detail like fullname, email, mobile
    // number, gender, password
    public boolean updateFullName(String newFullName) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String queryString = "UPDATE users SET full_name=" + utils.wrapQuotesString(newFullName)
                    + " WHERE user_id = "
                    + this.currentUserId;
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

    public boolean updateEmail(String newEmail) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String queryString = "UPDATE users SET email=" + utils.wrapQuotesString(newEmail) + " WHERE user_id = "
                    + this.currentUserId;

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

    public boolean updateMobileNumber(String newMobileNumber) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String queryString = "UPDATE users SET mobile_number=" + utils.wrapQuotesString(newMobileNumber)
                    + " WHERE user_id = "
                    + this.currentUserId;

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

    public boolean updateGender(String newGender) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String queryString = "UPDATE users SET gender=" + utils.wrapQuotesString(newGender) + " WHERE user_id = "
                    + this.currentUserId;

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

    public boolean changePassword(String currentPassword, String newPassword) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            String queryString = "SELECT * FROM users WHERE user_id = " + this.currentUserId;
            resultSet = statement.executeQuery(queryString);

            resultSet.next();
            if (resultSet.getString("password").equals(currentPassword)) {
                try {
                    queryString = "UPDATE users SET password=" + utils.wrapQuotesString(newPassword)
                            + " WHERE user_id = "
                            + this.currentUserId;

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
            try {
                statement.close();
            } catch (SQLException exception) {
                System.out.println("Something went wrong");
            }
        }
    }

    // 🔑 only admin acces this deleteUserById function
    public boolean createUser(String fullName, String email, String mobileNumber, String gender, String password) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String query = "INSERT INTO users(full_name, email, mobile_number, gender, password) VALUES" +
                    "(" +
                    utils.wrapQuotesString(fullName) + ", " +
                    utils.wrapQuotesString(email) + ", " +
                    utils.wrapQuotesString(mobileNumber) + ", " +
                    utils.wrapQuotesString(gender) + ", " +
                    utils.wrapQuotesString(password) +
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
                users.add(createUserFromResultSet(resultSet));
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

    public User searchUserById(int userId) {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = this.connection.createStatement();
            String queryString = "SELECT * FROM users WHERE user_id = " + userId;
            resultSet = statement.executeQuery(queryString);
            resultSet.next();
            return createUserFromResultSet(resultSet);
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

    public User myAccountDetails() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            String queryString = "SELECT * FROM users WHERE user_id = " + this.currentUserId;
            resultSet = statement.executeQuery(queryString);

            resultSet.next();
            return createUserFromResultSet(resultSet);
        } catch (SQLException exception) {
            System.out.println("Something went wrong");
            return null;
        } finally {
            try {
                statement.close();
            } catch (SQLException exception) {
                System.out.println("Something went wrong");
            }
        }
    }
}
