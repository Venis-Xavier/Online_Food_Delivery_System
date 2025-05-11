package com.cts.orderservice.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;


/**
 * Data Transfer Object (DTO) for Menu requests.
 * 
 * @author Dabarra Vishnu
 * @since 27 Feb 2025
 */
public class MenuItemRequest {
	
	@NotNull(message = "Item ID must not be Empty")
	private UUID itemId;
	

	public MenuItemRequest() {
		
	}

	/**
	 * @param itemId
	 */
	public MenuItemRequest(UUID itemId) {
		this.itemId = itemId;
	}

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
	
	
}
