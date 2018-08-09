package com.tagroup.fparking.controller.webadmin;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tagroup.fparking.dto.DriverFineDTO;
import com.tagroup.fparking.service.BookingService;
import com.tagroup.fparking.service.DriverService;
import com.tagroup.fparking.service.DriverVehicleService;
import com.tagroup.fparking.service.FineService;
import com.tagroup.fparking.service.OwnerService;
import com.tagroup.fparking.service.ParkingService;
import com.tagroup.fparking.service.TariffService;
import com.tagroup.fparking.service.VehicleService;
import com.tagroup.fparking.service.domain.Booking;
import com.tagroup.fparking.service.domain.Driver;
import com.tagroup.fparking.service.domain.Fine;
import com.tagroup.fparking.service.domain.Owner;
import com.tagroup.fparking.service.domain.Parking;
import com.tagroup.fparking.service.domain.Tariff;
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
	@Autowired
	private OwnerService ownerService;
	@Autowired
	private TariffService tariffService;
	@Autowired
	private BookingService bookingService;

	// Management Drivers Account

	// get all account
	@RequestMapping(path = "/driver", method = RequestMethod.GET)

	public String accountDriver(Map<String, Object> model) throws Exception {
		List<Driver> listDriver;
		try {
			listDriver = driverService.getByStatus(1);
		} catch (Exception e) {
			model.put("messError", "Đã có lỗi xảy ra với hệ thống. Vui lòng thử lại!");
			return "error";
		}
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
		List<Driver> listDriver;
		try {
			listDriver = driverService.getByStatus(0);
		} catch (Exception e) {
			model.put("messError", "Đã có lỗi xảy ra với hệ thống. Vui lòng thử lại!");
			return "error";
		}
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
		NumberFormat currencyVN = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
		Driver driver;
		try {
			driver = driverService.getById(id);
		} catch (Exception e) {
			return "404";
		}
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
		double totalPriceFine = 0;
		ArrayList<Map<String, Object>> arrayListdriverVehiclet = new ArrayList<>();
		for (DriverFineDTO driverFineDTO : dfList) {
			HashMap<String, Object> m = new HashMap<>();

			m.put("dateFine", driverFineDTO.getDate());
			m.put("licenseplate", driverFineDTO.getLicenseplate());
			m.put("address", driverFineDTO.getNameParking());
			m.put("status", driverFineDTO.getStatus());
			m.put("priceFine", currencyVN.format(driverFineDTO.getPrice()));

			if (driverFineDTO.getStatus() == "Chưa thu") {
				totalPriceFine = totalPriceFine + driverFineDTO.getPrice();
			}

			arrayListdriverVehiclet.add(m);
		}
		model.put("driverFine", arrayListdriverVehiclet);
		model.put("totalPriceFine", currencyVN.format(totalPriceFine));

		return "acountdriverdetail";
	}

	// block account by id
	@RequestMapping(path = "/driver/blockaccount/{id}", method = RequestMethod.GET)
	public String blockAccountDriver(Map<String, Object> model, @PathVariable Long id) throws Exception {
		Driver driver;
		try {
			driver = driverService.getById(id);
		} catch (Exception e) {
			return "404";
		}
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
		Driver driver;
		try {
			driver = driverService.getById(id);
		} catch (Exception e) {
			return "404";
		}
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
		Driver driver;
		try {
			driver = driverService.getById(id);
		} catch (Exception e) {
			return "404";
		}
		model.put("id", id);
		model.put("name", driver.getName());
		model.put("phonenumber", driver.getPhone());
		return "editdriver";
	}

	// save account driver by id
	@RequestMapping(path = "/driver/editaccount/{id}", method = RequestMethod.POST)
	public String saveAccountDriver(Map<String, Object> model, @PathVariable("id") Long id,
			@RequestParam("name") String name, @RequestParam("phone") String phone) throws Exception {
		Driver driver;
		try {
			driver = driverService.getById(id);
		} catch (Exception e) {
			return "404";
		}
		driver.setName(name);
		driver.setPhone(phone);
		try {
			driverService.update(driver);
		} catch (Exception e) {
			model.put("messError", "Sửa không thành công!");
			return "editdriver";
		}

		Driver driver2;
		try {
			driver2 = driverService.getById(id);
		} catch (Exception e) {
			model.put("messError", "Đã có lỗi xảy ra với hệ thống. Vui lòng thử lại!");
			return "error";
		}
		model.put("id", id);
		model.put("name", driver.getName());
		model.put("phonenumber", driver.getPhone());

		model.put("messSuss", "Sửa thành công!");
		return "editdriver";
	}

	// Management Parking Account

	// get all account parking by id with status =1
	@RequestMapping(path = "/parking", method = RequestMethod.GET)
	public String accountParking(Map<String, Object> model) throws Exception {
		NumberFormat currencyVN = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
		List<Parking> listParking;
		try {
			listParking = parkingService.getByStatus(1);
		} catch (Exception e) {
			model.put("messError", "Đã có lỗi xảy ra với hệ thống. Vui lòng thử lại!");
			return "error";
		}
		ArrayList<Map<String, Object>> arrayListParking = new ArrayList<>();
		double totalDeposit = 0;
		if (listParking != null && listParking.size() > 0) {
			for (Parking parking : listParking) {
				HashMap<String, Object> m = new HashMap<>();
				m.put("id", parking.getId());
				m.put("address", parking.getAddress());
				m.put("currentspace", parking.getCurrentspace());
				m.put("totalspace", parking.getTotalspace());
				if (parking.getDeposits() % 1 == 0) {
					m.put("deposits", currencyVN.format((int) parking.getDeposits()));
					totalDeposit += (int) parking.getDeposits();
				} else {
					m.put("deposits", currencyVN.format(parking.getDeposits()));
					totalDeposit += parking.getDeposits();
				}

				arrayListParking.add(m);
			}
			model.put("listParking", arrayListParking);
			model.put("totalAccount", listParking.size());
		} else {
			model.put("totalAccount", 0);
		}
		model.put("totalDeposit", currencyVN.format(totalDeposit));
		return "accountparking";
	}

	// get all blockaccount parking by id with status = 0
	@RequestMapping(path = "/parking/block", method = RequestMethod.GET)
	public String blockAccountParking(Map<String, Object> model) throws Exception {
		NumberFormat currencyVN = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
		List<Parking> listParking;
		try {
			listParking = parkingService.getByStatus(0);
		} catch (Exception e) {
			model.put("messError", "Đã có lỗi xảy ra với hệ thống. Vui lòng thử lại!");
			return "error";
		}

		ArrayList<Map<String, Object>> arrayListParking = new ArrayList<>();
		double totalDeposit = 0;
		if (listParking != null && listParking.size() > 0) {
			for (Parking parking : listParking) {
				HashMap<String, Object> m = new HashMap<>();
				m.put("id", parking.getId());
				m.put("address", parking.getAddress());
				m.put("currentspace", parking.getCurrentspace());
				m.put("totalspace", parking.getTotalspace());
				if (parking.getDeposits() % 1 == 0) {
					m.put("deposits", currencyVN.format((int) parking.getDeposits()));
					totalDeposit += (int) parking.getDeposits();
				} else {
					m.put("deposits", currencyVN.format(parking.getDeposits()));
					totalDeposit += parking.getDeposits();
				}

				arrayListParking.add(m);
			}
			model.put("listParking", arrayListParking);
			model.put("totalAccount", listParking.size());
		} else {
			model.put("totalAccount", 0);
		}
		model.put("totalDeposit", currencyVN.format(totalDeposit));
		return "blockaccountparking";
	}

	// get detail parking by id
	@RequestMapping(path = "/parking/detail/{id}", method = RequestMethod.GET)
	public String accountDriverDetail(Map<String, Object> model, @PathVariable("id") Long id) throws Exception {
		NumberFormat currencyVN = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
		String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
		Parking parking;
		List<Tariff> listTariff;
		List<Fine> listFine;
		List<Booking> listBooking;
		try {
			parking = parkingService.getById(id);
			listTariff = tariffService.getAll();
			listFine = fineService.getAll();
			listBooking = bookingService.getAll();
		} catch (Exception e) {
			return "404";
		}
		// tab information parking
		model.put("idOwner", parking.getOwner().getId());
		model.put("nameOwner", parking.getOwner().getName());
		model.put("phoneOwner", parking.getOwner().getPhone());
		model.put("addressParking", parking.getAddress());
		model.put("timeoc", parking.getTimeoc());
		model.put("totalSpace", parking.getTotalspace());
		model.put("latitude", parking.getLatitude());
		model.put("longitude", parking.getLongitude());
		model.put("deposits", currencyVN.format(parking.getDeposits()));

		// tab information price by type car
		ArrayList<Map<String, Object>> arrayListVehicletype = new ArrayList<>();
		for (Tariff tariff : listTariff) {
			HashMap<String, Object> m = new HashMap<>();
			if (tariff.getParking().getId() == id) {
				m.put("typeCar", tariff.getVehicletype().getType());
				m.put("priceType", currencyVN.format(tariff.getPrice()));
				arrayListVehicletype.add(m);
			}
		}
		model.put("arrayListVehicletype", arrayListVehicletype);
		// tab fine history
		ArrayList<Map<String, Object>> arrayListFine = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
		for (Fine fine : listFine) {
			HashMap<String, Object> m = new HashMap<>();
			if (fine.getParking().getId() == id && fine.getType() == 1) {
				m.put("dateFine", sdf.format(fine.getDate()));
				m.put("licenseplate", fine.getDrivervehicle().getVehicle().getLicenseplate());
				m.put("typeCar", fine.getDrivervehicle().getVehicle().getVehicletype().getType());
				m.put("priceFine", currencyVN.format(fine.getPrice()));
				arrayListFine.add(m);
			}
		}
		model.put("arrayListFine", arrayListFine);
		// tab transaction history
		ArrayList<Map<String, Object>> arrayListBooking = new ArrayList<>();
		for (Booking booking : listBooking) {
			HashMap<String, Object> m = new HashMap<>();
			if (booking.getParking().getId() == id && booking.getTimeout() != null && booking.getTimeout() != null) {
				m.put("timein", sdf.format(booking.getTimein()));
				m.put("timeout", sdf.format(booking.getTimeout()));
				m.put("type", booking.getDrivervehicle().getVehicle().getVehicletype().getType());
				double totalTime = (booking.getTimeout().getTime() - booking.getTimein().getTime()) / (60 * 60 * 1000);
				if (totalTime % 1 == 0) {
					m.put("totalTime", (int) totalTime);
				} else {
					m.put("totalTime", (int) totalTime + 1);
				}
				m.put("address", booking.getParking().getAddress());
				m.put("licenseplate", booking.getDrivervehicle().getVehicle().getLicenseplate());

				if (booking.getPrice() % 1 == 0) {
					m.put("price", currencyVN.format((int) booking.getPrice()));
				} else {
					m.put("price", currencyVN.format((booking.getPrice())));
				}

				if (booking.getTotalfine() % 1 == 0) {
					m.put("totalFine", currencyVN.format((int) booking.getTotalfine()));
				} else {
					m.put("totalFine", currencyVN.format(booking.getTotalfine()));
				}

				m.put("commssion", booking.getComission());

				if (booking.getAmount() % 1 == 0) {
					m.put("amount", currencyVN.format((int) booking.getAmount()));
				} else {
					m.put("amount", currencyVN.format(booking.getAmount()));
				}
				arrayListBooking.add(m);
			}
		}
		model.put("arrayListBooking", arrayListBooking);
		return "accountparkingdetail";
	}

	// block account parking by id
	@RequestMapping(path = "/parking/blockaccount/{id}", method = RequestMethod.GET)
	public String blockAccountParking(Map<String, Object> model, @PathVariable Long id) throws Exception {
		Parking parking;
		try {
			parking = parkingService.getById(id);
		} catch (Exception e) {
			return "404";
		}
		parking.setStatus(0);
		parkingService.update(parking);
		List<Parking> listParking;
		try {
			listParking = parkingService.getByStatus(1);
		} catch (Exception e) {
			model.put("messError", "Đã có lỗi xảy ra với hệ thống. Vui lòng thử lại!");
			return "error";
		}

		return "redirect:/account/parking";
	}

	// unblock account parking by id
	@RequestMapping(path = "/parking/unblockaccount/{id}", method = RequestMethod.GET)
	public String unBlockAccountParking(Map<String, Object> model, @PathVariable Long id) throws Exception {
		Parking parking;
		try {
			parking = parkingService.getById(id);
			parking.setStatus(1);
			parkingService.update(parking);
		} catch (Exception e) {
			return "404";
		}

		return "redirect:/account/parking/block";
	}

	// go to edit form parking
	@RequestMapping(path = "/parking/edit/{id}", method = RequestMethod.GET)
	public String getEditParkingForm(Map<String, Object> model, @PathVariable Long id) throws Exception {
		Parking parking;
		try {
			parking = parkingService.getById(id);
		} catch (Exception e) {
			return "404";
		}

		model.put("address", parking.getAddress());
		model.put("longitude", parking.getLongitude());
		model.put("latitude", parking.getLatitude());
		model.put("timeoc", parking.getTimeoc());
		model.put("totalSpace", parking.getTotalspace());
		if (parking.getDeposits() % 1 == 0) {
			model.put("deposits", (int) parking.getDeposits());
		} else {
			model.put("deposits", parking.getDeposits());
		}
		return "editparking";
	}

	// save edit form parking by id
	@RequestMapping(path = "/parking/edit/{id}", method = RequestMethod.POST)
	public String saveEditParkingForm(Map<String, Object> model, @PathVariable Long id,
			@RequestParam("address") String address, @RequestParam("longitude") Double longitude,
			@RequestParam("latitude") Double latitude, @RequestParam("timeoc") String timeoc,
			@RequestParam("totalSpace") Integer totalSpace, @RequestParam("deposits") Double deposits)
			throws Exception {

		Parking parking;
		try {
			parking = parkingService.getById(id);
		} catch (Exception e) {
			return "404";
		}

		parking.setAddress(address);
		parking.setLatitude(latitude + "");
		parking.setLongitude(longitude + "");
		parking.setTimeoc(timeoc);
		parking.setTotalspace(totalSpace);
		parking.setDeposits(deposits);
		try {
			parkingService.update(parking);
		} catch (Exception e) {
			model.put("messError", "Sửa không thành công!");
			return "editparking";
		}
		model.put("messSuss", "Sửa thành công!");

		Parking parking2;
		try {
			parking2 = parkingService.getById(id);
		} catch (Exception e) {
			return "404";
		}

		model.put("address", parking2.getAddress());
		model.put("longitude", parking2.getLongitude());
		model.put("latitude", parking2.getLatitude());
		model.put("timeoc", parking2.getTimeoc());
		model.put("totalSpace", parking2.getTotalspace());
		if (parking.getDeposits() % 1 == 0) {
			model.put("deposits", (int) parking2.getDeposits());
		} else {
			model.put("deposits", parking2.getDeposits());
		}

		return "editparking";
	}

	// Manager Account Owner

	// get account owner by id
	@RequestMapping(path = "/owner", method = RequestMethod.GET)
	public String accountOwner(Map<String, Object> model) throws Exception {
		List<Owner> listOwner;
		try {
			listOwner = ownerService.getAll();
		} catch (Exception e) {
			model.put("messError", "Đã có lỗi xảy ra với hệ thống. Vui lòng thử lại!");
			return "error";
		}

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
	public String accountOwnerDetail(Map<String, Object> model, @PathVariable("id") Long id) throws Exception {
		NumberFormat currencyVN = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
		Owner owner;
		try {
			owner = ownerService.getById(id);
		} catch (Exception e) {
			return "404";
		}
		if (owner != null) {
			model.put("id", id);
			model.put("name", owner.getName());
			model.put("phonenumber", owner.getPhone());
			model.put("address", owner.getAddress());
		}

		List<Parking> respone;
		try {
			respone = parkingService.getByOwnerID(id);
		} catch (Exception e) {
			return "404";
		}
		ArrayList<Map<String, Object>> arrayListParking = new ArrayList<>();
		double totalDeposit = 0;
		for (Parking parking : respone) {
			HashMap<String, Object> m = new HashMap<>();
			m.put("id", parking.getId());
			m.put("address", parking.getAddress());
			m.put("latitude", parking.getLatitude());
			m.put("longitude", parking.getLongitude());
			m.put("currentspace", parking.getCurrentspace());
			m.put("totalspace", parking.getTotalspace());
			m.put("timeoc", parking.getTimeoc());
			m.put("latitude", parking.getLatitude());

//			JSONObject jo = new JSONObject();
//			JSONArray jsonArray = new JSONArray(parking.getImage());
//			for (int i = 0; i < jsonArray.length(); i++) {
//				jo = jsonArray.getJSONObject(i);
//				m.put("image" + i + 1, jo.getString("link"));
//			}

			m.put("desposits", currencyVN.format(parking.getDeposits()));
			totalDeposit += parking.getDeposits();
			if (parking.getStatus() == 1) {
				m.put("status", "Hoạt động");
			} else if (parking.getStatus() == 2) {
				m.put("status", "Bị khóa");
			}
			arrayListParking.add(m);
		}
		model.put("totalDeposit", currencyVN.format(totalDeposit));
		model.put("toatalParking", respone.size());
		model.put("arrayListParking", arrayListParking);
		return "accountownerdetail";
	}

	// edit owner by id
	@RequestMapping(path = "/owner/edit/{id}", method = RequestMethod.GET)
	public String editAccountOwner(Map<String, Object> model, @PathVariable("id") Long id) throws Exception {
		Owner owner;
		try {
			owner = ownerService.getById(id);
		} catch (Exception e) {
			return "404";
		}
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
	public String saveAccountOwner(Map<String, Object> model, @PathVariable("id") Long id,
			@RequestParam("name") String name, @RequestParam("phone") String phone,
			@RequestParam("address") String address) throws Exception {
		Owner owner;
		try {
			owner = ownerService.getById(id);
		} catch (Exception e) {
			return "404";
		}
		owner.setName(name);
		owner.setPhone(phone);
		owner.setAddress(address);
		try {
			ownerService.update(owner);
		} catch (Exception e) {
			model.put("messError", "Sửa không thành công!");
			return "editowner";
		}

		Owner owner2;
		try {
			owner2 = ownerService.getById(id);
		} catch (Exception e) {
			return "404";
		}
		if (owner2 != null) {
			model.put("id", id);
			model.put("name", owner2.getName());
			model.put("phonenumber", owner2.getPhone());
			model.put("address", owner2.getAddress());
		}
		model.put("messSuss", "Sửa thành công!");
		return "editowner";
	}

	// management account admin

	// go to form edit
	@RequestMapping(path = "/admin/editaccount/{id}", method = RequestMethod.GET)
	public String editAccountAdmin(Map<String, Object> model, @PathVariable Long id) throws Exception {
		model.put("username", "admin");
		return "changepass";
	}
}
