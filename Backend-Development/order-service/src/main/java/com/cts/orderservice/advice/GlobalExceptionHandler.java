/**
 * 
 */
package com.cts.orderservice.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cts.orderservice.dto.Response;
import com.cts.orderservice.exceptions.FailedServiceRequestException;
import com.cts.orderservice.exceptions.ForbiddenRequestException;
import com.cts.orderservice.exceptions.OrderNotFoundException;
import com.cts.orderservice.exceptions.RestaurantMismatchException;

/**
 * 
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ForbiddenRequestException.class)
	public ResponseEntity<?> handleForbiddenRequestException(ForbiddenRequestException ex) {
		Response<String> response = 
				new Response<>(false, HttpStatus.FORBIDDEN, null, ex.getMessage());
	    return ResponseEntity.status(HttpStatus.FORBIDDEN)
	        .body(response);
	}
	
	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<?> handleGeneralException(OrderNotFoundException ex) {
		Response<String> response = 
				new Response<>(false, HttpStatus.BAD_REQUEST, null, ex.getMessage());
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	        .body(response);
	}
	
	@ExceptionHandler(RestaurantMismatchException.class)
	public ResponseEntity<?> handleRestaurantMismatchException(RestaurantMismatchException ex) {
		Response<String> response = 
				new Response<>(false, HttpStatus.BAD_REQUEST, null, ex.getMessage());
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	        .body(response);
	}
	
	@ExceptionHandler(FailedServiceRequestException.class)
	public ResponseEntity<?> handleFailedServiceRequestException(FailedServiceRequestException ex) {
		Response<String> response = 
				new Response<>(false, HttpStatus.SERVICE_UNAVAILABLE, null, ex.getMessage());
	    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
	        .body(response);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getFieldErrors().forEach(error -> 
	        errors.put(error.getField(), error.getDefaultMessage())
	    );
	    return ResponseEntity.badRequest().body(errors);
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGeneralException(Exception ex) {
		ex.printStackTrace();
		Response<String> response = 
				new Response<>(false, HttpStatus.INTERNAL_SERVER_ERROR, null, ex.getMessage());
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	        .body(response);
	}
}
