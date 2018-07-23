package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.Parking;

public interface BookingService {
	public List<Booking> findByParking(Parking parking) throws Exception;

	public List<Booking> getAll() throws Exception;

	public Booking getById(Long id) throws Exception;

	public Booking create(Booking booking) throws Exception;

	public Booking update(Booking booking) throws Exception;

	public void delete(Long id) throws Exception;

	public List<Booking> findByDriverPhone(String phone) throws Exception;

	public Booking findByStatus(int status) throws Exception;
	
	public Booking updateByStatus(Booking booking) throws Exception;
}
