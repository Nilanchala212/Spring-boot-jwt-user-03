package com.neosoft.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neosoft.user.entity.User;
import com.neosoft.user.exception.InvalidId;
import com.neosoft.user.response.UserResponse;
import com.neosoft.user.service.IUserService;
import com.neosoft.user.util.JwtUtil;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private IUserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@DeleteMapping("/remove/hard/{userId}")
	public UserResponse deleteUser(@PathVariable("userId") int userId) {
		System.out.println(userId);
		if (userId == 0) {
			throw new InvalidId("Id should not be Null");
		} else {
			String message = userService.removeUser(userId);
			UserResponse userResponse = new UserResponse();
			userResponse.setMessage(message);
			userResponse.setStatus(HttpStatus.OK);
			return userResponse;
		}
	}

	@GetMapping("/fetchuserbydob")
	public UserResponse sortUserByDob() {
		List<User> userlist = userService.featchUserByDob();
		UserResponse userResponse = new UserResponse();
		userResponse.setBody(userlist);
		return userResponse;
	}

	@GetMapping("/fetchuserbydoj")
	public UserResponse sortUserByDoj() {
		List<User> userlist = userService.featchUserByDoj();
		UserResponse userResponse = new UserResponse();
		userResponse.setBody(userlist);
		return userResponse;
	}

	@DeleteMapping("/soft/remove/{id}")
	public int softDelete(@PathVariable("id") int id) {
		if (id == 0) {
			throw new InvalidId("id should not be null");
		} else {
			int message = userService.softDelete(id);
			return message;
		}

	}

	@GetMapping("/fetchalluser")
	public List<User> fetchAllUser() {
		return userService.featchAllUser();
	}

	@PostMapping("/authenticate")
	public String generateToken(@RequestBody User authRequest) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		} catch (Exception ex) {
			throw new Exception("inavalid username/password");
		}
		return jwtUtil.generateToken(authRequest.getUsername());
	}

}
