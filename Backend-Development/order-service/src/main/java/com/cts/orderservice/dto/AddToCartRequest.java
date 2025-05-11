package com.cts.orderservice.dto;

import java.util.UUID;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) for adding an item to the cart.
 * Contains information about the order ID, item ID, and quantity.
 * 
 * @Author Bhimisetty Renu Sai Ritvik
 * @Since 27 Feb 2025
 */
public class AddToCartRequest {
	
//	@NotNull(message = "Customer ID must not be Empty")
//	private UUID customerId;
	
	@NotNull(message = "Item ID must not be Empty")
	private UUID itemId;

	@NotNull(message = "Restaurant ID must not be Empty")
	private UUID restaurantId;

	@Min(value = 1, message = "Quantity must be greater than or equal to 1")
	private int quantity;
	
	
	/**
	 * @return the customerId
	 */
//	public UUID getCustomerId() {
//		return customerId;
//	}
//	/**
//	 * @param customerId the customerId to set
//	 */
//	public void setCustomerId(UUID customerId) {
//		this.customerId = customerId;
//	}
	/**
	 * @return the itemId
	 */
	public UUID getItemId() {
		return itemId;
	}
	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(UUID itemId) {
		this.itemId = itemId;
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
	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
