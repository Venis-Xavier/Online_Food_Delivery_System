package com.cts.deliverymodule.dto;

import java.util.UUID;

import com.cts.deliverymodule.enums.DeliveryStatus;

import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) for updating the status of a delivery.
 * Contains information about the delivery ID and the new delivery status.
 * 
 * This DTO is used in the request body for the endpoint that updates the delivery status.
 * 
 * @author Jeswin Joseph J
 * @since 26th Feb 2025
 */
public class StatusUpdateRequest {
	
	@NotNull(message = "Delivery ID must not be Null")
	private UUID deliveryId;
	
	@NotNull(message = "Delivery Status must not be Null")
	private DeliveryStatus deliveryStatus;
	
	
	/**
	 * @return the deliveryId
	 */
	public UUID getDeliveryId() {
		return deliveryId;
	}
	/**
	 * @param deliveryId the deliveryId to set
	 */
	public void setDeliveryId(UUID deliveryId) {
		this.deliveryId = deliveryId;
	}
	/**
	 * @return the deliveryStatus
	 */
	public DeliveryStatus getDeliveryStatus() {
		return deliveryStatus;
	}
	/**
	 * @param deliveryStatus the deliveryStatus to set
	 */
	public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	
}
