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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	public DriverVehicle() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


}