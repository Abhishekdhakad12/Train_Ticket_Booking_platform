package com.railconnect.service;

import com.railconnect.DTO.PaymentInitiateRequest;
import com.railconnect.DTO.PaymentResponse;

public interface PaymentService {
	
	public PaymentResponse PaymentInitiate(PaymentInitiateRequest request);

	PaymentResponse completePayment(Long paymentId, Boolean success);
	
	PaymentResponse getPaymentStatus(long paymentId);
}
