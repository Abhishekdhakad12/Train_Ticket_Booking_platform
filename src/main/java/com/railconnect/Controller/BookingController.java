package com.railconnect.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.railconnect.DTO.BookingRequest;
import com.railconnect.DTO.BookingResponse;
import com.railconnect.Serviceimp.BookingServiceimp;
import com.railconnect.jwtutil.Jwtutil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/ticketbooking")
public class BookingController {

	@Autowired
	private Jwtutil jwtutil;
	
	@Autowired
	private BookingServiceimp bookingServiceimp;


	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BookingResponse> getMe1(
			@RequestBody BookingRequest request,
			HttpServletRequest httpRequest)
	{
		String authHeader = httpRequest.getHeader("Authorization");
		String token = authHeader.substring(7);
		String userEmail = jwtutil.extractUserName(token);
		
		BookingResponse response = this.bookingServiceimp.createBooking(request, userEmail);
		return ResponseEntity.ok(response);

	}

	@GetMapping("/calculateFare/{BookingId}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<BookingResponse> calculateFare(@PathVariable Long BookingId ) {
		return ResponseEntity.ok(bookingServiceimp.calculateFare(BookingId));
	}
}
