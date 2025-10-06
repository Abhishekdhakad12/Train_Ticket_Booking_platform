package com.railconnect.Serviceimp;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railconnect.DTO.PaymentInitiateRequest;
import com.railconnect.DTO.PaymentResponse;
import com.railconnect.entities.Booking;
import com.railconnect.entities.Payment;
import com.railconnect.entities.PaymentStatus;
import com.railconnect.repo.BookingRepo;
import com.railconnect.repo.PaymentRepo;
import com.railconnect.service.PaymentService;

import jakarta.transaction.Transactional;

@Service
public class PaymentServiceimp implements PaymentService {

	@Autowired
	private BookingRepo bookingRepo;

	@Autowired
	private PaymentRepo paymentRepo;

	@Transactional
	@Override
	public PaymentResponse PaymentInitiate(PaymentInitiateRequest request) {

		Booking booking = bookingRepo.findById(request.getBookingId())
				.orElseThrow(() -> new RuntimeException("Booking id no found : " + request.getBookingId()));

		Payment payment = new Payment();
		payment.setPaymentId(UUID.randomUUID().toString());
		payment.setPaymentMethod(request.getPaymentMethod());
		payment.setAmount(request.getAmount());
		payment.setStatus(PaymentStatus.PENDING);
		payment.setCreatedAt(LocalDateTime.now());
		payment.setBooking(booking);

		Payment saved = paymentRepo.save(payment);

		PaymentResponse response = new PaymentResponse();
		response.setPaymentId(saved.getPaymentId());
		response.setPaymentMethod(saved.getPaymentMethod());
		response.setAmount(saved.getAmount());
		response.setPaymentStatus(saved.getStatus());
		response.setMessage("Payment initiated successfully!");

		return response;
	}

	@Override
	public PaymentResponse completePayment(Long paymentId, Boolean success) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaymentResponse getPaymentStatus(long paymentId) {
		// TODO Auto-generated method stub
		return null;
	}

}
