package com.cts.deliverymodule.dto;

import java.util.UUID;

import com.cts.deliverymodule.enums.DeliveryStatus;


public class DeliveryDetails {
	private UUID deliveryId;
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
	
	/**
	 * @param deliveryId
	 * @param deliveryStatus
	 * @param assignedAgent
	 */
	public DeliveryDetails(UUID deliveryId, DeliveryStatus deliveryStatus) {
		this.deliveryId = deliveryId;
		this.deliveryStatus = deliveryStatus;
	}
	
}
