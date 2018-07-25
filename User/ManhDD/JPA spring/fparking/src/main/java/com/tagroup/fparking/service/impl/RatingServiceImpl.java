package com.tagroup.fparking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.controller.error.APIException;
import com.tagroup.fparking.repository.RatingRepository;
import com.tagroup.fparking.service.RatingService;
import com.tagroup.fparking.service.domain.Rating;

@Service
public class RatingServiceImpl implements RatingService {
	@Autowired
	private RatingRepository raingRepository;

	@Autowired
	RatingRepository ratingRepository;

	@Override
	public List<Rating> getAll() {
		return raingRepository.findAll();

	}

	@Override
	public Rating getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		try {
			return raingRepository.getOne(id);
		} catch (Exception e) {
			throw new APIException(HttpStatus.NOT_FOUND, "The food was not found");
		}
		
	}

	@Override
	public Rating create(Rating rating) {
		// TODO Auto-generated method stub
		return raingRepository.save(rating);

	}

	@Override
	public Rating update(Rating rating) {
		// TODO Auto-generated method stub
		return raingRepository.save(rating);

	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Rating rating = raingRepository.getOne(id);
		raingRepository.delete(rating);
	}

}
