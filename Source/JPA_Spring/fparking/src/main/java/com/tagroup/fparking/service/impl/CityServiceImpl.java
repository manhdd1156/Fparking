package com.tagroup.fparking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.repository.CityRepository;
import com.tagroup.fparking.security.Token;
import com.tagroup.fparking.service.CityService;
import com.tagroup.fparking.service.domain.City;
@Service
public class CityServiceImpl implements CityService{
@Autowired
private CityRepository cityRepository;

@Override
public List<City> getAll() {
	Token t = (Token) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	System.out.println(t.getType());
	// TODO Auto-generated method stub
	return cityRepository.findAll();
}

@Override
public City getById(Long id) throws Exception {
	// TODO Auto-generated method stub
	return cityRepository.getOne(id);
}

@Override
public City create(City city) throws Exception {
	// TODO Auto-generated method stub
	return cityRepository.save(city);
}

@Override
public City update(City city) throws Exception {
	// TODO Auto-generated method stub
	return cityRepository.save(city);
}

@Override
public void delete(Long id) throws Exception {
	// TODO Auto-generated method stub
	
}


	

}
