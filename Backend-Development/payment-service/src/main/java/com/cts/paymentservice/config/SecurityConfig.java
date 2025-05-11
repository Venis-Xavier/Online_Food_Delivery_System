package com.cts.paymentservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cts.paymentservice.filter.AuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private AuthFilter authFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(request -> request
						.requestMatchers("/api/payment/customer").hasAuthority("CUSTOMER")
						.requestMatchers("/api/payment/update").hasAuthority("AGENT")
						.requestMatchers("/api/payment/restaurant", "/api/payment/new").hasAuthority("MANAGER")
						.requestMatchers("/api/payment/order").hasAnyAuthority("CUSTOMER", "MANAGER", "AGENT")
						.anyRequest().authenticated())
				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
}
