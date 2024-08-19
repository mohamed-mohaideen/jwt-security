package com.msoft.security.jwt.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.msoft.security.jwt.dto.LoginData;
import com.msoft.security.jwt.security.MyUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManager authManager;
	private final MyUserDetails userDetails;
	
	public UserDetails authenticate(final LoginData loginData) {
		Authentication authenticate = authManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginData.username(), loginData.password()));
		if(authenticate.isAuthenticated()) {
			return userDetails.loadUserByUsername(loginData.username());
		} else {
			throw new UsernameNotFoundException("Invalid Credentials");
		}
	}
}
