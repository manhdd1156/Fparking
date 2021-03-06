package com.example.hung.fparking.dto;

public class DriverDTO {

    private Long id;

    private String name;

    private String phone;

    private String password;

    private String status;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {

        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }

    public DriverDTO() {

    }

    public DriverDTO(Long id, String name, String phone, String password, String status) {

        this.id = id;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.status = status;
    }
}
