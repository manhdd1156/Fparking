package com.tagroup.fparking.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tagroup.fparking.service.BookingService;
import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.Parking;

@Controller
@RequestMapping("/api/bookings")
public class BookingController {
	@Autowired
	private BookingService bookingService;
	// get all bookings
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ResponseEntity<?> getall() {
		List<Booking> respone = bookingService.getAll();
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}
	// get booking by booking id
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getbyid(@PathVariable Long id) {
		Booking respone = bookingService.getById(id);
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}
//@RequestMapping(path = "def", method = RequestMethod.POST)
//public ResponseEntity<?> create(@RequestBody Student student) {
//	Student respone = studentService.create(student);
//	return new ResponseEntity<>(respone,HttpStatus.OK);
//}

}
