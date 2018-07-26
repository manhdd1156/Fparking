package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.dto.DriverVehicleDTO;
import com.tagroup.fparking.service.domain.DriverVehicle;
import com.tagroup.fparking.service.domain.Vehicle;


public interface VehicleService {
	public List<Vehicle> getAll();
	public Vehicle getById(Long id)throws Exception;
	public DriverVehicle create(DriverVehicleDTO drivervehicle)throws Exception;
	public Vehicle update(Vehicle vehicle)throws Exception;
	public void delete(Long id)throws Exception;
	public Vehicle getVehicleByDriverVehicle(Long id)throws Exception;
	public List<Vehicle> getVehicleByDriver(String phone)throws Exception;
	public double getRatingByVehicle(Vehicle vehicle)throws Exception;
	public Vehicle findByLicenseplate(String licenseplate) throws Exception;
	
}
