package com.cts.deliverymodule.model;

import java.util.UUID;

import com.cts.deliverymodule.enums.DeliveryStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Represents an order delivery in the online food delivery system.
 * This entity is mapped to "delivery" table.
 * 
 * @author Jeswin Joseph J
 * @since 22 Feb 2025
 */
	
@Entity
@Table(name = "delivery")
public class Delivery {

    /**
     * The unique identifier for the delivery.
     * Uses UUID as the primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID deliveryId;

    /**
     * Order ID reference from Order Service.
     */
    @Column(nullable = false, unique = true)
    private UUID orderId;
    
    /**
     * The customer to whom the order should be delivered.
     */
    @Column(nullable = false)
    private UUID customerId;
    
    /**
     * The restaurant from where the food is collected.
     */
    @Column(nullable = false)
    private UUID restaurantId;

	/**
     * The agent assigned to handle this delivery.
     * Many Deliveries can be assigned to one agent.
     */
    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    /**
     * The status of the delivery.
     * Can be "PENDING", "IN_TRANSIT" or "DELIVERED".
     * 
     * Default Value: {@code PENDING}
     */
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status = DeliveryStatus.PENDING;


    public Delivery() {
    	
    }
    

    /**
	 * @param orderId
	 * @param customerId
	 * @param restaurantId
	 * @param agent
	 */
	public Delivery(UUID orderId, UUID customerId, UUID restaurantId, Agent agent) {
		this.orderId = orderId;
		this.customerId = customerId;
		this.restaurantId = restaurantId;
		this.agent = agent;
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
	 * @return the agent
	 */
	public Agent getAgent() {
		return agent;
	}

	/**
	 * @param agent the agent to set
	 */
	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	/**
	 * @return the status
	 */
	public DeliveryStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(DeliveryStatus status) {
		this.status = status;
	}

}