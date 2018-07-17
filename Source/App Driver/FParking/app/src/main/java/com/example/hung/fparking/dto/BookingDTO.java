package com.example.hung.fparking.dto;

public class BookingDTO {

    private int bookingID;

    private int vehicleID;

    private int parkingID;

    private String address;

    private String timeIn;

    private String timeOut;

    private double price;

    private int status;

    private String licenseplate;

    public BookingDTO(int bookingID, int vehicleID, int parkingID, String address, String timeIn, String timeOut, double price, int status, String licenseplate) {
        this.bookingID = bookingID;
        this.vehicleID = vehicleID;
        this.parkingID = parkingID;
        this.address = address;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.price = price;
        this.status = status;
        this.licenseplate = licenseplate;
    }

    public int getBookingID() {
        return bookingID;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public int getParkingID() {
        return parkingID;
    }

    public String getAddress() {
        return address;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public double getPrice() {
        return price;
    }

    public int getStatus() {
        return status;
    }

    public String getLicenseplate() {
        return licenseplate;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public void setParkingID(int parkingID) {
        this.parkingID = parkingID;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setLicenseplate(String licenseplate) {
        this.licenseplate = licenseplate;
    }
}
