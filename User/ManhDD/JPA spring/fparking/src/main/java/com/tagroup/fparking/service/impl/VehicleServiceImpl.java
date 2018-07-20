package com.tagroup.fparking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.repository.DriverVehicleRepository;
import com.tagroup.fparking.repository.VehicleRepository;
import com.tagroup.fparking.service.VehicleService;
import com.tagroup.fparking.service.domain.DriverVehicle;
import com.tagroup.fparking.service.domain.Vehicle;
@Service
public class VehicleServiceImpl implements VehicleService{
@Autowired
private VehicleRepository vehicleRepository;
@Autowired
private DriverVehicleRepository drivervehicleRepository;
	@Override
	public List<Vehicle> getAll() {
		// TODO Auto-generated method stub
		return vehicleRepository.findAll();
		
	}

	@Override
	public Vehicle getById(Long id) {
		// TODO Auto-generated method stub
		return vehicleRepository.getOne(id);
	}

	@Override
	public Vehicle create(Vehicle vehicle) {
		// TODO Auto-generated method stub
		return vehicleRepository.save(vehicle);
	
	}

	@Override
	public Vehicle update(Vehicle vehicle) {
		// TODO Auto-generated method stub
		return vehicleRepository.save(vehicle);
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Vehicle vehicle = vehicleRepository.getOne(id);
		vehicleRepository.delete(vehicle);
	}

	@Override
	public Vehicle getVehicleByDriverVehicle(Long id) {
		DriverVehicle dv = drivervehicleRepository.getOne(id);
		// TODO Auto-generated method stub
		return dv.getVehicle();
	}
	

}
