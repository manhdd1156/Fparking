package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.Parking;
import com.tagroup.fparking.service.domain.Tariff;


public interface BookingService {
	public List<Booking> findByParking(Parking parking);
	public List<Booking> getAll();
	public Booking getById(Long id);
	public Booking create(Booking booking);
	public Booking update(Booking booking);
	public void delete(Long id);
	public List<Tariff> getTariffByBid(Booking booking); 
	public Booking findByStatus(int status);
}
