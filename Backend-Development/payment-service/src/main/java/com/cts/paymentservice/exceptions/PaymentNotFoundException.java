package com.cts.paymentservice.exceptions;

/**
 * Custom exception class for handling cases where a payment is not found.
 * 
 * @author Venkata Ramakrishna Kambhampati
 * @since 5 Mar 2025
 */

public class PaymentNotFoundException extends RuntimeException {
    
    /**
     * Constructor for PaymentNotFoundException.
     * @param message - The error message to be displayed when the exception is thrown.
     */
    public PaymentNotFoundException(String message) {
        super(message);
    }
}