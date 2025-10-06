package com.railconnect.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.railconnect.entities.Seat;

public interface SeatRepo extends JpaRepository<Seat, Long>{
	
    Optional<Seat> findByCoachIdAndSeatNumber(Long coachId, String seatNumber);

}
