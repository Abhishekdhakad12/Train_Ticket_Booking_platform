package com.railconnect.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.railconnect.Serviceimp.PaymentServiceimp;
import com.railconnect.repo.BookingRepo;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

	@Autowired
	private PaymentServiceimp paymentServiceimp;

	@Autowired
	private BookingRepo bookingRepo;

	@PostMapping("/create-order/{bookingId}")
	public ResponseEntity<JsonNode> createOrder(@PathVariable Long bookingId) throws Exception {
		JsonNode response = paymentServiceimp.createOrder(bookingId);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/capture-order/{orderId}")
	public ResponseEntity<JsonNode> captureOrder(@PathVariable String orderId) {
		try {
			JsonNode response = paymentServiceimp.captureOrder(orderId);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(null);
		}
	}
}
