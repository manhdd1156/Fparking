package com.tagroup.fparking.controller.webadmin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tagroup.fparking.service.AdminService;
import com.tagroup.fparking.service.domain.Admin;

@Controller
public class HomeController {
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(path = "", method = RequestMethod.GET)
	public String home() {
		return "accountDriver";
	}

	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(path = "/home", method = RequestMethod.GET)
	public String fparkingLogoClick() {
		return "home";
	}

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String login(Map<String, Object> model, @ModelAttribute Admin admin) {
		
		try {
		System.out.println(admin.getUsername() + " |||||||||| " + admin.getPassword());
		Admin admin2 = adminService.checklogin(admin);
		System.out.println(admin2.getEmail());
		}catch(Exception e) {
			model.put("DMRAROI", "Tên đăng nhập hoặc mật khẩu không đúng!");
			return "login";
		}
		return "home";
//		if (userLoginRequestDTO.getUser().equals("admin") && userLoginRequestDTO.getPassword().equals("123")) {
//			return "home";
//		} else if(userLoginRequestDTO.getUser().isEmpty() || userLoginRequestDTO.getPassword().isEmpty()) {
//			model.put("DMRAROI", "Không để trống tên đăng nhập và mật khẩu!");
//		}else {
//			model.put("DMRAROI", "Tên đăng nhập hoặc mật khẩu không đúng!");
//		}
//		return "login";
		
	}	
}