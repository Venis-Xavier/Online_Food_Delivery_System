package com.cts.menumodule.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.menumodule.dto.MenuItemRequest;
import com.cts.menumodule.dto.MenuRequest;
import com.cts.menumodule.dto.MenuUpdateRequest;
import com.cts.menumodule.dto.Response;
import com.cts.menumodule.dto.RestaurantDetailRequest;
import com.cts.menumodule.model.Menu;
import com.cts.menumodule.model.Restaurant;
import com.cts.menumodule.service.MenuService;

import jakarta.validation.Valid;

/**
 * REST controller for managing menu items.
 * 
 * @author Dabarra Vishnu
 * @since 27 Feb 2025
 */
@Validated
@RestController
@RequestMapping("/api/menus")
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	
	/**
     * Endpoint to get all menu items in a restaurant.
     * 
     * @return a ResponseEntity containing the list of all menu items.
     */
	@PostMapping("/")
	public ResponseEntity<Response<?>> getMenu(@Valid @RequestBody RestaurantDetailRequest restaurant){
		Restaurant restaurantMenus = menuService.getMenuOfRestaurant(restaurant.getRestaurantId());
		Response<Restaurant> response = new Response<>(true, HttpStatus.OK, restaurantMenus, null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
     * Endpoint to get all menu items.
     * 
     * @return a ResponseEntity containing the list of all menu items.
     */
	@GetMapping("/all")
	public ResponseEntity<Response<?>> getAllMenu(@RequestAttribute("userId") UUID userId){
		List<Menu> allMenu = menuService.getAllMenu(userId);
		Response<List<Menu>> response = new Response<>(true, HttpStatus.OK, allMenu, null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
     * Endpoint to get a menu item by its ID.
     * 
     * @param itemId the UUID of the menu item.
     * @return a ResponseEntity containing the menu item.
     */
	@PostMapping("/view")
	public ResponseEntity<Response<?>> getItems(@Valid @RequestBody Set<UUID> itemIds){
		List<Menu> menu = menuService.getItems(itemIds);
		Response<List<Menu>> response = new Response<>(true, HttpStatus.OK, menu, null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
     * Endpoint to create a new menu item.
     * 
     * @param request the request object containing menu item details.
     * @return a ResponseEntity containing the created menu item.
	 * @throws IOException 
     */
	@PostMapping("/create")
	public ResponseEntity<Response<?>> createNewMenuItem(@RequestAttribute("userId") UUID userId, 
														@Valid @ModelAttribute MenuRequest request) throws IOException {
		Menu menu = menuService.createMenu(request.getItemName(), request.getItemImage(), request.getIsVeg(), 
				request.getItemDesc(), request.getPrice(), request.getIsAvailable(), userId);
		Response<Menu> response = new Response<>(true, HttpStatus.OK, menu, null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
     * Endpoint to update an existing menu item by its ID.
     * 
     * @param itemId the UUID of the menu item.
     * @param request the request object containing updated menu item details.
     * 
     * @return a ResponseEntity containing the updated menu item.
	 * @throws IOException 
	 * @throws  
     */
	@PutMapping("/update")
	public ResponseEntity<Response<?>> updateItem(@Valid @ModelAttribute MenuUpdateRequest request) throws IOException{
		Menu menu = menuService.updateMenu(request.getItemName(), request.getItemDesc(), request.getPrice(), 
				request.getIsAvailable(), request.getItemId(), request.getIsVeg(), request.getItemImage());
		Response<Menu> response = new Response<>(true, HttpStatus.OK, menu, null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
     * Endpoint to toggle the availability of a menu item by its ID.
     * 
     * @param itemId the UUID of the menu item.
     * @return a ResponseEntity containing the updated menu item.
     */
	@PutMapping("/toggle")
	public ResponseEntity<Response<?>> updateAvailability(@Valid @RequestBody MenuItemRequest item) {
		Menu menu = menuService.toggleAvailability(item.getItemId());
		Response<Menu> response = new Response<>(true, HttpStatus.OK, menu, null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
     * Endpoint to delete a menu item by its ID.
     * 
     * @param itemId the UUID of the menu item.
     */
	@DeleteMapping("/delete")
	public void deleteItem(@Valid @RequestBody MenuItemRequest request) {
		menuService.deleteItem(request.getItemId());
	}
	
	
	@PostMapping("/price")
	public ResponseEntity<Response<?>> getPriceOfItem(@Valid @RequestBody MenuItemRequest request){
		double price = menuService.priceOfItem(request.getItemId());
		Response<Double> response = new Response<>(true, HttpStatus.OK, price, null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
