package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Driver;
import com.tagroup.fparking.service.domain.Staff;
import com.tagroup.fparking.service.domain.Vehicle;
import com.tagroup.fparking.service.domain.Vehicletype;


public interface DriverService {
	public List<Driver> getAll();
	public Driver getById(Long id);
	public Driver create(Driver driver);
	public Driver update(Driver driver);
	public void delete(Long id);
	public Driver findByPhoneAndPassword(String phone, String password);
	public List<Vehicle> getVehicleByDriver(String phone);
}
