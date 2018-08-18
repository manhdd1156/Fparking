package com.example.hung.fparkingowner.dto;

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

    private int city_id;

    private double price9;

    private double price1629;

    private double price3445;

    public ParkingDTO() {

    }

    public ParkingDTO(int id, String address, int currentspace, double deposits, String image, String latitude, String longitude, int status, String timeoc, int totalspace, int city_id, double price9, double price1629, double price3445) {
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
        this.city_id = city_id;
        this.price9 = price9;
        this.price1629 = price1629;
        this.price3445 = price3445;
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

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public double getPrice9() {
        return price9;
    }

    public double getPrice1629() {
        return price1629;
    }

    public double getPrice3445() {
        return price3445;
    }

    public void setPrice9(double price9) {
        this.price9 = price9;
    }

    public void setPrice1629(double price1629) {
        this.price1629 = price1629;
    }

    public void setPrice3445(double price3445) {
        this.price3445 = price3445;
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
                ", city_id=" + city_id +
                ", price9=" + price9 +
                ", price1629=" + price1629 +
                ", price3445=" + price3445 +
                '}';
    }
}
