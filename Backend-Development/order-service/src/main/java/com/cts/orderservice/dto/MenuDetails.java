package com.cts.orderservice.dto;

import java.util.UUID;

public class MenuDetails {
	private UUID cartId;
	
	private UUID itemId;
	
	private String itemName;
	
	private String itemDesc;
	
	private double price;
	
	private boolean isVeg;
	
	private Integer quantity;
	
	private byte[] itemImg;
	
	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public MenuDetails() {}

	/**
	 * @param itemId
	 * @param itemName
	 * @param itemDesc
	 * @param price
	 * @param isVeg
	 */
	public MenuDetails(UUID cartId, UUID itemId, String itemName, String itemDesc, double price, boolean isVeg) {
		this.cartId = cartId;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemDesc = itemDesc;
		this.price = price;
		this.isVeg = isVeg;
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
 	 * @return the cartId
 	 */
 	public UUID getCartId() {
 		return cartId;
 	}
 
 	/**
 	 * @param cartId the cartId to set
 	 */
 	public void setCartId(UUID cartId) {
 		this.cartId = cartId;
 	}

	/**
	 * @return the itemImage
	 */
	public byte[] getItemImg() {
		return itemImg;
	}

	/**
	 * @param itemImage the itemImage to set
	 */
	public void setItemImg(byte[] itemImg) {
		this.itemImg = itemImg;
	}
	
}
