package controllers;

import java.sql.Connection;
import java.util.ArrayList;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.Room;
import utils.Utils;

public class RoomControllers {
    private Connection connection;
    private Utils utils = new Utils();

    public RoomControllers(Connection connection) {
        this.connection = connection;
    }

    public boolean addRoom(int roomNumber, String roomCategory, double price, String description) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String query = "INSERT INTO rooms(room_number, room_category, price, description) VALUES" +
                    "(" +
                    roomNumber + ", " +
                    utils.wrapQuotesString(roomCategory) + ", " +
                    price + ", " +
                    utils.wrapQuotesString(description) +
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

    public ArrayList<Room> getAllRooms() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM rooms");

            ArrayList<Room> rooms = new ArrayList<Room>();

            while (resultSet.next()) {
                Room room = new Room(resultSet.getInt("room_id"), resultSet.getInt("room_number"),
                        resultSet.getString("room_category"), resultSet.getDouble("price"),
                        resultSet.getString("description"), resultSet.getBoolean("availability"));
                rooms.add(room);
            }
            return rooms;
        } catch (SQLException exception) {
            System.out.println("failed to get the rooms data " + exception);
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

    public Room getRoomById(int roomId) {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = this.connection.createStatement();
            String queryString = "SELECT * FROM rooms WHERE room_id = " + roomId;
            resultSet = statement.executeQuery(queryString);
            resultSet.next();
            Room room = new Room(resultSet.getInt("room_id"), resultSet.getInt("room_number"),
                    resultSet.getString("room_category"), resultSet.getDouble("price"),
                    resultSet.getString("description"), resultSet.getBoolean("availability"));

            return room;
        } catch (SQLException exception) {
            System.out.println("failed to get the data");
            return null;
        }
    }

    public boolean deleteRoomById(int roomId) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String queryString = "DELETE FROM rooms WHERE room_id = " + roomId;

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

    public boolean updateAvailability(int roomId, boolean availability) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String queryString = "UPDATE users SET availability=" + availability + " WHERE user_id = "
                    + roomId;

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
