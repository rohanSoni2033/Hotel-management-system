package models;

public class Room {
    private int roomId;
    private int roomNumber;
    private String roomCategory;
    private double price;
    private String description;
    private boolean availability;

    public Room(int roomId, int roomNumber, String roomCategory, double price, String description,
            boolean availability) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.roomCategory = roomCategory;
        this.price = price;
        this.description = description;
        this.availability = availability;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomCategory() {
        return roomCategory;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public boolean getAvailability() {
        return availability;
    }

    public String getObjecString() {
        return "{\n" +
                "\troom id : " + this.roomId + ",\n" +
                "\troom number : " + this.roomNumber + ",\n" +
                "\troom category : " + this.roomCategory + ",\n" +
                "\tavailability : " + this.availability + ",\n" +
                "\tprice : " + this.price + ",\n" +
                "\tdescription : " + this.description + ",\n" +
                "}";
    }

}
