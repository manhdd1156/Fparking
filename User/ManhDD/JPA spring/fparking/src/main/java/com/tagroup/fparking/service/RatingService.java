package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Rating;
import com.tagroup.fparking.service.domain.Vehicle;


public interface RatingService {
	public List<Rating> getAll();
	public Rating getById(Long id);
	public Rating create(Rating rating);
	public Rating update(Rating rating);
	public void delete(Long id);
	public double getByVehicle(Vehicle vehicle);
}
