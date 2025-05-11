package com.cts.orderservice.dto;

import java.util.UUID;

import com.cts.orderservice.enums.OrderStatus;

import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) for updating the status of an order.
 * Contains information about the order ID and the new status.
 * 
 * @Author Bhimisetty Renu Sai Ritvik
 * @Since 27 Feb 2025
 */
public class UpdateStatusRequest {
	
	@NotNull(message = "Order ID must not be Empty")
	private UUID orderId;
	
	@NotNull(message = "Order Status must not be Empty")
	private OrderStatus status;
	
	public UpdateStatusRequest() {
		
	}
	
	/**
	 * @param orderId
	 * @param status
	 */
	public UpdateStatusRequest(UUID orderId, OrderStatus status) {
		this.orderId = orderId;
		this.status = status;
	}
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
	 * @return the status
	 */
	public OrderStatus getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
}
