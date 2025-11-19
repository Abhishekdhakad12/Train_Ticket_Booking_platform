package com.railconnect.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.railconnect.jwt.JwtFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
//@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class Securityconfig {
	
	@Autowired
	private JwtFilter jwtFilter;
	
	
	private static final String[] PUBLIC_ENDPOINTS = {
			"/api/role/roleinsert",
			"/api/users/register",
			"/api/users/login",
			"/api/payment/**",
//			"/api/coaches/**",
//			 "/api/trainRoute/**",
			"/api/fares/**",
			"/api/payment/**"
	};
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
		.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(auth -> auth
				 .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                 .anyRequest().authenticated()
                 )
		.exceptionHandling(ex -> ex
                .authenticationEntryPoint(authenticationEntryPoint())   // 401 errors
                .accessDeniedHandler(accessDeniedHandler())             // 403 errors
            )
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
		.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
		return httpSecurity.build();
	}
	
	

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return(request, response, authException) -> {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        response.setContentType("application/json");
	        response.getWriter().write("{\"error\": \"Unauthorized or Invalid Token\"}");
		};
	}
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
	    return (request, response, accessDeniedException) -> {
	        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
	        response.setContentType("application/json");
	        response.getWriter().write("{\"error\": \"Access Denied: You don’t have required role\"}");
	    };
	}

	

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
