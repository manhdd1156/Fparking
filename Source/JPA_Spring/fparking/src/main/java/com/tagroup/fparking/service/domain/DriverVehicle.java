package com.tagroup.fparking.service.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the driver_vehicle database table.
 * 
 */
@Entity
@Table(name="driver_vehicle")
@NamedQuery(name="DriverVehicle.findAll", query="SELECT d FROM DriverVehicle d")
public class DriverVehicle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
//
//	//bi-directional many-to-one association to Driver
//	@ManyToOne
//	@JoinColumn(name = "driver_id")
//	private Driver driver1;
//
//	//bi-directional many-to-one association to Vehicle
//	@ManyToOne
//	@JoinColumn(name = "vehicle_id")
//	private Vehicle vehicle;

	public DriverVehicle() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

//	
//
//	public Driver getDriver1() {
//		return driver1;
//	}
//
//	public void setDriver1(Driver driver1) {
//		this.driver1 = driver1;
//	}
//
//	public Vehicle getVehicle() {
//		return this.vehicle;
//	}
//
//	public void setVehicle(Vehicle vehicle) {
//		this.vehicle = vehicle;
//	}

}