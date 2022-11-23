import java.util.ArrayList;
import java.util.Scanner;

import controllers.UserControllers;
import models.Database;
import models.User;

public class App {
    public static void main(String[] args) {
        Database db = new Database();
        db.connect();

        // db.createUserTable();

        boolean exit = false;
        // Scanner scanner = new Scanner(System.in);

        ArrayList<User> users = db.getAllUsers();
        for (User user : users) {
            System.out.println(user.getObjecString());
        }

        db.login("rohan123@gmail.com", "password");
        // while (!exit) {
        // System.out.println("enter the command : ");
        // String command = scanner.next();
        // switch (command) {

        // case "show": {
        // System.out.println("Fetching users.... : ");
        // ArrayList<User> users = db.getAllUsers();
        // System.out.println("total users : " + users.size());
        // for (User user : users) {
        // System.out.println(user.getObjecString());
        // }
        // }
        // break;
        // case "add": {
        // System.out.println("enter fullname : ");
        // String fullName = scanner.next();
        // scanner.nextLine();
        // System.out.println("enter email : ");
        // String email = scanner.next();
        // System.out.println("enter mobile number : ");
        // String mobileNumber = scanner.next();
        // System.out.println("select gender [male , female , other]: ");
        // String gender = scanner.next();
        // db.createUser(fullName, email, mobileNumber, gender);
        // }
        // break;
        // case "delete": {
        // System.out.println("enter user id to delete : ");
        // int userId = scanner.nextInt();

        // if (db.deleteUserByUserId(userId)) {
        // System.out.println("user deleted successfully ");
        // } else {
        // System.out.println("couldn't delete user ");
        // }
        // }
        // break;
        // case "update": {
        // System.out.println("Enter the user id : ");
        // int userId = scanner.nextInt();
        // System.out
        // .println(
        // "which field you want to update select [fullname, email, mobile number,
        // gender] : ");

        // String field = scanner.next();
        // scanner.nextLine();
        // switch (field) {
        // case "fullname": {
        // System.out.println("Enter the new fullname : ");
        // String newFullName = scanner.next();
        // scanner.nextLine();

        // if (db.updateFullName(userId, newFullName)) {
        // System.out.println("fullname changed succussfully : ");
        // } else {
        // System.out.println("failed to change fullname : ");
        // }
        // }
        // break;
        // case "email": {
        // System.out.println("Enter the new email : ");
        // String newEmail = scanner.next();

        // if (db.updateEmail(userId, newEmail)) {
        // System.out.println("email changed succussfully : ");
        // } else {
        // System.out.println("failed to change email : ");
        // }
        // }
        // break;
        // case "mobile_number": {
        // System.out.println("Enter the new mobile number : ");
        // String newMobileNumber = scanner.next();

        // if (db.updateMobileNumber(userId, newMobileNumber)) {
        // System.out.println("mobile number changed succussfully : ");
        // } else {
        // System.out.println("failed to change mobile number : ");
        // }
        // }
        // break;
        // case "gender": {
        // System.out.println("Enter the new gender : ");
        // String newGender = scanner.next();

        // if (db.updateGender(userId, newGender)) {
        // System.out.println("Gender changed succussfully : ");
        // } else {
        // System.out.println("failed to change Gender : ");
        // }
        // }
        // break;
        // default: {
        // System.out.println("invalid field selection : ");
        // }
        // }
        // }
        // case "exit": {
        // exit = true;
        // }
        // default: {
        // System.out.println("invali selection ");
        // }
        // }

        // }

        // db.close();
    }
}
