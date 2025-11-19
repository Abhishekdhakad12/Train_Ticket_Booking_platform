package com.railconnect.service;

import java.util.List;

import com.railconnect.entities.Roles;

public interface Roleservice {

	public Roles createrole(Roles roles);

	Roles updateRole(int id, Roles role);

	String deleteRole(int id);
	
	Roles getRoleByID(int id);

	List<Roles> getAllRoles();

}
