package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.City;

public interface CityService {
	public List<City> getAll();

	public City getById(Long id) throws Exception;

	public City create(City city) throws Exception;

	public City update(City city) throws Exception;

	public void delete(Long id) throws Exception;

	public City checklogin(City city) throws Exception;
}
