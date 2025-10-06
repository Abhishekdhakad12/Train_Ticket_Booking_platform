package com.railconnect.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.railconnect.entities.Fare;

public interface Faresrepo extends JpaRepository<Fare, Long> {

	Optional<Fare> findByTrainIdAndCoachType(long trainid, String coachType);
//	List<Fare> findByTrainIdAndCoachType(long trainid, String coachType);
	
	
}
