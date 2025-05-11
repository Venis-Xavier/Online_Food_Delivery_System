package com.cts.orderservice.feign;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cts.orderservice.dto.AssignAgentRequest;
import com.cts.orderservice.dto.DeliveryDetails;
import com.cts.orderservice.dto.Response;
import com.cts.orderservice.dto.StatusUpdateRequest;

/**
 * Feign client interface for communication with the Delivery Service.
 * 
 * @author Jeswin Joseph J
 * @since 10 Mar 2025
 */
@FeignClient("DELIVERY-SERVICE")
public interface DeliveryInterface {
	
	@PostMapping("/api/delivery/assign")
	public ResponseEntity<Response<?>> assignDeliveryAgent(@RequestBody AssignAgentRequest request);
	
	@PutMapping("/api/delivery/status")
	public ResponseEntity<Response<?>> updateStatus(@RequestBody StatusUpdateRequest request);
	
	@PostMapping("api/delivery/summary")
	public ResponseEntity<Response<List<DeliveryDetails>>> fetchDeliveryData(@RequestBody Set<UUID> deliveryIds);
}
