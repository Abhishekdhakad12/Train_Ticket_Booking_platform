package com.railconnect.Serviceimp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.railconnect.entities.Roles;
import com.railconnect.entities.Users;
import com.railconnect.jwtutil.Jwtutil;
import com.railconnect.repo.Rolerepo;
import com.railconnect.repo.UserRepo;
import com.railconnect.service.UsersService;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserServiceimp implements UsersService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private Jwtutil jwtutil;

	@Autowired
	private Rolerepo rolerepo;

	@Transactional
	@Override
	public Users ragister(Users user) {

		// Encode password
		String encodepassword = passwordEncoder.encode(user.getPassword());
		Set<Roles> roleSet = new HashSet<>();

		if (user.getRoles() != null) {
			for (Roles rolename : user.getRoles()) {
				// Get role from DB
				Roles role = rolerepo.findByRolename(rolename.getRolename())
						.orElseThrow(() -> new RuntimeException("Invalid role assignment: " + rolename));
				log.info("Invalid role assignment: " + rolename);

				roleSet.add(role);
			}
			System.err.println("Hell");
		}

//		Assign roles to user
		user.setRoles(roleSet);
		user.setPassword(encodepassword);
		return userRepo.save(user);
	}

	@Override
	public String loginuser(Users user) {

		Users dbUser = userRepo.findByEmail(user.getEmail());

		if (dbUser == null) {
			return "Invalid username or password!";
		}

		// password mismatch
		if (!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
			return "Invalid username or password!";
		}
		System.out.println(dbUser);
		return jwtutil.generateJwtToken(dbUser);

	}

	@Override
	public List<Users> getallUser() {
		List<Users> user = userRepo.findAll();
		return user;
	}

	@Override
	public Users updateUser(int id, Users updatedUser) {

//		 existingUser = userRepo.findById(id)

		// Find existing user
		Users existingUser = userRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

		// Update allowed fields only
		if (updatedUser.getFullName() != null && !updatedUser.getFullName().isBlank()) {
			existingUser.setFullName(updatedUser.getFullName());
		}

		// 2️⃣ Update allowed fields only
		if (updatedUser.getEmail() != null && !updatedUser.getEmail().isBlank()) {

			if (userRepo.existsByEmail(updatedUser.getEmail())
					&& existingUser.getEmail().equals(updatedUser.getEmail())) {
				throw new RuntimeException("Email already registered: " + updatedUser.getEmail());
			}
			existingUser.setEmail(updatedUser.getEmail());
		}

		// 2️⃣ Update allowed fields only
		if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
			existingUser.setPassword(updatedUser.getPassword());

			String updatepassword = passwordEncoder.encode(updatedUser.getPassword());

			existingUser.setPassword(updatepassword);
		}

		if (updatedUser.getRoles() != null && !updatedUser.getRoles().isEmpty()) {
			existingUser.setRoles(updatedUser.getRoles());

            Set<Roles> roleSet = new HashSet<>();

			for (Roles updaterole : updatedUser.getRoles()) {
				Roles roles = rolerepo.findByRolename(updaterole.getRolename())
						.orElseThrow(() -> new RuntimeException("Invalid role: " + updaterole.getRolename()));
                roleSet.add(roles);
			}
			existingUser.setRoles(roleSet);
			
		}

		return userRepo.save(existingUser);
	}

	@Override
	public String deleteUser(int id) {
		Users existingUser = userRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
		userRepo.delete(existingUser);
		return "User deleted successfully with ID: " + id;
	}
	
	
	@Override
	public Users getUserById(int id) {
		Users existingUser = userRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
		return existingUser;
	}

}
