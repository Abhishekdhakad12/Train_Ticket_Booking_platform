package com.railconnect.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.railconnect.jwtutil.Jwtutil;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class AuthHelper {

	@Autowired
	private Jwtutil jwtutil;
	
	public String getUserEmail(HttpServletRequest httpServletRequest) {
		String authHeader  = httpServletRequest.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			 String token = authHeader.substring(7);			
	            return jwtutil.extractUserName(token);
			
		} 
        throw new RuntimeException("Authorization header is missing or invalid");
	}
}
