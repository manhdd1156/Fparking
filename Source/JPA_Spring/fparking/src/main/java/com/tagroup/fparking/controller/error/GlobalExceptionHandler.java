package com.tagroup.fparking.controller.error;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler
	ResponseEntity<?> handleException(APIException apiException, HttpServletRequest request) {
		APIError apiError = new APIError(apiException.getReponseStatus(), apiException.getMsg());
		apiError.setPath(request.getRequestURI());
		apiError.setMethod(request.getMethod());
		System.out.println("bố ở đây rồi");
		return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.getStatus()));
	}
}
