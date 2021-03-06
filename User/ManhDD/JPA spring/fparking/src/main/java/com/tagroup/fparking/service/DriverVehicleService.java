package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.dto.DriverFineDTO;
import com.tagroup.fparking.service.domain.DriverVehicle;
import com.tagroup.fparking.service.domain.Vehicle;

public interface DriverVehicleService {
	public List<DriverVehicle> getAll();

	public DriverVehicle getById(Long id) throws Exception;

	public DriverVehicle create(DriverVehicle drivervehicle) throws Exception;

	public DriverVehicle update(Long id) throws Exception;

	public void delete(Long id) throws Exception;

	public List<DriverFineDTO> findByDriverId(Long driverid) throws Exception;

	public Vehicle getInfoVehicle(Long parkingID, String event) throws Exception;

	public List<DriverVehicle> getDriverVehicleByDriver(Long id) throws Exception;
	
}
