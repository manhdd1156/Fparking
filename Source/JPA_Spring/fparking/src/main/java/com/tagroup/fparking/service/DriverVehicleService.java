package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Driver;
import com.tagroup.fparking.service.domain.DriverVehicle;


public interface DriverVehicleService {
	public List<DriverVehicle> getAll();
	public DriverVehicle getById(Long id);
	public DriverVehicle create(DriverVehicle drivervehicle);
	public DriverVehicle update(DriverVehicle drivervehicle);
	public void delete(Long id);
	public List<Driver> getbyDriverId(Driver driver);
}
