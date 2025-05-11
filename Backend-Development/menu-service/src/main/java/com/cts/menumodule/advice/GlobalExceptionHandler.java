/**
 * 
 */
package com.cts.menumodule.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cts.menumodule.dto.Response;
import com.cts.menumodule.exceptions.MenuNotFoundException;
import com.cts.menumodule.exceptions.RestaurantNotFoundException;

/**
 * 
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MenuNotFoundException.class)
	public ResponseEntity<?> handleGeneralException(MenuNotFoundException ex) {
		Response<String> response = 
				new Response<>(false, HttpStatus.BAD_REQUEST, null, ex.getMessage());
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	        .body(response);
	}
	
	
	@ExceptionHandler(RestaurantNotFoundException.class)
	public ResponseEntity<?> handleGeneralException(RestaurantNotFoundException ex) {
		Response<String> response = 
				new Response<>(false, HttpStatus.BAD_REQUEST, null, ex.getMessage());
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
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
		Response<String> response = 
				new Response<>(false, HttpStatus.INTERNAL_SERVER_ERROR, null, ex.getMessage());
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	        .body(response);
	}
}
