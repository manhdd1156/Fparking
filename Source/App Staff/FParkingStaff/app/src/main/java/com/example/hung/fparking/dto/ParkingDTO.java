package com.example.hung.fparking.dto;

public class ParkingDTO {
    private int id;

    private String address;

    private int currentspace;

    private double deposits;

    private String image;

    private String latitude;
    private int city_id;
    private String longitude;

    private int status;

    private String timeoc;

    private int totalspace;

    public ParkingDTO() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCurrentspace() {
        return currentspace;
    }

    public void setCurrentspace(int currentspace) {
        this.currentspace = currentspace;
    }

    public double getDeposits() {
        return deposits;
    }

    public void setDeposits(double deposits) {
        this.deposits = deposits;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTimeoc() {
        return timeoc;
    }

    public void setTimeoc(String timeoc) {
        this.timeoc = timeoc;
    }

    public int getTotalspace() {
        return totalspace;
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
                ", city_id=" + city_id +
                ", longitude='" + longitude + '\'' +
                ", status=" + status +
                ", timeoc='" + timeoc + '\'' +
                ", totalspace=" + totalspace +
                '}';
    }
}
