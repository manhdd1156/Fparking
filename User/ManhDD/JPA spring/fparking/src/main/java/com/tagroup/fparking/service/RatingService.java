package com.tagroup.fparking.service;

import java.util.List;

import com.tagroup.fparking.service.domain.Rating;


public interface RatingService {
	public List<Rating> getAll();
	public Rating getById(Long id)throws Exception;
	public Rating create(Rating rating)throws Exception;
	public Rating update(Rating rating)throws Exception;
	public void delete(Long id)throws Exception;
}
