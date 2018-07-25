package com.tagroup.fparking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tagroup.fparking.service.DriverService;
import com.tagroup.fparking.service.VehicleService;
import com.tagroup.fparking.service.domain.Driver;
import com.tagroup.fparking.service.domain.Vehicle;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {
	@Autowired
	private DriverService driverService;
	@Autowired
	private VehicleService driverVehicleService;

	// get driver by id
	@PreAuthorize("hasAnyAuthority('DRIVER')")
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getTypesByDrivers(@PathVariable int id) throws Exception {
		
			List<Vehicle> respone = driverVehicleService.getAll();
			return new ResponseEntity<>(respone, HttpStatus.OK);
	}

	// update pass for driver
	@PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'OWNER','STAFF')")
	@RequestMapping(path = "", method = RequestMethod.PUT)
	public ResponseEntity<?> changePassword(@RequestBody Driver driver) throws Exception {
		
			Driver respone = driverService.update(driver);
			return new ResponseEntity<>(respone, HttpStatus.OK);
		
	}
	@PreAuthorize("hasAnyAuthority('DRIVER')")
	@RequestMapping(path = "/profile", method = RequestMethod.GET)
	public ResponseEntity<?> getProfile() throws Exception {
		Driver respone = driverService.getProfile();
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}
}
