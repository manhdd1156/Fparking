package com.tagroup.fparking.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.Parking;
import com.tagroup.fparking.service.domain.Rating;
import com.tagroup.fparking.service.domain.Tariff;


public interface ParkingService {
	public List<Parking> getAll();
	public Parking getById(Long id);
	public Parking create(Parking parking);
	public Parking update(Parking parking);
	public void delete(Long id);
	public List<Parking> findByLatitudeANDLongitude(String latitude, String longitude);
    public String getRatingByPid(Long parkingId); 
    public List<Tariff> getTariffByBid(Parking parking); 
}
