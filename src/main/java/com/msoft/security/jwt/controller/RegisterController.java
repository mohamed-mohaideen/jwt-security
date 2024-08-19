package com.msoft.security.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.msoft.security.jwt.dao.UserRepository;
import com.msoft.security.jwt.model.User;

@RestController
public class RegisterController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@PostMapping("/signup")
	public String registerUser(@RequestBody User user) {
	user.setPassword(encoder.encode(user.getPassword()));
	userRepo.save(user);
		return "User Registered Successfully";
	}

}
