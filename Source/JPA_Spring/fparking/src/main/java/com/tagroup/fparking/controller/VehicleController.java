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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tagroup.fparking.dto.DriverVehicleDTO;
import com.tagroup.fparking.service.DriverVehicleService;
import com.tagroup.fparking.service.VehicleService;
import com.tagroup.fparking.service.VehicletypeService;
import com.tagroup.fparking.service.domain.DriverVehicle;
import com.tagroup.fparking.service.domain.Vehicle;
import com.tagroup.fparking.service.domain.Vehicletype;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private VehicletypeService vehicleTypeService;
	@Autowired
	private DriverVehicleService drivervehicleService;

	// get vehicle by drivervehicle_id
	@PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'OWNER','STAFF')")
	@RequestMapping(path = "/drivervehicles/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getVehicleByDrivervehicle(@PathVariable Long id) throws Exception {
		Vehicle respone = vehicleService.getVehicleByDriverVehicle(id);
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}

	// get all vehicletype
	@PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER','STAFF')")
	@RequestMapping(path = "/types", method = RequestMethod.GET)
	public ResponseEntity<?> getAllVehicleType() throws Exception {
		List<Vehicletype> respone = vehicleTypeService.getAll();
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}

	// get drivervehicles by driverid
	@PreAuthorize("hasAnyAuthority('DRIVER','ADMIN','STAFF')")
	@RequestMapping(path = "/drivers/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getTypesByDriver(@PathVariable Long id) throws Exception {
		List<DriverVehicle> respone = drivervehicleService.getDriverVehicleByDriver(id);
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}
//
//	// get rating by vehicle
//	@PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'OWNER','STAFF')")
//	@RequestMapping(path = "/ratings/{id}", method = RequestMethod.GET)
//	public ResponseEntity<?> getbyid(@PathVariable Long id) throws Exception {
//		double respone = vehicleService.getRatingByVehicle(vehicleService.getById(id));
//		return new ResponseEntity<>(respone, HttpStatus.OK);
//	}

	// get driverVehicle by parking_id and check notification
	@PreAuthorize("hasAnyAuthority('STAFF')")
	@RequestMapping(path = "/notifications", method = RequestMethod.GET)
	public ResponseEntity<?> getbydParkingid(@RequestParam("parkingid") Long parkingID,
			@RequestParam("event") String event) throws Exception {
		System.out.println("/api/vehicles/notifications : " + parkingID);
		DriverVehicle respone = drivervehicleService.getInfoVehicle(parkingID, event);
		return new ResponseEntity<>(respone, HttpStatus.OK);
	}

	// update status = 0 drivervehicle by drivervehicleid
	@PreAuthorize("hasAnyAuthority('DRIVER')")
	@RequestMapping(path = "/drivervehicles/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> updateDriverVehicle(@PathVariable Long id) throws Exception {
		DriverVehicle respone = drivervehicleService.updateStatus(id);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// create DriverVehicle by licenseplate, type,color, driverid
	@PreAuthorize("hasAnyAuthority('DRIVER','STAFF')")
	@RequestMapping(path = "", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody DriverVehicleDTO drivervehicle) throws Exception {
		DriverVehicle respone = vehicleService.create(drivervehicle);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// change car of Driver
	@PreAuthorize("hasAnyAuthority('DRIVER','STAFF')")
	@RequestMapping(path = "", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody DriverVehicleDTO drivervehicle) throws Exception {
		DriverVehicle respone = drivervehicleService.update(drivervehicle);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// delete DriverVehicle by licenseplate, type, driverid
	@PreAuthorize("hasAnyAuthority('DRIVER')")
	@RequestMapping(path = "", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@RequestBody DriverVehicleDTO drivervehicle) throws Exception {
		// System.out.println("=======");
		vehicleService.delete(drivervehicle);
		return new ResponseEntity<>("ok", HttpStatus.OK);

	}
}
