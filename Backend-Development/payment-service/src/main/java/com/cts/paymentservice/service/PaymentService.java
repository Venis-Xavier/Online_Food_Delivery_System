package com.cts.paymentservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.paymentservice.dao.PaymentDAO;
import com.cts.paymentservice.dto.PaymentDetails;
import com.cts.paymentservice.enums.PaymentMethod;
import com.cts.paymentservice.enums.PaymentStatus;
import com.cts.paymentservice.exceptions.PaymentNotFoundException;
import com.cts.paymentservice.model.Payment;

/**
 * Service class to handle the  business logic.
 * 
 * @author Venkata Ramakrishna Kambhampati
 * @since 5 Mar 2025
 */

@Service
public class PaymentService {
	
	@Autowired
	private PaymentDAO paymentDAO;
	
	/**
	 * This method initializes a new payment record.
	 * @param orderId - ID of the order
	 * @param customerId - ID of the customer
	 * @param agentId - ID of the agent
	 * @param restaurantId - ID of the restaurant
	 * @param method - Payment method used
	 * @param amount - Amount to be paid
	 * @return Payment - The initialized payment record
	 */
	public UUID newPaymentRecord(UUID orderId, UUID customerId, UUID agentId, 
			UUID restaurantId, PaymentMethod method, double amount) {
		Payment payment = new Payment(orderId, customerId, agentId, restaurantId, method, amount);		
		return paymentDAO.save(payment).getPaymentId();
	}
	
	/**
	 * This method updates the status of a payment to COMPLETED.
	 * @param paymentId - ID of the payment to be updated
	 * @return Payment - The updated payment record
	 * @throws PaymentNotFoundException - If no payment is found with the given ID
	 */
	public Payment updateStatus(UUID paymentId) {
		Payment payment = paymentDAO.findById(paymentId)
				.orElseThrow(() -> new PaymentNotFoundException("No Payment Found with ID: " + paymentId));
		payment.setPaymentStatus(PaymentStatus.COMPLETED);
		
		return paymentDAO.save(payment);
	}
	
	/**
	 * This method retrieves all payments made by a specific customer.
	 * @param customerId - ID of the customer
	 * @return List<Payment> - List of payments made by the customer
	 */
	public List<Payment> findAllByCustomerId(UUID customerId) {
		return paymentDAO.findAllByCustomerId(customerId);
	}
	
	/**
	 * This method retrieves all payments made to a specific restaurant.
	 * @param restaurantId - ID of the restaurant
	 * @return List<Payment> - List of payments made to the restaurant
	 */
	public List<Payment> findAllByRestaurantId(UUID restaurantId) {
		return paymentDAO.findAllByRestaurantId(restaurantId);
	}
	
	/**
	 * This method retrieves a payment record by order ID.
	 * @param orderId - ID of the order
	 * @return Payment - The payment record associated with the order ID
	 * @throws PaymentNotFoundException - If no payment is found with the given order ID
	 */
	public Payment findByOrderId(UUID orderId) {
		return paymentDAO.findByOrderId(orderId)
				.orElseThrow(() -> new PaymentNotFoundException("No Payment Found with Order ID: " + orderId)); 
	}

	public List<PaymentDetails> fetchDetails(Set<UUID> paymentList) {
	    return paymentList.stream()
	                      .map(paymentId -> {
	                          Payment payment = paymentDAO.findById(paymentId)
	                                                      .orElseThrow(() -> 
	                                                          new PaymentNotFoundException("No Payment Found with ID: " + paymentId));
	                          return new PaymentDetails(payment.getAmount(), 
	                                                     payment.getPaymentMethod(), 
	                                                     payment.getPaymentStatus(),
	                                                     payment.getPaymentId());
	                      })
	                      .collect(Collectors.toList());
	}


	
}