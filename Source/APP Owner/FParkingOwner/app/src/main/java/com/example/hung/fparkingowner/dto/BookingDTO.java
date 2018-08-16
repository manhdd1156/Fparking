package com.example.hung.fparkingowner.dto;

public class BookingDTO {
    private int bookingID;
    private int parkingID;
    private int vehicleID;
    private int driverID;
    private int driverVehicleID;
    private String timein;
    private String timeout;
    private double price;
    private String licensePlate;
    private String typeCar;
    private double amount;
    private double comission;
    private double totalfine;

    private int status;

    public BookingDTO() {

    }

    public BookingDTO(int bookingID, int parkingID, int driverID, int vehicleID, int driverVehicleID, String timein, String timeout, double price, String licensePlate, String typeCar, int status) {
        this.bookingID = bookingID;
        this.parkingID = parkingID;
        this.driverID = driverID;
        this.vehicleID = vehicleID;
        this.timein = timein;
        this.timeout = timeout;
        this.price = price;
        this.licensePlate = licensePlate;
        this.typeCar = typeCar;
        this.status = status;
        this.driverVehicleID = driverVehicleID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public int getParkingID() {
        return parkingID;
    }

    public int getDriverVehicleID() {
        return driverVehicleID;
    }

    public void setDriverVehicleID(int driverVehicleID) {
        this.driverVehicleID = driverVehicleID;
    }

    public String getTimein() {
        return timein;
    }

    public String getTimeout() {
        return timeout;
    }

    public double getPrice() {
        return price;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getTypeCar() {
        return typeCar;
    }


    public int getStatus() {
        return status;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public void setParkingID(int parkingID) {
        this.parkingID = parkingID;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public int getDriverID() {
        return driverID;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public void setTimein(String timein) {
        this.timein = timein;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setTypeCar(String typeCar) {
        this.typeCar = typeCar;
    }


    public void setStatus(int status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getComission() {
        return comission;
    }

    public void setComission(double comission) {
        this.comission = comission;
    }

    public double getTotalfine() {
        return totalfine;
    }

    public void setTotalfine(double totalfine) {
        this.totalfine = totalfine;
    }

    @Override
    public String toString() {
        return "BookingDTO{" +
                "bookingID=" + bookingID +
                ", parkingID=" + parkingID +
                ", vehicleID=" + vehicleID +
                ", driverID=" + driverID +
                ", driverVehicleID=" + driverVehicleID +
                ", timein='" + timein + '\'' +
                ", timeout='" + timeout + '\'' +
                ", price=" + price +
                ", licensePlate='" + licensePlate + '\'' +
                ", typeCar='" + typeCar + '\'' +
                ", amount=" + amount +
                ", comission=" + comission +
                ", totalfine=" + totalfine +
                ", status=" + status +
                '}';
    }
}
