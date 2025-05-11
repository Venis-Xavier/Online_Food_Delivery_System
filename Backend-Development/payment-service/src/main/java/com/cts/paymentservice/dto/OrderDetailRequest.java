package com.cts.paymentservice.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public class OrderDetailRequest {
	
	@NotNull(message = "Order ID must not be Null")
	private UUID orderId;

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
	
	
}
