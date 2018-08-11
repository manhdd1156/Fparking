package com.tagroup.fparking.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		if(request.getRequestURI().startsWith("/api/")) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
		}else {
			response.sendRedirect("/login");
		}
	}

}
