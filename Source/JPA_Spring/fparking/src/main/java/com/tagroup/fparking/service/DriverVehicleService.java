package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.dto.DriverFineDTO;
import com.tagroup.fparking.service.domain.DriverVehicle;
import com.tagroup.fparking.service.domain.Vehicle;

public interface DriverVehicleService {
	public List<DriverVehicle> getAll();
<<<<<<< HEAD
	public DriverVehicle getById(Long id)throws Exception;
	public DriverVehicle create(DriverVehicle drivervehicle)throws Exception;
	public DriverVehicle update(DriverVehicle drivervehicle)throws Exception;
	public void delete(Long id)throws Exception;
	public List<DriverFineDTO> findByDriverId(Long driverid) throws Exception;
	public DriverVehicle getInfoDriverVehicle(Long parkingID, String event) throws Exception;
	public List<DriverVehicle> getDriverVehicleByDriver(String phone) throws Exception;
=======

	public DriverVehicle getById(Long id) throws Exception;

	public DriverVehicle create(DriverVehicle drivervehicle) throws Exception;

	public DriverVehicle update(Long id) throws Exception;

	public void delete(Long id) throws Exception;

	public List<DriverFineDTO> findByDriverId(Long driverid) throws Exception;

	public Vehicle getInfoVehicle(Long parkingID, String event) throws Exception;

	public List<DriverVehicle> getDriverVehicleByDriver(Long id) throws Exception;
	
>>>>>>> 535ad99745d408a0b0ad021408946687af6b84e8
}
