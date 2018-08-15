package com.tagroup.fparking.dto;

public class DriverVehicleDTO {

	private Long id;
	private Long driverid;
	private Long vehicleid;
	private String licenseplate;
	private String type;
	private String color;

	
	public DriverVehicleDTO() {
		// TODO Auto-generated constructor stub
	}


	public DriverVehicleDTO(Long driverid, String licenseplate, String type) {
		super();
		this.driverid = driverid;
		this.licenseplate = licenseplate;
		this.type = type;
	}


	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getVehicleid() {
		return vehicleid;
	}


	public void setVehicleid(Long vehicleid) {
		this.vehicleid = vehicleid;
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


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public Long getDriverid() {
		return driverid;
	}


	public void setDriverid(Long driverid) {
		this.driverid = driverid;
	}


	@Override
	public String toString() {
		return "DriverVehicleDTO [driverid=" + driverid + ", licenseplate=" + licenseplate + ", type=" + type
				+ ", color=" + color + "]";
	}



	

}
