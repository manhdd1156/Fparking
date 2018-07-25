package com.tagroup.fparking.dto;

public class LoginResponseDTO {
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "LoginResponseDTO [token=" + token + "]";
	}

}
