package com.cts.paymentservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cts.paymentservice.dto.AuthResponse;

@FeignClient("USER-SERVICE")
public interface AuthInterface {
	
	@PostMapping("/api/auth/")
	public AuthResponse validateAndGet(@RequestBody String token);
}
