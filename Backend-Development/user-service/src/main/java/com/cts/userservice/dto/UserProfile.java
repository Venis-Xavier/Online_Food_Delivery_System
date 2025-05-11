package com.cts.userservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UserProfile {
	@NotEmpty(message = "User Name must not be Empty")
	private String name;

	@NotEmpty(message = "Email must not be Empty")
	private String email;
	
	@Min(value = 1000000000L, message = "Invalid Phone Number")
	@Max(value = 9999999999L, message = "Invalid Phone Number")
	@NotNull(message = "Email must not be Empty")
	private Long phone;

	@NotEmpty(message = "Address must not be Empty")
	private String address;
	
	private String currentPwd;
	private String updatedPwd;
	

	
	/**
	 * @param userName
	 * @param email
	 * @param phone
	 * @param address
	 */
	public UserProfile(String name, String email, Long phone, String address) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}
	/**
	 * @return the userName
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param userName the userName to set
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
	 * @return the currentPwd
	 */
	public String getCurrentPwd() {
		return currentPwd;
	}
	/**
	 * @param currentPwd the currentPwd to set
	 */
	public void setCurrentPwd(String currentPwd) {
		this.currentPwd = currentPwd;
	}
	/**
	 * @return the updatedPwd
	 */
	public String getUpdatedPwd() {
		return updatedPwd;
	}
	/**
	 * @param updatedPwd the updatedPwd to set
	 */
	public void setUpdatedPwd(String updatedPwd) {
		this.updatedPwd = updatedPwd;
	}
}
