package com.railconnect.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.railconnect.entities.Users;

	
	public interface UserRepo extends JpaRepository<Users, Integer> {

		public Users findByEmail(String email);
		boolean existsByEmail(String email);

		

}
