package com.cts.orderservice.exceptions;

public class RestaurantMismatchException extends RuntimeException{
	public RestaurantMismatchException(String message){
		super(message);
	}
}
