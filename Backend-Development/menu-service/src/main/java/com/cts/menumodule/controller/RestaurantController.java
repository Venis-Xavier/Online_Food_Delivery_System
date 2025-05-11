package com.cts.menumodule.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.menumodule.dto.ImageUploadRequest;
import com.cts.menumodule.dto.Response;
import com.cts.menumodule.dto.RestaurantData;
import com.cts.menumodule.dto.RestaurantDetailRequest;
import com.cts.menumodule.dto.RestaurantRequest;
import com.cts.menumodule.dto.RestaurantUpdateRequest;
import com.cts.menumodule.model.Restaurant;
import com.cts.menumodule.service.RestaurantService;

import jakarta.validation.Valid;


/**
 * REST controller for managing restaurant operations.
 * 
 * @author Vishnu Dabbara
 * @since 05 Mar 2025
 */
@Validated
@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {
	
	@Autowired
	RestaurantService restaurantService;
	
	/**
     * Endpoint to create a new restaurant.
     * 
     * @param request the request object containing restaurant details
     * @return a ResponseEntity containing the created restaurant and HTTP status
     */
	@PostMapping("/create")
	public ResponseEntity<Response<?>> createNewRestaurant(@Valid @RequestBody RestaurantRequest request) {
		Restaurant restaurant = restaurantService.createRestaurant(request.getManagerId(),
				request.getRestaurantName(), request.getAddress());
		Response<Restaurant> response = new Response<Restaurant>(true, HttpStatus.OK, restaurant, null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
     * Endpoint to update an existing restaurant.
     * 
     * @param request the request object containing updated restaurant details
     * @return a ResponseEntity containing the updated restaurant and HTTP status
     */
	@PutMapping("/update")
	public ResponseEntity<Response<?>> updateRestaurant(@Valid @RequestBody RestaurantUpdateRequest request) {
		Restaurant restaurant = restaurantService.updateRestaurant(request.getRestaurantId(), 
				    request.getRestaurantName(), request.getAddress());
		Response<Restaurant> response = new Response<Restaurant>(true, HttpStatus.OK, restaurant, null);
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}
	
	/**
     * Endpoint to set a restaurant open/close.
     * 
     * @return a ResponseEntity containing the state of restaurant and HTTP status
     */
	@PatchMapping("/toggle")
	public ResponseEntity<Response<?>> toggleRestaurantOpen(@RequestAttribute("userId") UUID userId){
		boolean state = restaurantService.toggleRestaurantOpen(userId);
		Response<Boolean> response = new Response<>(true, HttpStatus.OK, state, null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
     * Endpoint to get all restaurants.
     * 
     * @return a ResponseEntity containing the list of all restaurants and HTTP status
     */
	@GetMapping("/view")
	public ResponseEntity<Response<?>> getAllRestaurant(){
		List<RestaurantData> restaurants = restaurantService.getAllRestaurant();
		Response<List<RestaurantData>> response = new Response<>(true, HttpStatus.OK, restaurants, null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/upload")
	public ResponseEntity<Response<?>> uploadRestaurantImage(@Valid @ModelAttribute ImageUploadRequest request, 
															@RequestAttribute("userId") UUID userId) throws IOException{
		Restaurant restaurant = restaurantService.editRestaurantImage(request.getImage(), userId);
		Response<Restaurant> response = new Response<Restaurant>(true, HttpStatus.OK, restaurant, null);
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}
}
