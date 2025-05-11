package com.cts.deliverymodule.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.deliverymodule.enums.DeliveryStatus;
import com.cts.deliverymodule.model.Delivery;

/**
 * Data Access Object (DAO) interface for the Delivery entity.
 * 
 * @author Jeswin Joseph J
 * @since 26th Feb 2025
 */
@Repository
public interface DeliveryDAO extends JpaRepository<Delivery, UUID>{
	List<Delivery> findAllByRestaurantId(UUID restaurantId);
	
	Delivery findByAgentAgentIdAndStatus(UUID userId, DeliveryStatus status);

}
