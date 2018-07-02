package com.tagroup.fparking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.repository.StaffRepository;
import com.tagroup.fparking.service.StaffService;
import com.tagroup.fparking.service.domain.Staff;
@Service
public class StaffServiceImpl implements StaffService{
@Autowired
private StaffRepository staffRepository;
	@Override
	public List<Staff> getAll() {
		// TODO Auto-generated method stub
		return staffRepository.findAll();
		
	}

	@Override
	public Staff getById(Long id) {
		// TODO Auto-generated method stub
		return staffRepository.getOne(id);
	}

	@Override
	public Staff create(Staff staff) {
		// TODO Auto-generated method stub
		return staffRepository.save(staff);
	
	}

	@Override
	public Staff update(Staff staff) {
		// TODO Auto-generated method stub
		return staffRepository.save(staff);
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Staff staff = staffRepository.getOne(id);
		staffRepository.delete(staff);
	}
	

}
