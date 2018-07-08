package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Driver;


public interface DriverService {
	public List<Driver> getAll();
	public Driver getById(Long id);
	public Driver create(Driver driver);
	public Driver update(Driver driver);
	public void delete(Long id);
}
