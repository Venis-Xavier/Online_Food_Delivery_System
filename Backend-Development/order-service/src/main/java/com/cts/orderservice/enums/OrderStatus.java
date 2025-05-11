package com.cts.orderservice.enums;

/**
 * Represents the status of an order in the online food delivery system.
 * This enum is used to track the current status of an order.
 * 
 * @author Bhimisetty Renu Sai Ritvik
 * @since 22 Feb 2025
 */

public enum OrderStatus {
    
    /**
     * The order is in the cart and not yet placed.
     */
    IN_CART,

    /**
     * The order has been placed by the user.
     */
    PLACED,

    /**
     * The order has been accepted by the restaurant.
     */
    ACCEPTED,

    /**
     * The order has not been accepted by the restaurant.
     */
    DECLINED,

    /**
     * The order is completed and delivered.
     */
    COMPLETED,
    
}
