package com.cts.orderservice.model;

import java.util.UUID;
import java.time.LocalDateTime;
import java.util.List;

import com.cts.orderservice.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Represents an order in the online food delivery system.
 * This entity is mapped to the "order" table.
 * 
 * @author Bhimisetty Renu Sai Ritvik
 * @since 25 Feb 2025
 */
@Data
@Entity
@Table(name = "orders")
public class Order {
	
	public Order() {
		
	}
	
	public Order(UUID customerId, UUID restaurantId) {
		this.customerId = customerId;
		this.restaurantId = restaurantId;
	}
    
    /**
     * The unique identifier for the order.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderId;
    
    /**
     * The customer who placed the order.
     */
    @Column(nullable = false)
    private UUID customerId;
    
    /**
     * The restaurant where the order was placed.
     */
    @Column(nullable = false)
    private UUID restaurantId;
    
    /**
     * The delivery details for the order.
     */
    @Column(unique = true)
    private UUID deliveryId;
    
    /**
     * ID of the delivery agent
     */
    @Column
    private UUID agentId;
    
    /**
     * The payment details for the order.
     */
    @Column(unique = true)
    private UUID paymentId;
    
    /**
     * The status of the order.
     */
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.IN_CART;
    
    /**
     * The total amount for the order.
     */
    @Column(nullable = false)
    private double totalAmount;
    
    /**
     * The date when order is placed.
     */
    @Column(nullable = true)
    private LocalDateTime orderedAt;
    
    /**
     * The list of items in the order.
     * One order can have multiple items.
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<CartItem> items;
}
