package com.tagroup.fparking.service;

import com.tagroup.fparking.dto.LoginRequestDTO;
import com.tagroup.fparking.dto.LoginResponseDTO;

public interface LoginService {
	public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) throws Exception;
}
