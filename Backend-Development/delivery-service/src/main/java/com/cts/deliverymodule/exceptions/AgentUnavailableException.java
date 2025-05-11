package com.cts.deliverymodule.exceptions;


/**
 * This exception is used to indicate that there are no available agents to handle a delivery.
 * 
 * @author Jeswin Joseph J
 * @since 26th Feb 2025
 */
public class AgentUnavailableException extends RuntimeException{
	
	/**
     * Constructs a new AgentUnavailableException with the specified detail message.
     * 
     * @param message the detail message
     */
	public AgentUnavailableException(String message) {
		super(message);
	}
}
