package com.cts.deliverymodule.exceptions;

/**
 * This exception is used to indicate that no delivery was found for the given ID.
 * 
 * @author Jeswin Joseph J
 * @since 26th Feb 2025
 */
public class DeliveryNotFoundException extends RuntimeException{
	
	/**
     * Constructs a new DeliveryNotFoundException with the specified detail message.
     * 
     * @param message - the detail message
     */
	public DeliveryNotFoundException(String message) {
		super(message);
	}
}
