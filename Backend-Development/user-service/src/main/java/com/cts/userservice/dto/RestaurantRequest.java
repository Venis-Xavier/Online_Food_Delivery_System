package com.cts.userservice.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


/**
 * Data Transfer Object (DTO) for Restaurant requests.
 * 
 * @author Dabarra Vishnu
 * @since 10 Mar 2025
 */
public class RestaurantRequest {

	@NotNull(message = "Manager ID must not be Null")
	private UUID managerId;
	
	@NotEmpty(message = "Restaurant Name must not be Empty")
	private String restaurantName;
	
	@NotEmpty(message = "Address must not be Empty")
	private String address;

	public RestaurantRequest(){

	}

	public RestaurantRequest(UUID managerId, String restaurantName, String address) {
		this.managerId = managerId;
		this.restaurantName = restaurantName;
		this.address = address;
	}

	/**
	 * @return the managerId
	 */
	public UUID getManagerId() {
		return managerId;
	}

	/**
	 * @param managerId the managerId to set
	 */
	public void setManagerId(UUID managerId) {
		this.managerId = managerId;
	}

	/**
	 * @return the restaurantName
	 */
	public String getRestaurantName() {
		return restaurantName;
	}

	/**
	 * @param restaurantName the restaurantName to set
	 */
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
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
