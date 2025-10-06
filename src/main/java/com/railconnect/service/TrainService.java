package com.railconnect.service;

import java.util.List;

import com.railconnect.entities.Train;

public interface TrainService {

//	train add 
	 public Train addNewtrain(Train train, String userEmail);
	 
//	 All trains get 
	 public List<Train> getAllTrains();
	 
//	 get by id , trainNumber
	 public Train gettrainByIdORTrainNumber(Long id, String trainNumber);
	 
//	 update
	 public Train updateTrain(Train updates, Long id, String updatedByEmail, String trainNumber);
	 
//	 delete
	 public Train deleteTrainByIdORTrainNumber(Long id, String trainNumber);

}
