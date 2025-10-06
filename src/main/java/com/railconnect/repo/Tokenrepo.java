package com.railconnect.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.railconnect.entities.Tokensave;



public interface Tokenrepo extends JpaRepository<Tokensave, Integer>{
	
	public com.railconnect.entities.Tokensave findBytoken(String token);
   Tokensave findBySubject(String subject);
	

	
}
