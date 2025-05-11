package com.cts.userservice.controller;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.cts.userservice.dto.*;
import com.cts.userservice.dto.LoginRequest;
import com.cts.userservice.dto.UserRequest;
import com.cts.userservice.exception.UserNotFoundException;
import com.cts.userservice.model.User;
import com.cts.userservice.service.UserService;

import jakarta.validation.Valid;

/**
* Represents the controller class for User Authentication
* 
* @author Harish Raju M R
* @since 22 Feb 2025
*/
@Validated
@RestController
@RequestMapping("/api/auth")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/all")
	public List<User> getAllUser(){
		return userService.getAllUser();
	}

	@PostMapping("/validate")
	public boolean validateRequest(@RequestBody String token){
		return userService.validate(token);
	}
	
	@PostMapping("/")
	public AuthResponse validateAndGet(@RequestBody String token) {
		return userService.validateAndGetData(token);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Response<?>> loginUser(@Valid @RequestBody LoginRequest request) {
		String token = userService.verify(request.getEmail(), request.getPassword(), request.getRole());
		Response<String> response = new Response<>(true, HttpStatus.OK, token, null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<Response<?>> registerUser(@Valid @RequestBody UserRequest request) {
		User user = userService.register(request.getName(), request.getEmail(), 
			request.getPhone(), request.getAddress(), request.getPassword(), request.getRole());
		Response<User> response = new Response<>(true, HttpStatus.OK, user, null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Response<?>> update(@RequestAttribute("userId") UUID userId, @Valid @RequestBody UserRequest request) {
		
			if(userId == null){
				throw new UserNotFoundException("No User Found!");
			}
			
			User user = userService.update(userId, request.getName(), request.getPhone(),
					request.getAddress(), request.getPassword());
			Response<User> response = new Response<>(true, HttpStatus.OK, user, null);
			return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/view")
	public ResponseEntity<Response<?>> fetchUserData(@RequestBody Set<UUID> userIds){
			List<UserDetails> user = userService.fetchData(userIds);
			Response<List<UserDetails>> response = new Response<>(true, HttpStatus.OK, user, null);
			return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/profile")
	public ResponseEntity<Response<?>> fetchUserProfile(@RequestAttribute("userId") UUID userId){
		UserProfile user = userService.fetchProfile(userId);
		Response<UserProfile> response = new Response<>(true, HttpStatus.OK, user, null);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}

