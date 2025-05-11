package com.cts.paymentservice.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public class CustomerDetailRequest {
	
	@NotNull(message = "Customer ID must not be Null")
	private UUID customerId;

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
	
	
}
