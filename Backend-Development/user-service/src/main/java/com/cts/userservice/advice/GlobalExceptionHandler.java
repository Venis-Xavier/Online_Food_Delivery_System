package com.cts.userservice.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cts.userservice.dto.Response;
import com.cts.userservice.exception.UserNotFoundException;

/**
 * Class to handle all global exceptions
 * 
 * @author Harish Raju M R
 * @since 11 Mar 2025
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> handleGeneralException(UserNotFoundException ex) {
		Response<String> response = 
				new Response<>(false, HttpStatus.BAD_REQUEST, null, ex.getMessage());
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	        .body(response);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> handleUsernameNotFoundException(BadCredentialsException ex) {
		Response<String> response = 
				new Response<>(false, HttpStatus.UNAUTHORIZED, null, ex.getMessage());
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
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

