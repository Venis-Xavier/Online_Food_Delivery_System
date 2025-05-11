package com.cts.orderservice.feign;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cts.orderservice.dto.PaymentDetails;
import com.cts.orderservice.dto.PaymentRequest;
import com.cts.orderservice.dto.Response;

/**
 * Feign client interface for communication with the Payment Service.
 * 
 * @author Jeswin Joseph J
 * @since 10 Mar 2025
 */
@FeignClient("PAYMENT-SERVICE")
public interface PaymentInterface {

	@PostMapping("/api/payment/create")
	public ResponseEntity<Response<?>> initializePayment(@RequestBody PaymentRequest request);
	
	@PutMapping("api/payment/update")
	public ResponseEntity<Response<?>> updatePaymentStatus(@RequestBody UUID paymentId);
	
	@PostMapping("api/payment/view")
	public ResponseEntity<Response<List<PaymentDetails>>> fetchPaymentData(@RequestBody Set<UUID> paymentList);
}
