package com.railconnect.Serviceimp;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railconnect.DTO.TrainSearchResultDto;
import com.railconnect.entities.Train;
import com.railconnect.entities.TrainRoute;
import com.railconnect.repo.TrainRepo;
import com.railconnect.service.TrainSearchService;

@Service
public class TrainSearchServiceimp implements TrainSearchService{
	
	@Autowired
	private TrainRepo trainRepo;

	@Override
	public List<TrainSearchResultDto> trainSearchResultDto(String fromStation, String toStation) {
		List<Train> trains = trainRepo.findTrainsBetweenStations(fromStation, toStation);
		
		
		return trains.stream().map(train -> {
			
			TrainRoute trainRoutefromStation = train.getRoutes().stream()
					.filter(r -> r.getStationName().equalsIgnoreCase(fromStation)).
					findFirst().orElse(null);
			
			TrainRoute trainRoutetoStation = train.getRoutes().stream()
					.filter(r -> r.getStationName().equalsIgnoreCase(toStation)).
					findFirst().orElse(null);
			
			TrainSearchResultDto dto = new TrainSearchResultDto();
			  dto.setTrainNumber(train.getTrainNumber());
	            dto.setTrainName(train.getTrainName());
	            dto.setTrainType(train.getTrainType());
	            dto.setFromStation(fromStation);
	            dto.setToStation(toStation);

			
			if (trainRoutefromStation != null) {
				dto.setDepartureTime(trainRoutefromStation.getDepartureTime());
			}
			if(trainRoutetoStation != null){
				dto.setArrivalTime(trainRoutetoStation.getArrivalTime());
			}
			return dto;
		}).collect(Collectors.toList());
	}

}
