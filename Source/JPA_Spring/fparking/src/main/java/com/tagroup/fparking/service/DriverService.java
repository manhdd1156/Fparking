package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Driver;


public interface DriverService {
	public List<Driver> getAll();
	public Driver getById(Long id)throws Exception;
	public Driver create(Driver driver)throws Exception;
	public Driver update(Driver driver)throws Exception;
	public void delete(Long id)throws Exception;
	public List<Driver> getByStatus(int status)throws Exception;
	public Driver findByPhoneAndPassword(String phone, String password)throws Exception;
}
