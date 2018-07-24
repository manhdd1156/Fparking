package com.tagroup.fparking.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tagroup.fparking.service.DriverVehicleService;
import com.tagroup.fparking.service.VehicleService;
import com.tagroup.fparking.service.domain.Vehicle;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private DriverVehicleService drivervehicleService;
//get vehicle by drivervehicle_id
	@PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'OWNER','STAFF')")
@RequestMapping(path = "/drivervehicles/{id}", method = RequestMethod.GET)
public ResponseEntity<?> getVehicleByDrivervehicle(@PathVariable Long id) throws Exception {
	Vehicle respone = vehicleService.getVehicleByDriverVehicle(id);
	return new ResponseEntity<>(respone, HttpStatus.OK);
}

//get vehicles by phone of driver
@PreAuthorize("hasAnyAuthority('DRIVER')")
@RequestMapping(path = "/drivers", method = RequestMethod.GET)
public ResponseEntity<?> getTypesByDriver(@RequestParam("phone") String phone) throws Exception {
	List<Vehicle> respone = vehicleService.getVehicleByDriver(phone);
	return new ResponseEntity<>(respone, HttpStatus.OK);
}

//get rating by vehicle
@PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'OWNER','STAFF')")
	@RequestMapping(path = "/ratings/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getbyid(@PathVariable Long id) throws Exception{
		double respone = vehicleService.getRatingByVehicle(vehicleService.getById(id));
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}

//get driverVehicle by parking_id and check notification
//@PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'OWNER','STAFF')")
//	@RequestMapping(path = "/notifications", method = RequestMethod.GET)
//	public ResponseEntity<?> getbydParkingid(@RequestParam("parkingID") int parkingID) throws Exception{
//		DriverVehicle respone = drivervehicleService.getInfoDriverVehicle(parkingID);
//		return new ResponseEntity<>(respone, HttpStatus.OK);
//	}

}
