package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Booking;


public interface BookingService {
	public List<Booking> getAll();
	public Booking getById(Long id);
	public Booking create(Booking booking);
	public Booking update(Booking booking);
	public void delete(Long id);
}
