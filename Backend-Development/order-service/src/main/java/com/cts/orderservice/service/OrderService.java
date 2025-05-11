package com.cts.orderservice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.orderservice.dao.CartDAO;
import com.cts.orderservice.dao.OrderDAO;
import com.cts.orderservice.dto.AssignAgentRequest;
import com.cts.orderservice.dto.DeliveryDetail;
import com.cts.orderservice.dto.DeliveryDetails;
import com.cts.orderservice.dto.MenuDetails;
import com.cts.orderservice.dto.MenuItemRequest;
import com.cts.orderservice.dto.OrderSummary;
import com.cts.orderservice.dto.PaymentDetails;
import com.cts.orderservice.dto.PaymentRequest;
import com.cts.orderservice.dto.Response;
import com.cts.orderservice.dto.StatusUpdateRequest;
import com.cts.orderservice.dto.UserDetails;
import com.cts.orderservice.enums.DeliveryStatus;
import com.cts.orderservice.enums.IdentifierType;
import com.cts.orderservice.enums.OrderStatus;
import com.cts.orderservice.enums.PaymentMethod;
import com.cts.orderservice.enums.Roles;
import com.cts.orderservice.exceptions.FailedServiceRequestException;
import com.cts.orderservice.exceptions.ForbiddenRequestException;
import com.cts.orderservice.exceptions.OrderNotFoundException;
import com.cts.orderservice.exceptions.RestaurantMismatchException;
import com.cts.orderservice.feign.AuthInterface;
import com.cts.orderservice.feign.DeliveryInterface;
import com.cts.orderservice.feign.MenuInterface;
import com.cts.orderservice.feign.PaymentInterface;
import com.cts.orderservice.model.CartItem;
import com.cts.orderservice.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

/**
 * Service class for managing orders and cart items in the online food delivery
 * system. Provides methods to create orders, add items to cart, view all
 * orders, and update order status.
 * 
 * @Author Bhimisetty Renu Sai Ritvik
 * @Since 27 Feb 2025
 */
@Service
public class OrderService {

	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	private CartDAO cartDAO;

	@Autowired
	private DeliveryInterface deliveryInterface;

	@Autowired
	private MenuInterface menuInterface;

	@Autowired
	private PaymentInterface paymentInterface;

	@Autowired
	private AuthInterface authInterface;

	/**
	 * Retrieves all orders.
	 * 
	 * @return List of all orders.
	 */
	public List<Order> getAllOrders(UUID restaurantId) {
		return orderDAO.findAllByRestaurantId(restaurantId);
	}

	/**
	 * Adds an item to the cart of an existing order.
	 * 
	 * @param customerId   The unique identifier of the customer.
	 * @param itemId       The unique identifier of the item.
	 * @param restaurantId The unique identifier of the restaurant.
	 * @param quantity     The quantity of the item to be added.
	 * @return The added cart item.
	 */
	@Transactional
	public Order addToCart(UUID customerId, UUID itemId, UUID restaurantId, int quantity) {
		Order order = orderDAO.findByCustomerIdAndStatus(customerId, OrderStatus.IN_CART)
				.orElseGet(() -> orderDAO.save(new Order(customerId, restaurantId)));
		
		if(!order.getRestaurantId().equals(restaurantId)) {
			throw new RestaurantMismatchException("Couldn't Add Item to Cart.  Clear Existing Cart to Continue");
		}

		Double price = 0.0;
		
		try {
			price = fetchItemPrice(itemId);
		} catch (Exception e) {

		}

		CartItem cartItem = cartDAO.findByItemIdAndOrderOrderId(itemId, order.getOrderId())
				.orElse(new CartItem(itemId, order));

		cartItem.setQuantity(quantity);
		cartItem.setSubTotal(quantity * price);
		cartItem.setInCart(true);
		cartDAO.save(cartItem);

		List<CartItem> cart = cartDAO.findAllByOrderOrderId(order.getOrderId());

		Double totalAmount = 0.0;

		for (CartItem item : cart) {
			if(item.isInCart()) {
				totalAmount += item.getSubTotal();
			}
		}

		order.setTotalAmount(totalAmount);

		return orderDAO.save(order);
	}
	

	/**
	 * Updates the status of an existing order.
	 *
	 * @param userId  The unique identifier of the user making the request.
	 * @param orderId The unique identifier of the order.
	 * @param status  The new status of the order.
	 * @return The updated order.
	 * @throws OrderNotFoundException    if the order is not found.
	 * @throws ForbiddenRequestException if the user is unauthorized to perform the
	 *                                   update.
	 */
	@Transactional
	public Order updateOrderStatus(UUID userId, UUID orderId, OrderStatus status) {

		Order order = orderDAO.findById(orderId)
				.orElseThrow(() -> new OrderNotFoundException("No Order Found with ID: " + orderId));

		switch (status) {
		case PLACED:
			handlePlacedStatus(order);
			break;
		case ACCEPTED:
			handleAcceptedStatus(userId, order);
			break;
		case COMPLETED:
			handleCompletedStatus(userId, order);
			break;
		default:
			break;
		}

		order.setStatus(status);
		return orderDAO.save(order);
	}

	/**
	 * To fetch delivery details from the delivery service.
	 * 
	 * @param order
	 * @return the delivery details.
	 */
	private DeliveryDetail fetchDeliveryDetail(Order order) {
		AssignAgentRequest agentRequest = new AssignAgentRequest(order.getOrderId(), order.getCustomerId(),
				order.getRestaurantId());
		Response<?> deliveryResponse = deliveryInterface.assignDeliveryAgent(agentRequest).getBody();

		if (!deliveryResponse.getSuccess()) {
			throw new FailedServiceRequestException("Failed to Fetch Delivery Details from Delivery Service");
		}

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.convertValue(deliveryResponse.getData(), DeliveryDetail.class);
	}

	/**
	 * To fetch payment details from the payment service.
	 * 
	 * @param order
	 * @param agentId
	 * @return unique ID of payment record.
	 */
	private UUID fetchPaymentId(Order order, UUID agentId) {
		PaymentRequest paymentRequest = new PaymentRequest(order.getOrderId(), order.getCustomerId(), agentId,
				order.getRestaurantId(), PaymentMethod.CASH, order.getTotalAmount());
		Response<?> paymentResponse = paymentInterface.initializePayment(paymentRequest).getBody();

		if (paymentResponse == null || !paymentResponse.getSuccess()) {
			throw new FailedServiceRequestException("Failed to Fetch Payment ID from Payment Service");
		}

		return UUID.fromString(paymentResponse.getData().toString());
	}

	/**
	 * To fetch item price from the menu service.
	 * 
	 * @param itemId
	 * @return price of the Item.
	 */
	private Double fetchItemPrice(UUID itemId) {
		MenuItemRequest request = new MenuItemRequest(itemId);
		Response<?> response = menuInterface.getPriceOfItem(request).getBody();

		if (response == null || !response.getSuccess()) {
			throw new FailedServiceRequestException("Failed to fetch price from menu service");
		}
		return (Double) response.getData();
	}

	/**
	 * To update the delivery status using deliveryId
	 * 
	 * @param deliveryId
	 */
	private void updateDeliveryStatus(UUID deliveryId) {
		StatusUpdateRequest updateRequest = new StatusUpdateRequest(deliveryId, DeliveryStatus.DELIVERED);
		Response<?> response = deliveryInterface.updateStatus(updateRequest).getBody();

		if (response == null || !response.getSuccess()) {
			throw new FailedServiceRequestException("Unable to update Delivery Status");
		}
	}

	/**
	 * To update the delivery status using paymentId
	 * 
	 * @param paymentId
	 */
	private void updatePaymentStatus(UUID paymentId) {
		Response<?> response = paymentInterface.updatePaymentStatus(paymentId).getBody();
		if (response == null || !response.getSuccess()) {
			throw new FailedServiceRequestException("Unable to update Payment Status");
		}
	}

	@Transactional
	public void removeItemFromCart(UUID cartId) {
		CartItem item = cartDAO.findById(cartId)
				.orElseThrow(() -> new RuntimeException("No Item found with ID: " + cartId));

		Order order = orderDAO.findById(item.getOrder().getOrderId()).orElseThrow(
				() -> new OrderNotFoundException("No Order found with ID: " + item.getOrder().getOrderId()));

		if(item.isInCart()) {
			order.setTotalAmount(order.getTotalAmount() - item.getSubTotal());
		}
		
		order = orderDAO.save(order);

		item.setInCart(false);
		cartDAO.save(item);
		
		List<CartItem> items = order.getItems().stream()
				.filter(CartItem::isInCart)
				.collect(Collectors.toList());
		
		if(items.size() == 0) {
			deleteOrder(order.getOrderId());
		}

	}

	private void handlePlacedStatus(Order order) {
		order.setOrderedAt(LocalDateTime.now());
	}

	private void handleAcceptedStatus(UUID userId, Order order) throws ForbiddenRequestException {

		if (!userId.equals(order.getRestaurantId())) {
			throw new ForbiddenRequestException("Unauthorized! Cannot update the order status for another restaurant.");
		}

		DeliveryDetail deliveryDetail = fetchDeliveryDetail(order);
		assignDeliveryAgent(order, deliveryDetail);
		initiatePayment(order, deliveryDetail.getAgentId());
	}

	private void assignDeliveryAgent(Order order, DeliveryDetail deliveryDetail) {
		order.setDeliveryId(deliveryDetail.getDeliveryId());
		order.setAgentId(deliveryDetail.getAgentId());
	}

	private void initiatePayment(Order order, UUID agentId) {
		UUID paymentId = fetchPaymentId(order, agentId);
		order.setPaymentId(paymentId);
	}

	private void handleCompletedStatus(UUID userId, Order order) throws ForbiddenRequestException {

		if (!userId.equals(order.getAgentId())) {
			throw new ForbiddenRequestException("Unauthorized! Cannot update status of unassigned order.");
		}
		updateDeliveryStatus(order.getDeliveryId());
		updatePaymentStatus(order.getPaymentId());
	}

	public List<OrderSummary> findOrdersByStatus(UUID userId, OrderStatus status, Roles role) {

		List<Order> orders;
		List<OrderSummary> orderSummary = null;

		switch (role) {
		case MANAGER:
			orders = orderDAO.findAllByRestaurantIdAndStatus(userId, status);
			orderSummary = prepareSummary(orders);
			break;

		case CUSTOMER:
			orders = orderDAO.findAllByCustomerIdAndStatus(userId, status);
			orderSummary = prepareSummary(orders);
			break;

		case AGENT:
			orders = orderDAO.findAllByAgentIdAndStatus(userId, status);
			orderSummary = prepareSummary(orders);
			break;

		}

		return orderSummary;
	}

	private List<OrderSummary> prepareSummary(List<Order> orders) {
		Set<UUID> customerIds = extractIds(orders, IdentifierType.CUSTOMER);
		Set<UUID> restaurantIds = extractIds(orders, IdentifierType.RESTAURANT);
		Set<UUID> deliveryIds = extractIds(orders, IdentifierType.DELIVERY);
		Set<UUID> agentIds = extractIds(orders, IdentifierType.AGENT);
		Set<UUID> paymentIds = extractIds(orders, IdentifierType.PAYMENT);
		Set<UUID> menuIds = extractIds(orders, IdentifierType.MENU);

		Map<UUID, PaymentDetails> paymentDetailsMap = paymentInterface.fetchPaymentData(paymentIds).getBody().getData()
				.stream().collect(Collectors.toMap(PaymentDetails::getPaymentId, Function.identity()));

		Map<UUID, DeliveryDetails> deliveryDetailsMap = deliveryInterface.fetchDeliveryData(deliveryIds).getBody()
				.getData().stream().collect(Collectors.toMap(DeliveryDetails::getDeliveryId, Function.identity()));

		Map<UUID, UserDetails> agentDetailsMap = authInterface.fetchUserData(agentIds).getBody().getData().stream()
				.collect(Collectors.toMap(UserDetails::getUserId, Function.identity()));

		Map<UUID, UserDetails> restaurantDetailsMap = authInterface.fetchUserData(restaurantIds).getBody().getData()
				.stream().collect(Collectors.toMap(UserDetails::getUserId, Function.identity()));

		Map<UUID, UserDetails> customerDetailsMap = authInterface.fetchUserData(customerIds).getBody().getData()
				.stream().collect(Collectors.toMap(UserDetails::getUserId, Function.identity()));

		Map<UUID, MenuDetails> menuDetailsMap = menuInterface.getItems(menuIds).getBody().getData().stream()
				.collect(Collectors.toMap(MenuDetails::getItemId, Function.identity()));

		return orders.stream().map(order -> {
			List<MenuDetails> orderedItems = order.getItems().stream()
					.filter(CartItem::isInCart)
					.map(item -> {
						MenuDetails details = menuDetailsMap.get(item.getItemId());
						details.setQuantity(item.getQuantity());
						details.setCartId(item.getCartId());
//						details.setItemImage(item.get)
						return details;
					})
					.filter(Objects::nonNull)
					.collect(Collectors.toList());

			return new OrderSummary(order.getOrderId(), order.getStatus(), order.getTotalAmount(), order.getOrderedAt(),
					agentDetailsMap.get(order.getAgentId()), customerDetailsMap.get(order.getCustomerId()),
					restaurantDetailsMap.get(order.getRestaurantId()), deliveryDetailsMap.get(order.getDeliveryId()),
					paymentDetailsMap.get(order.getPaymentId()), orderedItems);
		}).collect(Collectors.toList());
	}

	private Set<UUID> extractIds(List<Order> orders, IdentifierType type) {
		Set<UUID> ids = new HashSet<>();

		switch (type) {
		case AGENT:
			ids = orders.stream().map(Order::getAgentId).filter(Objects::nonNull).collect(Collectors.toSet());
			break;

		case CUSTOMER:
			ids = orders.stream().map(Order::getCustomerId).collect(Collectors.toSet());
			break;

		case RESTAURANT:
			ids = orders.stream().map(Order::getRestaurantId).collect(Collectors.toSet());
			break;

		case DELIVERY:
			ids = orders.stream().map(Order::getDeliveryId).filter(Objects::nonNull).collect(Collectors.toSet());
			break;

		case PAYMENT:
			ids = orders.stream().map(Order::getPaymentId).filter(Objects::nonNull).collect(Collectors.toSet());
			break;

		case MENU:
			ids = orders.stream().flatMap(order -> order.getItems().stream()).map(CartItem::getItemId)
					.collect(Collectors.toSet());
			break;
		}

		return ids;
	}

	public void deleteOrder(UUID orderId) {
		orderDAO.deleteById(orderId);
	}
}
