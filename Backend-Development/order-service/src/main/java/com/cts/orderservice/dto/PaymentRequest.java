package com.cts.orderservice.dto;

import java.util.UUID;

import com.cts.orderservice.enums.PaymentMethod;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PaymentRequest {

	@NotNull(message = "Order ID must not be null")
	private UUID orderId;

	@NotNull(message = "Customer ID must not be null")
	private UUID customerId;

	@NotNull(message = "Agent ID must not be null")
    private UUID agentId;

	@NotNull(message = "Restaurant ID must not be null")
    private UUID restaurantId;


	@NotNull(message = "Payment Method must not be null")
    private PaymentMethod paymentMethod;

	@Min(value = 1)
    private double amount;
    
    
    public PaymentRequest() {
    	
    }

	/**
	 * @param orderId
	 * @param customerId
	 * @param agentId
	 * @param restaurantId
	 * @param paymentMethod
	 * @param amount
	 */
	public PaymentRequest(UUID orderId, UUID customerId, UUID agentId, UUID restaurantId, 
			PaymentMethod paymentMethod, double amount) {
		
		this.orderId = orderId;
		this.customerId = customerId;
		this.agentId = agentId;
		this.restaurantId = restaurantId;
		this.paymentMethod = paymentMethod;
		this.amount = amount;
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
	 * @return the paymentMethod
	 */
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @param paymentMethod the paymentMethod to set
	 */
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
    
    
}
