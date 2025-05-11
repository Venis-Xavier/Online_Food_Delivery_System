package com.cts.menumodule.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Represents menu in the online food delivery system
 * This entity is mapped to "Menu" Table
 * 
 * @author Dabbara Vishnu
 * @since 25 Feb 2025
 */
@Entity
@Table(name="menu")
public class Menu {
	
	/**
     * The unique identifier for the menu item.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID itemId;
	
	/**
     * The name of the menu item.
     */
	@Column(nullable = false)
	private String itemName;
	
	/**
     * A brief description of the menu item.
     */
	@Column(nullable = false)
	private String itemDesc;
	
	/**
     * The price of the menu item.
     */
	@Column(nullable = false)
	private double price;
	
	/**
     * Indicates whether the menu item is available.
     */
	@Column(nullable = false)
	private boolean isAvailable;
	
	/**
     * Indicates whether the menu item is veg or non veg
     */
	@Column(nullable = false)
	private boolean isVeg;

	/**
     * Menu Item Image
     */
	@Lob
	@Column(nullable = false, columnDefinition = "longblob")
	private byte[] itemImg;

	/**
     * The restaurant to which this menu item belongs.
     * The foreign key column is 'restaurant_id'.
     */
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "restaurant_id", nullable = false)
	private Restaurant restaurant;

	public Menu() {}
	
	/**
	 * @param itemName
	 * @param itemDesc
	 * @param price
	 * @param isAvailable
	 * @param restaurant
	 */
	public Menu(String itemName, String itemDesc, double price, boolean isAvailable, 
			Restaurant restaurant, boolean isVeg, byte[] itemImage) {
		this.itemName = itemName;
		this.itemDesc = itemDesc;
		this.price = price;
		this.isAvailable = isAvailable;
		this.restaurant = restaurant;
		this.isVeg = isVeg;
		this.itemImg = itemImage;
	}

	/**
	 * @return the itemId
	 */
	public UUID getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(UUID itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the itemDesc
	 */
	public String getItemDesc() {
		return itemDesc;
	}

	/**
	 * @param itemDesc the itemDesc to set
	 */
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the isAvailable
	 */
	public boolean isAvailable() {
		return isAvailable;
	}

	/**
	 * @param isAvailable the isAvailable to set
	 */
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	/**
	 * @return the isVeg
	 */
	public boolean isVeg() {
		return isVeg;
	}

	/**
	 * @param isVeg the isVeg to set
	 */
	public void setVeg(boolean isVeg) {
		this.isVeg = isVeg;
	}

	/**
	 * @return the itemImg
	 */
	public byte[] getItemImg() {
		return itemImg;
	}

	/**
	 * @param itemImg the itemImg to set
	 */
	public void setItemImg(byte[] itemImg) {
		this.itemImg = itemImg;
	}

	/**
	 * @return the restaurant
	 */
	public Restaurant getRestaurant() {
		return restaurant;
	}

	/**
	 * @param restaurant the restaurant to set
	 */
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

}
