package com.msoft.security.jwt.config;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

	@Value("${security.jwt.secret-key}")
	private String secretKey;

	@Value("${security.jwt.expiration-time}")
	private long jwtExpiration;

	public String generateToken(UserDetails userDetails) {
		Map<String, String> claims = new HashMap<>();
		claims.put("iss", "www.msofttech.com");
		return Jwts.builder()
				.claims(claims)
				.subject(userDetails.getUsername())
				.issuedAt(Date.from(Instant.now()))
				.expiration(Date.from(Instant.now().plusMillis(jwtExpiration)))
				.signWith(generateKey())
				.compact();
	}

	private SecretKey generateKey() {
		byte[] decodedKey = Base64.getDecoder().decode(secretKey);
		return Keys.hmacShaKeyFor(decodedKey);
	}

	public String extractUsername(String jwt) {
		Claims claims = getClaims(jwt);
		return claims.getSubject();
	}
	
	private Claims getClaims(String jwt) {
		return Jwts.parser()
				.verifyWith(generateKey())
				.build()
				.parseSignedClaims(jwt)
				.getPayload();
	}
	
	public boolean isTokenValid(String jwt) {
		Claims claims = getClaims(jwt);
		return claims.getExpiration().after(Date.from(Instant.now()));
	}
	
	public long expirationTime() {
		return this.jwtExpiration;
	}
}
