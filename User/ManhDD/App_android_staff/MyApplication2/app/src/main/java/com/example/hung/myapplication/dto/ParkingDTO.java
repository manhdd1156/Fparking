package com.example.hung.myapplication.dto;

public class ParkingDTO {
    private int id;

    private String address;

    private int currentspace;

    private double deposits;

    private String image;

    private String latitude;

    private String longitude;

    private int status;

    private String timeoc;

    private int totalspace;

    public ParkingDTO() {

    }

    public ParkingDTO(int id, String address, int currentspace, double deposits, String image, String latitude, String longitude, int status, String timeoc, int totalspace) {
        this.id = id;
        this.address = address;
        this.currentspace = currentspace;
        this.deposits = deposits;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.timeoc = timeoc;
        this.totalspace = totalspace;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public int getCurrentspace() {
        return currentspace;
    }

    public double getDeposits() {
        return deposits;
    }

    public String getImage() {
        return image;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public int getStatus() {
        return status;
    }

    public String getTimeoc() {
        return timeoc;
    }

    public int getTotalspace() {
        return totalspace;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCurrentspace(int currentspace) {
        this.currentspace = currentspace;
    }

    public void setDeposits(double deposits) {
        this.deposits = deposits;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTimeoc(String timeoc) {
        this.timeoc = timeoc;
    }

    public void setTotalspace(int totalspace) {
        this.totalspace = totalspace;
    }

    @Override
    public String toString() {
        return "ParkingDTO{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", currentspace=" + currentspace +
                ", deposits=" + deposits +
                ", image='" + image + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", status=" + status +
                ", timeoc='" + timeoc + '\'' +
                ", totalspace=" + totalspace +
                '}';
    }
}
