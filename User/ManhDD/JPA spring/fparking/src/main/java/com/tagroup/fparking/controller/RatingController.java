package com.tagroup.fparking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tagroup.fparking.repository.VehicleRepository;
import com.tagroup.fparking.service.BookingService;
import com.tagroup.fparking.service.ParkingService;
import com.tagroup.fparking.service.RatingService;
import com.tagroup.fparking.service.VehicleService;
import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.Parking;
import com.tagroup.fparking.service.domain.Rating;
import com.tagroup.fparking.service.domain.Tariff;

@Controller
@RequestMapping("/tat/ratings")
public class RatingController {
	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private ParkingService parkingService;
	@Autowired
	private RatingService ratingService;
	// get all bookings
	@RequestMapping(path = "", method = RequestMethod.GET)
	public ResponseEntity<?> getall() {
		List<Rating> respone = ratingService.getAll();
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}

	// get rating by vehicle
	@RequestMapping(path = "/vehicles/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getbyid(@PathVariable Long id) {
		double respone = ratingService.getByVehicle(vehicleService.getById(id));
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}

}
