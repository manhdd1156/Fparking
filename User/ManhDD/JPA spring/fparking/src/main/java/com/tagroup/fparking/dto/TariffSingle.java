package com.tagroup.fparking.dto;

import java.util.List;



import com.tagroup.fparking.service.domain.Parking;
import com.tagroup.fparking.service.domain.Tariff;


/**
 * The persistent class for the tariff database table.
 * 
 */
public class TariffSingle {

	private int id;

	private double price;

	
	public TariffSingle(int id, double price) {
		super();
		this.id = id;
		this.price = price;
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
	

}