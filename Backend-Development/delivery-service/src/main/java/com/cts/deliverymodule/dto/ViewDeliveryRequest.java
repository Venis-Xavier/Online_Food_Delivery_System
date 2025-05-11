package com.cts.deliverymodule.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * A Data Transfer Object (DTO) representing View Delivery by Restaurant Id request.
 * 
 * @author Jeswin Joseph J
 * @since 10 Mar 2025
 */
public class ViewDeliveryRequest {
	
	@NotNull(message = "Restaurant ID must not be Null")
	private UUID restaurantId;

	/**
	 * @return the restaurantId
	 */
	public UUID getRestaurantId() {
		return restaurantId;
	}

	/**
	 * @param restaurantId the restaurantId to set
	 */
	public void setRestaurantId(UUID restaurantId) {
		this.restaurantId = restaurantId;
	}
	
	
}
