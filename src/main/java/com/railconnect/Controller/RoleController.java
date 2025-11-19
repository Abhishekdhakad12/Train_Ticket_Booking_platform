package com.railconnect.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.railconnect.Serviceimp.Roleserviceimp;
import com.railconnect.entities.Roles;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> deleteRole(@PathVariable int id) {
		String message = roleserviceimp.deleteRole(id);
		return ResponseEntity.ok(Map.of("message", message));
	}

	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Roles>> getAllRoles() {
		return ResponseEntity.ok(roleserviceimp.getAllRoles());
	}

	@DeleteMapping("/get/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getRoneById(@PathVariable int id) {
		Roles message = roleserviceimp.getRoleByID(id);
		return ResponseEntity.ok(Map.of("message", message));
	}

}
