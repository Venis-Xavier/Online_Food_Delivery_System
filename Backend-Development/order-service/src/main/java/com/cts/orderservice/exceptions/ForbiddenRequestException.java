package com.cts.orderservice.exceptions;

public class ForbiddenRequestException extends RuntimeException{
	public ForbiddenRequestException(String message) {
		super(message);
	}
}
