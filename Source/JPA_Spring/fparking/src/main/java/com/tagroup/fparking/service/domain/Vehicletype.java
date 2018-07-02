package com.tagroup.fparking.service.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the vehicletype database table.
 * 
 */
@Entity
@NamedQuery(name="Vehicletype.findAll", query="SELECT v FROM Vehicletype v")
public class Vehicletype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String type;

//	//bi-directional many-to-one association to Tariff
//	@OneToMany(mappedBy="vehicletype")
//	private List<Tariff> tariffs;
//
//	//bi-directional many-to-one association to Vehicle
//	@OneToMany(mappedBy="vehicletype")
//	private List<Vehicle> vehicles;

	public Vehicletype() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

//	
//	public List<Tariff> getTariffs() {
//		return tariffs;
//	}
//
//	public void setTariffs(List<Tariff> tariffs) {
//		this.tariffs = tariffs;
//	}
//
//	public static long getSerialversionuid() {
//		return serialVersionUID;
//	}
//
//	public List<Vehicle> getVehicles() {
//		return this.vehicles;
//	}
//
//	public void setVehicles(List<Vehicle> vehicles) {
//		this.vehicles = vehicles;
//	}
//
//	public Vehicle addVehicle(Vehicle vehicle) {
//		getVehicles().add(vehicle);
//		vehicle.setVehicletype(this);
//
//		return vehicle;
//	}
//
//	public Vehicle removeVehicle(Vehicle vehicle) {
//		getVehicles().remove(vehicle);
//		vehicle.setVehicletype(null);
//
//		return vehicle;
//	}

}