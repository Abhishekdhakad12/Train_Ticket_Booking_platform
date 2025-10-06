package com.railconnect.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.railconnect.DTO.PaymentInitiateRequest;
import com.railconnect.DTO.PaymentResponse;
import com.railconnect.Serviceimp.PaymentServiceimp;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
	
	@Autowired
	private PaymentServiceimp paymentServiceimp;

	@PostMapping("/initiate")
	public ResponseEntity<PaymentResponse> PaymentInitiate(
			@RequestBody PaymentInitiateRequest request) {
		
		if (request == null) {
			throw new RuntimeException("PaymentInitiateRequest is null");
		}
		
		return ResponseEntity.ok(paymentServiceimp.PaymentInitiate(request));
	}
}
