package com.cts.deliverymodule.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cts.deliverymodule.filter.AuthFilter;


/**
 * Configuration class for setting up the application's security using Spring Security.
 * 
 * @author Jeswin Joseph J
 * @since 11 Mar 2025
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private AuthFilter authFilter;

	/**
     * Bean definition for the SecurityFilterChain, which configures the application's
     * HTTP security settings.
     * 
     * @param http the {@link HttpSecurity} object used for configuring security settings
     * @return a {@link SecurityFilterChain} object with the configured security settings
     * @throws Exception if an error occurs during security configuration
     */
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(request -> request
						.requestMatchers("/api/delivery/addAgent").permitAll()
						.requestMatchers("/api/delivery/updateStatus", "/api/delivery/view").hasAuthority("AGENT")
						.requestMatchers("/api/delivery/all", "/api/delivery/assign").hasAuthority("MANAGER")
						.anyRequest().authenticated())
				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
}
