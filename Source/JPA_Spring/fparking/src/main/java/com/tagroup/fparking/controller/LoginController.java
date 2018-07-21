package com.tagroup.fparking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tagroup.fparking.dto.LoginRequestDTO;
import com.tagroup.fparking.dto.LoginResponseDTO;
import com.tagroup.fparking.service.LoginService;

@RestController
@RequestMapping("/api/login")
public class LoginController {
	@Autowired
	private LoginService loginService;

	@RequestMapping(path = "", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) throws Exception {
		LoginResponseDTO response = loginService.login(loginRequestDTO);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
