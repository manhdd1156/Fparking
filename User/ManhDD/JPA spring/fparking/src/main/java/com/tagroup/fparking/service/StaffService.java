package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Parking;
import com.tagroup.fparking.service.domain.Staff;


public interface StaffService {
	public List<Staff> getAll();
	public Staff getById(Long id);
	public Staff create(Staff staff);
	public Staff update(Staff staff);
	public void delete(Long id);
	public List<Staff> findByParking(Parking parking);
	public Staff findByPhoneAndPassword(String phone, String password);
	public Staff findByPhone(String phone);
}
