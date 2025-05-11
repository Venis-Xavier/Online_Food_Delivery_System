package com.cts.paymentservice.model;

import java.util.UUID;

import com.cts.paymentservice.enums.PaymentMethod;
import com.cts.paymentservice.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a Payment module in the online food delivery system.
 * This entity is mapped to "payments" table.
 * 
 * @author Venkata Ramakrishna Kambhampati
 * @since 25 Feb 2025
 */

@Entity
@Table(name = "payment")
public class Payment {
    
	/**
     * The unique identifier for the payment.
     * Uses UUID as the primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID paymentId;
    
    /**
     * for the order.
     * Must be provided and unique.
     */
    @Column(unique = true, nullable = false)
    private UUID orderId;
    
    /**
     * Unique identifier for the customer.
     * Must be provided.
     */
    @Column(nullable = false)
    private UUID customerId;
    
    /**
     * Unique identifier for the agent.
     * Must be provided.
     */
    @Column(nullable = false)
    private UUID agentId;
    
    /**
     * Unique identifier for the restaurant.
     * Must be provided.
     */
    @Column(nullable = false)
    private UUID restaurantId;
    
    /**
     * The payment method used.
     * Enum representing the payment method (e.g., CREDIT_CARD, DEBIT_CARD).
     */
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    
    /**
     * The amount of the payment.
     * Must be provided.
     */
    @Column(nullable = false)
    private double amount;
    
    /**
     * The status of the payment.
     * Enum representing the payment status, default is PENDING.
     */
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;
    
    public Payment() {
    	
    }
    
    

	/**
	 * @param orderId
	 * @param customerId
	 * @param agentId
	 * @param restaurantId
	 * @param paymentMethod
	 * @param amount
	 */
	public Payment(UUID orderId, UUID customerId, UUID agentId, UUID restaurantId, 
			PaymentMethod paymentMethod,double amount) {

		this.orderId = orderId;
		this.customerId = customerId;
		this.agentId = agentId;
		this.restaurantId = restaurantId;
		this.paymentMethod = paymentMethod;
		this.amount = amount;
	}



	/**
	 * @return the paymentId
	 */
	public UUID getPaymentId() {
		return paymentId;
	}

	/**
	 * @param paymentId the paymentId to set
	 */
	public void setPaymentId(UUID paymentId) {
		this.paymentId = paymentId;
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

	/**
	 * @return the paymentStatus
	 */
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * @param paymentStatus the paymentStatus to set
	 */
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
    
}
