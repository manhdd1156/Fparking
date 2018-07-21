package com.tagroup.fparking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tagroup.fparking.service.DriverService;
import com.tagroup.fparking.service.StaffService;
import com.tagroup.fparking.service.domain.Driver;
import com.tagroup.fparking.service.domain.Staff;

@RestController
@RequestMapping("/api/login")
public class LoginController {
	@Autowired
	private DriverService driverService;
	@Autowired
	private StaffService staffService;

	// login by info of driver
@RequestMapping(path = "/drivers", method = RequestMethod.POST)
public ResponseEntity<?> Driverlogin(@RequestBody Driver driverLogin) throws Exception {
	
		Driver respone = driverService.findByPhoneAndPassword(driverLogin.getPhone(), driverLogin.getPassword());
		return new ResponseEntity<>(respone, HttpStatus.OK);
	
}

	// login by phone and password of staff
	@RequestMapping(path = "/staffs", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody Staff staffLogin) throws Exception {
		
			Staff respone = staffService.findByPhoneAndPassword(staffLogin.getPhone(), staffLogin.getPassword());
			return new ResponseEntity<>(respone, HttpStatus.OK);
		
	}

	// login by phone of staff
	@RequestMapping(path = "/staffs", method = RequestMethod.GET)
	public ResponseEntity<?> getinfo(@RequestParam("phone") String phone) throws Exception {
		
			Staff respone = staffService.findByPhone(phone);
			return new ResponseEntity<>(respone, HttpStatus.OK);
		
	}

}
