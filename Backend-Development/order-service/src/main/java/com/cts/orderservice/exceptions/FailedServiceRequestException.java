package com.cts.orderservice.exceptions;

public class FailedServiceRequestException extends RuntimeException{
	public FailedServiceRequestException(String message) {
		super(message);
	}
}
