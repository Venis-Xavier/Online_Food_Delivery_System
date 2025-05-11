package com.cts.deliverymodule.exceptions;

public class ForbiddenRequestException extends RuntimeException{
	public ForbiddenRequestException(String message) {
		super(message);
	}
}
