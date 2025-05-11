package com.cts.userservice.service;

import com.cts.userservice.dto.AuthResponse;
import com.cts.userservice.dto.RestaurantRequest;
import com.cts.userservice.dto.UserProfile;
import com.cts.userservice.feign.DeliveryInterface;
import com.cts.userservice.feign.MenuInterface;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.cts.userservice.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.userservice.dao.UserDAO;
import com.cts.userservice.enums.Roles;
import com.cts.userservice.exception.UserNotFoundException;

/**
 * Represents the service class for the User.
 * 
 * @author Harish Raju M R
 * @since 22 Feb 2025
 */

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private JWTService jwtService;
	
	@Lazy
	@Autowired
	private AuthenticationManager authManager;

	@Lazy
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Lazy
	@Autowired
	private MenuInterface menuInterface;

	@Lazy
	@Autowired
	private DeliveryInterface deliveryInterface;


	//TODO: This is not needed for now. Remove this later.
	public List<User> getAllUser(){
		return userDAO.findAll();
	}
	
	public String verify(String email, String password, Roles role) {
	    Authentication authentication = authManager
	            .authenticate(new UsernamePasswordAuthenticationToken(email, password));

	    if (!authentication.isAuthenticated()) {
	        throw new UserNotFoundException("Invalid Login Credentials!");
	    }
	    User authUser = userDAO.findByEmail(email);

	    if (authUser.getRole() != role) {
	        throw new UserNotFoundException("Forbidden! You don't have access!");
	    }
	    return jwtService.getToken(authUser.getUserId(), role, email);
	}



	public User update(UUID userId, String name,
					   Long phone, String address, String password) {
		
		User userRecord = userDAO.findById(userId).orElseThrow(
				() -> new UserNotFoundException("No User Found With ID: " + userId)
		);
		
		Authentication authentication = authManager
	            .authenticate(new UsernamePasswordAuthenticationToken(userRecord.getEmail(), password));

	    if (authentication.isAuthenticated()){
	    	userRecord.setName(name);
			userRecord.setPassword(passwordEncoder.encode(password));
			userRecord.setPhone(phone);
			userRecord.setAddress(address);
	    }
	    
		return userDAO.save(userRecord);
	}

	@Transactional
	public User register(String name, String email, Long phone, 
			String address, String password, Roles role) {

		User newUser = new User(name, email, phone,
				address, role, passwordEncoder.encode(password));

		newUser = userDAO.save(newUser);
		
		try {
			if(role == Roles.MANAGER){
				RestaurantRequest restaurantRequest =
						new RestaurantRequest(newUser.getUserId(), newUser.getName(), newUser.getAddress());
	
				menuInterface.createNewRestaurant(restaurantRequest);
			} else if (role == Roles.AGENT){
				deliveryInterface.addAgent(newUser.getUserId());
			}
		} catch (Exception e) {
			
		}
		
		return newUser;
	}

	public boolean validate(String token){
		String email = jwtService.extractEmail(token);
		return jwtService.validateToken(token, loadUserByUsername(email));
	}
	
	public AuthResponse validateAndGetData(String token){
		String email = jwtService.extractEmail(token);
		
		if(jwtService.validateToken(token, loadUserByUsername(email))){
			String userId = jwtService.extractUserId(token);
			String role = jwtService.extractRole(token);

			return new AuthResponse(email, userId, role, true);
		}
		return new AuthResponse(email, null, null, false);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDAO.findByEmail(username);

		if(user == null) {
			throw new UsernameNotFoundException("User Not Found....");
		}
		
		return new UserPrincipal(user);
	}

	public List<com.cts.userservice.dto.UserDetails> fetchData(Set<UUID> userIds) {
		return userIds.stream()
                .map(userId -> {
                    User user = userDAO.findById(userId)
                                                .orElseThrow(() -> 
                                                    new UserNotFoundException("No User Found with ID: " + userId));
                    return new com.cts.userservice.dto.UserDetails(
                    						   user.getUserId(),
                                               user.getName(), 
                                               user.getPhone() + "",
                                               user.getAddress());
                })
                .collect(Collectors.toList());
	}

	public UserProfile fetchProfile(UUID userId) {
		User user = userDAO.findById(userId).orElseThrow(() -> 
												new UserNotFoundException("No User Found with ID: " + userId));
		
		return new UserProfile(user.getName(), user.getEmail(), user.getPhone(), user.getAddress());
	}
}
