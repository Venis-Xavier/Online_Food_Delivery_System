package com.cts.deliverymodule.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.deliverymodule.model.Agent;

/**
 * Data Access Object (DAO) interface for the Agent entity.
 * 
 * @author Jeswin Joseph J
 * @since 26th Feb 2025
 */
@Repository
public interface AgentDAO extends JpaRepository<Agent, UUID>{

}
