package com.cts.menumodule.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cts.menumodule.dao.RestaurantDAO;
import com.cts.menumodule.dto.RestaurantData;
import com.cts.menumodule.exceptions.RestaurantNotFoundException;
import com.cts.menumodule.model.Restaurant;

import lombok.extern.slf4j.Slf4j;

/**
 * Service class for handling business logic related to restaurant operations.
 * 
 * @author Vishnu Dabbara
 * @since 05 Mar 2025
 */
@Slf4j
@Service
public class RestaurantService {
	
	@Autowired
	private RestaurantDAO restaurantDAO;
	
	/**
     * Creates a new restaurant.
     * 
     * @param managerId the ID of the restaurant manager
     * @param restaurantName the name of the restaurant
     * @param address the address of the restaurant
     * @return the created Restaurant object
     */
	public Restaurant createRestaurant(UUID managerId, String restaurantName, String address) {
		
		Restaurant newRestaurant = new Restaurant();
		newRestaurant.setRestaurantName(restaurantName);
		newRestaurant.setRestaurantId(managerId);
		newRestaurant.setAddress(address);
		return restaurantDAO.save(newRestaurant);
	}
	
	/**
     * Toggle if the restaurant is Open/Close
     * 
     * @param restaurantId the unique ID of the restaurant
     */
	public boolean toggleRestaurantOpen(UUID restaurantId) {
		Restaurant restaurant = restaurantDAO.findById(restaurantId)
				.orElseThrow(() ->{ 
					log.error("No Restaurant Found With ID: {}", restaurantId);
					return new RestaurantNotFoundException("No Restaurant found With ID: " + restaurantId);
				});
		
		restaurant.setOpen(!restaurant.isOpen());
		restaurantDAO.save(restaurant);
		
		return restaurant.isOpen();
	}
	
	/**
     * Updates an existing restaurant by its ID.
     * 
     * @param restaurantId the ID of the restaurant to be updated
     * @param restaurantName the new name of the restaurant
     * @param address the new address of the restaurant
     * @return the updated Restaurant object
     * @throws RestaurantNotFoundException if no restaurant is found with the given ID
     */
	public Restaurant updateRestaurant(UUID restaurantId, String restaurantName, String address){
		
		Restaurant restaurant = restaurantDAO.findById(restaurantId)
				.orElseThrow(() ->{ 
					return new RestaurantNotFoundException("No Restaurant found With ID: " + restaurantId);
					});
		
		restaurant.setRestaurantId(restaurantId);
		restaurant.setRestaurantName(restaurantName);
		restaurant.setAddress(address);
		return restaurantDAO.save(restaurant);
	}
	
	/**
     * Retrieves all restaurants from the database.
     * 
     * @return a list of all restaurants
     */
	public List<RestaurantData> getAllRestaurant() {
		
	    List<Restaurant> restaurants = restaurantDAO.findAllOpenRestaurants();

	    List<RestaurantData> restaurantList = restaurants.stream().map(restaurant -> {
	        return new RestaurantData(
	            restaurant.getRestaurantId(), 
	            restaurant.getMenuItems(),   
	            restaurant.getRestaurantName(), 
	            restaurant.getAddress(), 
	            restaurant.isOpen(),
	            restaurant.getRestaurantImg() 
	        );
	    }).collect(Collectors.toList());

	    return restaurantList;
	}


	
	public Restaurant editRestaurantImage(MultipartFile image, UUID restaurantId) throws IOException {
		Restaurant restaurant = restaurantDAO.findById(restaurantId)
				.orElseThrow(() ->{ 
					return new RestaurantNotFoundException("No Restaurant found With ID: " + restaurantId);
				});
		restaurant.setRestaurantImg(image.getBytes());
		return restaurantDAO.save(restaurant);
	}
	
//	public byte[] compressImage(MultipartFile imageFile) throws IOException {
//	    BufferedImage bufferedImage = ImageIO.read(imageFile.getInputStream());
//
//	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//	    ImageIO.write(bufferedImage, "jpg", outputStream);
//	    return outputStream.toByteArray();
//	}
}
