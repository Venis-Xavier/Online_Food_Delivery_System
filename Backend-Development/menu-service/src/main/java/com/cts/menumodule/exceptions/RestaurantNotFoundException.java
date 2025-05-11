package com.cts.menumodule.exceptions;

import lombok.extern.slf4j.Slf4j;

/**
 * This Exception is thrown when there in no restaurant available with specified ID
 * 
 * @author Dabarra Vishnu
 * @since 05 Mar 2025
 */
@Slf4j
public class RestaurantNotFoundException extends RuntimeException{
	public RestaurantNotFoundException(String message) {
		super(message);
	}
}
