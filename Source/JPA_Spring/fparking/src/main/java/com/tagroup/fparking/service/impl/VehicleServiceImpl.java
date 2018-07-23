package com.tagroup.fparking.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.controller.error.APIException;
import com.tagroup.fparking.repository.DriverRepository;
import com.tagroup.fparking.repository.DriverVehicleRepository;
import com.tagroup.fparking.repository.RatingRepository;
import com.tagroup.fparking.repository.VehicleRepository;
import com.tagroup.fparking.service.VehicleService;
import com.tagroup.fparking.service.domain.DriverVehicle;
import com.tagroup.fparking.service.domain.Rating;
import com.tagroup.fparking.service.domain.Vehicle;
@Service
public class VehicleServiceImpl implements VehicleService{
@Autowired
private VehicleRepository vehicleRepository;
@Autowired
private DriverVehicleRepository drivervehicleRepository;
@Autowired
private DriverRepository driverRepository;
@Autowired
RatingRepository ratingRepository;
@Override
	public List<Vehicle> getAll() {
		// TODO Auto-generated method stub
		return vehicleRepository.findAll();
		
	}

	@Override
	public Vehicle getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		
		try {
			return vehicleRepository.getOne(id);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The food was not found");
		}
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
	
	@Override
	public List<Vehicle> getVehicleByDriver(String phone) {
		// TODO Auto-generated method stub
		List<DriverVehicle> dv = drivervehicleRepository.findByDriver(driverRepository.findByPhone(phone));
		List<Vehicle> listVT = new ArrayList<>();
		for (DriverVehicle driverVehicle : dv) {
			listVT.add(driverVehicle.getVehicle());
		}

		return listVT;
	}
	
	@Override
	public double getRatingByVehicle(Vehicle vehicle) {
		// TODO Auto-generated method stub
		double totalrating =0;
		double count = 0;
		List<DriverVehicle> driverVehicle = drivervehicleRepository.findByVehicle(vehicle);
		for (DriverVehicle driverVehicle2 : driverVehicle) {
			List<Rating> r = ratingRepository.findByDriver(driverVehicle2.getDriver());
			for (Rating rating : r) {
				if(rating.getType()==2) {
					count++;
					totalrating+=rating.getPoint();
				}
			}
		}
		return totalrating/count;
	}

	
}
