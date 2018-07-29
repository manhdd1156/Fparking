package com.example.hung.fparking.dto;

public class TypeDTO {

    private int vehicleTypeID;

    private String type;

    public TypeDTO() {
    }

    public TypeDTO(int vehicleTypeID, String type) {
        this.vehicleTypeID = vehicleTypeID;
        this.type = type;
    }

    public int getVehicleTypeID() {
        return vehicleTypeID;
    }

    public String getType() {
        return type;
    }

    public void setVehicleTypeID(int vehicleTypeID) {
        this.vehicleTypeID = vehicleTypeID;
    }

    public void setType(String type) {
        this.type = type;
    }
}
