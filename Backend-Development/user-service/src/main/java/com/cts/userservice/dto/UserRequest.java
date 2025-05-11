package com.cts.userservice.dto;

import com.cts.userservice.enums.Roles;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UserRequest {
	@NotEmpty(message = "Name must not be empty")
	private String name;
	
	@NotNull(message = "Email must not be null")
	@Email(message = "Invalid Email Address")
	private String email;
	
	@Max(value = 9999999999L)
	@Min(value = 1000000000L)
	private Long phone;
	

	@NotEmpty(message = "Address must not be empty")
	private String address;
	
//	@NotNull(message = "Role must not be null")
	private Roles role;
	
	@NotEmpty(message = "Password must not be empty")
	private String password;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the phone
	 */
	public Long getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the role
	 */
	public Roles getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(Roles role) {
		this.role = role;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
