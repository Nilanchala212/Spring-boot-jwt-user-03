package com.neosoft.user.entity;

public class Employee {
	private int id;
	private String name;
	private String email;
	private long number;

	public Employee(int id, String name, String email, long number) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.number = number;
	}

	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", email=" + email + ", number=" + number + "]";
	}

}
