package com.railconnect.service;

import com.railconnect.DTO.BookingRequest;
import com.railconnect.DTO.BookingResponse;

public interface BookingService {
	
	BookingResponse createBooking(BookingRequest request, String userEmail);
	
	BookingResponse calculateFare(Long bookingId);

}
