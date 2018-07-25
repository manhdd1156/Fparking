package com.tagroup.fparking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.controller.error.APIException;
import com.tagroup.fparking.repository.DriverRepository;
import com.tagroup.fparking.service.DriverService;
import com.tagroup.fparking.service.domain.Driver;

@Service
public class DriverServiceImpl implements DriverService {
	@Autowired
	private DriverRepository driverRepository;

	@Override
	public List<Driver> getAll() {
		// TODO Auto-generated method stub
		return driverRepository.findAll();
	}

	@Override
	public Driver getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		try {
			return driverRepository.getOne(id);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The food was not found");
		}

	}

	@Override
	public Driver create(Driver driver) {
		// TODO Auto-generated method stub
		return driverRepository.save(driver);

	}

	@Override
	public Driver update(Driver driver) {
		// TODO Auto-generated method stub
		return driverRepository.save(driver);

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Driver driver = driverRepository.getOne(id);
		driverRepository.delete(driver);
	}

	@Override
	public List<Driver> getByStatus(int status) {
		// TODO Auto-generated method stub
		
		try {
			List<Driver> listDriver = driverRepository.findByStatus(status);
			return listDriver;
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Driver was not found");
		}

	}

	@Override
	public Driver findByPhoneAndPassword(String phone, String password) {
		// TODO Auto-generated method stub
		try {
			return driverRepository.findByPhoneAndPassword(phone, password);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The Driver was not found");
		}
	}

}
