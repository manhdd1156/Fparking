package com.tagroup.fparking.dto;

import java.util.List;

import com.tagroup.fparking.service.domain.Parking;


/**
 * The persistent class for the tariff database table.
 * 
 */
public class ParkingTariffDTO {

	private Parking parking;
	private List<TariffSingle> tariffList;
	public Parking getParking() {
		return parking;
	}
	public void setParking(Parking parking) {
		this.parking = parking;
	}
	public List<TariffSingle> getTariffList() {
		return tariffList;
	}
	public void setTariffList(List<TariffSingle> tariffList) {
		this.tariffList = tariffList;
	}
}