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

import com.tagroup.fparking.service.StaffService;
import com.tagroup.fparking.service.domain.Staff;

@RestController
@RequestMapping("/tat/staffs")
public class StaffController {
	@Autowired
	private StaffService staffService;
@RequestMapping(path = "", method = RequestMethod.GET)
public ResponseEntity<?> getAll() {
	List<Staff> respone = staffService.getAll();
	return new ResponseEntity<>(respone,HttpStatus.OK);
}
//get booking by booking id
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody Staff staffLogin) throws Exception {
		Staff respone = staffService.findByPhoneAndPassword(staffLogin.getPhone(), staffLogin.getPassword());
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}
//@RequestMapping(path = "def", method = RequestMethod.POST)
//public ResponseEntity<?> create(@RequestBody Student student) {
//	Student respone = studentService.create(student);
//	return new ResponseEntity<>(respone,HttpStatus.OK);
//}

}
