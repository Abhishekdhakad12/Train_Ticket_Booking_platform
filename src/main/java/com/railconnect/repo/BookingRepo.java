package com.railconnect.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.railconnect.entities.Booking;

public interface BookingRepo extends JpaRepository<Booking, Long> {
	
	
	 

}
