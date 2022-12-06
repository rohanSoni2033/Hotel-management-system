package controllers;

import java.sql.Connection;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.Room;
import utils.Utils;

@SuppressWarnings("unused")
public class RoomControllers {
    private final Connection connection;

    public RoomControllers(Connection connection) {
        this.connection = connection;
        this.createRoomTable();
    }

    private Room covertResultSetToRoom(ResultSet resultSet) {
        try {
            return new Room(
                    resultSet.getInt("room_number"),
                    resultSet.getString("room_category"),
                    resultSet.getBoolean("available"),
                    resultSet.getDouble("price"),
                    resultSet.getString("description"));
        } catch (SQLException exception) {
            System.out.println("failed to covert the result set into room object " + exception.getMessage());
            return null;
        }
    }

    private boolean createRoomTable() {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String queryString = "CREATE TABLE IF NOT EXISTS rooms(" +
                    "room_number INT PRIMARY KEY," +
                    "room_category VARCHAR(255) NOT NULL," +
                    "available BOOLEAN NOT NULL DEFAULT TRUE," +
                    "price DOUBLE NOT NULL," +
                    "description VARCHAR(255)" +
                    ")";
            statement.execute(queryString);
            return true;
        } catch (SQLException exception) {
            System.out.println("failed to create table" + exception.getMessage());
            return false;
        } finally {
            Utils.closeStatement(statement);
        }
    }

    public boolean createRoom(int roomNumber, String roomCategory, double price, String description) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String query = "INSERT INTO rooms(room_number, room_category, price, description) VALUES" +
                    "(" +
                    roomNumber + ", " +
                    Utils.wrapQuotesString(roomCategory) + ", " +
                    price + ", " +
                    Utils.wrapQuotesString(description) +
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

    public ArrayList<Room> getAllRooms() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM rooms");

            ArrayList<Room> rooms = new ArrayList<>();

            while (resultSet.next()) {
                rooms.add(covertResultSetToRoom(resultSet));
            }
            return rooms;
        } catch (SQLException exception) {
            System.out.println("failed to get the rooms data " + exception);
            return null;
        } finally {
            Utils.closeResultSet(resultSet);
            Utils.closeStatement(statement);
        }
    }

    public ArrayList<Room> getAllAvailableRooms() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM rooms WHERE available = true");
            ArrayList<Room> rooms = new ArrayList<>();

            while (resultSet.next()) {
                rooms.add(covertResultSetToRoom(resultSet));
            }
            return rooms;
        } catch (SQLException exception) {
            System.out.println("failed to fetch the available rooms " + exception.getMessage());
            return null;
        } finally {
            Utils.closeResultSet(resultSet);
            Utils.closeStatement(statement);
        }
    }

    public Room getRoom(int roomNumber) {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = this.connection.createStatement();
            String queryString = "SELECT * FROM rooms WHERE room_number = " + roomNumber;
            resultSet = statement.executeQuery(queryString);
            resultSet.next();

            return covertResultSetToRoom(resultSet);
        } catch (SQLException exception) {
            System.out.println("failed to get the data");
            return null;
        } finally {
            Utils.closeResultSet(resultSet);
            Utils.closeStatement(statement);
        }
    }

    public boolean deleteRoom(int roomNumber) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String queryString = "DELETE FROM rooms WHERE room_number = " + roomNumber;

            statement.execute(queryString);
            return true;
        } catch (SQLException exception) {
            System.out.println("failed to delete the data : " + exception.getMessage());
            return false;
        } finally {
            Utils.closeStatement(statement);
        }
    }

    public boolean updateAvailability(int roomNumber, boolean availability) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String queryString = "UPDATE users SET availability=" + availability + " WHERE room_number = "
                    + roomNumber;

            statement.execute(queryString);
            return true;
        } catch (SQLException exception) {
            System.out.println("failed to delete the data : " + exception.getMessage());
            return false;
        } finally {
            Utils.closeStatement(statement);
        }
    }

}
