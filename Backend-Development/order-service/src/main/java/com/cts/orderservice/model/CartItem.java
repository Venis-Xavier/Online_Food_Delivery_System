package com.cts.orderservice.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Represents a cart item in the online food delivery system.
 * This entity is mapped to the "cart" table.
 * 
 * @author Bhimisetty Renu Sai Ritvik
 * @since 25 Feb 2025
 */

@Data
@Entity
@Table(name = "cart_items")
public class CartItem{
	
	public CartItem(){
		
	}
	
	public CartItem(UUID itemId, Order order) {
		this.itemId = itemId;
		this.order = order;
	}
    
    /**
     * The unique identifier for the cart item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID cartId;

    /**
     * The item ID for the product in the cart.
     */
    @Column(nullable = false)
    private UUID itemId;
    
    /**
     * Quantity of selected menu item.
     */
    @Column(nullable = false)
    private int quantity;
    
    /**
     * Quantity * price of each item.
     */
    @Column(nullable = false)
    private double subTotal;
    
    /**
     * The order to which this cart item belongs.
     * The foreign key column is 'order_id'.
     */
//    @ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    private boolean inCart = true; 
}
