package com.railconnect.service;

import java.util.List;

import com.railconnect.entities.Coach;

public interface CoachService {
	
	public Coach addCoach(Coach coach);
	
	public List<Coach> getAllCoach();
	
	public Coach update(Coach coachDetails, Long coachId, String trainNumber, Long trainId);
	
	public Coach getCoach(Long trainId, Long coachId);

	Coach deleteCoach(Long trainId, Long coachId);

}