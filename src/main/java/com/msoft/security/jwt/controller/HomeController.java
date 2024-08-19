package com.msoft.security.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping("/home")
	public String handleHome() {
		return "This is a default home page.";
	}
	
	@GetMapping("/user/home")
	public String handleUserHome() {
		return "This is an user home page.";
	}
	
	@GetMapping("/admin/home")
	public String handleAdminHome() {
		return "This is an admin home page.";
	}
	
}
