package controllers;

import java.sql.Connection;
import java.util.ArrayList;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.Booking;
import utils.Utils;

@SuppressWarnings("unused")
public class BookingControllers {
    private final Connection connection;

    public BookingControllers(Connection connection) {
        this.connection = connection;
        createBookingTable();
    }

    private void createBookingTable() {
        Statement statement;
        try {
            statement = this.connection.createStatement();
            String queryString = "CREATE TABLE IF NOT EXISTS bookings(" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "guest_name VARCHAR(255) NOT NULL," +
                    "total_guest INT NOT NULL," +
                    "guest_mobile_number VARCHAR(10) NOT NULL," +
                    "guest_email VARCHAR(255) NOT NULL," +
                    "room_number INT NOT NULL," +
                    "payment_completed BOOLEAN NOT NULL," +
                    "booked_at DATETIME DEFAULT NOW()," +
                    "user_id INT NOT NULL," +
                    "FOREIGN KEY (user_id) REFERENCES users(id)," +
                    "FOREIGN KEY (room_number) REFERENCES rooms(room_number)," +
                    "CHECK (total_guest > 0)" +
                    ")";
            statement.execute(queryString);
        } catch (SQLException exception) {
            System.out.println("failed to create table" + exception.getMessage());
        }
    }

    public boolean createBooking(String guestName, int totalGuest, String guestMobileNumber, String guestEmail,
                                 int roomNumber, boolean paymentCompleted) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = this.connection.createStatement();
            // check if room is available
            resultSet = statement.executeQuery("SELECT available FROM rooms WHERE room_number = " + roomNumber);
            if (resultSet != null) {
                resultSet.next();
                // if room is not available throw a new Exception return from the function
                if (!resultSet.getBoolean("available")) {
                    throw new SQLException("Sorry!, room number " + roomNumber + " is not available");
                }
                // if room is available insert booking data into the bookings table and
                // update the availability of that room to false and return false

                statement = this.connection.createStatement();
                String query = "INSERT INTO bookings(guest_name, total_guest, guest_mobile_number, guest_email, room_number, payment_completed, user_id)" +
                        "VALUES(" + Utils.wrapQuotesString(guestName) + ", " +
                        totalGuest + ", " +
                        Utils.wrapQuotesString(guestMobileNumber) + ", " +
                        Utils.wrapQuotesString(guestEmail) + ", " +
                        roomNumber + ", " +
                        paymentCompleted + ", " +
                        UserControllers.currentUserId +
                        ")";

                statement.execute(query);
                statement.execute("UPDATE rooms SET available = false WHERE room_number = " + roomNumber);
                return true;
            }
            System.out.println("room number not found");
            return false;
        } catch (SQLException e) {
            System.out.println("failed to insert the data into bookings" + e.getMessage());
            return false;
        } finally {
            Utils.closeResultSet(resultSet);
            Utils.closeStatement(statement);
        }
    }

    public boolean cancelBooking(int bookingId) {
        Statement statement = null;
        try {
            statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT rood_id FROM bookings WHERE id = " + bookingId);
            resultSet.next();
            int roomNumber = resultSet.getInt("room_number");
            statement.execute("UPDATE rooms SET available = true WHERE room_number = " + roomNumber);

            return true;
        } catch (SQLException e) {
            System.out.println("failed to cancel the bookings " + e.getMessage());

            return false;
        } finally {
            Utils.closeStatement(statement);
        }
    }

    public ArrayList<Booking> getMyBookings() {
        Statement statement;
        ResultSet resultSet;
        try {
            statement = this.connection.createStatement();

            resultSet = statement.executeQuery("SELECT * FROM bookings WHERE user_id = " + UserControllers.currentUserId);
            ArrayList<Booking> bookings = new ArrayList<>();

            while (resultSet.next()) {
                bookings.add(new Booking(
                        resultSet.getInt("id"),
                        resultSet.getString("guest_name"),
                        resultSet.getInt("total_guest"),
                        resultSet.getString("guest_mobile_number"),
                        resultSet.getString("guest_email"),
                        resultSet.getInt("room_number"),
                        resultSet.getBoolean("payment_completed"),
                        resultSet.getDate("booked_at"),
                        resultSet.getInt("user_id")
                ));
            }
            return bookings;
        } catch (SQLException exception) {
            System.out.println("failed to get the bookings");
            return null;
        }
    }
}
