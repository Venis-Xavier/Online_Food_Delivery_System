package com.cts.deliverymodule.model;

import java.util.UUID;

import com.cts.deliverymodule.enums.Availability;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a delivery agent in the online food delivery system.
 * This entity is mapped to "agents" table.
 * 
 * @author Jeswin Joseph J
 * @since 22 Feb 2025
 */

@Entity
@Table(name = "agents")
public class Agent {

    /**
     * User ID reference from User Service.
     */
    @Id
    @Column(nullable = false, unique = true)
    private UUID agentId;

    /**
     * The availability of the delivery agent.
     * Can be "AVAILABLE" or "UNAVAILABLE".
     * 
     * Default Value: {@code AVAILABLE}
     */
    @Enumerated(EnumType.STRING)
    private Availability availability = Availability.AVAILABLE;

    public Agent(){

	}

	public Agent(UUID agentId){
		this.agentId = agentId;
	}
    
	/**
	 * @return the agentId
	 */
	public UUID getAgentId() {
		return agentId;
	}

	/**
	 * @param agentId the agentId to set
	 */
	public void setAgentId(UUID agentId) {
		this.agentId = agentId;
	}

	/**
	 * @return the availability
	 */
	public Availability getAvailability() {
		return availability;
	}

	/**
	 * @param availability the availability to set
	 */
	public void setAvailability(Availability availability) {
		this.availability = availability;
	} 
    
}
