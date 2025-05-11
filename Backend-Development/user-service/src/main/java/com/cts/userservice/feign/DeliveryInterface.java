package com.cts.userservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cts.userservice.config.SecurityConfig;

import java.util.UUID;

@FeignClient("DELIVERY-SERVICE")
public interface DeliveryInterface {

    @PostMapping("/api/delivery/addAgent")
    public void addAgent(@RequestBody UUID userId);
}
