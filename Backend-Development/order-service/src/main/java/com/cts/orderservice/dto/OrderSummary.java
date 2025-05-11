package com.cts.orderservice.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.cts.orderservice.enums.OrderStatus;

public class OrderSummary {
	private UUID orderId;
	private OrderStatus orderStatus;
	private Double billAmount;
	private LocalDateTime orderedAt;
	private UserDetails agentDetails;
	private UserDetails customerDetails;
	private UserDetails restaurantDetails;
	private DeliveryDetails deliverydetails;
	private PaymentDetails paymentDetails;
	private List<MenuDetails> orderedItems;
	
	
	public OrderSummary() {}
	
	/**
	 * @param orderId
	 * @param orderStatus
	 * @param billAmount
	 * @param orderedAt
	 * @param agentDetails
	 * @param customerDetails
	 * @param restaurantDetails
	 * @param deliverydetails
	 * @param paymentDetails
	 * @param orderedItems
	 */
	public OrderSummary(UUID orderId, OrderStatus orderStatus, Double billAmount, LocalDateTime orderedAt,
			UserDetails agentDetails, UserDetails customerDetails, UserDetails restaurantDetails,
			DeliveryDetails deliverydetails, PaymentDetails paymentDetails, List<MenuDetails> orderedItems) {
		this.orderId = orderId;
		this.orderStatus = orderStatus;
		this.billAmount = billAmount;
		this.orderedAt = orderedAt;
		this.agentDetails = agentDetails;
		this.customerDetails = customerDetails;
		this.restaurantDetails = restaurantDetails;
		this.deliverydetails = deliverydetails;
		this.paymentDetails = paymentDetails;
		this.orderedItems = orderedItems;
	}
	/**
	 * @return the orderId
	 */
	public UUID getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(UUID orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the orderStatus
	 */
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	/**
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	/**
	 * @return the billAmount
	 */
	public Double getBillAmount() {
		return billAmount;
	}
	/**
	 * @param billAmount the billAmount to set
	 */
	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}
	/**
	 * @return the orderedAt
	 */
	public LocalDateTime getOrderedAt() {
		return orderedAt;
	}
	/**
	 * @param orderedAt the orderedAt to set
	 */
	public void setOrderedAt(LocalDateTime orderedAt) {
		this.orderedAt = orderedAt;
	}
	/**
	 * @return the agentDetails
	 */
	public UserDetails getAgentDetails() {
		return agentDetails;
	}
	/**
	 * @param agentDetails the agentDetails to set
	 */
	public void setAgentDetails(UserDetails agentDetails) {
		this.agentDetails = agentDetails;
	}
	/**
	 * @return the customerDetails
	 */
	public UserDetails getCustomerDetails() {
		return customerDetails;
	}
	/**
	 * @param customerDetails the customerDetails to set
	 */
	public void setCustomerDetails(UserDetails customerDetails) {
		this.customerDetails = customerDetails;
	}
	/**
	 * @return the restaurantDetails
	 */
	public UserDetails getRestaurantDetails() {
		return restaurantDetails;
	}
	/**
	 * @param restaurantDetails the restaurantDetails to set
	 */
	public void setRestaurantDetails(UserDetails restaurantDetails) {
		this.restaurantDetails = restaurantDetails;
	}
	/**
	 * @return the deliverydetails
	 */
	public DeliveryDetails getDeliverydetails() {
		return deliverydetails;
	}
	/**
	 * @param deliverydetails the deliverydetails to set
	 */
	public void setDeliverydetails(DeliveryDetails deliverydetails) {
		this.deliverydetails = deliverydetails;
	}
	/**
	 * @return the paymentDetails
	 */
	public PaymentDetails getPaymentDetails() {
		return paymentDetails;
	}
	/**
	 * @param paymentDetails the paymentDetails to set
	 */
	public void setPaymentDetails(PaymentDetails paymentDetails) {
		this.paymentDetails = paymentDetails;
	}
	/**
	 * @return the orderedItems
	 */
	public List<MenuDetails> getOrderedItems() {
		return orderedItems;
	}
	/**
	 * @param orderedItems the orderedItems to set
	 */
	public void setOrderedItems(List<MenuDetails> orderedItems) {
		this.orderedItems = orderedItems;
	}
}
