package com.cts.menumodule.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Represents a restaurant in the online food delivery system. This entity is
 * mapped to the "Restaurant" table.
 * 
 * @author Dabbara Vishnu
 * @since 25 Feb 2025
 */
@Entity
@Table(name = "restaurant")
public class Restaurant {

	/**
	 * The unique identifier for the manager of the restaurant.
	 */

	@Id
	@Column(nullable = false, unique = true)
	private UUID restaurantId;

	/**
	 * The list of menu items associated with the restaurant. This establishes a
	 * one-to-many relationship with the Menu entity.
	 */
	@OneToMany(mappedBy = "restaurant")
	private List<Menu> menuItems;

	/**
	 * The name of the restaurant.
	 */
	@Column(nullable = false, unique = true)
	private String restaurantName;

	/**
	 * The address of the restaurant.
	 */
	@Column(nullable = false, unique = true)
	private String address;

	/**
	 * If the restaurant is Open/Close
	 */
	@Column(nullable = false)
	private boolean isOpen = true;

	/**
	 * Restaurant Image
	 */
	@Lob
	@Column(nullable = true, columnDefinition = "longblob")
	private byte[] restaurantImg;

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

	/**
	 * @return the isOpen
	 */
	public Boolean isOpen() {
		return isOpen;
	}

	/**
	 * @param isOpen the isOpen to set
	 */
	public void setOpen(Boolean isOpen) {
		this.isOpen = isOpen;
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

}
