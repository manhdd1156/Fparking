package com.example.hung.fparking.dto;

public class ParkingDTO {

    private int parkingID;
    private String address;
    private int currentspace;
    private int totalspace;
    private Double deposits;
    private String image;
    private Double latitude;
    private Double longitude;
    private String timeoc;

    public ParkingDTO(int parkingID, String address, int currentspace, int totalspace, Double deposits, String image, Double latitude, Double longitude, String timeoc) {
        this.parkingID = parkingID;
        this.address = address;
        this.currentspace = currentspace;
        this.totalspace = totalspace;
        this.deposits = deposits;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeoc = timeoc;
    }

    public ParkingDTO() {
    }

    public int getParkingID() {
        return parkingID;
    }

    public String getAddress() {
        return address;
    }

    public int getCurrentspace() {
        return currentspace;
    }

    public int getTotalspace() {
        return totalspace;
    }

    public Double getDeposits() {
        return deposits;
    }

    public String getImage() {
        return image;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getTimeoc() {
        return timeoc;
    }

    public void setParkingID(int parkingID) {
        this.parkingID = parkingID;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCurrentspace(int currentspace) {
        this.currentspace = currentspace;
    }

    public void setTotalspace(int totalspace) {
        this.totalspace = totalspace;
    }

    public void setDeposits(Double deposits) {
        this.deposits = deposits;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setTimeoc(String timeoc) {
        this.timeoc = timeoc;
    }
}
