package com.tagroup.fparking.service.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Proxy;



/**
 * The persistent class for the finetariff database table.
 * 
 */
@Entity
@Proxy(lazy = false)
@NamedQuery(name="Finetariff.findAll", query="SELECT f FROM Finetariff f")
public class Finetariff implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private double price;

	private int type;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "vehicletype_id", referencedColumnName = "id")
	private Vehicletype vehicletype;
	
	public Finetariff() {
	}

	public int getId() {
		return this.id;
	}

	public Vehicletype getVehicletype() {
		return vehicletype;
	}

	public void setVehicletype(Vehicletype vehicletype) {
		this.vehicletype = vehicletype;
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

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

}