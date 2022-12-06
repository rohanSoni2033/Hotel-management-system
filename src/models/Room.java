package models;

public class Room {
    private int roomNumber;
    private String roomCategory;
    private boolean available;
    private double price;
    private String description;

    public Room(int roomNumber, String roomCategory, boolean available, double price, String description) {
        this.roomNumber = roomNumber;
        this.roomCategory = roomCategory;
        this.available = available;
        this.price = price;
        this.description = description;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomCategory() {
        return roomCategory;
    }

    public boolean isAvailable() {
        return available;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
