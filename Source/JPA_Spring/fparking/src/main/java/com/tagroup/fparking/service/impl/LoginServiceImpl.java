package com.tagroup.fparking.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tagroup.fparking.controller.error.APIException;
import com.tagroup.fparking.dto.LoginRequestDTO;
import com.tagroup.fparking.dto.LoginResponseDTO;
import com.tagroup.fparking.security.Token;
import com.tagroup.fparking.security.TokenProvider;
import com.tagroup.fparking.service.DriverService;
import com.tagroup.fparking.service.LoginService;
import com.tagroup.fparking.service.OwnerService;
import com.tagroup.fparking.service.StaffService;
import com.tagroup.fparking.service.domain.Admin;
import com.tagroup.fparking.service.domain.Driver;
import com.tagroup.fparking.service.domain.Owner;
import com.tagroup.fparking.service.domain.Staff;

@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private TokenProvider tokenProvider;
	@Autowired
	private DriverService driverService;
	@Autowired
	private StaffService staffService;
	@Autowired
	private OwnerService ownerService;

	@Override
	public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) throws Exception {
		System.out.println(loginRequestDTO);
		Long id;
		switch (loginRequestDTO.getType()) {
		case "ADMIN":
			id = loginByAdmin(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()).getId();
			break;

		case "STAFF":
			
			id = loginByStaff(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()).getId(); 
			break;

		case "DRIVER":
			id = loginByDriver(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()).getId();
			break;

		case "OWNER":
			id = loginByOwner(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()).getId();
			break;

		default:
			return null;
		}

		Token token = new Token();
		token.setId(id);
		token.setType(loginRequestDTO.getType());
		String jwt = tokenProvider.generateToken(token);
		LoginResponseDTO response = new LoginResponseDTO();
		response.setToken(jwt);
		return response;
	}

	private Driver loginByDriver(String phone, String password) throws Exception {
		try {
			Driver d = driverService.findByPhoneAndPassword(phone, password);
			
			return driverService.findByPhoneAndPassword(phone, password);
			
		} catch (Exception e) {
			
			throw new APIException(HttpStatus.UNAUTHORIZED, "Wrong password or username");
		}
	}

	private Staff loginByStaff(String phone, String password) throws Exception {
		try {
			return staffService.findByPhoneAndPassword(phone, password);
		} catch (Exception e) {
			System.out.println(e);
			throw new APIException(HttpStatus.UNAUTHORIZED, "Wrong password or username");
		}
	}

	private Owner loginByOwner(String phone, String password) throws Exception {
		
		try {
			return ownerService.findByPhoneAndPassword(phone, password);
		} catch (Exception e) {
			System.out.println(e);
			throw new APIException(HttpStatus.UNAUTHORIZED, "Wrong password or username");
		}
	}

	private Admin loginByAdmin(String phone, String password) throws Exception {
		return null;
	}

}
