package com.example.hung.myapplication.dto;

public class BookingDTO {
    private int bookingID;
    private int parkingID;
    private int vehicleID;
    private String timein;
    private String timeout;
    private double price;
    private String licensePlate;
    private String typeCar;

    private double rating;
    private String status;

    public BookingDTO() {

    }

    public BookingDTO(int bookingID, int parkingID, int vehicleID, String timein, String timeout, double price, String licensePlate, String typeCar, double rating, String status) {
        this.bookingID = bookingID;
        this.parkingID = parkingID;
        this.vehicleID = vehicleID;
        this.timein = timein;
        this.timeout = timeout;
        this.price = price;
        this.licensePlate = licensePlate;
        this.typeCar = typeCar;
        this.rating = rating;
        this.status = status;

    }

    public int getBookingID() {
        return bookingID;
    }

    public int getParkingID() {
        return parkingID;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public String getTimein() {
        return timein;
    }

    public String getTimeout() {
        return timeout;
    }

    public double getPrice() {
        return price;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getTypeCar() {
        return typeCar;
    }

    public double getRating() {
        return rating;
    }

    public String getStatus() {
        return status;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public void setParkingID(int parkingID) {
        this.parkingID = parkingID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public void setTimein(String timein) {
        this.timein = timein;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setTypeCar(String typeCar) {
        this.typeCar = typeCar;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BookingDTO{" +
                "bookingID=" + bookingID +
                ", parkingID=" + parkingID +
                ", vehicleID=" + vehicleID +
                ", timein='" + timein + '\'' +
                ", timeout='" + timeout + '\'' +
                ", price=" + price +
                ", licensePlate='" + licensePlate + '\'' +
                ", typeCar='" + typeCar + '\'' +
                ", rating=" + rating +
                ", status='" + status + '\'' +
                '}';
    }
}
