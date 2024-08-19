package com.msoft.security.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.msoft.security.jwt.dto.LoginData;
import com.msoft.security.jwt.dto.LoginResponse;
import com.msoft.security.jwt.service.AuthService;

@RestController
public class AuthController {

	@Autowired
	private AuthService authService;
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("/authenticate")
	public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginData request) {
		final UserDetails user = authService.authenticate(request);
		final String token = jwtService.generateToken(user);
		return ResponseEntity.ok(new LoginResponse(token, jwtService.expirationTime()));
	}
	
}
