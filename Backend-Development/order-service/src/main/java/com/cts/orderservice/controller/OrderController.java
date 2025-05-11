package com.cts.orderservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.orderservice.dto.AddToCartRequest;
import com.cts.orderservice.dto.OrderSummary;
import com.cts.orderservice.dto.RemoveCartItemRequest;
import com.cts.orderservice.dto.Response;
import com.cts.orderservice.dto.UpdateStatusRequest;
import com.cts.orderservice.enums.OrderStatus;
import com.cts.orderservice.enums.Roles;
import com.cts.orderservice.model.Order;
import com.cts.orderservice.service.OrderService;

import jakarta.validation.Valid;

/**
 * REST controller for managing orders in the online food delivery system.
 * Provides endpoints to create orders, add items to cart, view all orders, and
 * update order status.
 * 
 * @Author Bhimisetty Renu Sai Ritvik
 * @Since 27 Feb 2025
 */
@Validated
@RestController
@RequestMapping("/api/order")
//@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class OrderController {

	@Autowired
	private OrderService orderService;

	/**
	 * Retrieves all orders.
	 * 
	 * @return ResponseEntity containing a list of all orders and HTTP status code.
	 */
	@GetMapping("/all")
	public ResponseEntity<Response<?>> viewAllOrders(@RequestAttribute("userId") UUID managerId) {
		List<Order> orders = orderService.getAllOrders(managerId);
		Response<List<Order>> response = new Response<>(true, HttpStatus.OK, orders, null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Adds an item to the cart of an existing order.
	 * 
	 * @param request AddToCartRequest object containing item details and order ID.
	 * @return ResponseEntity containing the added cart item and HTTP status code.
	 */
	@PostMapping("/add")
	public ResponseEntity<Response<?>> addToCart(@RequestAttribute("userId") UUID userId,
			@Valid @RequestBody AddToCartRequest request) {
		Order order = orderService.addToCart(userId, request.getItemId(), request.getRestaurantId(),
				request.getQuantity());
		Response<Order> response = new Response<>(true, HttpStatus.OK, order, null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Removes an item from the cart of an existing order.
	 * 
	 * @param itemId - UUID of the Item to be removed.
	 * @return ResponseEntity denoting success/failure.
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<Response<?>> removeFromCart(@RequestBody UUID cartId) {
		orderService.removeItemFromCart(cartId);
		Response<Void> response = new Response<>(true, HttpStatus.OK, null, null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Updates the status of an existing order.
	 * 
	 * @param request UpdateStatusRequest object containing order ID and new status.
	 * @return ResponseEntity containing the updated order and HTTP status code.
	 */
	@PutMapping("/update")
	public ResponseEntity<Response<?>> updateStatus(@RequestAttribute("userId") UUID userId,
			@Valid @RequestBody UpdateStatusRequest request) {
		Order order = orderService.updateOrderStatus(userId, request.getOrderId(), request.getStatus());
		Response<Order> response = new Response<>(true, HttpStatus.OK, order, null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/view")
	public ResponseEntity<Response<?>> getOrdersByStatus(@RequestAttribute("userId") UUID userId,
			@RequestAttribute("role") Roles role, @RequestBody OrderStatus status) {
		List<OrderSummary> order = orderService.findOrdersByStatus(userId, status, role);
		Response<List<OrderSummary>> response = new Response<>(true, HttpStatus.OK, order, null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/clear")
	public ResponseEntity<Response<?>> deleteOrder(@RequestBody UUID orderId) {
		orderService.deleteOrder(orderId);
		Response<Boolean> response = new Response<>(true, HttpStatus.OK, true, null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
