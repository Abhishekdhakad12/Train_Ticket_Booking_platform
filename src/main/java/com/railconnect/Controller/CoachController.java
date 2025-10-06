package com.railconnect.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.railconnect.Serviceimp.CoachServiceimp;
import com.railconnect.entities.Coach;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/coaches")
public class CoachController {

	@Autowired
	private CoachServiceimp coachServiceimp;

	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addCoachs(@Valid @RequestBody Coach coach) {

		// Null check at controller level
		if (coach == null) {
			return ResponseEntity.badRequest().body("Coach request body cannot be null");
		}
		Coach savecoach = this.coachServiceimp.addCoach(coach);
		return new ResponseEntity<>(savecoach, HttpStatus.CREATED);
	}

	@PatchMapping("/updatecoach/{coachId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> updateCoach(@RequestBody Coach coachDetails,
			@RequestParam(required = false) Long trainId, @RequestParam(required = false) String trainNumber,
			@PathVariable Long coachId) {

		Coach coach = this.coachServiceimp.update(coachDetails, coachId, trainNumber, trainId);

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Coach update successfully");
		response.put("coach", coach);

		return ResponseEntity.ok(response);

	}

	@GetMapping("/getAll")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Coach>> getAllCoach() {
		List<Coach> coachslist = this.coachServiceimp.getAllCoach();
		return ResponseEntity.ok(coachslist);

	}

	@GetMapping("/getCoach")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Coach> getCoach(@RequestParam(required = false) Long trainId,
			@RequestParam(required = false) Long coachId) {
		return ResponseEntity.ok(coachServiceimp.getCoach(trainId, coachId));
	}

	@DeleteMapping("/deleteCoach")
	@PreAuthorize("hasRole('ADMIN')")	
	public ResponseEntity<Map<String, Object>> deleteCoach(@RequestParam(required = false) Long trainId,
			@RequestParam(required = false) Long coachId) {
	     this.coachServiceimp.deleteCoach(trainId, coachId);

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Coach deleted successfully");
		response.put("coachId", coachId);

		return ResponseEntity.ok(response);
	}

}
