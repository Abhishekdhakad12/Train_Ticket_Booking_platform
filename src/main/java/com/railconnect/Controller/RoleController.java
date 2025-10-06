package com.railconnect.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.railconnect.Serviceimp.Roleserviceimp;
import com.railconnect.entities.Roles;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/role")
public class RoleController {

	
	@Autowired
	private Roleserviceimp roleserviceimp;

	
	@PostMapping("/roleinsert")
	public ResponseEntity<Roles> createroles(@Valid @RequestBody Roles roles) {
		Roles roles2 = this.roleserviceimp.createrole(roles);
		
		return new ResponseEntity<Roles>(roles2, HttpStatus.ACCEPTED);
	}


	
	}

