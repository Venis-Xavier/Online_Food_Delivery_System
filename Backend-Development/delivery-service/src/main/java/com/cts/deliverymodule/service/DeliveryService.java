package com.cts.deliverymodule.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.deliverymodule.dao.AgentDAO;
import com.cts.deliverymodule.dao.DeliveryDAO;
import com.cts.deliverymodule.dto.DeliveryDetail;
import com.cts.deliverymodule.dto.DeliveryDetails;
import com.cts.deliverymodule.enums.Availability;
import com.cts.deliverymodule.enums.DeliveryStatus;
import com.cts.deliverymodule.exceptions.AgentUnavailableException;
import com.cts.deliverymodule.exceptions.DeliveryNotFoundException;
import com.cts.deliverymodule.exceptions.ForbiddenRequestException;
import com.cts.deliverymodule.feign.AuthInterface;
import com.cts.deliverymodule.model.Agent;
import com.cts.deliverymodule.model.Delivery;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class for managing delivery operations. Provides methods to assign
 * delivery agents and update delivery status.
 * 
 * @author Jeswin Joseph J
 * @since 26th Feb 2025
 */
@Slf4j
@Service
public class DeliveryService {

	@Autowired
	private DeliveryDAO deliveryDAO;

	@Autowired
	private AgentDAO agentDAO;
	
	@Autowired
	private AuthInterface authInterface;

	private static AtomicInteger LAST_ASSIGNED_AGENT_INDEX = new AtomicInteger(-1);

	/**
	 * Create a new Delivery Agent
	 *
	 * @param userId - the ID of the user.
	 * @return a list of deliveries of a restaurant.
	 */
	public void createAgent(UUID userId) {
		Agent agent = new Agent(userId);
		agentDAO.save(agent);
	}

	/**
	 * Gets the list of deliveries of a particular restaurant.
	 * 
	 * @param restaurantId - the ID of the restaurant.
	 * @return a list of deliveries of a restaurant.
	 */
	public List<Delivery> getAllDeliveriesOfRestaurant(UUID restaurantId) {
		return deliveryDAO.findAllByRestaurantId(restaurantId);
	}

	/**
	 * Updates the status of a delivery.
	 * 
	 * @param deliveryId - the ID of the delivery to be updated
	 * @param status     - the new status of the delivery
	 * 
	 * @throws DeliveryNotFoundException - if no delivery is found with the
	 *                                   specified ID
	 */
	@Transactional
	public void updateDeliveryStatus(UUID userId, UUID deliveryId, DeliveryStatus status) {
		Delivery delivery = deliveryDAO.findById(deliveryId).orElseThrow(() -> {
			log.error("DeliveryService::updateDeliveryStatus: No Delivery Found with ID: {}", deliveryId);
			return new DeliveryNotFoundException("No Delivery Found with ID: " + deliveryId);
		});
		
		if(!userId.equals(delivery.getAgent().getAgentId())) {
			throw new ForbiddenRequestException("Forbidded! Can't update status of unassigned Delivery");
		}
		
		delivery.setStatus(status);

		if (status == DeliveryStatus.DELIVERED) {
			Agent agent = agentDAO.findById(delivery.getAgent().getAgentId()).orElseThrow(() -> {
							log.error("DeliveryService::updateDeliveryStatus: No Agent Found with ID: {}", delivery.getAgent().getAgentId());
							return new AgentUnavailableException("No Agent Found with ID: " + delivery.getAgent().getAgentId());
						});
			
			if (agent != null) {
				agent.setAvailability(Availability.AVAILABLE);
				agentDAO.save(agent);
			}
		}

		deliveryDAO.save(delivery);
	}

	/**
	 * Assigns a delivery agent to a new delivery.
	 * 
	 * @param orderId       - the ID of the order to be delivered
	 * @param customerId    - the ID of the customer
	 * 
	 * @return DeliveryDetail - the deliveryId and agentId
	 * 
	 * @throws AgentUnavailableException, if no agents are available
	 */
	@Transactional
	public DeliveryDetail assignDelivery(UUID orderId, UUID customerId,
										 UUID restaurantId){

		List<Agent> agents = agentDAO.findAll();
		
		Agent agent = getAgentOnRoundRobinBasis(agents);

		if (agent == null) {
			log.error("DeliveryService::assignDelivery: No Agents Available");
			throw new AgentUnavailableException("No Agents Available, Please try again after some time...");
		}

		agent.setAvailability(Availability.UNAVAILABLE);
		agent = agentDAO.save(agent);

		Delivery delivery = new Delivery(orderId, customerId, restaurantId, agent);
		delivery = deliveryDAO.save(delivery);
		
		return new DeliveryDetail(delivery.getDeliveryId(), agent.getAgentId());
	}

	/**
	 * Selects an available agent on a round-robin basis.
	 * 
	 * @param agents - the list of all agents
	 * 
	 * @return the selected agent, or null if no agents are available
	 */
	private Agent getAgentOnRoundRobinBasis(List<Agent> agents) {
		int size = agents.size();

		Agent agent = null;

		for (int i = 0; i < size; i++) {
			int idx = (LAST_ASSIGNED_AGENT_INDEX.incrementAndGet()) % size;

			if (agents.get(idx).getAvailability() == Availability.AVAILABLE) {
				agent = agents.get(idx);
				break;
			}
		}
		
		return agent;
	}

	public Delivery viewAssignedDelivery(UUID userId) {
		return deliveryDAO.findByAgentAgentIdAndStatus(userId, DeliveryStatus.PENDING);
	}

	public List<DeliveryDetails> fetchData(Set<UUID> deliveryIds) {
		return deliveryIds.stream()
                .map(deliveryId -> {
                    Delivery delivery = deliveryDAO.findById(deliveryId)
                                                .orElseThrow(() -> 
                                                    new DeliveryNotFoundException("No Delivery Found with ID: " + deliveryId));
                    return new DeliveryDetails(delivery.getDeliveryId(), 
                    						   delivery.getStatus());
                })
                .collect(Collectors.toList());
	}
}
