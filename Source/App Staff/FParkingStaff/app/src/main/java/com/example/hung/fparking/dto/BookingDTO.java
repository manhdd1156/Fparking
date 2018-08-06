package com.example.hung.fparking.dto;

public class BookingDTO {
    private int bookingID;
    private int parkingID;
    private int drivervehicleID;
    private String timein;
    private String timeout;
    private double price;
    private String licensePlate;
    private String typeCar;
    private double amount;
    private double comission;
    private double totalfine;

    private double rating;
    private int status;

    public BookingDTO() {

    }

    public BookingDTO(int bookingID, int parkingID, int drivervehicleID, String timein, String timeout, double price, String licensePlate, String typeCar, double rating, int status) {
        this.bookingID = bookingID;
        this.parkingID = parkingID;
        this.drivervehicleID = drivervehicleID;
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

    public int getStatus() {
        return status;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public void setParkingID(int parkingID) {
        this.parkingID = parkingID;
    }

    public int getDrivervehicleID() {
        return drivervehicleID;
    }

    public void setDrivervehicleID(int drivervehicleID) {
        this.drivervehicleID = drivervehicleID;
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

    public void setStatus(int status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getComission() {
        return comission;
    }

    public void setComission(double comission) {
        this.comission = comission;
    }

    public double getTotalfine() {
        return totalfine;
    }

    public void setTotalfine(double totalfine) {
        this.totalfine = totalfine;
    }

    @Override
    public String toString() {
        return "BookingDTO{" +
                "bookingID=" + bookingID +
                ", parkingID=" + parkingID +
                ", drivervehicleID=" + drivervehicleID +
                ", timein='" + timein + '\'' +
                ", timeout='" + timeout + '\'' +
                ", price=" + price +
                ", licensePlate='" + licensePlate + '\'' +
                ", typeCar='" + typeCar + '\'' +
                ", amount='" + amount + '\'' +
                ", comission='" + comission + '\'' +
                ", totalfine='" + totalfine + '\'' +
                ", rating=" + rating +
                ", status=" + status +
                '}';
    }
}
