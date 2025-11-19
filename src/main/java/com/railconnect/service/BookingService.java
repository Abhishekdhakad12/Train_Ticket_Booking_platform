package com.railconnect.service;

import java.util.Map;

import com.railconnect.DTO.BookingRequest;
import com.railconnect.DTO.BookingResponse;

public interface BookingService {
	
	BookingResponse createBooking(BookingRequest request, String userEmail);
	
	BookingResponse calculateFare(Long bookingId);
	
	Map<String, Object> bookinggetById(Long bookingId);

	Map<String, Object>  deletebookinggetById(Long bookingId);
	
//	Map<String, Object>  cancelBooking(Long bookingId);

	Map<String, Object> cancelBooking(Long bookingId, String userEmail);
}
