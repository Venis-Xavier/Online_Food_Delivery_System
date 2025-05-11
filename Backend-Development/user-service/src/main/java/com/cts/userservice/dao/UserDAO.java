package com.cts.userservice.dao;

import org.springframework.stereotype.Repository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cts.userservice.model.User;

	/**
	 * Data Access Object for the user table.
	 * 
	 * @author Harish Raju M R
	 * @since 22 Feb 2025
	 */

@Repository
public interface UserDAO extends JpaRepository<User, UUID>{
	
	User findByEmail(String email);

}
