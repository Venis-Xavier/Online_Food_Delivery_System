package com.cts.orderservice.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public class RemoveCartItemRequest {
	
	@NotNull(message = "Cart ID must not be Empty")
	private UUID cartId;

	/**
	 * @return the cartId
	 */
	public UUID getCartId() {
		return cartId;
	}

	/**
	 * @param cartId the cartId to set
	 */
	public void setCartId(UUID cartId) {
		this.cartId = cartId;
	}
	

}
