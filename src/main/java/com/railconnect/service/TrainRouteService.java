package com.railconnect.service;

import com.railconnect.entities.TrainRoute;

public interface TrainRouteService {
	
	public TrainRoute addRouteTime(TrainRoute trainRoute);
	
	public TrainRoute updateTrainRoute(TrainRoute trainRoute, Long trainId, Long trainRouteId);
	
	public TrainRoute deleteTrainRoute(Long trainId, Long trainRouteId);
	

}
