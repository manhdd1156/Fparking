package com.tagroup.fparking.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class AuthenticationFilter extends GenericFilterBean {
	@Autowired
	private TokenProvider tokenProvider;;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		try {
			String jwt = getJwtFromRequest((HttpServletRequest) servletRequest);
			if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
				List<GrantedAuthority> authorities = new ArrayList<>();
				Token token = tokenProvider.getInfoFromJWT(jwt);
				UsernamePasswordAuthenticationToken authentication = null;
				authorities.add(new SimpleGrantedAuthority(token.getType()));
				authentication = new UsernamePasswordAuthenticationToken(token, null, authorities);
				authentication.setDetails(
						new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) servletRequest));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
}
