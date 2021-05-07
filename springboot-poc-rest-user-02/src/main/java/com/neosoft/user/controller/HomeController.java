package com.neosoft.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	/*
	 * @Autowired private JwtUtil jwtUtil;
	 * 
	 * @Autowired private AuthenticationManager authenticationManager;
	 */
	@GetMapping("/home")
	public String home() {
		return "This is Home Page";
	}

	@GetMapping("/admin")
	public String admin() {
		return "This is Admin Page";
	}

}
