package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Vehicle;


public interface VehicleService {
	public List<Vehicle> getAll();
	public Vehicle getById(Long id);
	public Vehicle create(Vehicle vehicle);
	public Vehicle update(Vehicle vehicle);
	public void delete(Long id);
}
