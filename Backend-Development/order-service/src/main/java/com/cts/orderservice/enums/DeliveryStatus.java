package com.cts.orderservice.enums;

/**
 * Represents the status of the delivery.
 * This enum is used to track the order's current delivery status.
 * 
 * @author Jeswin Joseph J
 * @since 22 Feb 2025
 */
public enum DeliveryStatus {
    /**
     * The delivery agent has not accepted the delivery.
     */
    PENDING,

    /**
     * The delivery agent has accepted the delivery and now under transit. 
     */
    IN_TRANSIT,

    /**
     * The delivery agent has delivered the order to the customer.
     */
    DELIVERED
}
