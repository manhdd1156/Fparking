package com.tagroup.fparking.dto;

import java.util.Date;

/**
 * The persistent class for the booking database table.
 * 
 */
/**
 * @author duymanhr
 *
 */

public class BookingDTO {

	private Long id;
	
	private int status;
	
	private Date timein;

	private Date timeout;
	
	private double price;
	
	private double amount;
	
	private double comission;
	
	private double totalfine;

	private Long parkingid;
	
	private Long drivervehicleid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getTimein() {
		return timein;
	}

	public void setTimein(Date timein) {
		this.timein = timein;
	}

	public Date getTimeout() {
		return timeout;
	}

	public void setTimeout(Date timeout) {
		this.timeout = timeout;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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

	public Long getParkingid() {
		return parkingid;
	}

	public void setParkingid(Long parkingid) {
		this.parkingid = parkingid;
	}

	public Long getDrivervehicleid() {
		return drivervehicleid;
	}

	public void setDrivervehicleid(Long drivervehicleid) {
		this.drivervehicleid = drivervehicleid;
	}

	@Override
	public String toString() {
		return "BookingDTO [id=" + id + ", status=" + status + ", timein=" + timein + ", timeout=" + timeout
				+ ", price=" + price + ", amount=" + amount + ", comission=" + comission + ", totalfine=" + totalfine
				+ ", parkingid=" + parkingid + ", drivervehicleid=" + drivervehicleid + "]";
	}
	

}