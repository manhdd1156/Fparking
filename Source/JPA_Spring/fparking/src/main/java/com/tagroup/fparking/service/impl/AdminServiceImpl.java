package com.tagroup.fparking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.repository.AdminRepository;
import com.tagroup.fparking.service.AdminService;
import com.tagroup.fparking.service.domain.Admin;
@Service
public class AdminServiceImpl implements AdminService{
@Autowired
private AdminRepository adminRepository;

@Override
public List<Admin> getAll() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Admin getById(Long id) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Admin create(Admin tariff) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Admin update(Admin tariff) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void delete(Long id) {
	// TODO Auto-generated method stub
	
}
	
//	@Override
//	public Booking findByParking_id(long id) {
//		// TODO Auto-generated method stub
//		List<Booking> bookings = bookingRepository.findAll();
//		for (Booking booking : bookings) {
//			if(booking.getParking_id()== id) {
//				return booking;
//			}
//		}
//		return new Booking();
//	}

@Override
public Admin checklogin(Admin admin) {
	// TODO Auto-generated method stub
	return adminRepository.findByUsernameAndPassword(admin.getUsername(), admin.getPassword());
}



	

}
