package com.cts.orderservice.exceptions;

/**
 * Custom exception thrown when an order is not found in the system.
 * 
 * @Author Bhimisetty Renu Sai Ritvik
 * @Since 27 Feb 2025
 */
public class OrderNotFoundException extends RuntimeException{
	
	/**
     * Constructs a new OrderNotFoundException with the specified detail message.
     * 
     * @param the detail error message.
     */
	public OrderNotFoundException(String message) {
		super(message);
	}
}
