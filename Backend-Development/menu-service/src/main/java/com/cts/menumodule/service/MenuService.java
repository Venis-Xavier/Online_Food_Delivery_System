package com.cts.menumodule.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cts.menumodule.dao.MenuDAO;
import com.cts.menumodule.dao.RestaurantDAO;
import com.cts.menumodule.exceptions.MenuNotFoundException;
import com.cts.menumodule.exceptions.RestaurantNotFoundException;
import com.cts.menumodule.model.Menu;
import com.cts.menumodule.model.Restaurant;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class for handling business logic related to menu items.
 * 
 * @author Vishnu Dabbara
 * @since 05 Mar 2025
 */
@Slf4j
@Service
public class MenuService {
	@Autowired
	private MenuDAO menuDAO;
	
	@Autowired
	private RestaurantDAO restaurantDAO;
	
	/**
     * Retrieves all menu items from the database.
     * 
     * @return a list of all menu items.
     */
	public List<Menu> getAllMenu(UUID restaurantId){
		return menuDAO.findAllByRestaurantRestaurantId(restaurantId);
	}
	
	
	/**
     * Retrieves all menu items from a specified restaurant.
     * 
     * @param restaurantId - the unique ID of restaurant
     * @return a list of all menu items.
	 * @throws RestaurantNotFoundException 
     */
	public Restaurant getMenuOfRestaurant(UUID restaurantId) throws RestaurantNotFoundException{
		return restaurantDAO.findById(restaurantId)
				.orElseThrow(() -> {
					log.error("No Restaurant Found With ID: {}", restaurantId);
					return new RestaurantNotFoundException("No Restaurant Found with ID: " + restaurantId);
					});
	}
	
	/**
     * Retrieves a List of menu items by its IDs.
     * 
     * @param itemId the UUID of the menu item.
     * @return a list of menu item with the specified IDs.
     * 
     * @throws MenuNotFoundException if no menu item is found with the given ID.
     */
	public List<Menu> getItems(Set<UUID> itemIds) {
	    return itemIds.stream()
	        .map(itemId -> menuDAO.findById(itemId)
	            .orElseThrow(() -> {
	                log.error("No Menu Found With ID: {}", itemId);
	                return new MenuNotFoundException("No Menu Found With ID: " + itemId);
	            })
	        )
	        .collect(Collectors.toList());
	}

	
	/**
     * Creates a new menu item.
     * 
     * @param itemName the name of the menu item.
	 * @param isVeg 
	 * @param multipartFile 
     * @param itemDesc a brief description of the menu item.
     * @param price the price of the menu item.
     * @param isAvailable indicates whether the menu item is available.
     * 
     * @return the created menu item.
	 * @throws IOException 
     */
	@Transactional
	public Menu createMenu(String itemName, MultipartFile itemImage, boolean isVeg, String itemDesc, 
			double price, boolean isAvailable, UUID restaurantId) throws IOException {
		
		Restaurant restaurant = restaurantDAO.findById(restaurantId)
                .orElseThrow(() -> {
                    log.error("No Restaurant found with ID: {}", restaurantId);
                    return new RestaurantNotFoundException("No Restaurant found with ID: " + restaurantId);
                });
		
		return menuDAO.save(new Menu(itemName, itemDesc, price, isAvailable, restaurant, isVeg, itemImage.getBytes()));
	}
	
	/**
     * Updates an existing menu item by its ID.
     * 
     * @param itemName the name of the menu item.
     * @param itemDesc a brief description of the menu item.
     * @param price the price of the menu item.
     * @param isAvailable indicates whether the menu item is available.
     * @param itemId the UUID of the menu item to be updated.
	 * @param multipartFile 
	 * @param boolean1 
     * 
     * @return the updated menu item.
     * 
     * @throws MenuNotFoundException if no menu item is found with the given ID.
	 * @throws IOException 
     */
	public Menu updateMenu(String itemName, String itemDesc, 
			double price, boolean isAvailable, UUID itemId, Boolean isVeg, MultipartFile multipartFile) throws MenuNotFoundException, IOException {
		Menu menu = getItem(itemId);
		
		menu.setItemName(itemName);
		menu.setItemDesc(itemDesc);
		menu.setPrice(price);
		menu.setAvailable(isAvailable);
		
		if(multipartFile != null) {
			menu.setItemImg(multipartFile.getBytes());
		}
		menu.setVeg(isVeg);
		return menuDAO.save(menu);
	}
	
	/**
     * Toggles the availability status of a menu item by its ID.
     * 
     * @param itemId the UUID of the menu item.
     * 
     * @return the updated menu item with the toggled availability status.
     */
	public Menu toggleAvailability(UUID itemId) throws MenuNotFoundException {
		Menu menu = getItem(itemId);
		menu.setAvailable(!menu.isAvailable());
		return menuDAO.save(menu);
	}
	
	/**
     * Deletes a menu item by its ID.
     * 
     * @param itemId the UUID of the menu item to be deleted.
     */
	public void deleteItem(UUID itemId) {
		menuDAO.deleteById(itemId);
	}
	
	/**
     * Returns the price of a menu item by its ID.
     * 
     * @param itemId the UUID of the menu item.
     * @return price of the item.
     */
	public double priceOfItem(UUID itemId) {
		return getItem(itemId).getPrice();
	}
	
	private Menu getItem(UUID itemId) {
	    return menuDAO.findById(itemId)
	        .orElseThrow(() -> {
	            log.error("No Menu Found With ID: {}", itemId);
	            return new MenuNotFoundException("No Menu Found With ID: " + itemId);
	        });
	}
}
