package com.cts.paymentservice.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.paymentservice.model.Payment;

/**
 * Data Access Object (DAO) interface for the Payment entity.
 * Extends JpaRepository to provide CRUD operations and more for the Payment entity.
 * 
 * @author Venkata Ramakrishna Kambhampati
 * @since 25 Feb 2025
 */

@Repository
public interface PaymentDAO extends JpaRepository<Payment, UUID> {
    
	// JpaRepository provides methods for CRUD operations and more

	List<Payment> findAllByCustomerId(UUID customerId);

	List<Payment> findAllByRestaurantId(UUID restaurantId);

	Optional<Payment> findByOrderId(UUID orderId);
}
