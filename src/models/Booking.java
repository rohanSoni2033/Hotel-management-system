package models;

public class Booking {
    private int bookingId;
    private String bookingTime;
    private boolean payment;
    private String guestName;
    private int totalGuest;
    private int roomId;

    public Booking(int bookingId, String bookingTime, boolean payment, String guestName, int totalGuest, int roomId) {
        this.bookingId = bookingId;
        this.bookingTime = bookingTime;
        this.payment = payment;
        this.guestName = guestName;
        this.totalGuest = totalGuest;
        this.roomId = roomId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public boolean isPayment() {
        return payment;
    }

    public String getGuestName() {
        return guestName;
    }

    public int getTotalGuest() {
        return totalGuest;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getObjecString() {
        return "{\n" +
                "booking id : " + this.bookingId + ",\n" +
                "booking time : " + this.bookingTime + ",\n" +
                "payment : " + this.payment + ",\n" +
                "guest name : " + this.guestName + ",\n" +
                "total guest : " + this.totalGuest + ",\n" +
                "room id : " + this.roomId + ",\n" +
                "}";
    }

}
