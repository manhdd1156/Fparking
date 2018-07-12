package com.example.hung.fparking.entity;

public class GetNearPlace {

    int id ;
    double lattitude;
    double longitude;
    String addressParking;

    public GetNearPlace(int id, double lattitude, double longitude, String addressParking) {
        this.id = id;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.addressParking = addressParking;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddressParking() {
        return addressParking;
    }

    public void setAddressParking(String addressParking) {
        this.addressParking = addressParking;
    }



}
