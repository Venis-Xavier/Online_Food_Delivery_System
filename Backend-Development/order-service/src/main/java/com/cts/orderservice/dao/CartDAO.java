package com.cts.orderservice.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.orderservice.model.CartItem;

/**
 * Data access object for CartItem table
 * 
 * @author Bhimisetty Renu Sai Ritvik
 * @since 25 Feb 2025
 */

public interface CartDAO extends JpaRepository<CartItem, UUID>{
	Optional<CartItem> findByItemIdAndOrderOrderId(UUID itemId, UUID orderId);
	
	List<CartItem> findAllByOrderOrderId(UUID orderId);
}
