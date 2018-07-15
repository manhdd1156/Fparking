package com.tagroup.fparking.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.repository.DriverRepository;
import com.tagroup.fparking.repository.DriverVehicleRepository;
import com.tagroup.fparking.service.DriverService;
import com.tagroup.fparking.service.domain.Driver;
import com.tagroup.fparking.service.domain.DriverVehicle;
import com.tagroup.fparking.service.domain.Vehicletype;

@Service
public class DriverServiceImpl implements DriverService {
	@Autowired
	private DriverRepository driverRepository;

	@Autowired
	private DriverVehicleRepository driverVehicleRepository;

	@Override
	public List<Driver> getAll() {
		// TODO Auto-generated method stub
		return driverRepository.findAll();
	}

	@Override
	public Driver getById(Long id) {
		// TODO Auto-generated method stub
		return driverRepository.getOne(id);
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
	public Driver findByPhoneAndPassword(String phone, String password) {
		// TODO Auto-generated method stub
		return driverRepository.findByPhoneAndPassword(phone, password);
	}

	@Override
	public List<Vehicletype> getTypesByDriver(String phone) {
		List<DriverVehicle> dv = driverVehicleRepository.findByDriver(driverRepository.findByPhone(phone));
		List<Vehicletype> listVT = new ArrayList<>();
		for (DriverVehicle driverVehicle : dv) {
			listVT.add(driverVehicle.getVehicle().getVehicletype());
		}

		// TODO Auto-generated method stub
		return listVT;
	}

}
