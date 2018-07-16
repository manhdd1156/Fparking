package com.tagroup.fparking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tagroup.fparking.service.BookingService;
import com.tagroup.fparking.service.ParkingService;
import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.Parking;
import com.tagroup.fparking.service.domain.Staff;
import com.tagroup.fparking.service.domain.Tariff;

@Controller
@RequestMapping("/tat/bookings")
public class BookingController {
	@Autowired
	private BookingService bookingService;
	@Autowired
	private ParkingService parkingService;
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

	// get booking by status of booking
	@RequestMapping(path = "", method = RequestMethod.GET)
	public ResponseEntity<?> getbystatus(@RequestParam("status") int status) {
		Booking respone = bookingService.findByStatus(status);
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}
	// get tariff by booking id = ?
	@RequestMapping(path = "/{id}/tariffs", method = RequestMethod.GET)
	public ResponseEntity<?> getTariffByBId(@PathVariable Long id) {
		List<Tariff> respone = bookingService.getTariffByBid(bookingService.getById(id));
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}
	//get booking by parking id = ?
	@RequestMapping(path = "/parkings/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getTariffByPId(@PathVariable Long id) {
		List<Booking> respone = bookingService.findByParking(parkingService.getById(id));
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}
	// update booking 
	@RequestMapping(path = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@RequestBody Booking booking) throws Exception {
		Booking respone = bookingService.update(booking);
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}
}
