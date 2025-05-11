package com.cts.menumodule.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public class RestaurantDetailRequest {
	
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
