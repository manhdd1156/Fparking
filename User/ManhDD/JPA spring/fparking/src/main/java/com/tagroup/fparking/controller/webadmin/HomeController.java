package com.tagroup.fparking.controller.webadmin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tagroup.fparking.service.AdminService;
import com.tagroup.fparking.service.DriverService;
import com.tagroup.fparking.service.ParkingService;
import com.tagroup.fparking.service.domain.Admin;
import com.tagroup.fparking.service.domain.Driver;
import com.tagroup.fparking.service.domain.Parking;

@Controller
public class HomeController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private ParkingService parkingService;
	
	@Autowired
	private DriverService driverService;
	
	@RequestMapping(path = "", method = RequestMethod.GET)
	public String home(Map<String, Object> model) {
		List<Driver> listDriver = driverService.getAll();
		List<Parking> listParking = parkingService.getAll();
		
		if (listDriver != null && listDriver.size() > 0) {
			model.put("totalAccountDriver", listDriver.size());
		} else {
			model.put("totalAccountDriver", 0);
		}
		
		if (listDriver != null && listDriver.size() > 0) {
			model.put("totalAccountParking", listParking.size());
		} else {
			model.put("totalAccountParking", 0);
		}
			
		return "home";
	}

	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(path = "/home", method = RequestMethod.GET)
	public String fparkingLogoClick(Map<String, Object> model) {
		List<Driver> listDriver = driverService.getAll();
		List<Parking> listParking = parkingService.getAll();
		
		if (listDriver != null && listDriver.size() > 0) {
			model.put("totalAccountDriver", listDriver.size());
		} else {
			model.put("totalAccountDriver", 0);
		}
		
		if (listDriver != null && listDriver.size() > 0) {
			model.put("totalAccountParking", listParking.size());
		} else {
			model.put("totalAccountParking", 0);
		}
		return "home";
	}

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String login(Map<String, Object> model, @ModelAttribute Admin admin) {
		try {
		Admin admin2 = adminService.checklogin(admin);
		System.out.println(admin2.getEmail());
		}catch(Exception e) {
			model.put("messError", "Tên đăng nhập hoặc mật khẩu không đúng!");
			return "login";
		}
		
		List<Driver> listDriver = driverService.getAll();
		List<Parking> listParking = parkingService.getAll();
		
		if (listDriver != null && listDriver.size() > 0) {
			model.put("totalAccountDriver", listDriver.size());
		} else {
			model.put("totalAccountDriver", 0);
		}
		
		if (listDriver != null && listDriver.size() > 0) {
			model.put("totalAccountParking", listParking.size());
		} else {
			model.put("totalAccountParking", 0);
		}
		return "home";	
	}	
}