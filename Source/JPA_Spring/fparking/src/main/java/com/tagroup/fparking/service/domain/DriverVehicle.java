package com.tagroup.fparking.service.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;


/**
 * The persistent class for the driver_vehicle database table.
 * 
 */
@Entity
@Table(name="driver_vehicle")
@Proxy(lazy = false)
@NamedQuery(name="DriverVehicle.findAll", query="SELECT d FROM DriverVehicle d")
public class DriverVehicle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private int status;
	public DriverVehicle() {
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "driver_id", referencedColumnName = "id")
	private Driver driver;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "vehicle_id", referencedColumnName = "id")
	private Vehicle vehicle;
	public Long getId() {
		return this.id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}


}