package com.cts.paymentservice.enums;

/**
 * Enumeration for different payment methods.
 * This enum defines the types of payment methods available in the online food delivery system.
 * 
 * @author Venkata Ramakrishna Kambhampati
 * @since 25 Feb 2025
 */
public enum PaymentMethod {
    
    /**
     * Payment through Unified Payments Interface (UPI).
     */
    UPI,
    
    /**
     * Payment made using cash.
     */
    CASH,
    
    /**
     * Payment made using a card (credit or debit).
     */
    CARD
}
