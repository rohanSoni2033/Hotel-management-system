import java.util.ArrayList;
import java.util.Scanner;

import controllers.BookingControllers;
import controllers.Database;
import controllers.RoomControllers;
import controllers.UserControllers;
import models.*;

import java.sql.Connection;

public class App {
    public static void main(String[] args) {

        Database db = new Database();
        Connection connection = db.connect();
        Scanner scanner = new Scanner(System.in);

        UserControllers userControllers = new UserControllers(connection);
        RoomControllers roomControllers = new RoomControllers(connection);
        BookingControllers bookingControllers = new BookingControllers(connection);

        System.out.println("plase login into your account : ");
        System.out.println("Enter email ");
        String LoginEmail = scanner.next();
        System.out.println("Enter password ");
        String LoginPassword = scanner.next();

        if (userControllers.login(LoginEmail, LoginPassword)) {
            System.out.println("welcome to your account ");

            boolean exit = false;
            while (!exit) {
                System.out.println(
                        "1. all_user\n2. delete_user\n3. update_user\n4. add room\n4. all_rooms\n5. delete_room\n6. book\n7. get_booking\n8. all_bookings\n9. delete_booking");
                System.out.println("enter the command : ");
                String command = scanner.next();
                switch (command) {
                    case "show_user": {
                        System.out.println("Fetching users.... : ");
                        ArrayList<User> users = userControllers.getAllUsers();
                        System.out.println("total users : " + users.size());
                        for (User user : users) {
                            System.out.println(user.getObjecString());
                        }
                    }
                        break;
                    case "add_user": {
                        System.out.println("Enter staff's fullname: ");
                        String fullName = scanner.next();
                        scanner.nextLine();
                        System.out.println("Enter staff's email: ");
                        String email = scanner.next();
                        System.out.println("Enter staff's mobile number: ");
                        String mobileNumber = scanner.next();
                        System.out.println("select gender [male , female , other]: ");
                        String gender = scanner.next();
                        System.out.println("Enter 8 digit password: ");
                        String password = scanner.next();
                        if (userControllers.createUser(fullName, email, mobileNumber, gender, password)) {
                            System.out.println("user create successfully ");
                        } else {
                            System.out.println("couldn't create user ");
                        }
                    }
                        break;
                    case "delete_users": {
                        System.out.println("enter user id to delete : ");
                        int userId = scanner.nextInt();
                        if (userControllers.deleteUserById(userId)) {
                            System.out.println("user deleted successfully ");
                        } else {
                            System.out.println("couldn't delete user ");
                        }
                    }
                        break;
                    case "update_user": {
                        System.out.println("Enter the user id : ");
                        int userId = scanner.nextInt();
                        System.out
                                .println(
                                        "which field you want to update select [fullname, email, mobile number,gender] : ");

                        String field = scanner.next();
                        scanner.nextLine();
                        switch (field) {
                            case "fullname": {
                                System.out.println("Enter the new fullname : ");
                                String newFullName = scanner.next();
                                scanner.nextLine();

                                if (userControllers.updateFullName(userId, newFullName)) {
                                    System.out.println("fullname changed succussfully : ");
                                } else {
                                    System.out.println("failed to change fullname : ");
                                }
                            }
                                break;
                            case "email": {
                                System.out.println("Enter the new email : ");
                                String newEmail = scanner.next();

                                if (userControllers.updateEmail(userId, newEmail)) {
                                    System.out.println("email changed succussfully : ");
                                } else {
                                    System.out.println("failed to change email : ");
                                }
                            }
                                break;
                            case "mobile_number": {
                                System.out.println("Enter the new mobile number : ");
                                String newMobileNumber = scanner.next();

                                if (userControllers.updateMobileNumber(userId, newMobileNumber)) {
                                    System.out.println("mobile number changed succussfully : ");
                                } else {
                                    System.out.println("failed to change mobile number : ");
                                }
                            }
                                break;
                            case "gender": {
                                System.out.println("Enter the new gender : ");
                                String newGender = scanner.next();

                                if (userControllers.updateGender(userId, newGender)) {
                                    System.out.println("Gender changed succussfully : ");
                                } else {
                                    System.out.println("failed to change Gender : ");
                                }
                            }
                                break;
                            default: {
                                System.out.println("invalid field selection : ");
                            }
                        }
                    }
                        break;

                    case "add_room": {
                        System.out.println("Enter room number : ");
                        int roomNumber = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Enter room category :  ");
                        String roomCategory = scanner.next();
                        System.out.println("Enter price : ");
                        double price = scanner.nextDouble();
                        System.out.println("Enter description :  ");
                        String description = scanner.next();
                        if (roomControllers.addRoom(roomNumber, roomCategory, price, description)) {
                            System.out.println("room added successfully");
                        } else {
                            System.out.println("couldn't add room");
                        }
                    }
                        break;
                    case "all_rooms": {
                        System.out.println("Fetching rooms.... : ");
                        ArrayList<Room> rooms = roomControllers.getAllRooms();
                        System.out.println("total rooms : " + rooms.size());
                        for (Room room : rooms) {
                            if (room.getAvailability()) {
                                System.out.println(room.getObjecString());
                            }
                        }

                    }
                        break;
                    case "delete_room": {
                        System.out.println("enter room id to delete : ");
                        int roomId = scanner.nextInt();
                        if (roomControllers.deleteRoomById(roomId)) {
                            System.out.println("room deleted successfully ");
                        } else {
                            System.out.println("couldn't delete room ");
                        }
                    }
                        break;
                    case "book": {
                        System.out.println("is payment done : ");
                        Boolean payment = scanner.nextBoolean();
                        System.out.println("Enter guest name : ");
                        String guestName = scanner.next();
                        System.out.println("Enter total number of guest : ");
                        int totalGuest = scanner.nextInt();
                        System.out.println("Enter room number : ");
                        int roomNumber = scanner.nextInt();
                        if (bookingControllers.addBooking(payment, guestName, totalGuest, roomNumber)) {
                            System.out.println("room booked successfully");
                        } else {
                            System.out.println("couldn't book room");
                        }
                    }
                        break;
                    case "all_bookings": {
                        System.out.println("Fetching rooms.... : ");
                        ArrayList<Booking> bookings = bookingControllers.getAllBookings();
                        System.out.println("total bookings : " + bookings.size());
                        for (Booking booking : bookings) {
                            System.out.println(booking.getObjecString());
                        }
                    }
                        break;
                    case "get_booking": {
                        System.out.println("booking id to delete : ");
                        int bookingId = scanner.nextInt();
                        System.out.println(bookingControllers.getBookingById(bookingId).getObjecString());
                    }
                        break;
                    case "delete_booking": {
                        System.out.println("enter booking id to delete : ");
                        int bookingId = scanner.nextInt();
                        if (roomControllers.deleteRoomById(bookingId)) {
                            System.out.println("booking deleted successfully ");
                        } else {
                            System.out.println("couldn't delete booking ");
                        }
                    }
                        break;

                    case "exit": {
                        exit = true;
                    }
                    default: {
                        System.out.println("invali selection ");
                    }
                }

            }
        }

        // userControllers.login("rohansoni123@gmail.com", "password@123");

        // bookingControllers.addBooking(false, "rohan soni", 3, 3);

        // ArrayList<Booking> bookings = bookingControllers.getAllBookings();

        // for (Booking booking : bookings) {
        // System.out.println(booking.getObjecString());
        // }
        // roomControllers.addRoom(1026, "single bed", 1200, "single bed, wifi, single
        // bathroom");

        // ArrayList<Room> rooms = roomControllers.getAllRooms();

        // for (Room room : rooms) {
        // System.out.println(room.getObjecString());
        // }

        // System.out.println(roomControllers.getRoomById(2).getObjecString());

        // users.add(userControllers.getUserById(3));
        // userControllers.updateEmail(2, "muskan4321@gmail.com");

        // userControllers.deleteUserById(2);
        // ArrayList<User> users = userControllers.getAllUsers();

        // for (User user : users) {
        // System.out.println(user.getObjecString());
        // }
        // Scanner scanner = new Scanner(System.in);
        //
        // }
        db.close();
    }
}
