package com.cts.orderservice.dto;

import java.util.UUID;

public class UserDetails {
	private UUID userId;
	private String name;
	private String contact;
	private String address;
	
	public UserDetails() {}

	/**
	 * @return the userId
	 */
	public UUID getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(UUID userId) {
		this.userId = userId;
	}

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
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * @param contact the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
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
	 * @param userId
	 * @param name
	 * @param contact
	 * @param address
	 */
	public UserDetails(UUID userId, String name, String contact, String address) {
		this.userId = userId;
		this.name = name;
		this.contact = contact;
		this.address = address;
	}

	@Override
	public String toString() {
		return "UserDetails [userId=" + userId + ", name=" + name + ", contact=" + contact + ", address=" + address
				+ "]";
	}
	
	
	

}
