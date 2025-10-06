package com.railconnect.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.railconnect.Serviceimp.FaresServiceimp;
import com.railconnect.entities.Fare;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/fares")
public class FaresController {

	@Autowired
	private FaresServiceimp faresServiceimp;

	@PostMapping("/add")
	public ResponseEntity<Fare> addFareAmount(@Valid @RequestBody Fare fare) {
		Fare saveFare = this.faresServiceimp.addFareAmount(fare);
		return new ResponseEntity<>(saveFare, HttpStatus.CREATED);
	}

	@GetMapping("/calculate")
	public ResponseEntity<Double> calculateFare(@RequestParam Long bookingId) {

		double fare = faresServiceimp.calculateFare(bookingId);

		return ResponseEntity.ok(fare);

	}
}
