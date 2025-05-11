package com.cts.paymentservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cts.paymentservice.service.PaymentService;
import com.cts.paymentservice.dao.PaymentDAO;
import com.cts.paymentservice.dto.PaymentDetails;
import com.cts.paymentservice.enums.PaymentMethod;
import com.cts.paymentservice.enums.PaymentStatus;
import com.cts.paymentservice.exceptions.PaymentNotFoundException;
import com.cts.paymentservice.model.Payment;

class PaymentServiceApplicationTests {

    @Mock
    private PaymentDAO paymentDAO;

    @InjectMocks
    private PaymentService paymentService;

    private UUID orderId;
    private UUID customerId;
    private UUID agentId;
    private UUID restaurantId;
    private UUID paymentId;
    private PaymentMethod paymentMethod;
    private double amount;
    private Payment payment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderId = UUID.randomUUID();
        customerId = UUID.randomUUID();
        agentId = UUID.randomUUID();
        restaurantId = UUID.randomUUID();
        paymentId = UUID.randomUUID();
        paymentMethod = PaymentMethod.UPI;
        amount = 100.0;
        payment = new Payment(orderId, customerId, agentId, restaurantId, paymentMethod, amount);
        payment.setPaymentId(paymentId);
    }

    @Test
    void testNewPaymentRecord() {
        when(paymentDAO.save(any(Payment.class))).thenReturn(payment);
        UUID newPaymentId = paymentService.newPaymentRecord(orderId, customerId, agentId, restaurantId, paymentMethod, amount);
        assertEquals(paymentId, newPaymentId);
        verify(paymentDAO, times(1)).save(any(Payment.class));
    }

    @Test
    void testUpdateStatus_Success() {
        when(paymentDAO.findById(paymentId)).thenReturn(Optional.of(payment));
        when(paymentDAO.save(any(Payment.class))).thenReturn(payment);
        Payment updatedPayment = paymentService.updateStatus(paymentId);
        assertEquals(PaymentStatus.COMPLETED, updatedPayment.getPaymentStatus());
        verify(paymentDAO, times(1)).findById(paymentId);
        verify(paymentDAO, times(1)).save(any(Payment.class));
    }

    @Test
    void testUpdateStatus_NotFound() {
        when(paymentDAO.findById(paymentId)).thenReturn(Optional.empty());
        assertThrows(PaymentNotFoundException.class, () -> paymentService.updateStatus(paymentId));
        verify(paymentDAO, times(1)).findById(paymentId);
        verify(paymentDAO, never()).save(any(Payment.class));
    }

    @Test
    void testFindAllByCustomerId() {
        Payment payment2 = new Payment(UUID.randomUUID(), customerId, UUID.randomUUID(), UUID.randomUUID(), PaymentMethod.UPI, 50.0);
        List<Payment> payments = Arrays.asList(payment, payment2);
        when(paymentDAO.findAllByCustomerId(customerId)).thenReturn(payments);
        List<Payment> foundPayments = paymentService.findAllByCustomerId(customerId);
        assertEquals(2, foundPayments.size());
        assertEquals(customerId, foundPayments.get(0).getCustomerId());
        assertEquals(customerId, foundPayments.get(1).getCustomerId());
        verify(paymentDAO, times(1)).findAllByCustomerId(customerId);
    }

    @Test
    void testFindAllByRestaurantId() {
        Payment payment2 = new Payment(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), restaurantId, PaymentMethod.CARD, 75.0);
        List<Payment> payments = Arrays.asList(payment, payment2);
        when(paymentDAO.findAllByRestaurantId(restaurantId)).thenReturn(payments);
        List<Payment> foundPayments = paymentService.findAllByRestaurantId(restaurantId);
        assertEquals(2, foundPayments.size());
        assertEquals(restaurantId, foundPayments.get(0).getRestaurantId());
        assertEquals(restaurantId, foundPayments.get(1).getRestaurantId());
        verify(paymentDAO, times(1)).findAllByRestaurantId(restaurantId);
    }

    @Test
    void testFindByOrderId_Success() {
        when(paymentDAO.findByOrderId(orderId)).thenReturn(Optional.of(payment));
        Payment foundPayment = paymentService.findByOrderId(orderId);
        assertEquals(paymentId, foundPayment.getPaymentId());
        assertEquals(orderId, foundPayment.getOrderId());
        verify(paymentDAO, times(1)).findByOrderId(orderId);
    }

    @Test
    void testFindByOrderId_NotFound() {
        when(paymentDAO.findByOrderId(orderId)).thenReturn(Optional.empty());
        assertThrows(PaymentNotFoundException.class, () -> paymentService.findByOrderId(orderId));
        verify(paymentDAO, times(1)).findByOrderId(orderId);
    }

    @Test
    void testFetchDetails_Success() {
        UUID paymentId2 = UUID.randomUUID();
        Payment payment2 = new Payment(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), PaymentMethod.UPI, 60.0);
        payment2.setPaymentId(paymentId2);
        Set<UUID> paymentList = new HashSet<>(Arrays.asList(paymentId, paymentId2));
        when(paymentDAO.findById(paymentId)).thenReturn(Optional.of(payment));
        when(paymentDAO.findById(paymentId2)).thenReturn(Optional.of(payment2));

        List<PaymentDetails> paymentDetailsList = paymentService.fetchDetails(paymentList);

        assertEquals(2, paymentDetailsList.size());

        PaymentDetails details1 = paymentDetailsList.stream().filter(pd -> pd.getPaymentId().equals(paymentId)).findFirst().orElse(null);
        assertNotNull(details1);
        assertEquals(amount, details1.getAmountPaid());
        assertEquals(paymentMethod, details1.getPaymentMode());
        assertEquals(PaymentStatus.PENDING, details1.getPaymentStatus());
        assertEquals(paymentId, details1.getPaymentId());

        PaymentDetails details2 = paymentDetailsList.stream().filter(pd -> pd.getPaymentId().equals(paymentId2)).findFirst().orElse(null);
        assertNotNull(details2);
        assertEquals(60.0, details2.getAmountPaid());
        assertEquals(PaymentMethod.UPI, details2.getPaymentMode());
        assertEquals(PaymentStatus.PENDING, details2.getPaymentStatus());
        assertEquals(paymentId2, details2.getPaymentId());

        verify(paymentDAO, times(1)).findById(paymentId);
        verify(paymentDAO, times(1)).findById(paymentId2);
    }

    @Test
    void testFetchDetails_NotFound() {
        UUID nonExistingPaymentId = UUID.randomUUID();
        Set<UUID> paymentList = new HashSet<>(Arrays.asList(paymentId, nonExistingPaymentId));
        when(paymentDAO.findById(paymentId)).thenReturn(Optional.of(payment));
        when(paymentDAO.findById(nonExistingPaymentId)).thenReturn(Optional.empty());

        assertThrows(PaymentNotFoundException.class, () -> paymentService.fetchDetails(paymentList));

        //verify(paymentDAO, times(1)).findById(paymentId);
        verify(paymentDAO, times(1)).findById(nonExistingPaymentId);
    }
}