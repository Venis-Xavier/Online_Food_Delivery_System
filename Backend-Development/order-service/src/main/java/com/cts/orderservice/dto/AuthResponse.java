package com.cts.orderservice.dto;

public class AuthResponse {
	private String email;
    private String userId;
    private String role;
    private boolean valid;
    
	/**
	 * @param email
	 * @param userId
	 * @param role
	 * @param valid
	 */
	public AuthResponse(String email, String userId, String role, boolean valid) {
		this.email = email;
		this.userId = userId;
		this.role = role;
		this.valid = valid;
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
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the valid
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * @param valid the valid to set
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	
}
