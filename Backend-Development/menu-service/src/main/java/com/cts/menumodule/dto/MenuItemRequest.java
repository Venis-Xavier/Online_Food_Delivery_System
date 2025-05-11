package com.cts.menumodule.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public class MenuItemRequest {
	
	@NotNull(message = "Item ID must not be Empty")
	private UUID itemId;

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
