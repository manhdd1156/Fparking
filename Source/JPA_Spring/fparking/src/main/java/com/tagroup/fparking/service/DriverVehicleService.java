package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.dto.DriverFineDTO;
import com.tagroup.fparking.service.domain.DriverVehicle;

public interface DriverVehicleService {
	public List<DriverVehicle> getAll();
<<<<<<< HEAD
	public DriverVehicle getById(Long id)throws Exception;
	public DriverVehicle create(DriverVehicle drivervehicle)throws Exception;
	public DriverVehicle update(DriverVehicle drivervehicle)throws Exception;
	public void delete(Long id)throws Exception;
	public List<DriverFineDTO> findByDriverId(Long driverid) throws Exception;
=======

	public DriverVehicle getById(Long id) throws Exception;

	public DriverVehicle create(DriverVehicle drivervehicle) throws Exception;

	public DriverVehicle update(DriverVehicle drivervehicle) throws Exception;

	public void delete(Long id) throws Exception;

	public DriverVehicle getInfoDriverVehicle(Long parkingID, String event) throws Exception;

	public List<DriverVehicle> getDriverVehicleByDriver(String phone) throws Exception;

>>>>>>> 644b42bb1c00b3a1b700fc0fccf1e08ed2e4b947
}
