package com.cts.userservice.feign;

import com.cts.userservice.dto.Response;
import com.cts.userservice.dto.RestaurantRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("MENU-SERVICE")
public interface MenuInterface {

    @PostMapping("api/restaurant/create")
    public ResponseEntity<Response<?>> createNewRestaurant(@RequestBody RestaurantRequest request);
}
