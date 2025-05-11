package com.cts.menumodule.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.menumodule.model.Menu;

/**
 * Data access object for menu table
 * 
 * @author Dabbara Vishnu
 * @since 25 Feb 2025
 */
@Repository
public interface MenuDAO extends JpaRepository<Menu, UUID>{
	List<Menu> findAllByRestaurantRestaurantId(UUID restaurantId);
}
