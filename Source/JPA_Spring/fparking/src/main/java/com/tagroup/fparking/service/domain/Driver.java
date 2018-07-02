package com.tagroup.fparking.service.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the driver database table.
 * 
 */
@Entity
@NamedQuery(name="Driver.findAll", query="SELECT d FROM Driver d")
public class Driver implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String name;

	private String phone;

	private int status;

//	//bi-directional many-to-one association to DriverVehicle
//	@OneToMany(fetch=FetchType.LAZY,mappedBy="driver1")
//	private List<DriverVehicle> driverVehicles;
//
//	//bi-directional many-to-one association to Fine
//	@OneToMany(fetch=FetchType.LAZY,mappedBy="driver2")
//	private List<Fine> fines;
//
//	//bi-directional many-to-one association to Rating
//	@OneToMany(fetch=FetchType.LAZY,mappedBy="driver3")
//	private List<Rating> ratings;

	public Driver() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

//	public List<DriverVehicle> getDriverVehicles() {
//		return this.driverVehicles;
//	}
//
//	public void setDriverVehicles(List<DriverVehicle> driverVehicles) {
//		this.driverVehicles = driverVehicles;
//	}
//
//
//	public List<Fine> getFines() {
//		return this.fines;
//	}
//
//	public void setFines(List<Fine> fines) {
//		this.fines = fines;
//	}
//	
//
//	public List<Rating> getRatings() {
//		return this.ratings;
//	}
//
//	public void setRatings(List<Rating> ratings) {
//		this.ratings = ratings;
//	}

	
}