package com.tagroup.fparking.controller.error;

import org.springframework.http.HttpStatus;

public class APIException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private HttpStatus reponseStatus;
	private String msg;

	public APIException(HttpStatus status, String message) {
		this.reponseStatus = status;
		this.msg = message;
	}

	public HttpStatus getReponseStatus() {
		return reponseStatus;
	}

	public void setReponseStatus(HttpStatus reponseStatus) {
		this.reponseStatus = reponseStatus;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
