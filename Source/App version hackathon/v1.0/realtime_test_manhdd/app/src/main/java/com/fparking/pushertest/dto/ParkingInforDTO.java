package com.fparking.pushertest.dto;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by DungNA on 10/20/17.
 */

public class ParkingInforDTO implements Serializable {
    private int parkingID;
    private String address;
    private String phoneNumber;
    private String longitude;
    private String tatitude;
    private int space;
    private String status;
    private int flag_del;


    public ParkingInforDTO() {
    }

    public ParkingInforDTO(int parkingID, String address, String phoneNumber, String longitude, String tatitude, int space, String status, int flag_del) {
        this.parkingID = parkingID;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.longitude = longitude;
        this.tatitude = tatitude;
        this.space = space;
        this.status = status;
        this.flag_del = flag_del;
    }

    public int getParkingID() {
        return parkingID;
    }

    public int getFlag_del() {
        return flag_del;
    }

    public int getSpace() {
        return space;
    }

    public String getAddress() {
        return address;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public String getTatitude() {
        return tatitude;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setParkingID(int parkingID) {
        this.parkingID = parkingID;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFlag_del(int flag_del) {
        this.flag_del = flag_del;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public void setTatitude(String tatitude) {
        this.tatitude = tatitude;
    }
    @Override
    public String toString() {
        return "ParkingDTO{" +
                "parkingID=" + parkingID + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber=" + phoneNumber + '\'' +
                ", longitude='" + longitude + '\'' +
                ", tatitude='" + tatitude + '\'' +
                ", space='" + space + '\'' +
                ", status='" + status + '\'' +
                ", flag_del='" + flag_del + '\'' +
                '}';
    }

    public HashMap<String, String> toHashMap() {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("parkingID", "" + parkingID);
        hm.put("address","" + address);
        hm.put("phoneNumber",phoneNumber);
        hm.put("longitude",longitude);
        hm.put("tatitude",tatitude);
        hm.put("space","" + space);
        hm.put("status","" + status);
        hm.put("flag_del","" + flag_del);
        return hm;
    }
}
