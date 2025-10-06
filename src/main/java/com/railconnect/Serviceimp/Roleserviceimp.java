package com.railconnect.Serviceimp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railconnect.entities.Roles;
import com.railconnect.repo.Rolerepo;
import com.railconnect.service.Roleservice;



@Service
public class Roleserviceimp implements Roleservice {

	@Autowired
	private Rolerepo rolerepo;

	@Override
	public Roles createrole(Roles roles) {
		Roles roles2 = this.rolerepo.save(roles);
		return roles2;
	}
	
	

}
