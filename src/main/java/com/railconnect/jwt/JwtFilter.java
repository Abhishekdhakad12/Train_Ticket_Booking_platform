package com.railconnect.jwt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.railconnect.entities.Roles;
import com.railconnect.entities.Users;
import com.railconnect.jwtutil.Jwtutil;
import com.railconnect.repo.UserRepo;

//import com.example.Repo.UserRepo;
//import com.example.entitie.Roles;
//import com.example.entitie.User;
//import com.example.jwtutil.Jwtutil;

import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private Jwtutil jwtutil;

	@Autowired
	private UserRepo userRepo;

	private static final String SECRET_KEY = "Qb3/9JVSvEsb9Ym9TBkBue8oIW8vns4MHLOEEvDI9uJ+ZwVpd6Roy89Pj3B2UDj7VT1fZ9IKXKURJgmXLKNdhg==";

	private static final SecretKey SECRET = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String auth = request.getHeader("Authorization");
			String username = null;
			String jwt = null;

			if (auth != null && auth.startsWith("Bearer ")) {
				jwt = auth.substring(7);

				username = jwtutil.extractUserName(jwt);

			}

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				Users user = userRepo.findByEmail(username);

				Set<Roles> roles = user.getRoles();

				List<String> roleFromToken = jwtutil.extractRoles(jwt);
				List<GrantedAuthority> authorities = roleFromToken.stream()
						.map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());

				List<String> roleFromDblist = roles.stream().map(role -> role.getRolename())
						.collect(Collectors.toList());

				if (!roleFromDblist.containsAll(roleFromToken) || !roleFromToken.containsAll(roleFromDblist)) {
					throw new AccessDeniedException("Role mismatch: Access denied");
				}

//			if (jwtutil.validateToekn(jwt)) {
				if (jwtutil.validateToekn(jwt)) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,
							null, authorities);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				} else {
					throw new RuntimeException("Invalid or expired token");
				}

//			}
			}

			filterChain.doFilter(request, response);

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
