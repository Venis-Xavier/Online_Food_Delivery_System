package com.cts.orderservice.feign;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cts.orderservice.dto.MenuDetails;
import com.cts.orderservice.dto.MenuItemRequest;
import com.cts.orderservice.dto.Response;

import jakarta.validation.Valid;

/**
 * Feign client interface for communication with the Menu Service.
 * 
 * @author Jeswin Joseph J
 * @since 10 Mar 2025
 */
@FeignClient("MENU-SERVICE")
public interface MenuInterface {

	@PostMapping("/api/menus/price")
	public ResponseEntity<Response<?>> getPriceOfItem(@Valid @RequestBody MenuItemRequest request);
	
	@PostMapping("/api/menus/view")
	public ResponseEntity<Response<List<MenuDetails>>> getItems(@Valid @RequestBody Set<UUID> itemIds);
}
