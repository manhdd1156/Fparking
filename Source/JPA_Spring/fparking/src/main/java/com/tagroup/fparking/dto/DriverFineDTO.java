package com.tagroup.fparking.dto;

import java.util.Date;

public class DriverFineDTO {

	private String licenseplate;
	private String nameParking;
	private Date date;
	private double price;
	private String status;

	public DriverFineDTO(String licenseplate, String nameParking, Date date, double price, String status) {
		super();
		this.licenseplate = licenseplate;
		this.nameParking = nameParking;
		this.date = date;
		this.price = price;
		this.status = status;
	}

	public String getLicenseplate() {
		return licenseplate;
	}

	public void setLicenseplate(String licenseplate) {
		this.licenseplate = licenseplate;
	}

	public String getNameParking() {
		return nameParking;
	}

	public void setNameParking(String nameParking) {
		this.nameParking = nameParking;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
