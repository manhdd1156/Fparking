package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Parking;


public interface ParkingService {
	public List<Parking> getAll();
	public Parking getById(Long id);
	public Parking create(Parking parking);
	public Parking update(Parking parking);
	public void delete(Long id);
}
