package com.cts.deliverymodule;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cts.deliverymodule.service.DeliveryService;
import com.cts.deliverymodule.dao.AgentDAO;
import com.cts.deliverymodule.dao.DeliveryDAO;
import com.cts.deliverymodule.dto.DeliveryDetail;
import com.cts.deliverymodule.dto.DeliveryDetails;
import com.cts.deliverymodule.enums.Availability;
import com.cts.deliverymodule.enums.DeliveryStatus;
import com.cts.deliverymodule.exceptions.AgentUnavailableException;
import com.cts.deliverymodule.exceptions.DeliveryNotFoundException;
import com.cts.deliverymodule.exceptions.ForbiddenRequestException;
import com.cts.deliverymodule.model.Agent;
import com.cts.deliverymodule.model.Delivery;

@ExtendWith(MockitoExtension.class)
public class DeliveryServiceApplicationTests {

    @Mock
    private DeliveryDAO deliveryDAO;

    @Mock
    private AgentDAO agentDAO;

    @InjectMocks
    private DeliveryService deliveryService;

    private UUID userId;
    private UUID deliveryId;
    private UUID orderId;
    private UUID customerId;
    private UUID restaurantId;
    private Agent agent;
    private Delivery delivery;

    @BeforeEach
    public void setUp() {
        userId = UUID.randomUUID();
        deliveryId = UUID.randomUUID();
        orderId = UUID.randomUUID();
        customerId = UUID.randomUUID();
        restaurantId = UUID.randomUUID();

        agent = new Agent(userId);
        agent.setAvailability(Availability.AVAILABLE);

        delivery = new Delivery(orderId, customerId, restaurantId, agent);
        delivery.setDeliveryId(deliveryId);
    }

    @Test
    public void testCreateAgent() {
        deliveryService.createAgent(userId);
        verify(agentDAO, times(1)).save(any(Agent.class));
    }

    @Test
    public void testGetAllDeliveriesOfRestaurant() {
        when(deliveryDAO.findAllByRestaurantId(restaurantId)).thenReturn(Arrays.asList(delivery));
        List<Delivery> deliveries = deliveryService.getAllDeliveriesOfRestaurant(restaurantId);
        assertEquals(1, deliveries.size());
        assertEquals(deliveryId, deliveries.get(0).getDeliveryId());
    }

    @Test
    public void testUpdateDeliveryStatus() {
        when(deliveryDAO.findById(deliveryId)).thenReturn(Optional.of(delivery));
        when(agentDAO.findById(userId)).thenReturn(Optional.of(agent));

        deliveryService.updateDeliveryStatus(userId, deliveryId, DeliveryStatus.DELIVERED);

        assertEquals(DeliveryStatus.DELIVERED, delivery.getStatus());
        assertEquals(Availability.AVAILABLE, agent.getAvailability());
        verify(deliveryDAO, times(1)).save(delivery);
        verify(agentDAO, times(1)).save(agent);
    }

    @Test
    public void testUpdateDeliveryStatus_DeliveryNotFound() {
        when(deliveryDAO.findById(deliveryId)).thenReturn(Optional.empty());
        assertThrows(DeliveryNotFoundException.class, () -> {
            deliveryService.updateDeliveryStatus(userId, deliveryId, DeliveryStatus.DELIVERED);
        });
    }

    @Test
    public void testUpdateDeliveryStatus_ForbiddenRequest() {
        UUID anotherUserId = UUID.randomUUID();
        when(deliveryDAO.findById(deliveryId)).thenReturn(Optional.of(delivery));
        assertThrows(ForbiddenRequestException.class, () -> {
            deliveryService.updateDeliveryStatus(anotherUserId, deliveryId, DeliveryStatus.DELIVERED);
        });
    }

    @Test
    public void testAssignDelivery() {
        when(agentDAO.findAll()).thenReturn(Arrays.asList(agent));
        when(agentDAO.save(any(Agent.class))).thenReturn(agent);
        when(deliveryDAO.save(any(Delivery.class))).thenReturn(delivery);

        DeliveryDetail deliveryDetail = deliveryService.assignDelivery(orderId, customerId, restaurantId);

        assertEquals(deliveryId, deliveryDetail.getDeliveryId());
        assertEquals(userId, deliveryDetail.getAgentId());
        assertEquals(Availability.UNAVAILABLE, agent.getAvailability());

        ArgumentCaptor<Delivery> deliveryCaptor = ArgumentCaptor.forClass(Delivery.class);
        verify(deliveryDAO, times(1)).save(deliveryCaptor.capture());
        Delivery savedDelivery = deliveryCaptor.getValue();
        assertEquals(orderId, savedDelivery.getOrderId());
        assertEquals(customerId, savedDelivery.getCustomerId());
        assertEquals(restaurantId, savedDelivery.getRestaurantId());
        assertEquals(agent, savedDelivery.getAgent());
    }

    @Test
    public void testAssignDelivery_NoAgentsAvailable() {
        when(agentDAO.findAll()).thenReturn(Arrays.asList());
        assertThrows(AgentUnavailableException.class, () -> {
            deliveryService.assignDelivery(orderId, customerId, restaurantId);
        });
    }

    @Test
    public void testViewAssignedDelivery() {
        when(deliveryDAO.findByAgentAgentIdAndStatus(userId, DeliveryStatus.PENDING)).thenReturn(delivery);
        Delivery assignedDelivery = deliveryService.viewAssignedDelivery(userId);
        assertEquals(deliveryId, assignedDelivery.getDeliveryId());
    }

    @Test
    public void testFetchData() {
        Set<UUID> deliveryIds = Set.of(deliveryId);
        when(deliveryDAO.findById(deliveryId)).thenReturn(Optional.of(delivery));

        List<DeliveryDetails> deliveryDetails = deliveryService.fetchData(deliveryIds);

        assertEquals(1, deliveryDetails.size());
        assertEquals(deliveryId, deliveryDetails.get(0).getDeliveryId());
        assertEquals(DeliveryStatus.PENDING, deliveryDetails.get(0).getDeliveryStatus());
    }

    @Test
    public void testFetchData_DeliveryNotFound() {
        Set<UUID> deliveryIds = Set.of(deliveryId);
        when(deliveryDAO.findById(deliveryId)).thenReturn(Optional.empty());
        assertThrows(DeliveryNotFoundException.class, () -> {
            deliveryService.fetchData(deliveryIds);
        });
    }
}
