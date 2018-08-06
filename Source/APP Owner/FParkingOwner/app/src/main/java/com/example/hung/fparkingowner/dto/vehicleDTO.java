package com.example.hung.fparkingowner.dto;

public class vehicleDTO {

    private Long id;

    private String licenseplate;

    private String color;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicenseplate() {
        return licenseplate;
    }

    public void setLicenseplate(String licenseplate) {
        this.licenseplate = licenseplate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "vehicleDTO{" +
                "id=" + id +
                ", licenseplate='" + licenseplate + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}

