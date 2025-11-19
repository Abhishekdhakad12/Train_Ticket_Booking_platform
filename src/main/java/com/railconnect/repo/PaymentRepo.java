package com.railconnect.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.railconnect.entities.Booking;
import com.railconnect.entities.Payment;

public interface PaymentRepo extends JpaRepository<Payment,Long > {

    Optional<Payment> findByBookingId(Long bookingId);

	Optional<Payment> findByPaypalOrderId(String orderId);

	

}
