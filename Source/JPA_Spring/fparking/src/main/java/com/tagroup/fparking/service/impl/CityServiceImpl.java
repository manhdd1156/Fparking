package com.tagroup.fparking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.repository.CityRepository;
import com.tagroup.fparking.service.CityService;
import com.tagroup.fparking.service.domain.City;
@Service
public class CityServiceImpl implements CityService{
@Autowired
private CityRepository cityRepository;

@Override
public List<City> getAll() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public City getById(Long id) throws Exception {
	// TODO Auto-generated method stub
	return null;
}

@Override
public City create(City city) throws Exception {
	// TODO Auto-generated method stub
	return null;
}

@Override
public City update(City city) throws Exception {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void delete(Long id) throws Exception {
	// TODO Auto-generated method stub
	
}

@Override
public City checklogin(City city) throws Exception {
	// TODO Auto-generated method stub
	return null;
}


	

}
