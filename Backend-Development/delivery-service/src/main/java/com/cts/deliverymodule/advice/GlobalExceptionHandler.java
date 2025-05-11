package com.cts.deliverymodule.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cts.deliverymodule.dto.Response;
import com.cts.deliverymodule.exceptions.AgentUnavailableException;
import com.cts.deliverymodule.exceptions.DeliveryNotFoundException;
import com.cts.deliverymodule.exceptions.ForbiddenRequestException;

/**
 * Global exception handler class for managing exceptions across the application.
 * 
 * This class captures specific exceptions thrown during execution and returns appropriate
 * HTTP status codes and error responses.
 * 
 * @author Jeswin Joseph J
 * @since 11 Mar 2025
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ForbiddenRequestException.
     * 
     * Returns a response with HTTP status code 403 (Forbidden) and the exception's message.
     * 
     * @param ex the ForbiddenRequestException thrown during execution
     * @return ResponseEntity containing the response object with the error details
     */
    @ExceptionHandler(ForbiddenRequestException.class)
    public ResponseEntity<?> handleForbiddenRequestException(ForbiddenRequestException ex) {
        Response<String> response = 
                new Response<>(false, HttpStatus.FORBIDDEN, null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
    
    /**
     * Handles DeliveryNotFoundException.
     * 
     * Returns a response with HTTP status code 400 (Bad Request) and the exception's message.
     * 
     * @param ex the DeliveryNotFoundException thrown during execution
     * @return ResponseEntity containing the response object with the error details
     */
    @ExceptionHandler(DeliveryNotFoundException.class)
    public ResponseEntity<?> handleDeliveryNotFoundException(DeliveryNotFoundException ex) {
        Response<String> response = 
                new Response<>(false, HttpStatus.BAD_REQUEST, null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    /**
     * Handles AgentUnavailableException.
     * 
     * Returns a response with HTTP status code 400 (Bad Request) and the exception's message.
     * 
     * @param ex the AgentUnavailableException thrown during execution
     * @return ResponseEntity containing the response object with the error details
     */
    @ExceptionHandler(AgentUnavailableException.class)
    public ResponseEntity<?> handleAgentUnavailableException(AgentUnavailableException ex) {
        Response<String> response = 
                new Response<>(false, HttpStatus.BAD_REQUEST, null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    /**
     * Handles MethodArgumentNotValidException, triggered by validation errors.
     * 
     * Returns a response with HTTP status code 400 (Bad Request) and a map of validation errors
     * where the key is the field name and the value is the error message.
     * 
     * @param ex the MethodArgumentNotValidException thrown during execution
     * @return ResponseEntity containing a map of validation error details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }
    
    /**
     * Handles general exceptions.
     * 
     * Returns a response with HTTP status code 500 (Internal Server Error) and the exception's message.
     * 
     * @param ex the generic Exception thrown during execution
     * @return ResponseEntity containing the response object with the error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) {
        Response<String> response = 
                new Response<>(false, HttpStatus.INTERNAL_SERVER_ERROR, null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}