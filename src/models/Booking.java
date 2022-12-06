package models;

import java.util.Date;

@SuppressWarnings("unused")
public class Booking {
    private int id;
    private String guestName;
    private int totalGuest;
    private String guestMobileNumber;
    private String guestEmail;
    private int roomNumber;
    private boolean paymentCompleted;
    private Date bookedAt;
    private int userId;

    public Booking(int id, String guestName, int totalGuest, String guestMobileNumber, String guestEmail,
            int roomNumber, boolean paymentCompleted, Date bookedAt, int userId) {
        this.id = id;
        this.guestName = guestName;
        this.totalGuest = totalGuest;
        this.guestMobileNumber = guestMobileNumber;
        this.guestEmail = guestEmail;
        this.paymentCompleted = paymentCompleted;
        this.roomNumber = roomNumber;
        this.userId = userId;
        this.bookedAt = bookedAt;
    }

    public int getId() {
        return id;
    }

    public String getGuestName() {
        return guestName;
    }

    public int getTotalGuest() {
        return totalGuest;
    }

    public String getGuestMobileNumber() {
        return guestMobileNumber;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public boolean isPaymentCompleted() {
        return paymentCompleted;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getUserId() {
        return userId;
    }

    public Date getBookedAt() {
        return bookedAt;
    }
}
