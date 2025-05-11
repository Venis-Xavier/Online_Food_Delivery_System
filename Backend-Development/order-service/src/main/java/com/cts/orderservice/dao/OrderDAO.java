package com.cts.orderservice.dao;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.orderservice.enums.OrderStatus;
import com.cts.orderservice.model.Order;


/**
 * Data access object for Order table
 * 
 * @author Bhimisetty Renu Sai Ritvik
 * @since 25 Feb 2025
 */

public interface OrderDAO extends JpaRepository<Order, UUID> {
	
	Optional<Order> findByCustomerIdAndStatus(UUID customerId, OrderStatus status);
	
	List<Order> findAllByRestaurantId(UUID restaurantId);
	
	List<Order> findAllByCustomerIdAndStatus(UUID customerId, OrderStatus status);
	
	List<Order> findAllByRestaurantIdAndStatus(UUID restaurantId, OrderStatus status);

	List<Order> findAllByAgentIdAndStatus(UUID userId, OrderStatus status);
}
