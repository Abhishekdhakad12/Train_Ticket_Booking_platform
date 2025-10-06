package com.railconnect.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.railconnect.entities.TrainRoute;

public interface TrainRouteRepo extends JpaRepository<TrainRoute, Long>{
	
	boolean existsByTrainIdAndStopNumber(Long trainid, Integer stopNumber);
	
	public Optional<TrainRoute> findByTrain_IdAndStationName(Long trainid, String stationName);
	
	Optional<TrainRoute> findByIdAndTrain_Id(Long trainRouteId, Long trainId);
	
//	Optional<TrainRoute> findByTrain_IdAndStationName(Long trainId, String stationName);
	
	

}
