package com.cts.deliverymodule.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * A Data Transfer Object (DTO) representing delivery details.
 * 
 * @author Jeswin Joseph J
 * @since 10 Mar 2025
 */
public class DeliveryDetail {
	
	@NotNull(message = "Delivery ID must not be Null")
	private UUID deliveryId;

	@NotNull(message = "Agent ID must not be Null")
	private UUID agentId;
	
	
	/**
	 * @param deliveryId
	 * @param agentId
	 */
	public DeliveryDetail(UUID deliveryId, UUID agentId) {
		this.deliveryId = deliveryId;
		this.agentId = agentId;
	}
	
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
	 * @return the agentId
	 */
	public UUID getAgentId() {
		return agentId;
	}
	/**
	 * @param agentId the agentId to set
	 */
	public void setAgentId(UUID agentId) {
		this.agentId = agentId;
	}
}
