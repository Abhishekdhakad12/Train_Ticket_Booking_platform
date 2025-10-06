package com.railconnect.Serviceimp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railconnect.entities.Train;
import com.railconnect.entities.TrainRoute;
import com.railconnect.repo.TrainRepo;
import com.railconnect.repo.TrainRouteRepo;
import com.railconnect.service.TrainRouteService;

import jakarta.transaction.Transactional;

@Service
public class TrainRouteServiceimp implements TrainRouteService {

	@Autowired
	private TrainRouteRepo trainRouteRepo;

	@Autowired
	private TrainRepo trainRepo;

	@Transactional
	@Override
	public TrainRoute addRouteTime(TrainRoute trainRoute) {

		Long trainid = trainRoute.getTrain().getId();

		// Train exist check
		Train train = trainRepo.findById(trainid)
				.orElseThrow(() -> new RuntimeException("Train not found with id: " + trainid));
		trainRoute.setTrain(train);

		// Unique StopNumber per Train
		boolean exists = trainRouteRepo.existsByTrainIdAndStopNumber(trainid, trainRoute.getStopNumber());
		if (exists) {
			throw new RuntimeException("Stop number " + trainRoute.getStopNumber() + " already exists for this train.");
		}

		// Time Validation (Arrival < Departure)
		if (trainRoute.getArrivalTime() != null && trainRoute.getDepartureTime() != null) {
			if (trainRoute.getArrivalTime().compareTo(trainRoute.getDepartureTime()) >= 0) {
				throw new RuntimeException("Arrival time must be before Departure time.");
			}
		}

		TrainRoute trainRoute1 = trainRouteRepo.save(trainRoute);
		return trainRoute1;
	}

	@Override
	public TrainRoute updateTrainRoute(TrainRoute trainRoute, Long trainId, Long trainRouteId) {


	    if (trainRoute == null) {
	        throw new IllegalArgumentException("TrainRoute data must not be null");
	    }

	    TrainRoute route;

	    // Case 1: Dono IDs diye gaye hain (best case)
	    if (trainId != null && trainRouteId != null) {
	        route = trainRouteRepo.findByIdAndTrain_Id(trainRouteId, trainId)
	                .orElseThrow(() -> new RuntimeException("TrainRoute not found with given TrainId and RouteId"));
	    } 
	    // Case 2: Sirf routeId diya hai
	    else if (trainRouteId != null) {
	        route = trainRouteRepo.findById(trainRouteId)
	                .orElseThrow(() -> new RuntimeException("TrainRoute not found with given RouteId"));
	    } 
	    // Case 3: Sirf trainId diya hai (galat case)
	    else if (trainId != null) {
	        throw new RuntimeException("RouteId is required along with TrainId to update TrainRoute");
	    } 
	    else {
	        throw new RuntimeException("Both TrainId and RouteId are missing");
	    }
	    
	    //  Update all fields safely
	    if (trainRoute.getStationName() != null) {
	        route.setStationName(trainRoute.getStationName());
	    }
	    if (trainRoute.getArrivalTime() != null) {
	        route.setArrivalTime(trainRoute.getArrivalTime());
	    }
	    if (trainRoute.getDepartureTime() != null) {
	        route.setDepartureTime(trainRoute.getDepartureTime());
	    }
	    if (trainRoute.getStopNumber() != null) {
	        route.setStopNumber(trainRoute.getStopNumber());
	    }
	    if (trainRoute.getPlatformNumber() != null) {
	        route.setPlatformNumber(trainRoute.getPlatformNumber());
	    }
	    if (trainRoute.getDistanceFromOrigin() != null) {
	        route.setDistanceFromOrigin(trainRoute.getDistanceFromOrigin());
	    }

	    return trainRouteRepo.save(route);
	}

	@Override
	public TrainRoute deleteTrainRoute(Long trainId, Long trainRouteId) {

		 TrainRoute route;

		    // Case 1: Dono IDs diye gaye hain (best case)
		    if (trainId != null && trainRouteId != null) {
		        route = trainRouteRepo.findByIdAndTrain_Id(trainRouteId, trainId)
		                .orElseThrow(() -> new RuntimeException("TrainRoute not found with given TrainId and RouteId"));
		    } 
		    // Case 2: Sirf routeId diya hai
		    else if (trainRouteId != null) {
		        route = trainRouteRepo.findById(trainRouteId)
		                .orElseThrow(() -> new RuntimeException("TrainRoute not found with given RouteId"));
		    } 
		    // Case 3: Sirf trainId diya hai (galat case)
		    else if (trainId != null) {
		        throw new RuntimeException("RouteId is required along with TrainId to update TrainRoute");
		    } 
		    else {
		        throw new RuntimeException("Both TrainId and RouteId are missing");
		    }
		
		    trainRouteRepo.delete(route);
		    
		return route;
	}

}
