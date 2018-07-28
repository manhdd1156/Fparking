package com.tagroup.fparking.controller.webadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

	// get parking by Owner
	@PreAuthorize("hasAnyAuthority('STAFF','ADMIN')")
	@RequestMapping(path = "/owner/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getByOwnerId(@PathVariable Long id) throws Exception {

		List<Parking> respone = parkingService.getByOwnerID(id);
		ArrayList<Map<String, Object>> arrayListParking = new ArrayList<>();
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
			JSONObject jo = new JSONObject();
			JSONArray jsonArray = new JSONArray(parking.getImage());
			for (int i = 0; i < jsonArray.length(); i++) {
				jo = jsonArray.getJSONObject(i);
				m.put("image" + i + 1, jo.getString("link"));
			}
			m.put("desposits", parking.getDeposits());
			if (parking.getStatus() == 1) {
				m.put("status", "đang tồn tại");
			} else if (parking.getStatus() == 2) {
				m.put("status", "Bị ban");
			}
			arrayListParking.add(m);
			// }
		}
		return null;

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
		} catch (Exception e) {
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