package com.tagroup.fparking.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tagroup.fparking.service.VehicleService;
import com.tagroup.fparking.service.domain.Vehicle;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
	@Autowired
	private VehicleService vehicleService;
//get vehicle by drivervehicle_id
@RequestMapping(path = "/drivervehicles/{id}", method = RequestMethod.GET)
public ResponseEntity<?> getVehicleByDrivervehicle(@PathVariable Long id) throws Exception {
	Vehicle respone = vehicleService.getVehicleByDriverVehicle(id);
	return new ResponseEntity<>(respone, HttpStatus.OK);
}

//get vehicles by phone of driver
@RequestMapping(path = "/drivers/{phone}", method = RequestMethod.GET)
public ResponseEntity<?> getTypesByDriver(@RequestParam("phone") String phone) throws Exception {
	List<Vehicle> respone = vehicleService.getVehicleByDriver(phone);
	return new ResponseEntity<>(respone, HttpStatus.OK);
}

//get rating by vehicle
	@RequestMapping(path = "/{id}/vehicles", method = RequestMethod.GET)
	public ResponseEntity<?> getbyid(@PathVariable Long id) throws Exception{
		double respone = vehicleService.getRatingByVehicle(vehicleService.getById(id));
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}

}
