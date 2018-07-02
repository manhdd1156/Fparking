package com.tagroup.fparking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.repository.ParkingRepository;
import com.tagroup.fparking.service.ParkingService;
import com.tagroup.fparking.service.domain.Parking;
@Service
public class ParkingServiceImpl implements ParkingService{
@Autowired
private ParkingRepository parkingRepository;
	@Override
	public List<Parking> getAll() {
		// TODO Auto-generated method stub
		return parkingRepository.findAll();
		
	}

	@Override
	public Parking getById(Long id) {
		// TODO Auto-generated method stub
		return parkingRepository.getOne(id);
	}

	@Override
	public Parking create(Parking parking) {
		// TODO Auto-generated method stub
		return parkingRepository.save(parking);
	
	}

	@Override
	public Parking update(Parking parking) {
		// TODO Auto-generated method stub
		return parkingRepository.save(parking);
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Parking parking = parkingRepository.getOne(id);
		parkingRepository.delete(parking);
	}
	

}
