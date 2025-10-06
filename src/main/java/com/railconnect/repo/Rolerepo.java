package com.railconnect.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.railconnect.entities.Roles;


public interface Rolerepo extends JpaRepository<Roles, Integer> {
	
	Optional<Roles> findByRolename(String rolename);
}
