package com.railconnect.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.railconnect.entities.Coach;
import com.railconnect.entities.Train;

public interface CoachRepo extends JpaRepository<Coach, Long> {
	
	
	
	public Optional<Coach> findById(Integer id);
	Optional<Coach> findByIdAndTrain(Long id, Train train);
//	public boolean existById(Integer id);

}
