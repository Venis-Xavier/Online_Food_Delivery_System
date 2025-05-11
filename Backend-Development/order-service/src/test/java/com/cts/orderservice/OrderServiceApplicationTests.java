package com.cts.orderservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.*;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cts.orderservice.service.OrderService;
import com.cts.orderservice.dao.CartDAO;
import com.cts.orderservice.dao.OrderDAO;
import com.cts.orderservice.dto.DeliveryDetails;
import com.cts.orderservice.dto.MenuDetails;
import com.cts.orderservice.dto.OrderSummary;
import com.cts.orderservice.dto.PaymentDetails;
import com.cts.orderservice.dto.Response;
import com.cts.orderservice.dto.UserDetails;
import com.cts.orderservice.enums.*;
import com.cts.orderservice.exceptions.*;
import com.cts.orderservice.feign.*;
import com.cts.orderservice.model.*;

class OrderServiceApplicationTests {

    @Mock
    private OrderDAO orderDAO;

    @Mock
    private CartDAO cartDAO;

    @Mock
    private DeliveryInterface deliveryInterface;

    @Mock
    private MenuInterface menuInterface;

    @Mock
    private PaymentInterface paymentInterface;

    @Mock
    private AuthInterface authInterface;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddToCart() {
        UUID customerId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        int quantity = 2;
        double price = 10.0;
        UUID orderId = UUID.randomUUID();

        Order order = new Order(customerId, restaurantId);
        order.setOrderId(orderId);

        when(orderDAO.findByCustomerIdAndRestaurantIdAndStatus(customerId, restaurantId, OrderStatus.IN_CART))
                .thenReturn(Optional.of(order));
        when(menuInterface.getPriceOfItem(any())).thenReturn(new ResponseEntity<>(new Response<>(true, HttpStatus.OK, price, null), HttpStatus.OK));
        when(cartDAO.findByItemIdAndOrderOrderId(itemId, orderId))
                .thenReturn(Optional.of(new CartItem(itemId, order)));
        when(orderDAO.save(any(Order.class))).thenReturn(order);
        when(orderDAO.findById(orderId)).thenReturn(Optional.of(order));
        when(cartDAO.save(any(CartItem.class))).thenReturn(new CartItem(itemId, order));

        Order result = orderService.addToCart(customerId, itemId, restaurantId, quantity);

        assertNotNull(result);
        assertEquals(quantity * price, result.getTotalAmount());
        verify(orderDAO, times(1)).save(order);
        verify(cartDAO, times(1)).save(any(CartItem.class));
        verify(menuInterface, times(1)).getPriceOfItem(any());
    }

    @Test
    void testUpdateOrderStatus() {
        UUID userId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        OrderStatus status = OrderStatus.PLACED;

        Order order = new Order();
        order.setOrderId(orderId);

        when(orderDAO.findById(orderId)).thenReturn(Optional.of(order));
        when(orderDAO.save(any(Order.class))).thenReturn(order);

        Order result = orderService.updateOrderStatus(userId, orderId, status);

        assertNotNull(result);
        assertEquals(status, result.getStatus());
        verify(orderDAO, times(1)).save(order);
    }

    @Test
    void testUpdateOrderStatus_OrderNotFound() {
        UUID userId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        OrderStatus status = OrderStatus.PLACED;

        when(orderDAO.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.updateOrderStatus(userId, orderId, status));
    }

    @Test
    void testRemoveItemFromCart() {
        UUID userId = UUID.randomUUID();
        UUID cartId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();

        Order order = new Order();
        order.setOrderId(orderId);
        order.setCustomerId(userId);

        CartItem cartItem = new CartItem();
        cartItem.setCartId(cartId);
        cartItem.setOrder(order);

        when(cartDAO.findById(cartId)).thenReturn(Optional.of(cartItem));
        when(orderDAO.findById(orderId)).thenReturn(Optional.of(order));

        orderService.removeItemFromCart(userId, cartId);

        verify(cartDAO, times(1)).deleteById(cartId);
    }

    @Test
    void testRemoveItemFromCart_ItemNotFound() {
        UUID userId = UUID.randomUUID();
        UUID cartId = UUID.randomUUID();

        when(cartDAO.findById(cartId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.removeItemFromCart(userId, cartId));
    }

    @Test
    void testRemoveItemFromCart_OrderNotFound() {
        UUID userId = UUID.randomUUID();
        UUID cartId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();

        Order order = new Order();
        order.setOrderId(orderId);

        CartItem cartItem = new CartItem();
        cartItem.setCartId(cartId);
        cartItem.setOrder(order);

        when(cartDAO.findById(cartId)).thenReturn(Optional.of(cartItem));
        when(orderDAO.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.removeItemFromCart(userId, cartId));
    }

    @Test
    void testRemoveItemFromCart_Forbidden() {
        UUID userId = UUID.randomUUID();
        UUID cartId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        UUID anotherUserId = UUID.randomUUID();

        Order order = new Order();
        order.setOrderId(orderId);
        order.setCustomerId(anotherUserId);

        CartItem cartItem = new CartItem();
        cartItem.setCartId(cartId);
        cartItem.setOrder(order);

        when(cartDAO.findById(cartId)).thenReturn(Optional.of(cartItem));
        when(orderDAO.findById(orderId)).thenReturn(Optional.of(order));

        assertThrows(ForbiddenRequestException.class, () -> orderService.removeItemFromCart(userId, cartId));
    }

    @Test
    void testFindOrdersByStatus_Manager() {
        UUID userId = UUID.randomUUID();
        OrderStatus status = OrderStatus.PLACED;
        Roles role = Roles.MANAGER;

        List<Order> orders = new ArrayList<>();
        Order order1 = new Order();
        UUID deliveryId1 = UUID.randomUUID();
        order1.setDeliveryId(deliveryId1);
        order1.setItems(new ArrayList<>()); // Initialize items list
        orders.add(order1);
        Set<UUID> deliveryIds = Collections.singleton(deliveryId1);
        when(orderDAO.findAllByRestaurantIdAndStatus(userId, status)).thenReturn(orders);

        ResponseEntity<Response<List<PaymentDetails>>> paymentResponse =
                new ResponseEntity<>(new Response<>(true, HttpStatus.OK, Collections.emptyList(), null), HttpStatus.OK);
        when(paymentInterface.fetchPaymentData(any())).thenReturn(paymentResponse);

        List<DeliveryDetails> mockDeliveryDetails = Arrays.asList(new DeliveryDetails(UUID.randomUUID(), DeliveryStatus.PENDING));
        ResponseEntity<Response<List<DeliveryDetails>>> deliveryResponse =
                new ResponseEntity<>(new Response<>(true, HttpStatus.OK, mockDeliveryDetails, null), HttpStatus.OK);
        when(deliveryInterface.fetchDeliveryData(eq(deliveryIds))).thenReturn(deliveryResponse);

        ResponseEntity<Response<List<UserDetails>>> authResponse =
                new ResponseEntity<>(new Response<>(true, HttpStatus.OK, Collections.emptyList(), null), HttpStatus.OK);
        when(authInterface.fetchUserData(any())).thenReturn(authResponse);

        ResponseEntity<Response<List<MenuDetails>>> menuResponse =
                new ResponseEntity<>(new Response<>(true, HttpStatus.OK, Collections.emptyList(), null), HttpStatus.OK);
        when(menuInterface.getItems(any())).thenReturn(menuResponse);

        List<OrderSummary> result = orderService.findOrdersByStatus(userId, status, role);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testFindOrdersByStatus_Customer() {
        UUID userId = UUID.randomUUID();
        OrderStatus status = OrderStatus.PLACED;
        Roles role = Roles.CUSTOMER;

        List<Order> orders = new ArrayList<>();
        Order order1 = new Order();
        UUID deliveryId1 = UUID.randomUUID();
        order1.setDeliveryId(deliveryId1);
        order1.setItems(new ArrayList<>()); // Initialize items list
        orders.add(order1);
        Set<UUID> deliveryIds = Collections.singleton(deliveryId1);
        when(orderDAO.findAllByCustomerIdAndStatus(userId, status)).thenReturn(orders);

        ResponseEntity<Response<List<PaymentDetails>>> paymentResponse =
                new ResponseEntity<>(new Response<>(true, HttpStatus.OK, Collections.emptyList(), null), HttpStatus.OK);
        when(paymentInterface.fetchPaymentData(any())).thenReturn(paymentResponse);

        List<DeliveryDetails> mockDeliveryDetails = Arrays.asList(new DeliveryDetails(UUID.randomUUID(), DeliveryStatus.PENDING));
        ResponseEntity<Response<List<DeliveryDetails>>> deliveryResponse =
                new ResponseEntity<>(new Response<>(true, HttpStatus.OK, mockDeliveryDetails, null), HttpStatus.OK);
        when(deliveryInterface.fetchDeliveryData(eq(deliveryIds))).thenReturn(deliveryResponse);

        ResponseEntity<Response<List<UserDetails>>> authResponse =
                new ResponseEntity<>(new Response<>(true, HttpStatus.OK, Collections.emptyList(), null), HttpStatus.OK);
        when(authInterface.fetchUserData(any())).thenReturn(authResponse);

        ResponseEntity<Response<List<MenuDetails>>> menuResponse =
                new ResponseEntity<>(new Response<>(true, HttpStatus.OK, Collections.emptyList(), null), HttpStatus.OK);
        when(menuInterface.getItems(any())).thenReturn(menuResponse);

        List<OrderSummary> result = orderService.findOrdersByStatus(userId, status, role);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testFindOrdersByStatus_Agent() {
        UUID userId = UUID.randomUUID();
        OrderStatus status = OrderStatus.PLACED;
        Roles role = Roles.AGENT;

        List<Order> orders = new ArrayList<>();
        Order order1 = new Order();
        UUID deliveryId1 = UUID.randomUUID();
        order1.setDeliveryId(deliveryId1);
        order1.setItems(new ArrayList<>()); // Initialize items list
        orders.add(order1);
        Set<UUID> deliveryIds = Collections.singleton(deliveryId1);
        when(orderDAO.findAllByAgentIdAndStatus(userId, status)).thenReturn(orders);

        ResponseEntity<Response<List<PaymentDetails>>> paymentResponse =
                new ResponseEntity<>(new Response<>(true, HttpStatus.OK, Collections.emptyList(), null), HttpStatus.OK);
        when(paymentInterface.fetchPaymentData(any())).thenReturn(paymentResponse);

        List<DeliveryDetails> mockDeliveryDetails = Arrays.asList(new DeliveryDetails(UUID.randomUUID(), DeliveryStatus.PENDING));
        ResponseEntity<Response<List<DeliveryDetails>>> deliveryResponse =
                new ResponseEntity<>(new Response<>(true, HttpStatus.OK, mockDeliveryDetails, null), HttpStatus.OK);
        when(deliveryInterface.fetchDeliveryData(eq(deliveryIds))).thenReturn(deliveryResponse);

        ResponseEntity<Response<List<UserDetails>>> authResponse =
                new ResponseEntity<>(new Response<>(true, HttpStatus.OK, Collections.emptyList(), null), HttpStatus.OK);
        when(authInterface.fetchUserData(any())).thenReturn(authResponse);

        ResponseEntity<Response<List<MenuDetails>>> menuResponse =
                new ResponseEntity<>(new Response<>(true, HttpStatus.OK, Collections.emptyList(), null), HttpStatus.OK);
        when(menuInterface.getItems(any())).thenReturn(menuResponse);

        List<OrderSummary> result = orderService.findOrdersByStatus(userId, status, role);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

//    @Test
//    void testGetMenuDetailsByRestaurantId() {
//        UUID restaurantId = UUID.randomUUID();
//        List<MenuDetails> menuDetailsList = Arrays.asList(
//                new MenuDetails(UUID.randomUUID(), "Item 1", "Desc 1", 10.0, true),
//                new MenuDetails(UUID.randomUUID(), "Item 2", "Desc 2", 15.0, false)
//        );
//
//        Response<List<MenuDetails>> response = new Response<>(true, HttpStatus.OK, menuDetailsList, null);
//        ResponseEntity<Response<List<MenuDetails>>> responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
//
//        when(menuInterface.getItems(any())).thenReturn(responseEntity);
//
//        ResponseEntity<Response<List<MenuDetails>>> result = orderService.getMenuDetailsByRestaurantId(restaurantId);
//
//        assertNotNull(result);
//        assertEquals(HttpStatus.OK, result.getStatusCode());
//        assertNotNull(result.getBody());
//        assertTrue(result.getBody().getSuccess());
//        assertEquals(menuDetailsList, result.getBody().getData());
//        assertNull(result.getBody().getErrorMessage());
//        verify(menuInterface, times(1)).getItems(any());
//    }
}