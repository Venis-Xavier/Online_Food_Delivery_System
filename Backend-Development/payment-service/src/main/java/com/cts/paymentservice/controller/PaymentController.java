package com.cts.paymentservice.controller;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.paymentservice.dto.CustomerDetailRequest;
import com.cts.paymentservice.dto.OrderDetailRequest;
import com.cts.paymentservice.dto.PaymentDetails;
import com.cts.paymentservice.dto.PaymentRequest;
import com.cts.paymentservice.dto.Response;
import com.cts.paymentservice.dto.RestaurantDetailRequest;
import com.cts.paymentservice.model.Payment;
import com.cts.paymentservice.service.PaymentService;

import jakarta.validation.Valid;

/**
 * Controller class for handling the requests.
 * 
 * @author Venkata Ramakrishna Kambhampati
 * @since 5 Mar 2025
 */
@Validated
@RestController
@RequestMapping("/api/payment")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	/**
	 * This method creates a new payment record.
	 * @param request - PaymentRequest object containing payment details
	 * @return Payment - The created payment record
	 */
	@PostMapping("/create")
	public ResponseEntity<Response<?>> initializePayment(@Valid @RequestBody PaymentRequest request) {

        UUID paymentId = paymentService.newPaymentRecord(request.getOrderId(), request.getCustomerId(), 
        		request.getAgentId(), request.getRestaurantId(), request.getPaymentMethod(), request.getAmount());
        Response<UUID> response = new Response<>(true, HttpStatus.OK, paymentId, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * This method updates the status of a payment.
	 * @param paymentId - ID of the payment to be updated
	 * @return ResponseEntity<?> - The updated payment record or an error message
	 */
	@PutMapping("/update")
	public ResponseEntity<Response<?>> updatePaymentStatus(@RequestBody UUID paymentId) {

		
		Payment payment = paymentService.updateStatus(paymentId);
		Response<Payment> response = new Response<>(true, HttpStatus.OK, payment, null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * This method retrieves all payments made by a specific customer.
	 * @param request - containing the customer ID
	 * @return ResponseEntity<?> - List of payments or an error message
	 */
	@GetMapping("/customer")
	public ResponseEntity<Response<?>> getPaymentsByCustomerId(@RequestAttribute("userId") UUID userId) {
		
        List<Payment> payments = paymentService.findAllByCustomerId(userId);
        Response<List<Payment>> response = new Response<>(true, HttpStatus.OK, payments, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * This method retrieves all payments made to a specific restaurant.
	 * @param request - containing the restaurant ID
	 * @return ResponseEntity<?> - List of payments or an error message
	 */
	@GetMapping("/restaurant")
	public ResponseEntity<Response<?>> getPaymentsByRestaurantId(@RequestAttribute("userId") UUID userId) {

		List<Payment> payments = paymentService.findAllByRestaurantId(userId);
		Response<List<Payment>> response = new Response<>(true, HttpStatus.OK, payments, null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * This method retrieves a payment record by order ID.
	 * @param request - containing the order ID
	 * @return ResponseEntity<?> - The payment record or an error message
	 */
	@PostMapping("/order")
	public ResponseEntity<Response<?>> getPaymentByOrderId(@Valid @RequestBody OrderDetailRequest request) {
		
		Payment payment = paymentService.findByOrderId(request.getOrderId());
		Response<Payment> response = new Response<>(true, HttpStatus.OK, payment, null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/view")
	public ResponseEntity<Response<?>> fetchPaymentData(@RequestBody Set<UUID> paymentList){
		List<PaymentDetails> payments = paymentService.fetchDetails(paymentList);
		Response<List<PaymentDetails>> response = new Response<>(true, HttpStatus.OK, payments, null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}