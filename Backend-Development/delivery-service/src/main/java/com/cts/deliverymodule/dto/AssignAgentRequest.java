package com.cts.deliverymodule.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) for assigning a delivery agent.
 * Contains information about the order, customer, and expected time of arrival.
 * 
 * This DTO is used in the request body for the endpoint that assigns a delivery agent.
 * 
 * @author Jeswin Joseph J
 * @since 26th Feb 2025
 */
public class AssignAgentRequest {

	@NotNull(message = "Order ID must not be Null")
	private UUID orderId;
	
	@NotNull(message = "Customer ID must not be Null")
	private UUID customerId;

	@NotNull(message = "Restaurant ID must not be Null")
	private UUID restaurantId;

	
	/**
	 * @return the orderId
	 */
	public UUID getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(UUID orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the customerId
	 */
	public UUID getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(UUID customerId) {
		this.customerId = customerId;
	}
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
