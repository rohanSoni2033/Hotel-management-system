package controllers;

import java.sql.Connection;
import java.util.ArrayList;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.Booking;
import utils.Utils;

public class BookingControllers {
    private Connection connection;
    private Utils utils = new Utils();

    public BookingControllers(Connection connection) {
        this.connection = connection;
    }

    public boolean addBooking(boolean payment, String guestName, int totalGuest, int roomId) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String query = "INSERT INTO bookings(payment , guest_name, total_guest, room_id) VALUES" +
                    "(" +
                    payment + ", " +
                    utils.wrapQuotesString(guestName) + ", " +
                    totalGuest + ", " +
                    roomId +
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

    public ArrayList<Booking> getAllBookings() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM bookings");

            ArrayList<Booking> bookings = new ArrayList<Booking>();

            while (resultSet.next()) {
                Booking room = new Booking(resultSet.getInt("booking_id"), resultSet.getDate("booking_time").toString(),
                        resultSet.getBoolean("payment"), resultSet.getString("guest_name"),
                        resultSet.getInt("total_guest"), resultSet.getInt("room_id"));
                bookings.add(room);
            }
            return bookings;
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

    public Booking getBookingById(int bookingId) {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = this.connection.createStatement();
            String queryString = "SELECT * FROM bookings WHERE booking_id = " + bookingId;
            resultSet = statement.executeQuery(queryString);
            resultSet.next();
            Booking room = new Booking(resultSet.getInt("booking_id"), resultSet.getString("booking_time"),
                    resultSet.getBoolean("payment"), resultSet.getString("guest_name"),
                    resultSet.getInt("total_guest"), resultSet.getInt("room_id"));

            return room;
        } catch (SQLException exception) {
            System.out.println("failed to get the data");
            return null;
        }
    }

    public boolean deleteBookingById(int bookingId) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            String queryString = "DELETE FROM bookings WHERE booking_id = " + bookingId;

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
