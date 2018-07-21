package com.tagroup.fparking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tagroup.fparking.service.BookingService;
import com.tagroup.fparking.service.ParkingService;
import com.tagroup.fparking.service.domain.Booking;

@Controller
@RequestMapping("/api/bookings")
public class BookingController {
	@Autowired
	private BookingService bookingService;
	@Autowired
	private ParkingService parkingService;

	// get all bookings
	@PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'OWNER','STAFF')")
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ResponseEntity<?> findAll() throws Exception {
		List<Booking> respone = bookingService.getAll();
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}

	// get booking by booking id
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getbyid(@PathVariable Long id) throws Exception {
		System.out.println("dsadsadassadsadsasdsa");
			Booking respone = bookingService.getById(id);
			return new ResponseEntity<>(respone, HttpStatus.OK);
		
	}

	// get booking by status of booking
	@RequestMapping(path = "", method = RequestMethod.GET)
	public ResponseEntity<?> getbystatus(@RequestParam("status") int status) throws Exception {
		
			Booking respone = bookingService.findByStatus(status);
			return new ResponseEntity<>(respone, HttpStatus.OK);
		
	}

	// get booking by parking id = ?
	@RequestMapping(path = "/parkings/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getBookingByPId(@PathVariable Long id) throws Exception {
		
			List<Booking> respone = bookingService.findByParking(parkingService.getById(id));
			return new ResponseEntity<>(respone, HttpStatus.OK);
		
	}

	// get booking by phone of driver ?
	@RequestMapping(path = "/drivers", method = RequestMethod.GET)
	public ResponseEntity<?> getBookingByDphone(@RequestParam("phone") String phone) throws Exception {
		
			List<Booking> respone = bookingService.findByDriverPhone(phone);
			return new ResponseEntity<>(respone, HttpStatus.OK);
		
	}

	// create new booking
	@RequestMapping(path = "", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody Booking booking) throws Exception {
			Booking respone = bookingService.create(booking);
			return new ResponseEntity<>(respone, HttpStatus.OK);
		
	}

	// update booking
	@RequestMapping(path = "/update", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody Booking booking) throws Exception {
		
			Booking respone = bookingService.update(booking);
			return new ResponseEntity<>(respone, HttpStatus.OK);
		
	}
}
