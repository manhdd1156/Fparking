package com.tagroup.fparking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tagroup.fparking.service.BookingService;
import com.tagroup.fparking.service.ParkingService;
import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.Parking;

@RestController
@RequestMapping("/api/parkings")
public class ParkingController {
	@Autowired
	private ParkingService parkingService;

	@Autowired
	private BookingService bookingService;

	@RequestMapping(path = "", method = RequestMethod.GET)
	public ResponseEntity<?> getall() {
		List<Parking> respone = parkingService.getAll();
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getbyid(@PathVariable Long id) {
		Parking respone = parkingService.getById(id);
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}
	
	// lấy thông tin theo link : http://localhost:9000/api/parkings/abc?id=3
	@RequestMapping(path = "abc", method = RequestMethod.GET)
	public ResponseEntity<?> getbyid2(@RequestParam("id") Long id) {
		Parking respone = parkingService.getById(id);
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}
	// get bookings by parking id
	@RequestMapping(path = "/{id}/bookings", method = RequestMethod.GET)
	public ResponseEntity<?> getbookingbyPid(@PathVariable Long id) {
		Parking parking = parkingService.getById(id);
		List<Booking> respone = bookingService.findByParking(parking);
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}

	// @RequestMapping(path = "def", method = RequestMethod.POST)
	// public ResponseEntity<?> create(@RequestBody Student student) {
	// Student respone = studentService.create(student);
	// return new ResponseEntity<>(respone,HttpStatus.OK);
	// }

}
