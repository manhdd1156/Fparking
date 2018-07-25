package com.tagroup.fparking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.controller.error.APIException;
import com.tagroup.fparking.repository.StaffRepository;
import com.tagroup.fparking.security.Token;
import com.tagroup.fparking.service.StaffService;
import com.tagroup.fparking.service.domain.Parking;
import com.tagroup.fparking.service.domain.Staff;

@Service
public class StaffServiceImpl implements StaffService {
	@Autowired
	private StaffRepository staffRepository;

	@Override
	public List<Staff> getAll() {
		// TODO Auto-generated method stub
		return staffRepository.findAll();

	}

	@Override
	public Staff getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		
		try {
			return staffRepository.getOne(id);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "Staff was not found");
		}
	}

	@Override
	public Staff create(Staff staff) {
		// TODO Auto-generated method stub
		return staffRepository.save(staff);

	}

	@Override
	public Staff update(Staff staff) throws Exception{
		// TODO Auto-generated method stub
		
		try {
			return staffRepository.save(staff);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "Staff was not found");
		}

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Staff staff = staffRepository.getOne(id);
		staffRepository.delete(staff);
	}

	@Override
	public List<Staff> findByParking(Parking parking) throws Exception{
		// TODO Auto-generated method stub
		
		try {
			return staffRepository.findByParking(parking);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "Staff was not found");
		}
	}

	@Override
	public Staff findByPhoneAndPassword(String phone, String password) throws Exception{
		// TODO Auto-generated method stub
		try {
			if(staffRepository.findByPhoneAndPassword(phone, password)!=null)
			return staffRepository.findByPhoneAndPassword(phone, password);
			else throw new APIException(HttpStatus.NOT_FOUND, "Staff was not found");
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "Staff was not found");
		}
	}

	@Override
	public Staff findByPhone(String phone) throws Exception{
		try {
			return staffRepository.findByPhone(phone);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "Staff was not found");
		}
	}

	@Override
	public Staff getProfile() throws Exception {
		Token t = (Token) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return staffRepository.getOne(t.getId());
	}

}
