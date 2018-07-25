package com.tagroup.fparking.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class TokenProvider {
	@Value("${security.jwt.expiration-time}")
	private Long jwtExpirationTime;


	@Value("${security.jwt.secret}")
	private String jwtSecret;

	public String generateToken(Token token) throws Exception {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationTime);
		ObjectMapper mapper = new ObjectMapper();
		return Jwts.builder().setSubject(mapper.writeValueAsString(token)).setIssuedAt(new Date()).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public Token getInfoFromJWT(String token) throws Exception{
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(claims.getSubject(), Token.class);
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException
				| IllegalArgumentException ex) {
		}
		return false;
	}
}
