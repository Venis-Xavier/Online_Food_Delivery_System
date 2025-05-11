package com.cts.menumodule.dto;

import java.util.List;
import java.util.UUID;

import com.cts.menumodule.model.Menu;


public class RestaurantData {

	private UUID restaurantId;
	
	private List<Menu> menuItems;
	
	private String restaurantName;
	
	private String address;
	
	private boolean isOpen;
	
	private byte[] restaurantImg;

	/**
	 * @param restaurantId
	 * @param menuItems
	 * @param restaurantName
	 * @param address
	 * @param isOpen
	 * @param restaurantImg
	 */
	public RestaurantData(UUID restaurantId, List<Menu> menuItems, String restaurantName, String address,
			boolean isOpen, byte[] restaurantImg) {
		
		this.restaurantId = restaurantId;
		this.menuItems = menuItems;
		this.restaurantName = restaurantName;
		this.address = address;
		this.isOpen = isOpen;
		this.restaurantImg = restaurantImg;
	}

	/**
	 * @return the restaurantId
	 */
	public UUID getRestaurantId() {
		return restaurantId;
	}

	/**
	 * @param restaurantId the restaurantId to set
	 */
	public void setRestaurantId(UUID restaurantId) {
		this.restaurantId = restaurantId;
	}

	/**
	 * @return the menuItems
	 */
	public List<Menu> getMenuItems() {
		return menuItems;
	}

	/**
	 * @param menuItems the menuItems to set
	 */
	public void setMenuItems(List<Menu> menuItems) {
		this.menuItems = menuItems;
	}

	/**
	 * @return the restaurantName
	 */
	public String getRestaurantName() {
		return restaurantName;
	}

	/**
	 * @param restaurantName the restaurantName to set
	 */
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the isOpen
	 */
	public boolean isOpen() {
		return isOpen;
	}

	/**
	 * @param isOpen the isOpen to set
	 */
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	/**
	 * @return the restaurantImg
	 */
	public byte[] getRestaurantImg() {
		return restaurantImg;
	}

	/**
	 * @param restaurantImg the restaurantImg to set
	 */
	public void setRestaurantImg(byte[] restaurantImg) {
		this.restaurantImg = restaurantImg;
	}
	
}
