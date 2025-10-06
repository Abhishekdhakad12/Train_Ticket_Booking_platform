package com.railconnect.jwtutil;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railconnect.entities.Roles;
import com.railconnect.entities.Tokensave;
import com.railconnect.entities.Users;
import com.railconnect.repo.Rolerepo;
import com.railconnect.repo.Tokenrepo;
import com.railconnect.repo.UserRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Parser;
import io.jsonwebtoken.security.Keys;

@Service
public class Jwtutil {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private Tokenrepo tokenrepo;

	@Autowired
	private Rolerepo rolerepo;

	private Key signingKey;

	private Parser parser;

	private static final String SECRET_KEY = "Qb3/9JVSvEsb9Ym9TBkBue8oIW8vns4MHLOEEvDI9uJ+ZwVpd6Roy89Pj3B2UDj7VT1fZ9IKXKURJgmXLKNdhg==";

	private static final SecretKey SECRET = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
//	private static final SecretKey SECRET = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));

	public String generateJwtToken(Users user) {

		Date issuedAt = new Date(System.currentTimeMillis());
		Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60);

		List<String> roles = user.getRoles().stream().map(Roles::getRolename).toList();

		String token = Jwts.builder()
				.subject(user.getEmail())
				.claim("roles", roles)
				.issuedAt(issuedAt)
				.expiration(expiration)
				.signWith(SECRET)
				.compact();

		// Pehle DB me search karo ki user ka token already hai ya nahi
		Tokensave tokensave = tokenrepo.findBySubject(user.getFullName());
		if (tokensave == null) {
			// agar pehli bar login hai to naya record banao
			tokensave = new Tokensave();
			tokensave.setSubject(user.getEmail());
		}

		tokensave.setIssueAt(issuedAt);
		tokensave.setExpiration(expiration);
		tokensave.setToken(token);
		tokenrepo.save(tokensave);

		return token;
	}

	// Token se saare claims nikalne ka method
	public Claims extractAllClaim(String token) {
		return Jwts.parser()
				.verifyWith(SECRET)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

    // Roles extract
	public List<String> extractRoles(String token) {
		Claims claims = extractAllClaim(token);
		return claims.get("roles", List.class);
	}

	public String extractUserName(String token) {
		return extractAllClaim(token).getSubject();
	}

    // Single role agar chahiye
	public String extractRole(String token) {
		return extractAllClaim(token).get("roles", String.class);
	}

	// Expiration extract
	public Date extractExpiration(String token) {
		return extractAllClaim(token).getExpiration();
	}

	// Expiry check
	public Boolean expiryCheck(String token) {
		return extractExpiration(token).before(new Date());
	}

	// Token validate
	public Boolean validateToekn(String token) {
		String extractusername = extractUserName(token);
		Users user = userRepo.findByEmail(extractusername);
		return (user != null && !expiryCheck(token));

	}

}
