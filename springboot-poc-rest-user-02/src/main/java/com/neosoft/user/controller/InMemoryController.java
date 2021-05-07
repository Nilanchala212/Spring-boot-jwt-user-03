package com.neosoft.user.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neosoft.user.entity.Employee;

@RestController
public class InMemoryController {

	@GetMapping("/")
	public String login() {
		return "authenticate successfully";
	}

	@GetMapping("/getUsers")
	@PostConstruct
	public List<Employee> getUsers() {

		return Stream.of(new Employee(1, "nilanchala", "nil@gmail.com", 783739213),
				new Employee(2, "raja", "raja@gmail.com", 796582410)).collect(Collectors.toList());
	}

}
