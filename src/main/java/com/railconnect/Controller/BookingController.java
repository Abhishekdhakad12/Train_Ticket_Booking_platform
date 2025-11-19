package com.railconnect.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
	
	@GetMapping("/getById/{bookingid}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<Map<String, Object>> getBookingById(
			@PathVariable long bookingid) {
		
		return ResponseEntity.ok(bookingServiceimp.bookinggetById(bookingid));
	}
	
	@DeleteMapping("/deleteById/{bookingid}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<Map<String, Object>> deleteBookingById(
			@PathVariable long bookingid) {
		return ResponseEntity.ok(bookingServiceimp.deletebookinggetById(bookingid));
	}
	
	@PatchMapping("/cancelBooking/{bookingid}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<Map<String, Object>> cancelBooking(
			@PathVariable long bookingid, HttpServletRequest httpRequest) {
		
		String authHeader = httpRequest.getHeader("Authorization");
		String token = authHeader.substring(7);
		String userEmail = jwtutil.extractUserName(token);
		return ResponseEntity.ok(bookingServiceimp.cancelBooking(bookingid, userEmail));
		
	}
	
}
