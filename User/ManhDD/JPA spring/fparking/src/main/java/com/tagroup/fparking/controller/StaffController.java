package com.tagroup.fparking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tagroup.fparking.service.ParkingService;
import com.tagroup.fparking.service.StaffService;
import com.tagroup.fparking.service.domain.Parking;
import com.tagroup.fparking.service.domain.Staff;

@RestController
@RequestMapping("/api/staffs")
public class StaffController {
	@Autowired
	private StaffService staffService;
	@Autowired
	private ParkingService parkingService;
	@PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'OWNER','STAFF')")
	@RequestMapping(path = "", method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		List<Staff> respone = staffService.getAll();
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}

	// get list staff
	@PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'OWNER','STAFF')")
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findByParking(@PathVariable Long id) throws Exception {
		Parking parking = parkingService.getById(id);
		List<Staff> respone = staffService.findByParking(parking);
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}
	// get list staff
	@PreAuthorize("hasAnyAuthority('STAFF')")
		@RequestMapping(path = "/profile", method = RequestMethod.GET)
		public ResponseEntity<?> getProfile() throws Exception {
			Staff respone = staffService.getProfile();
			return new ResponseEntity<>(respone, HttpStatus.OK);
		}
}
