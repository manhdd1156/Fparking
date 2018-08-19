package com.tagroup.fparking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tagroup.fparking.service.OwnerService;
import com.tagroup.fparking.service.ParkingService;
import com.tagroup.fparking.service.domain.Owner;
import com.tagroup.fparking.service.domain.Staff;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {
	@Autowired
	private OwnerService ownerService;
	@Autowired
	private ParkingService parkingService;

	@PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
	@RequestMapping(path = "", method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		List<Owner> respone = ownerService.getAll();
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}

	// get profile owner
	@PreAuthorize("hasAnyAuthority('OWNER')")
	@RequestMapping(path = "/profile", method = RequestMethod.GET)
	public ResponseEntity<?> getProfile() throws Exception {
		Owner respone = ownerService.getProfile();
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}

	@RequestMapping(path = "", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody Owner Owner) throws Exception {
		Owner respone = ownerService.create(Owner);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// update profile
	@PreAuthorize("hasAnyAuthority('OWNER')")
	@RequestMapping(path = "/update", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody Owner owner) throws Exception {
		Owner respone = ownerService.update(owner);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// change password when forgot password driver
	@RequestMapping(path = "/forgotpassword", method = RequestMethod.PUT)
	public ResponseEntity<?> forgotpassword(@RequestBody Owner owner) throws Exception {

		Owner respone = ownerService.forgotpassword(owner);

		return new ResponseEntity<>(respone, HttpStatus.OK);

	}
}
