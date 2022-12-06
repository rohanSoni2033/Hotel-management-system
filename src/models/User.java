package models;

import java.util.Date;

@SuppressWarnings("unused")
public class User {
    private final int id;
    private final String fullname;
    private final String email;
    private final String mobileNumber;
    private final String gender;
    private final String password;
    private final String userType;
    private final Date createdAt;

    public User(int id, String fullname, String email, String mobileNumber,
            String gender, String password, String userType, Date createdAt) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.gender = gender;
        this.password = password;
        this.userType = userType;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
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

    public String getUserType() {
        return userType;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
