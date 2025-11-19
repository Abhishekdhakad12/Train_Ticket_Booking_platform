package com.railconnect.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface PaymentService {

//	public PaymentResponse PaymentInitiate(PaymentInitiateRequest request);

//	JsonNode createToken() throws Exception;
	
	JsonNode createOrder(Long bookingId) throws Exception;
	JsonNode captureOrder(String orderId) throws Exception;

//	JsonNode createPayment(Long booingId) throws Exception;

//	PaymentResponse getPaymentStatus(long paymentId);

//	JsonNode capturePayment(Long paymentId) throws Exception;
}
