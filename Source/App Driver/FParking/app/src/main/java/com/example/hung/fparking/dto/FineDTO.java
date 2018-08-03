package com.example.hung.fparking.dto;

public class FineDTO {

    private int fineID;
    private String date;
    private int status;
    private int type;
    private double price;
    private int drivervehicleID;
    private int vehicleID;
    private String licenseplate;
    private String color;
    private int vehicletypeID;
    private String vehicletype;
    private int parkingID;
    private String address;

    public FineDTO() {
    }

    public FineDTO(int fineID, String date, int status, int type, double price, int drivervehicleID, int vehicleID, String licenseplate, String color, int vehicletypeID, String vehicletype, int parkingID, String address) {
        this.fineID = fineID;
        this.date = date;
        this.status = status;
        this.type = type;
        this.price = price;
        this.drivervehicleID = drivervehicleID;
        this.vehicleID = vehicleID;
        this.licenseplate = licenseplate;
        this.color = color;
        this.vehicletypeID = vehicletypeID;
        this.vehicletype = vehicletype;
        this.parkingID = parkingID;
        this.address = address;
    }

    public int getFineID() {
        return fineID;
    }

    public String getDate() {
        return date;
    }

    public int getStatus() {
        return status;
    }

    public int getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public int getDrivervehicleID() {
        return drivervehicleID;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public String getLicenseplate() {
        return licenseplate;
    }

    public String getColor() {
        return color;
    }

    public int getVehicletypeID() {
        return vehicletypeID;
    }

    public String getVehicletype() {
        return vehicletype;
    }

    public int getParkingID() {
        return parkingID;
    }

    public String getAddress() {
        return address;
    }

    public void setFineID(int fineID) {
        this.fineID = fineID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDrivervehicleID(int drivervehicleID) {
        this.drivervehicleID = drivervehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public void setLicenseplate(String licenseplate) {
        this.licenseplate = licenseplate;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setVehicletypeID(int vehicletypeID) {
        this.vehicletypeID = vehicletypeID;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }

    public void setParkingID(int parkingID) {
        this.parkingID = parkingID;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
