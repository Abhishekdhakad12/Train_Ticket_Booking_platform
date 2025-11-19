package com.railconnect.Serviceimp;

import java.util.List;

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

	@Override
	public Roles updateRole(int id, Roles role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteRole(int id) {
		Roles role = rolerepo.findById(id).orElseThrow(() -> new RuntimeException("Role not found with ID: " + id));
		rolerepo.delete(role);
		return "Role deleted successfully with ID: " + id;
	}

	@Override
	public List<Roles> getAllRoles() {
		return rolerepo.findAll();
	}

	@Override
	public Roles getRoleByID(int id) {
		Roles role = rolerepo.findById(id).orElseThrow(() -> new RuntimeException("Role not found with ID: " + id));
		return role;

	}

}
