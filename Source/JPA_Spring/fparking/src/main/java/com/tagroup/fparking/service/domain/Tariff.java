package com.tagroup.fparking.service.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tariff database table.
 * 
 */
@Entity
@NamedQuery(name="Tariff.findAll", query="SELECT t FROM Tariff t")
public class Tariff implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private double price;
//
//	//bi-directional many-to-one association to Parking
//	@ManyToOne
//	@JoinColumn(name = "parking_id")
//	private Parking parking;
//
//	//bi-directional many-to-one association to Vehicletype
//	@ManyToOne
//	@JoinColumn(name = "vehicletype_id")
//	private Vehicletype vehicletype;

	public Tariff() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
//
//	public Parking getParking() {
//		return this.parking;
//	}
//
//	public void setParking(Parking parking) {
//		this.parking = parking;
//	}
//
//	public Vehicletype getVehicletype() {
//		return this.vehicletype;
//	}
//
//	public void setVehicletype(Vehicletype vehicletype) {
//		this.vehicletype = vehicletype;
//	}

}