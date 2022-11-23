package models;

public class User {
    private int userId;
    private String fullName;
    private String email;
    private String mobileNumber;
    private String gender;
    private String password;

    public User(int userId, String fullName, String email, String mobileNumber, String gender, String password) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.gender = gender;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }

    public String getObjecString() {
        return "{\n" +
                "user id : " + this.userId + ",\n" +
                "full name : " + this.fullName + ",\n" +
                "email : " + this.email + ",\n" +
                "mobile number : " + this.mobileNumber + ",\n" +
                "gender : " + this.gender + ",\n" +
                "}";
    }

}
