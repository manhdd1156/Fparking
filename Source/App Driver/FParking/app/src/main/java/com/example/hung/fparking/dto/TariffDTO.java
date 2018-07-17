package com.example.hung.fparking.dto;

public class TariffDTO {

    private int tariffID;
    private int parkingID;
    private int vehicleTypeID;
    private double price;

    public TariffDTO() {
    }

    public TariffDTO(int tariffID, int parkingID, int vehicleTypeID, double price) {
        this.tariffID = tariffID;
        this.parkingID = parkingID;
        this.vehicleTypeID = vehicleTypeID;
        this.price = price;
    }

    public int getTariffID() {
        return tariffID;
    }

    public int getParkingID() {
        return parkingID;
    }

    public int getVehicleTypeID() {
        return vehicleTypeID;
    }

    public double getPrice() {
        return price;
    }

    public void setTariffID(int tariffID) {
        this.tariffID = tariffID;
    }

    public void setParkingID(int parkingID) {
        this.parkingID = parkingID;
    }

    public void setVehicleTypeID(int vehicleTypeID) {
        this.vehicleTypeID = vehicleTypeID;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
