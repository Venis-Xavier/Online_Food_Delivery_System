package com.cts.userservice.dto;

import com.cts.userservice.enums.Roles;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class LoginRequest {
	
	@Email(message = "User Name must be a valid Email ID")
	private String email;
	
	@NotEmpty(message = "Password must not be Empty")
	private String password;
	
	@NotNull(message = "Role not be Empty")
	private Roles role;
	

	
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
