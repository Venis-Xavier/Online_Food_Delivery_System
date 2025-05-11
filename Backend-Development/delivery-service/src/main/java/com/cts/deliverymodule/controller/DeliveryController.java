package com.cts.deliverymodule.controller;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.deliverymodule.dto.AssignAgentRequest;
import com.cts.deliverymodule.dto.DeliveryDetail;
import com.cts.deliverymodule.dto.DeliveryDetails;
import com.cts.deliverymodule.dto.Response;
import com.cts.deliverymodule.dto.StatusUpdateRequest;
import com.cts.deliverymodule.dto.ViewDeliveryRequest;
import com.cts.deliverymodule.model.Delivery;
import com.cts.deliverymodule.service.DeliveryService;

import jakarta.validation.Valid;

/**
 * Controller class for managing delivery-related endpoints.
 * 
 * @author Jeswin Joseph J
 * @since 26th Feb 2025
 */
@Validated
@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {

	@Autowired
	private DeliveryService deliveryService;

	/**
     * Adds a new delivery agent.
     * 
     * @param userId the UUID of the user to be added as a delivery agent
     */
	@PostMapping("/addAgent")
	public void addAgent(@RequestBody UUID userId) {
		deliveryService.createAgent(userId);
	}

	/**
     * Retrieves all deliveries for a specific restaurant.
     * 
     * @param userId the UUID of the restaurant's user making the request, extracted
     *               from the request attributes
     * @return a {@link ResponseEntity} containing a {@link Response} object with the
     *         list of deliveries
     */
	@GetMapping("/all")
	public ResponseEntity<Response<?>> viewAllDelivery(@RequestAttribute("userId") UUID userId) {
		List<Delivery> deliveries = deliveryService.getAllDeliveriesOfRestaurant(userId);
		Response<List<Delivery>> response = new Response<>(true, HttpStatus.OK, deliveries, null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
     * Assigns a delivery agent to a new delivery request.
     * 
     * @param request the {@link AssignAgentRequest} containing order ID, customer ID,
     *                and restaurant ID
     * @return a {@link ResponseEntity} containing a {@link Response} object with
     *         the assigned delivery details
     */
	@PostMapping("/assign")
	public ResponseEntity<Response<?>> assignDeliveryAgent(@Valid @RequestBody AssignAgentRequest request) {
		DeliveryDetail delivery = deliveryService.assignDelivery(request.getOrderId(), 
				request.getCustomerId(), request.getRestaurantId());
		Response<DeliveryDetail> response = new Response<>(true, HttpStatus.OK, delivery, null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
     * Retrieves the delivery assigned to a specific delivery agent.
     * 
     * @param userId the UUID of the agent, extracted from the request attributes
     * @return a {@link ResponseEntity} containing a {@link Response} object with the
     *         assigned delivery
     */
	@GetMapping("/view")
	public ResponseEntity<Response<?>> viewAssignedDelivery(@RequestAttribute("userId") UUID userId) {
		Delivery delivery = deliveryService.viewAssignedDelivery(userId);
		Response<Delivery> response = new Response<>(true, HttpStatus.OK, delivery, null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
     * Updates the status of an existing delivery.
     * 
     * @param userId  the UUID of the agent updating the delivery status, extracted
     *                from the request attributes
     * @param request the {@link StatusUpdateRequest} containing the delivery ID and
     *                the new status
     * @return a {@link ResponseEntity} containing a {@link Response} object with a
     *         success message indicating the updated delivery status
     */
	@PutMapping("/status")
	public ResponseEntity<Response<?>> updateStatus(@RequestAttribute("userId") UUID userId, 
													@Valid @RequestBody StatusUpdateRequest request) {
		deliveryService.updateDeliveryStatus(userId, request.getDeliveryId(), request.getDeliveryStatus());
		Response<String> response = new Response<>(true, HttpStatus.OK,
				"Updated Status: " + request.getDeliveryStatus(), null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/summary")
	public ResponseEntity<Response<?>> fetchDeliveryData(@RequestBody Set<UUID> deliveryIds){
		List<DeliveryDetails> deliveryDetails = deliveryService.fetchData(deliveryIds);
		Response<List<DeliveryDetails>> response = new Response<>(true, HttpStatus.OK, deliveryDetails, null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
