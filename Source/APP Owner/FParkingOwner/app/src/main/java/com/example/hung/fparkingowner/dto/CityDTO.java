package com.example.hung.fparkingowner.dto;

public class CityDTO {

    private int cityID;

    private String cityName;

    public CityDTO() {
    }

    public CityDTO(int cityID, String cityName) {
        this.cityID = cityID;
        this.cityName = cityName;
    }

    public int getCityID() {
        return cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
