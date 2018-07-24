package com.tagroup.fparking.service.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the booking database table.
 * 
 */
@Entity
@Proxy(lazy = false)
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
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.000 ", timezone="UTC")
	public Date getTimein() {
		return this.timein;
	}

	public void setTimein(Date timein) {
		this.timein = timein;
	}
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.000 ", timezone="UTC")
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