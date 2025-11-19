package com.railconnect.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.railconnect.Serviceimp.UserServiceimp;
import com.railconnect.entities.Users;
import com.railconnect.repo.UserRepo;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class Usercontroller {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserServiceimp userServiceimp;

	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> ragisteration(@Valid @RequestBody Users user) {

		// Email already exist check
		if (userRepo.existsByEmail(user.getEmail())) {
			throw new RuntimeException("Email already registered: " + user.getEmail());
		}

		// Roles check
		if (user.getRoles() == null || user.getRoles().isEmpty()) {
			throw new RuntimeException("At least one role must be assigned");

		}
		// Regex: At least 6 chars, 1 uppercase, 1 digit, 1 special char
		String pattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).{6,}$";
		if (!user.getPassword().matches(pattern)) {
			throw new RuntimeException("Password must be at least 6 characters long "
					+ "and contain one uppercase letter, " + "one number, and one special character");
		}

		Users savedUser = userServiceimp.ragister(user);
		// hide password in response
		savedUser.setPassword(null);

		System.out.println(user);

		System.out.println(user.getRoles());
		return ResponseEntity.ok(Map.of("message", "User registered successfully", "user", savedUser));
	}

	@PostMapping("/login")
	public ResponseEntity<HashMap<String, Object>> loginuser(@RequestBody Users user) {
		HashMap<String, Object> response = new HashMap<>();

		// Input validation
		if (user.getEmail() == null || user.getEmail().isBlank()) {
			throw new RuntimeException("Email cannot be blank");
		}
		if (user.getPassword() == null || user.getPassword().isBlank()) {
			throw new RuntimeException("Password cannot be blank");
		}

		String token = userServiceimp.loginuser(user);
		if ("Invalid username or password!".equals(token)) {
			throw new RuntimeException(token); // GlobalExceptionHandler will handle 400/401 response
		}

		// 4️⃣ Success response
		response.put("token", token);
		response.put("message", "Login successfully");
		return ResponseEntity.ok(response);
	}

	@GetMapping("/demo")
	@PreAuthorize("hasRole('USER')")
	public String demo() {
		return "hey";
	}

	@GetMapping("/AllUsers")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Users>> getallUsrs(Authentication authentication) {

		List<Users> user = userServiceimp.getallUser();

		return ResponseEntity.ok(user);
	}

	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> updateUser(@PathVariable int id, @RequestBody Users user) {
		Users updated = userServiceimp.updateUser(id, user);
		updated.setPassword(null);
		return ResponseEntity.ok(Map.of("message", "User updated successfully", "user", updated));
	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable int id) {
		String message = userServiceimp.deleteUser(id);
		return ResponseEntity.ok(Map.of("message", message));
	}
	
	@GetMapping("/get/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> GetUSerBYId(@PathVariable int id) {
		Users message = userServiceimp.getUserById(id);
		return ResponseEntity.ok(Map.of("message", "User Get successfully!",  "user", message));
	}

}
