package Entity;

import java.io.Serializable;

public class DetailInformationParking implements Serializable {

    String address;
    String phoneNumber;
    double price;
    String timeoc;
    int space;
    int parking;
    String urlImage;
    double latitude;
    double longitude;

    public DetailInformationParking(String address, String phoneNumber, double price, String timeoc, int space, int parking, String urlImage, double latitude, double longitude) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.price = price;
        this.timeoc = timeoc;
        this.space = space;
        this.parking = parking;
        this.urlImage = urlImage;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTimeoc() {
        return timeoc;
    }

    public void setTimeoc(String timeoc) {
        this.timeoc = timeoc;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public int getParking() {
        return parking;
    }

    public void setParking(int parking) {
        this.parking = parking;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


}
