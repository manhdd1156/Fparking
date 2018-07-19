package com.tagroup.fparking.service.domain;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * The persistent class for the booking database table.
 * 
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedQuery(name = "Booking.findAll", query = "SELECT b FROM Booking b")
public class Booking implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	@Column
	private int status;

	private Date timein;

	private Date timeout;
	
	private double price;
	
	private double amount;
	
	private double comission;
	
	private double totalfine;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parking_id", referencedColumnName = "id")
	private Parking parking;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "drivervehicle_id", referencedColumnName = "id")
	private DriverVehicle drivervehicle;
	

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

	public Booking() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getTimein() {
		return this.timein;
	}

	public void setTimein(Date timein) {
		this.timein = timein;
	}

	public Date getTimeout() {
		return this.timeout;
	}

	public void setTimeout(Date timeout) {
		this.timeout = timeout;
	}

	
	public DriverVehicle getDrivervehicle() {
		return drivervehicle;
	}

	public void setDrivervehicle(DriverVehicle drivervehicle) {
		this.drivervehicle = drivervehicle;
	}

	public Parking getParking() {
		return this.parking;
	}

	//
	public void setParking(Parking parking) {
		this.parking = parking;
	}

}