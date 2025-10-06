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
				//  Get role from DB
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

}
