package com.tagroup.fparking.controller.webadmin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tagroup.fparking.service.DriverService;
import com.tagroup.fparking.service.domain.Driver;

@Controller
@RequestMapping("/account/driver")
public class AccountDriversController {
	@Autowired
	private DriverService driverService;
	@RequestMapping(path = "", method = RequestMethod.GET)
	public String accountDriver(Map<String, Object> model) {
		List<Driver> listDriver = driverService.getByStatus(1);
		for (int i = 0; i < 100; i++) {
			listDriver.add(listDriver.get(0));
		}
		model.put("listDriver", listDriver);
		model.put("totalAccount", listDriver.size());
		return "accountDriver";
	}


	@RequestMapping(path = "/block", method = RequestMethod.GET)
	public String blockAccountDriver(Map<String, Object> model) {
		List<Driver> listDriver = driverService.getByStatus(0);
		if(listDriver!=null && listDriver.size()>0) {
			model.put("listDriver", listDriver);
			model.put("totalAccount", listDriver.size());
		}else {
			model.put("totalAccount", 0);
		}
		return "blockaccountdriver";
	}
}
