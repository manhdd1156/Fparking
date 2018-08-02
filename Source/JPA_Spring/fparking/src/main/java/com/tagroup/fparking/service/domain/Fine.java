package com.tagroup.fparking.service.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the fine database table.
 * 
 */
@Entity
@Proxy(lazy = false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedQuery(name="Fine.findAll", query="SELECT f FROM Fine f")
public class Fine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private Date date;

	private int status;
	
	private int type;
	
	private double price;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "drivervehicle_id", referencedColumnName = "id")
	private DriverVehicle drivervehicle;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parking_id", referencedColumnName = "id")
	private Parking parking;
	
	public Fine() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}


	public double getPrice() {
		return price;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public DriverVehicle getDrivervehicle() {
		return drivervehicle;
	}

	public void setDrivervehicle(DriverVehicle drivervehicle) {
		this.drivervehicle = drivervehicle;
	}

	public Parking getParking() {
		return parking;
	}

	public void setParking(Parking parking) {
		this.parking = parking;
	}

}