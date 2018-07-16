package com.tagroup.fparking.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.repository.DriverVehicleRepository;
import com.tagroup.fparking.repository.RatingRepository;
import com.tagroup.fparking.service.RatingService;
import com.tagroup.fparking.service.domain.DriverVehicle;
import com.tagroup.fparking.service.domain.Rating;
import com.tagroup.fparking.service.domain.Vehicle;
@Service
public class RatingServiceImpl implements RatingService{
@Autowired
private RatingRepository raingRepository;
	
	@Autowired
	RatingRepository ratingRepository;
	
	@Autowired
	DriverVehicleRepository drivervehicleRepository;
	
	@Override
	public List<Rating> getAll() {
		return raingRepository.findAll();
		
	}

	@Override
	public Rating getById(Long id) {
		// TODO Auto-generated method stub
		return raingRepository.getOne(id);
	}

	@Override
	public Rating create(Rating rating) {
		// TODO Auto-generated method stub
		return raingRepository.save(rating);
	
	}

	@Override
	public Rating update(Rating rating) {
		// TODO Auto-generated method stub
		return raingRepository.save(rating);
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Rating rating = raingRepository.getOne(id);
		raingRepository.delete(rating);
	}

	@Override
	public double getByVehicle(Vehicle vehicle) {
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
