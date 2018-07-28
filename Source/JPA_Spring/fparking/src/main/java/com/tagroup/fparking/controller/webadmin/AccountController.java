package com.tagroup.fparking.controller.webadmin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tagroup.fparking.service.DriverService;
import com.tagroup.fparking.service.DriverVehicleService;
import com.tagroup.fparking.service.FineService;
import com.tagroup.fparking.service.OwnerService;
import com.tagroup.fparking.service.ParkingService;
import com.tagroup.fparking.service.VehicleService;
import com.tagroup.fparking.service.domain.Driver;
import com.tagroup.fparking.service.domain.Owner;
import com.tagroup.fparking.service.domain.Parking;

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
	@Autowired
	private OwnerService ownerService;

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
<<<<<<< HEAD
	@RequestMapping(path = "/driver/detail/{id}", method = RequestMethod.GET)
	public String getInforDriver(Map<String, Object> model, @PathVariable Long id) throws Exception {

		Driver driver = driverService.getById(id);
		List<Vehicle> listDriver = vehicleService.getVehicleByDriver(driver.getPhone());
		ArrayList<Map<String, Object>> arrayListDriver = new ArrayList<>();

		// tab thong tin
		if (driver.getStatus() == 1) {
			model.put("status", "Hoạt động");
		} else {
			model.put("status", "Bị khóa");
		}
		model.put("name", driver.getName());
		model.put("phonenumber", driver.getPhone());

		for (Vehicle vehicle : listDriver) {
			HashMap<String, Object> m = new HashMap<>();
			m.put("licenseplate", vehicle.getLicenseplate());
			m.put("color", vehicle.getColor());
			m.put("typecar", vehicle.getVehicletype().getType());
			arrayListDriver.add(m);
		}

		String car = "";
		if (arrayListDriver != null && arrayListDriver.size() > 0) {
			for (int i = 0; i < arrayListDriver.size() - 1; i++) {
				car = car + arrayListDriver.get(i).get("licenseplate") + " (" + arrayListDriver.get(i).get("color")
						+ "," + arrayListDriver.get(i).get("typecar") + ")" + "; ";
			}
			car = car + arrayListDriver.get(arrayListDriver.size() - 1).get("licenseplate") + " ("
					+ arrayListDriver.get(arrayListDriver.size() - 1).get("color") + ","
					+ arrayListDriver.get(arrayListDriver.size() - 1).get("typecar") + ")";
		} else {
			car = "Không có";
		}
		model.put("TotalCar", car);

		// tab lich su phat
		List<DriverFineDTO> dfList = new ArrayList<>();
		dfList = driverVehicleService.findByDriverId(id);
		System.out.println("Size dfList: " + dfList.size());
		double totalPriceFine = 0;
		for (DriverFineDTO driverFineDTO : dfList) {
			if (driverFineDTO.getStatus() == "Chưa thu") {
				totalPriceFine = totalPriceFine + driverFineDTO.getPrice();
			}
		}
		model.put("driverFine", dfList);
		model.put("totalPriceFine", totalPriceFine);

		return "acountdriverdetail";
	}
=======
//	@RequestMapping(path = "/driver/detail/{id}", method = RequestMethod.GET)
//	public String getInforDriver(Map<String, Object> model, @PathVariable Long id) throws Exception {
//		
//		Driver driver = driverService.getById(id);
//		if (driver.getStatus() == 1) {
//			model.put("status", "Hoạt động");
//		} else {
//			model.put("status", "Bị khóa");
//		}
//		model.put("name", driver.getName());
//		model.put("phonenumber", driver.getPhone());
//
//		List<Vehicle> listDriver = vehicleService.getVehicleByDriver(driver.getPhone());
//		ArrayList<Map<String, Object>> arrayListDriver = new ArrayList<>();
//		for (Vehicle vehicle : listDriver) {
//			HashMap<String, Object> m = new HashMap<>();
//			m.put("licenseplate", vehicle.getLicenseplate());
//			m.put("color", vehicle.getColor());
//			m.put("typecar", vehicle.getVehicletype().getType());
//			arrayListDriver.add(m);
//		}
//		
//		String car = "";
//		if (arrayListDriver != null && arrayListDriver.size() > 0) {
//			for (int i = 0; i < arrayListDriver.size()-1; i++) {
//				car = car + arrayListDriver.get(i).get("licenseplate") +" ("+arrayListDriver.get(i).get("color")+","+arrayListDriver.get(i).get("typecar")+")"+ "; ";
//			}
//			car = car + arrayListDriver.get(arrayListDriver.size()-1).get("licenseplate") +" ("+arrayListDriver.get(arrayListDriver.size()-1).get("color")+","+arrayListDriver.get(arrayListDriver.size()-1).get("typecar")+")";
//		} else {
//			car = "Không có";
//		}
//		model.put("TotalCar", car);
//		
//		List<DriverFineDTO> dfList = new ArrayList<>();
//		dfList = driverVehicleService.findByDriverId(id);
//		model.put("driverFine", dfList);
//		
//		return "acountdriverdetail";
//	}
>>>>>>> 535ad99745d408a0b0ad021408946687af6b84e8

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

	// edit account driver by id
	@RequestMapping(path = "/driver/editaccount/{id}", method = RequestMethod.GET)
	public String editAccountDriver(Map<String, Object> model, @PathVariable Long id) throws Exception {
		Driver driver = driverService.getById(id);
		model.put("id", id);
		model.put("name", driver.getName());
		model.put("phonenumber", driver.getPhone());
		return "editdriver";
	}

	// save account driver by id
	@RequestMapping(path = "/driver/editaccount/{id}", method = RequestMethod.POST)
	public String saveAccountDriver(Map<String, Object> model, @PathVariable("id") Long id,
			@RequestParam("name") String name, @RequestParam("phone") String phone) throws Exception {
		Driver driver = driverService.getById(id);
		driver.setName(name);
		driver.setPhone(phone);
		driverService.update(driver);

		Driver driver2 = driverService.getById(id);
		model.put("id", id);
		model.put("name", driver.getName());
		model.put("phonenumber", driver.getPhone());

		return "editdriver";
	}

	// Management Parking Account

	// get all account parking by id with status =1
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

	// get all blockaccount parking by id with status = 0
	@RequestMapping(path = "/parking/block", method = RequestMethod.GET)
	public String blockAccountParking(Map<String, Object> model) throws Exception {
		List<Parking> listParking = parkingService.getByStatus(0);
		if (listParking != null && listParking.size() > 0) {
			model.put("listParking", listParking);
			model.put("totalAccount", listParking.size());
		} else {
			model.put("totalAccount", 0);
		}
		return "blockaccountparking";
	}

	// get detail parking by id
	@RequestMapping(path = "/patking/detail/{id}", method = RequestMethod.GET)
	public String accountDriverDetail(Map<String, Object> model) throws Exception {
		return "accountparkingdetail";
	}

	// block account parking by id
	@RequestMapping(path = "/parking/blockaccount/{id}", method = RequestMethod.GET)
	public String blockAccountParking(Map<String, Object> model, @PathVariable Long id) throws Exception {
		Parking parking = parkingService.getById(id);
		parking.setStatus(0);
		parkingService.update(parking);
		List<Parking> listParking = parkingService.getByStatus(1);
		if (listParking != null && listParking.size() > 0) {
			model.put("listParking", listParking);
			model.put("totalAccount", listParking.size());
		} else {
			model.put("totalAccount", 0);
		}
		return "accountparking";
	}

	// unblock account parking by id
	@RequestMapping(path = "/parking/unblockaccount/{id}", method = RequestMethod.GET)
	public String unBlockAccountParking(Map<String, Object> model, @PathVariable Long id) throws Exception {
		Parking parking = parkingService.getById(id);
		parking.setStatus(1);
		parkingService.update(parking);
		List<Parking> listParking = parkingService.getByStatus(0);
		if (listParking != null && listParking.size() > 0) {
			model.put("listParking", listParking);
			model.put("totalAccount", listParking.size());
		} else {
			model.put("totalAccount", 0);
		}
		return "blockaccountparking";
	}

	// Manager Account Owner

	// get account owner by id
	@RequestMapping(path = "/owner", method = RequestMethod.GET)
	public String accountOwner(Map<String, Object> model) throws Exception {
		List<Owner> listOwner = ownerService.getAll();
		if (listOwner != null && listOwner.size() > 0) {
			model.put("listOwner", listOwner);
			model.put("totalAccount", listOwner.size());
		} else {
			model.put("totalAccount", 0);
		}
		return "accountowner";
	}

	// get detail owner by id
	@RequestMapping(path = "/owner/detail/{id}", method = RequestMethod.GET)
	public String accountOwnerDetail(Map<String, Object> model,@PathVariable ("id") Long id) throws Exception {
		Owner owner = ownerService.getById(id);
		if (owner != null) {
			model.put("id", id);
			model.put("name", owner.getName());
			model.put("phonenumber", owner.getPhone());
			model.put("address", owner.getAddress());
		}
		
		
		return "accountownerdetail";
	}

	// edit owner by id
	@RequestMapping(path = "/owner/edit/{id}", method = RequestMethod.GET)
	public String editAccountOwner(Map<String, Object> model,@PathVariable("id") Long id) throws Exception {
		Owner owner = ownerService.getById(id);
		if (owner != null) {
			model.put("id", id);
			model.put("name", owner.getName());
			model.put("phonenumber", owner.getPhone());
			model.put("address", owner.getAddress());
		}
		return "editowner";
	}

	// save owner by id
	@RequestMapping(path = "/owner/edit/{id}", method = RequestMethod.POST)
	public String saveAccountOwner(Map<String, Object> model, @PathVariable("id") Long id,@RequestParam("name") String name, @RequestParam("phone") String phone, @RequestParam("address") String address) throws Exception {
		Owner owner = ownerService.getById(id);
		owner.setName(name);
		owner.setPhone(phone);
		owner.setAddress(address);
		ownerService.update(owner);

		Owner owner2 = ownerService.getById(id);
		if (owner2 != null) {
			model.put("id", id);
			model.put("name", owner2.getName());
			model.put("phonenumber", owner2.getPhone());
			model.put("address", owner2.getAddress());
		}
		
		return "editowner";
	}
}
