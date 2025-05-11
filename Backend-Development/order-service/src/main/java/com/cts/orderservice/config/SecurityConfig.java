package com.cts.orderservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cts.orderservice.filter.AuthFilter;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private AuthFilter authFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http.csrf(csrf -> csrf.disable())
				.cors(cors -> {}) // Enable CORS
				.authorizeHttpRequests(request -> request
						.requestMatchers("/api/order/add", "/api/order/remove").hasAuthority("CUSTOMER")
						.requestMatchers("/api/order/update").hasAnyAuthority("CUSTOMER", "MANAGER", "AGENT")
						.anyRequest().authenticated())
				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	@Bean
	RequestInterceptor requestInterceptor() {
		return requestTemplate -> {
			RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
			if(requestAttributes instanceof ServletRequestAttributes servletRequestAttributes) {
				HttpServletRequest request = servletRequestAttributes.getRequest();
				String authHeader = request.getHeader("AUTHORIZATION");
				System.out.print(">>>>>>>>>>>>" + authHeader);
				if(authHeader != null && !authHeader.isEmpty()) {
					requestTemplate.header("AUTHORIZATION", authHeader);
				}
			}
		};
	}
}
