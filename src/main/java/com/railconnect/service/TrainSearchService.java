package com.railconnect.service;

import java.util.List;

import com.railconnect.DTO.TrainSearchResultDto;

public interface TrainSearchService {
	
	public List<TrainSearchResultDto> trainSearchResultDto(String fromStation, String toStation);

}
