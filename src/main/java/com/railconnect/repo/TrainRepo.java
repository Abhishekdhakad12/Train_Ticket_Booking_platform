package com.railconnect.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.railconnect.entities.Train;

public interface TrainRepo extends JpaRepository<Train, Long> {
	
	Optional<Train> findByTrainNumber(String trainNumber);		
	@Query("SELECT DISTINCT t FROM Train t " +
		       "JOIN t.routes rFrom " +
		       "JOIN t.routes rTo " +
		       "WHERE LOWER(rFrom.stationName) = LOWER(:fromStation) " +
		       "AND LOWER(rTo.stationName) = LOWER(:toStation) " +
		       "AND rFrom.stopNumber < rTo.stopNumber " +
		       "AND t.status = 'Active'")
		  List<Train> findTrainsBetweenStations(@Param("fromStation") String fromStation,
		                                             @Param("toStation") String toStation);


}
