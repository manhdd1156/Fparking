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

import com.tagroup.fparking.dto.DriverDTO;
import com.tagroup.fparking.service.DriverService;
import com.tagroup.fparking.service.FeedbackService;
import com.tagroup.fparking.service.FineService;
import com.tagroup.fparking.service.VehicleService;
import com.tagroup.fparking.service.domain.Driver;
import com.tagroup.fparking.service.domain.Feedback;
import com.tagroup.fparking.service.domain.Fine;
import com.tagroup.fparking.service.domain.Vehicle;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {
	@Autowired
	private DriverService driverService;
	@Autowired
	private VehicleService driverVehicleService;
	@Autowired
	private FeedbackService feedbackService;

	@Autowired
	private FineService fineService;

	// get driver by id
	@PreAuthorize("hasAnyAuthority('DRIVER')")
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getTypesByDrivers(@PathVariable Long id) throws Exception {

		List<Vehicle> respone = driverVehicleService.getAll();
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}

	// get fine by driverID
	@PreAuthorize("hasAnyAuthority('DRIVER')")
	@RequestMapping(path = "/{id}/fines", method = RequestMethod.GET)
	public ResponseEntity<?> getFinesByDriverID(@PathVariable Long id) throws Exception {

		List<Fine> respone = fineService.getByDriverID(id);
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}

	// get fine by fineID
	@PreAuthorize("hasAnyAuthority('DRIVER')")
	@RequestMapping(path = "/fines/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getFinesByID(@PathVariable Long id) throws Exception {

		Fine respone = fineService.getById(id);
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}

	// update driver
	@PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'OWNER','STAFF')")
	@RequestMapping(path = "", method = RequestMethod.PUT)
	public ResponseEntity<?> updateProfile(@RequestBody Driver driver) throws Exception {

		Driver respone = driverService.update(driver);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// change password driver
	@PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'OWNER','STAFF')")
	@RequestMapping(path = "/password", method = RequestMethod.PUT)
	public ResponseEntity<?> changepassword(@RequestBody DriverDTO driver) throws Exception {

		Driver respone = driverService.changepassword(driver);

		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// change password when forgot password driver
	@RequestMapping(path = "/password", method = RequestMethod.PUT)
	public ResponseEntity<?> forgotpassword(@RequestBody DriverDTO driver) throws Exception {

		Driver respone = driverService.changepassword(driver);

		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// create driver
	@RequestMapping(path = "", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody Driver driver) throws Exception {

		Driver respone = driverService.create(driver);
		if (respone == null) {
			return new ResponseEntity<>("phone exist", HttpStatus.OK);
		}
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// create feedback from driver
	@RequestMapping(path = "/feedbacks", method = RequestMethod.POST)
	public ResponseEntity<?> createFeedback(@RequestBody Feedback feedback) throws Exception {

		Feedback respone = feedbackService.create(feedback);
		if (respone == null) {
			return new ResponseEntity<>("phone exist", HttpStatus.OK);
		}
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// get profile
	@PreAuthorize("hasAnyAuthority('DRIVER')")
	@RequestMapping(path = "/profile", method = RequestMethod.GET)
	public ResponseEntity<?> getProfile() throws Exception {
		Driver respone = driverService.getProfile();
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}
}
