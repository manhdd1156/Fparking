package com.tagroup.fparking.service.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Proxy;


/**
 * The persistent class for the tariff database table.
 * 
 */
@Entity
@Proxy(lazy = false)
@NamedQuery(name="Tariff.findAll", query="SELECT t FROM Tariff t")
public class Tariff implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private double price;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parking_id", referencedColumnName = "id")
	private Parking parking;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "vehicletype_id", referencedColumnName = "id")
	private Vehicletype vehicletype;
	
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

	public Parking getParking() {
		return parking;
	}

	public void setParking(Parking parking) {
		this.parking = parking;
	}

	public Vehicletype getVehicletype() {
		return vehicletype;
	}

	public void setVehicletype(Vehicletype vehicletype) {
		this.vehicletype = vehicletype;
	}
	

}