package com.tagroup.fparking.service.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.dto.ParkingTariffDTO;
import com.tagroup.fparking.dto.TariffSingle;
import com.tagroup.fparking.repository.ParkingRepository;
import com.tagroup.fparking.repository.RatingRepository;
import com.tagroup.fparking.repository.StaffRepository;
import com.tagroup.fparking.repository.TariffRepository;
import com.tagroup.fparking.service.ParkingService;
import com.tagroup.fparking.service.StaffService;
import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.Parking;
import com.tagroup.fparking.service.domain.Rating;
import com.tagroup.fparking.service.domain.Staff;
import com.tagroup.fparking.service.domain.Tariff;
@Service
public class ParkingServiceImpl implements ParkingService{
@Autowired
private ParkingRepository parkingRepository;
@Autowired
private StaffService staffService;
@Autowired
private RatingRepository ratingRepository;
@Autowired
private TariffRepository tariffRepository;
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
		Parking p = new Parking();
		try {
		p = parkingRepository.save(parking);
		}catch(Exception e) {
			System.out.println("lỗi");
			p = parkingRepository.getOne(parking.getId());
			p.setCurrentspace(parking.getCurrentspace());
			p = parkingRepository.save(p);
		}
		return p;
		
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
	@Override
	public String getRatingByPid(Long parkingId) {
		Parking p = parkingRepository.getOne(parkingId);
		List<Staff> staffs = staffService.findByParking(parkingRepository.getOne(parkingId));
		double totalPoint = 0;
		double totalRating =0;
		for (Staff staff : staffs) {
			List<Rating> ratings = ratingRepository.findByStaff(staff);
			
			for (Rating rating : ratings) {
				if(rating.getType()==1) {
				totalPoint+=rating.getPoint();
				totalRating++;
				}
			}
		}
		// TODO Auto-generated method stub
		return new DecimalFormat("#0.00").format(totalPoint/totalRating);
	}
	
	@Override
	public ParkingTariffDTO getTariffByPid(Parking parking) {
		List<Tariff> tarifflst = tariffRepository.findByParking(parking);
		ParkingTariffDTO ptDTO = new ParkingTariffDTO();
		ptDTO.setParking(parking);
		List<TariffSingle> ts = new ArrayList<>();
		for (Tariff tariff : tarifflst) {
			ts.add(new TariffSingle(tariff.getId(),tariff.getPrice(),tariff.getVehicletype()));
		}
		ptDTO.setTariffList(ts);
		return ptDTO;
	}

}
