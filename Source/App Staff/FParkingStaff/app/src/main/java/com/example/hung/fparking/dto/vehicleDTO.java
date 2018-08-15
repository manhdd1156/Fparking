package com.example.hung.fparking.dto;

public class VehicleDTO {

    private Long id;
    private int driverVehicleID;

    private int status;

    private int vehicleID;
    private String licenseplate;

    private String type;
    private int vehicleTypeID;
    private String color;
    public VehicleDTO() {

    }
    public VehicleDTO(Long id, int driverVehicleID, int status, int vehicleID, String licenseplate, String type, int vehicleTypeID, String color) {
        this.id = id;
        this.driverVehicleID = driverVehicleID;
        this.status = status;
        this.vehicleID = vehicleID;
        this.licenseplate = licenseplate;
        this.type = type;
        this.vehicleTypeID = vehicleTypeID;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public int getDriverVehicleID() {
        return driverVehicleID;
    }

    public void setDriverVehicleID(int driverVehicleID) {
        this.driverVehicleID = driverVehicleID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getVehicleTypeID() {
        return vehicleTypeID;
    }

    public void setVehicleTypeID(int vehicleTypeID) {
        this.vehicleTypeID = vehicleTypeID;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicenseplate() {
        return licenseplate;
    }

    public void setLicenseplate(String licenseplate) {
        this.licenseplate = licenseplate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "VehicleDTO{" +
                "id=" + id +
                ", driverVehicleID=" + driverVehicleID +
                ", status=" + status +
                ", vehicleID=" + vehicleID +
                ", licenseplate='" + licenseplate + '\'' +
                ", type='" + type + '\'' +
                ", vehicleTypeID=" + vehicleTypeID +
                ", color='" + color + '\'' +
                '}';
    }
}

