package com.cts.menumodule.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cts.menumodule.filter.AuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private AuthFilter authFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	    return http.csrf(csrf -> csrf.disable())
	            .authorizeHttpRequests(request -> request
	            		.requestMatchers("/api/restaurant/create").permitAll()
	            		
	                    .requestMatchers("/api/menus/create", 
	                                     "/api/menus/availability", 
	                                     "/api/menus/update", 
	                                     "/api/restaurant/update", 
	                                     "/api/restaurant/toggle", 
	                                     "/api/menus/delete")
	                    .hasAuthority("MANAGER")
	                    
	                    .requestMatchers("/api/restaurant/show", 
	                                     "/api/menus/price")
	                    .hasAuthority("CUSTOMER")
	                    
	                    .requestMatchers("/api/menus/show", 
	                                     "/api/menus/")
	                    .hasAnyAuthority("MANAGER", "CUSTOMER")
	                    
	                    .anyRequest().authenticated())
	            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
	            .build();
	}

	
} 
