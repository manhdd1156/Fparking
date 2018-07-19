package com.tagroup.fparking.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/tat/drivers")
public class DriverController {
	@Autowired
	private DriverService driverService;
	@Autowired
	private VehicleService driverVehicleService;
@RequestMapping(path = "getall", method = RequestMethod.GET)
public ResponseEntity<?> getAll() {
	List<Driver> respone = driverService.getAll();
	return new ResponseEntity<>(respone,HttpStatus.OK);
}
@RequestMapping(path = "/login", method = RequestMethod.POST)
public ResponseEntity<?> login(@RequestBody Driver driverLogin) throws Exception {
	Driver respone = driverService.findByPhoneAndPassword(driverLogin.getPhone(), driverLogin.getPassword());
	return new ResponseEntity<>(respone, HttpStatus.OK);
}
@RequestMapping(path = "/{phone}/vehicles", method = RequestMethod.GET)
public ResponseEntity<?> getTypesByDriver(@PathVariable String phone) throws Exception {
	List<Vehicle> respone = driverService.getVehicleByDriver(phone);
	return new ResponseEntity<>(respone, HttpStatus.OK);
}

@RequestMapping(path = "/{id}", method = RequestMethod.GET)
public ResponseEntity<?> getTypesByDriversss(@PathVariable int id) throws Exception {
	List<Vehicle> respone = driverVehicleService.getAll();
	return new ResponseEntity<>(respone, HttpStatus.OK);
}
//@RequestMapping(path = "def", method = RequestMethod.POST)
//public ResponseEntity<?> create(@RequestBody Student student) {
//	Student respone = studentService.create(student);
//	return new ResponseEntity<>(respone,HttpStatus.OK);
//}

}
