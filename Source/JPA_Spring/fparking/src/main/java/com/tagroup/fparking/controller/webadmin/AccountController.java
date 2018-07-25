package com.tagroup.fparking.controller.webadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tagroup.fparking.dto.DriverFineDTO;
import com.tagroup.fparking.service.DriverService;
import com.tagroup.fparking.service.DriverVehicleService;
import com.tagroup.fparking.service.FineService;
import com.tagroup.fparking.service.ParkingService;
import com.tagroup.fparking.service.VehicleService;
import com.tagroup.fparking.service.domain.Driver;
import com.tagroup.fparking.service.domain.Parking;
import com.tagroup.fparking.service.domain.Vehicle;

@Controller
@RequestMapping("/account")
public class AccountController {
	@Autowired
	private DriverService driverService;
	@Autowired
	private ParkingService parkingService;
	@Autowired
	private FineService fineService;
	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private DriverVehicleService driverVehicleService;

	// Management Drivers Account

	// get all account
	@RequestMapping(path = "/driver", method = RequestMethod.GET)
	public String accountDriver(Map<String, Object> model) throws Exception {
		List<Driver> listDriver = driverService.getByStatus(1);
		if (listDriver != null && listDriver.size() > 0) {
			model.put("listDriver", listDriver);
			model.put("totalAccount", listDriver.size());
		} else {
			model.put("totalAccount", 0);
		}
		return "accountdriver";
	}

	// get all account block
	@RequestMapping(path = "/driver/block", method = RequestMethod.GET)
	public String blockAccountDriver(Map<String, Object> model) throws Exception {
		List<Driver> listDriver = driverService.getByStatus(0);
		if (listDriver != null && listDriver.size() > 0) {
			model.put("listDriver", listDriver);
			model.put("totalAccount", listDriver.size());
		} else {
			model.put("totalAccount", 0);
		}
		return "blockaccountdriver";
	}

	// get detail account by id
	@RequestMapping(path = "/driver/detail/{id}", method = RequestMethod.GET)
	public String getInforDriver(Map<String, Object> model, @PathVariable Long id) throws Exception {
		
		Driver driver = driverService.getById(id);
		if (driver.getStatus() == 1) {
			model.put("status", "Hoạt động");
		} else {
			model.put("status", "Bị khóa");
		}
		model.put("name", driver.getName());
		model.put("phonenumber", driver.getPhone());

		List<Vehicle> listDriver = vehicleService.getVehicleByDriver(driver.getPhone());
		ArrayList<Map<String, Object>> arrayListDriver = new ArrayList<>();
		for (Vehicle vehicle : listDriver) {
			HashMap<String, Object> m = new HashMap<>();
			m.put("licenseplate", vehicle.getLicenseplate());
			m.put("color", vehicle.getColor());
			m.put("typecar", vehicle.getVehicletype().getType());
			arrayListDriver.add(m);
		}
		
		String car = "";
		if (arrayListDriver != null && arrayListDriver.size() > 0) {
			for (int i = 0; i < arrayListDriver.size()-1; i++) {
				car = car + arrayListDriver.get(i).get("licenseplate") +" ("+arrayListDriver.get(i).get("color")+","+arrayListDriver.get(i).get("typecar")+")"+ "; ";
			}
			car = car + arrayListDriver.get(arrayListDriver.size()-1).get("licenseplate") +" ("+arrayListDriver.get(arrayListDriver.size()-1).get("color")+","+arrayListDriver.get(arrayListDriver.size()-1).get("typecar")+")";
		} else {
			car = "Không có";
		}
		model.put("TotalCar", car);
		
		List<DriverFineDTO> dfList = new ArrayList<>();
		dfList = driverVehicleService.findByDriverId(id);
		model.put("driverFine", dfList);
		
		return "acountdriverdetail";
	}

	// block account by id
	@RequestMapping(path = "/driver/blockaccount/{id}", method = RequestMethod.GET)
	public String blockAccountDriver(Map<String, Object> model, @PathVariable Long id) throws Exception {
		Driver driver = driverService.getById(id);
		driver.setStatus(0);
		driverService.update(driver);
		List<Driver> listDriver = driverService.getByStatus(1);
		if (listDriver != null && listDriver.size() > 0) {
			model.put("listDriver", listDriver);
			model.put("totalAccount", listDriver.size());
		} else {
			model.put("totalAccount", 0);
		}
		return "accountdriver";
	}
	
	// unblock account by id
	@RequestMapping(path = "/driver/unblockaccount/{id}", method = RequestMethod.GET)
	public String unBlockAccountDriver(Map<String, Object> model, @PathVariable Long id) throws Exception {
		Driver driver = driverService.getById(id);
		driver.setStatus(1);
		driverService.update(driver);
		List<Driver> listDriver = driverService.getByStatus(0);
		if (listDriver != null && listDriver.size() > 0) {
			model.put("listDriver", listDriver);
			model.put("totalAccount", listDriver.size());
		} else {
			model.put("totalAccount", 0);
		}
		return "blockaccountdriver";
	}
	
	//edit account driver by id
	
	@RequestMapping(path = "/driver/editaccount/{id}", method = RequestMethod.GET)
	public String editAccountDriver(Map<String, Object> model, @PathVariable Long id) throws Exception {

		Driver driver = driverService.getById(id);
		model.put("name", driver.getName());
		model.put("phonenumber", driver.getPhone());
		return "editdriver";
	}
	

	// Management Parking Account

	@RequestMapping(path = "/parking", method = RequestMethod.GET)
	public String accountParking(Map<String, Object> model) throws Exception {
		List<Parking> listParking = parkingService.getByStatus(1);
		if (listParking != null && listParking.size() > 0) {
			model.put("listParking", listParking);
			model.put("totalAccount", listParking.size());
		} else {
			model.put("totalAccount", 0);
		}
		return "accountparking";
	}

	@RequestMapping(path = "/parking/block", method = RequestMethod.GET)
	public String blockAccountParking(Map<String, Object> model) throws Exception {
		List<Parking> listParking = parkingService.getByStatus(0);
		if (listParking != null && listParking.size() > 0) {
			model.put("listDriver", listParking);
			model.put("totalAccount", listParking.size());
		} else {
			model.put("totalAccount", 0);
		}
		return "blockaccountparking";
	}

	@RequestMapping(path = "/driver/Detail", method = RequestMethod.GET)
	public String accountDriverDetail(Map<String, Object> model) throws Exception {
		return "acountDriverDetail";
	}
}
