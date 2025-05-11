package com.cts.menumodule.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cts.menumodule.dto.AuthResponse;
import com.cts.menumodule.feign.AuthInterface;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends OncePerRequestFilter{
	
	@Autowired
	AuthInterface authInterface;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if(request.getRequestURI().equals("/api/restaurant/create")) {
			filterChain.doFilter(request, response);
		}
		
		String authHeader = request.getHeader("AUTHORIZATION");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
		    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
		    return;
		}

		String token = authHeader.substring(7);
		
		String email = "";
		String role = "";
		try {
	        AuthResponse authResponse = authInterface.validateAndGet(token);
            if (!authResponse.isValid()) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid Token");
                return;
            }
            
            email = authResponse.getEmail();
            role = authResponse.getRole();
            request.setAttribute("userId", authResponse.getUserId());
            request.setAttribute("role", authResponse.getRole());

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
            return;
        }

		Collection<? extends GrantedAuthority> authority = Collections.singletonList(new SimpleGrantedAuthority(role));
		UserDetails userDetails = new User(email, "N/A", authority);
		
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, authority);
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
 
        SecurityContextHolder.getContext().setAuthentication(authToken);
		
		filterChain.doFilter(request, response);
	}
}
