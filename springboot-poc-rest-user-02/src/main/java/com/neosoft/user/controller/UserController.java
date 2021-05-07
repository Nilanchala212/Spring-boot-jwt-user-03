package com.neosoft.user.controller;

import java.util.List;

import javax.naming.InvalidNameException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neosoft.user.entity.User;
import com.neosoft.user.exception.InvalidId;
import com.neosoft.user.exception.InvalidName;
import com.neosoft.user.exception.InvalidUser;
import com.neosoft.user.response.UserResponse;
import com.neosoft.user.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@PostMapping(path = "/registeruser")
	public UserResponse registerUser(@Valid @RequestBody User user) {
		String password = user.getPassword();
		String encrypt = passwordEncoder.encode(password);
		user.setPassword(encrypt);

		System.out.println(user);
		if (user.getFirstName() == null || user.getPincode() == null || user.getSurName() == null) {
			throw new InvalidUser("mandatory field should not be empty");
		} else {
			UserResponse userResponse = new UserResponse();
			String message = userService.userRegistration(user);
			userResponse.setMessage(message);
			userResponse.setStatus(HttpStatus.OK);
			return userResponse;
		}

	}

	@GetMapping("/fetch")
	public UserResponse fetchBy(@RequestParam(value = "firstName", required = false) String firstname,
			@RequestParam(value = "surName", required = false) String surname,
			@RequestParam(value = "pincode", required = false) Long pincode) throws InvalidNameException {

		if (firstname == null && surname == null && pincode == null) {
			throw new InvalidName("name should not be null");

		} else {

			UserResponse userResponse = new UserResponse();
			List<User> userlist = userService.findBy(firstname, surname, pincode);
			userResponse.setBody(userlist);
			userResponse.setMessage("this is the  list of user's");
			userResponse.setStatus(HttpStatus.OK);
			return userResponse;
		}
	}

	@PutMapping(path = "/update/{id}")
	public UserResponse updateUser(@RequestBody User user, @PathVariable("id") int userid) {
		if (userid == 0) {
			throw new InvalidId("id should not be null");
		} else {
			UserResponse userResponse = new UserResponse();
			String message = userService.updateUser(user, userid);
			userResponse.setMessage(message);
			return userResponse;
		}
	}

	@GetMapping("/fetchuserbyid/{id}")
	public UserResponse fetchuserById(@PathVariable("id") int id) {
		System.out.println(id);
		if (id == 0) {
			throw new InvalidId("id should not be null");
		} else {
			User user = userService.featchUserById(id);
			UserResponse userResponse = new UserResponse();
			System.out.println(user);
			userResponse.setBody(user);
			userResponse.setStatus(HttpStatus.OK);
			return userResponse;
		}

	}

}
