package com.railconnect.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.railconnect.Serviceimp.TrainRouteServiceimp;
import com.railconnect.entities.TrainRoute;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/trainRoute")
public class TrainRouteController {

	@Autowired
	private TrainRouteServiceimp trainRouteServiceimp;

	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addTrainRoute(@Valid @RequestBody TrainRoute trainRoute) {
		TrainRoute route = this.trainRouteServiceimp.addRouteTime(trainRoute);
		return new ResponseEntity<>(route, HttpStatus.CREATED);
	}

	@PatchMapping("/trains/{trainId}/routes/{trainRouteId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateTrainRoute(
	        @PathVariable(required = false) Long trainId,
	        @PathVariable(required = false) Long trainRouteId,
	        @RequestBody TrainRoute trainRoute) {

	    TrainRoute updated = trainRouteServiceimp.updateTrainRoute(trainRoute, trainId, trainRouteId);

	    Map<String, Object> response = new HashMap<>();
	    response.put("message", "TrainRoute updated successfully");
	    response.put("data", updated);

	    return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/trains/{trainId}/routes/{trainRouteId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteTrainRoute(
	        @PathVariable Long trainId,
	        @PathVariable Long trainRouteId) {

	    TrainRoute deleted = trainRouteServiceimp.deleteTrainRoute(trainId, trainRouteId);

	    Map<String, Object> response = new HashMap<>();
	    response.put("message", "TrainRoute deleted successfully");
	    response.put("deleted", deleted);

	    return ResponseEntity.ok(response);
	}
}
