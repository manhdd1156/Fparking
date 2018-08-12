package com.example.hung.fparkingowner.dto;

public class StaffDTO {
    private int id;

    private int parking_id;

    private String name;

    private String phone;

    private String address;
    private String parking_address;
    private String pass;

    public StaffDTO() {

    }

    public StaffDTO(int id, int parking_id, String name, String phone, String address, String parking_address, String pass) {
        this.id = id;
        this.parking_id = parking_id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.parking_address = parking_address;
        this.pass = pass;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParking_id() {
        return parking_id;
    }

    public void setParking_id(int parking_id) {
        this.parking_id = parking_id;
    }

    public String getName() {
        return name;
    }

    public String getParking_address() {
        return parking_address;
    }

    public void setParking_address(String parking_address) {
        this.parking_address = parking_address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "StaffDTO{" +
                "id=" + id +
                ", parking_id=" + parking_id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", parking_address='" + parking_address + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
