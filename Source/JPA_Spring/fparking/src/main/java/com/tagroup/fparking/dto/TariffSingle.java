package com.tagroup.fparking.dto;

import java.util.List;



import com.tagroup.fparking.service.domain.Parking;
import com.tagroup.fparking.service.domain.Tariff;
import com.tagroup.fparking.service.domain.Vehicletype;


/**
 * The persistent class for the tariff database table.
 * 
 */
public class TariffSingle {

	private int id;

	private double price;

	private Vehicletype vehicletype;

	public TariffSingle(int id, double price, Vehicletype vehicletype) {
		super();
		this.id = id;
		this.price = price;
		this.vehicletype = vehicletype;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Vehicletype getVehicletype() {
		return vehicletype;
	}

	public void setVehicletype(Vehicletype vehicletype) {
		this.vehicletype = vehicletype;
	}
	
	

}