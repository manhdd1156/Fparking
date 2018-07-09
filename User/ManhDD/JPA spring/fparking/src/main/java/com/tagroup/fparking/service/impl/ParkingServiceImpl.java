package com.tagroup.fparking.service.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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
	@Override
	public List<Parking> findByLatitudeANDLongitude(String latitude, String longitude) {
		// TODO Auto-generated method stub
		
		return parkingRepository.findByLatitudeANDLongitude(latitude, longitude);
	}
	
	   

}
