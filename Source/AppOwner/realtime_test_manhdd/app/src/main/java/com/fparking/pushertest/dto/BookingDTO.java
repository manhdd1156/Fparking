package com.fparking.pushertest.dto;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by DungNA on 10/20/17.
 */

public class BookingDTO implements Serializable {
    private int bookingID;
    private int parkingID;
    private int carID;
    private String status;
    private String checkinTime;
    private String checkoutTime;
    private String licensePlate;
    private String type;
    private double price;


    public BookingDTO() {
    }

    public BookingDTO(int bookingID,int parkingID,int carID, String status, String checkinTime, String checkoutTime, String licensePlate, String type, double price) {
        this.bookingID = bookingID;
        this.parkingID = parkingID;
        this.carID = carID;
        this.status = status;
        this.checkinTime = checkinTime;
        this.checkoutTime = checkoutTime;
        this.licensePlate = licensePlate;
        this.type = type;
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public int getBookingID() {
        return bookingID;
    }

    public int getParkingID() {
        return parkingID;
    }

    public int getCarID() {
        return carID;
    }

    public double getPrice() {
        return price;
    }

    public String getCheckinTime() {
        return checkinTime;
    }

    public String getCheckoutTime() {
        return checkoutTime;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getType() {
        return type;
    }

    public void setParkingID(int parkingID) {
        this.parkingID = parkingID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCheckinTime(String checkinTime) {
        this.checkinTime = checkinTime;
    }

    public void setCheckoutTime(String checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BookingDTO{" +
                "bookingID=" + bookingID + '\'' +
                ", status='" + status + '\'' +
                ", checkinTime=" + checkinTime + '\'' +
                ", checkoutTime='" + checkoutTime + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", type='" + type + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    public HashMap<String, String> toHashMap() {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("bookingID", "" + bookingID);
        hm.put("status","" + status);
        hm.put("checkinTime",checkinTime);
        hm.put("checkoutTime",checkoutTime);
        hm.put("licensePlate",licensePlate);
        hm.put("type","" + type);
        hm.put("price","" + price);
        return hm;
    }
}
