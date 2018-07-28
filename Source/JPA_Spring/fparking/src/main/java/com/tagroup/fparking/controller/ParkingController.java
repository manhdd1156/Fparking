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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tagroup.fparking.dto.ParkingTariffDTO;
import com.tagroup.fparking.service.ParkingService;
import com.tagroup.fparking.service.domain.Parking;

@RestController
@RequestMapping("/api/parkings")
public class ParkingController {
	@Autowired
	private ParkingService parkingService;

	@PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'OWNER','STAFF')")
	@RequestMapping(path = "", method = RequestMethod.GET)
	public ResponseEntity<?> getall() throws Exception {
		List<Parking> respone = parkingService.getAll();
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// get parking by id
	@PreAuthorize("hasAnyAuthority('DRIVER','STAFF')")
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getbyid(@PathVariable Long id) throws Exception {
		Parking respone = parkingService.getById(id);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// get parkings by latitude and longitude
	@PreAuthorize("hasAnyAuthority('DRIVER')")
	@RequestMapping(value = "", params = { "latitude", "longitude" }, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getBarBySimplePathWithExplicitRequestParams(@RequestParam("latitude") String latitude,
			@RequestParam("longitude") String longitude) throws Exception {

		List<Parking> respone = parkingService.findByLatitudeANDLongitude(latitude, longitude);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// get tariff by parking id = ?
	@PreAuthorize("hasAnyAuthority('DRIVER')")
	@RequestMapping(path = "/{id}/tariffs", method = RequestMethod.GET)
	public ResponseEntity<?> getTariffByBId(@PathVariable Long id) throws Exception {

		ParkingTariffDTO respone = parkingService.getTariffByPid(parkingService.getById(id));
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// get detail account by id
	// @RequestMapping(path = "/driver/detail/{id}", method = RequestMethod.GET)
	// public String getInforDriver(Map<String, Object> model, @PathVariable Long
	// id) throws Exception {
	//
	// Driver driver = driverService.getById(id);
	// if (driver.getStatus() == 1) {
	// model.put("status", "Hoạt động");
	// } else {
	// model.put("status", "Bị khóa");
	// }
	// model.put("name", driver.getName());
	// model.put("phonenumber", driver.getPhone());
	//
	// List<Vehicle> listDriver =
	// vehicleService.getVehicleByDriver(driver.getPhone());
	// ArrayList<Map<String, Object>> arrayListDriver = new ArrayList<>();
	// for (Vehicle vehicle : listDriver) {
	// HashMap<String, Object> m = new HashMap<>();
	// m.put("licenseplate", vehicle.getLicenseplate());
	// m.put("color", vehicle.getColor());
	// m.put("typecar", vehicle.getVehicletype().getType());
	// arrayListDriver.add(m);
	// }
	//
	// String car = "";
	// if (arrayListDriver != null && arrayListDriver.size() > 0) {
	// for (int i = 0; i < arrayListDriver.size()-1; i++) {
	// car = car + arrayListDriver.get(i).get("licenseplate") +"
	// ("+arrayListDriver.get(i).get("color")+","+arrayListDriver.get(i).get("typecar")+")"+
	// "; ";
	// }
	// car = car + arrayListDriver.get(arrayListDriver.size()-1).get("licenseplate")
	// +"
	// ("+arrayListDriver.get(arrayListDriver.size()-1).get("color")+","+arrayListDriver.get(arrayListDriver.size()-1).get("typecar")+")";
	// } else {
	// car = "Không có";
	// }
	// model.put("TotalCar", car);
	//
	// List<DriverFineDTO> dfList = new ArrayList<>();
	// dfList = driverVehicleService.findByDriverId(id);
	// model.put("driverFine", dfList);
	//
	// return "acountdriverdetail";
	// }

	// get tariff by parking id = ?
	@PreAuthorize("hasAnyAuthority('STAFF','ADMIN')")
		@RequestMapping(path = "/owner/{id}", method = RequestMethod.GET)
		public ResponseEntity<?> getByOwnerId(@PathVariable Long id)throws Exception {
			
				List<Parking> respone = parkingService.getByOwnerID(id);
				
				return new ResponseEntity<>(respone, HttpStatus.OK);
			
		}

	// get Rating by parking id
	@PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'OWNER','STAFF')")
	@RequestMapping(path = "/{id}/rates", method = RequestMethod.GET)
	public ResponseEntity<?> getRatebyPid(@PathVariable Long id) throws Exception {

		String respone = parkingService.getRatingByPid(id);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}

	// update parking
	@PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'OWNER','STAFF')")
	@RequestMapping(path = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@RequestBody Parking parking) throws Exception {

		Parking respone = parkingService.update(parking);
		return new ResponseEntity<>(respone, HttpStatus.OK);

	}
}
