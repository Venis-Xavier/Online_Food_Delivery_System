package com.cts.orderservice.feign;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cts.orderservice.dto.AuthResponse;
import com.cts.orderservice.dto.Response;
import com.cts.orderservice.dto.UserDetails;

@FeignClient("USER-SERVICE")
public interface AuthInterface {
	
	@PostMapping("/api/auth/")
	public AuthResponse validateAndGet(@RequestBody String token);
	
	
	@PostMapping("/api/auth/view")
	public ResponseEntity<Response<List<UserDetails>>> fetchUserData(@RequestBody Set<UUID> userIds);
}
