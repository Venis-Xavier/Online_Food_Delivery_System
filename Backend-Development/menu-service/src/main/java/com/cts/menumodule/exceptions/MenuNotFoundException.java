package com.cts.menumodule.exceptions;
/**
 * This Exception is thrown when there in no menu available with specified ID
 * 
 * @author Dabarra Vishnu
 * @since 27 Feb 2025
 */
public class MenuNotFoundException extends RuntimeException{
	public MenuNotFoundException(String message) {
		super(message);
	}
}
