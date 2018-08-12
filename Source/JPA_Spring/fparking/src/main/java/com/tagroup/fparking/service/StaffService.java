package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Parking;
import com.tagroup.fparking.service.domain.Staff;

public interface StaffService {
	public List<Staff> getAll();

	public Staff getById(Long id) throws Exception;

	public Staff create(Staff staff) throws Exception;

	public Staff update(Staff staff) throws Exception;

	public void delete(Long id) throws Exception;

	public List<Staff> findByParking(Parking parking) throws Exception;
	
	public List<Staff> findByOwner() throws Exception;

	public Staff findByPhoneAndPassword(String phone, String password) throws Exception;

	public Staff findByPhone(String phone) throws Exception;

	public Staff getProfile() throws Exception;
}
