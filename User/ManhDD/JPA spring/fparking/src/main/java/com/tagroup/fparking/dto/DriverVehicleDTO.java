package com.tagroup.fparking.dto;

import com.tagroup.fparking.service.domain.Driver;

public class DriverVehicleDTO {

	public DriverVehicleDTO() {
		// TODO Auto-generated constructor stub
	}


	public DriverVehicleDTO(Driver driver, String licenseplate, String type) {
		super();
		this.driver = driver;
		this.licenseplate = licenseplate;
		this.type = type;
	}


	private Driver driver;
	private String licenseplate;
	private String type;
	
	public Driver getDriver() {
		return driver;
	}
	public void setDriver(Driver driver) {
		this.driver = driver;
	}


	public String getLicenseplate() {
		return licenseplate;
	}


	public void setLicenseplate(String licenseplate) {
		this.licenseplate = licenseplate;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	

}
