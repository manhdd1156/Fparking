package com.example.hung.fparking.dto;

public class VehicleDTO {

    private int driverVehicleID;

    private int status;

    private int vehicleID;

    private String licenseplate;

    private int vehicleTypeID;

    private String type;

    public VehicleDTO() {
    }

    public VehicleDTO(int driverVehicleID, int status, int vehicleID, String licenseplate, int vehicleTypeID, String type) {
        this.driverVehicleID = driverVehicleID;
        this.status = status;
        this.vehicleID = vehicleID;
        this.licenseplate = licenseplate;
        this.vehicleTypeID = vehicleTypeID;
        this.type = type;
    }

    public int getDriverVehicleID() {
        return driverVehicleID;
    }

    public int getStatus() {
        return status;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public String getLicenseplate() {
        return licenseplate;
    }

    public int getVehicleTypeID() {
        return vehicleTypeID;
    }

    public String getType() {
        return type;
    }

    public void setDriverVehicleID(int driverVehicleID) {
        this.driverVehicleID = driverVehicleID;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public void setLicenseplate(String licenseplate) {
        this.licenseplate = licenseplate;
    }

    public void setVehicleTypeID(int vehicleTypeID) {
        this.vehicleTypeID = vehicleTypeID;
    }

    public void setType(String type) {
        this.type = type;
    }
}
