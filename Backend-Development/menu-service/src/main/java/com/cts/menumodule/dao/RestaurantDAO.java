package com.cts.menumodule.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cts.menumodule.model.Restaurant;

/**
 * Data access object for restaurant table
 * 
 * @author Dabbara Vishnu
 * @since 25 Feb 2025
 */
@Repository
public interface RestaurantDAO extends JpaRepository<Restaurant, UUID>{
	
	@Query("SELECT r FROM Restaurant r WHERE r.isOpen = true")
	List<Restaurant> findAllOpenRestaurants();
}
