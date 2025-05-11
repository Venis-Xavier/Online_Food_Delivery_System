package com.cts.userservice.dto;

import java.util.UUID;

public class UserDetails {
	private UUID userId;
	private String name;
	private String contact;
	private String address;
	
	public UserDetails() {}
	
	/**
	 * @param customerId
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
	/**
	 * @return the customerId
	 */
	public UUID getUserId() {
		return userId;
	}
	/**
	 * @param customerId the customerId to set
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
	
}
