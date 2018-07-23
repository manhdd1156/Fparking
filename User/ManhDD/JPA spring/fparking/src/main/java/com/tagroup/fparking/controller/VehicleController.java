package com.tagroup.fparking.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tagroup.fparking.service.DriverService;
import com.tagroup.fparking.service.VehicleService;
import com.tagroup.fparking.service.domain.Driver;
import com.tagroup.fparking.service.domain.Vehicle;

@RestController
@RequestMapping("/tat/vehicles")
public class VehicleController {
	@Autowired
	private DriverService driverService;
	@Autowired
	private VehicleService vehicleService;
@RequestMapping(path = "getall", method = RequestMethod.GET)
public ResponseEntity<?> getAll() {
	List<Driver> respone = driverService.getAll();
	return new ResponseEntity<>(respone,HttpStatus.OK);
}

@RequestMapping(path = "/drivervehicles/{id}", method = RequestMethod.GET)
public ResponseEntity<?> getVehicleByDrivervehicle(@PathVariable Long id) throws Exception {
	Vehicle respone = vehicleService.getVehicleByDriverVehicle(id);
	return new ResponseEntity<>(respone, HttpStatus.OK);
}

}
