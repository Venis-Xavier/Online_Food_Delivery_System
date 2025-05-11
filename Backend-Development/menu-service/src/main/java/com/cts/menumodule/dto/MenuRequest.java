package com.cts.menumodule.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;



/**
 * Data Transfer Object (DTO) for Menu requests.
 * 
 * @author Dabarra Vishnu
 * @since 27 Feb 2025
 */
public class MenuRequest {
	
	@NotEmpty(message = "Item name must not be Empty")
	private String itemName;

	@NotEmpty(message = "Item description must not be Empty")
	private String itemDesc;

	@Min(value = 1, message = "Price must not be 0")
	@NotNull(message = "Item price must not be Null")
	private Double price;

	@NotNull(message = "Item Availability must not be Null")
	private Boolean isAvailable;
	
	@NotNull(message = "Specify if the Item is veg or not")
	private Boolean isVeg;
	
	@NotNull(message = "Item Image must not be Empty")
	private MultipartFile itemImage;

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
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @return the isAvailable
	 */
	public Boolean getIsAvailable() {
		return isAvailable;
	}

	/**
	 * @param isAvailable the isAvailable to set
	 */
	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	/**
	 * @return the isVeg
	 */
	public Boolean getIsVeg() {
		return isVeg;
	}

	/**
	 * @param isVeg the isVeg to set
	 */
	public void setIsVeg(Boolean isVeg) {
		this.isVeg = isVeg;
	}

	/**
	 * @return the itemImage
	 */
	public MultipartFile getItemImage() {
		return itemImage;
	}

	/**
	 * @param itemImage the itemImage to set
	 */
	public void setItemImage(MultipartFile itemImage) {
		this.itemImage = itemImage;
	}
}
