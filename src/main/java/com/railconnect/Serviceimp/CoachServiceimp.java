package com.railconnect.Serviceimp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Service;

import com.railconnect.entities.Coach;
import com.railconnect.entities.Seat;
import com.railconnect.entities.Train;
import com.railconnect.repo.CoachRepo;
import com.railconnect.repo.SeatRepo;
import com.railconnect.repo.TrainRepo;
import com.railconnect.service.CoachService;

import jakarta.transaction.Transactional;

@Service
public class CoachServiceimp implements CoachService {

	private final AccessDeniedHandler accessDeniedHandler;

	@Autowired
	private CoachRepo coachRepo;

	@Autowired
	private TrainRepo trainRepo;

	@Autowired
	private SeatRepo seatRepo;

	CoachServiceimp(AccessDeniedHandler accessDeniedHandler) {
		this.accessDeniedHandler = accessDeniedHandler;
	}

	@Transactional
	@Override
	public Coach addCoach(Coach coach) {

		// 1. Check Train id present or not
		if (coach.getTrain() == null || coach.getTrain().getId() == null) {
			throw new IllegalArgumentException("Train ID is required to add a coach");
		}

		Long trainid = coach.getTrain().getId();
		Train train = trainRepo.findById(trainid)
				.orElseThrow(() -> new RuntimeException("Train not found with id: " + trainid));
		coach.setTrain(train);

		Coach savecoach = coachRepo.save(coach);

		// Prefix map
		Map<String, String> prefixMap = Map.of("Sleeper", "S", "AC", "A", "General", "G", "ChairCar", "C", "FirstClass",
				"F");

		String prefix = prefixMap.getOrDefault(coach.getCoachType(), "X");
		if ("X".equals(prefix)) {
			throw new IllegalArgumentException("Invalid coach type: " + coach.getCoachType());
		}
		List<Seat> seatsToSave = new ArrayList<>(coach.getTotalSeats());
		for (int i = 1; i <= coach.getTotalSeats(); i++) {
			Seat seat = new Seat();
			seat.setSeatNumber(prefix + savecoach.getId() + "-" + String.format("%02d", i));
			seat.setIsBooked(false);
			seat.setCoach(savecoach);
			seatsToSave.add(seat);

		}

		// save all seats in one go
		seatRepo.saveAll(seatsToSave);

		// attach seats to coach (optional - if you want to return coach with seats)
		savecoach.setSeats(seatsToSave);

		return savecoach;
	}

	@Transactional
	@Override
	public Coach update(Coach coachDetails, Long coachId, String trainNumber, Long trainId) {

		// Step 1: Validate input
		if (trainId == null && (trainNumber == null || trainNumber.isEmpty())) {
			throw new IllegalArgumentException("Either trainId or trainNumber must be provided");
		}
		if (coachId == null) {
			throw new IllegalArgumentException("Coach ID is required for update");
		}

		// Step 2: Fetch Train object
		Train existingTrain;
		if (trainId != null) {
			existingTrain = trainRepo.findById(trainId)
					.orElseThrow(() -> new RuntimeException("Train not found with id: " + trainId));
		} else {
			existingTrain = trainRepo.findByTrainNumber(trainNumber)
					.orElseThrow(() -> new RuntimeException("Train not found with number: " + trainNumber));
		}

		// Step 3: Fetch Coach linked to the train
		Coach existingCoach = coachRepo.findByIdAndTrain(coachId, existingTrain)
				.orElseThrow(() -> new RuntimeException("Coach not found with ID " + coachId + " for this train"));

		// Step 4: Partial update (update only non-null fields)
		if (coachDetails.getCoachType() != null) {
			existingCoach.setCoachType(coachDetails.getCoachType());
		}

		if (coachDetails.getTotalSeats() != null) {
			existingCoach.setTotalSeats(coachDetails.getTotalSeats());

			// Optional: adjust seats list if totalSeats changed
		}

//	    if (coachDetails.getSeats() != null && !coachDetails.getSeats().isEmpty()) {
//	    	
////	    	A: Fetch coach + seats
//	    	  List<Seat> seats = existingCoach.getSeats();    
//	    	  String oldCoachType = existingCoach.getCoachType();
//	    	  String newCoachType = coachDetails.getCoachType(); 
//	    	  
//	    	 if (!oldCoachType.equals(newCoachType)) {
//				
//	    		 for(Seat seat : seats) {
//	    			 
//	    		 }
//	    		 
//			}  
//	    }

		// Step 5: Save and return
		return coachRepo.save(existingCoach);
	}

	@Override
	public List<Coach> getAllCoach() {
		List<Coach> coach = coachRepo.findAll();

		if (coach == null) {

		}

		return coach;
	}

	@Override
	public Coach getCoach(Long trainId, Long coachId) {

		Coach coach = null;
		
		if (trainId == null && coachId == null) {
		    throw new RuntimeException("Please provide at least trainId or coachId");
		}
		
		Train train = null;
		if (trainId != null) {
		    train = trainRepo.findById(trainId)
		            .orElseThrow(() -> new RuntimeException("Train not found : " + trainId));
		}
		
		if (train != null && coachId != null) {
		    coach = coachRepo.findByIdAndTrain(coachId, train)
		            .orElseThrow(() -> new RuntimeException("Coach not found for this train"));
		} 
		else if (coachId != null) {
		    coach = coachRepo.findById(coachId)
		            .orElseThrow(() -> new RuntimeException("Coach not found : " + coachId));
		}
		else if (trainId != null && coachId == null) {
		        throw new RuntimeException("Please provide coachId when trainId is given");
		    }
		else {
			throw new RuntimeException("Unexpected condition");
		}

		
		return coach;
	}
	
	@Override
	public Coach deleteCoach(Long trainId, Long coachId) {

		Coach coach = null;
		
		if (trainId == null && coachId == null) {
		    throw new RuntimeException("Please provide at least trainId or coachId");
		}
		
		Train train = null;
		if (trainId != null) {
		    train = trainRepo.findById(trainId)
		            .orElseThrow(() -> new RuntimeException("Train not found : " + trainId));
		}
		
		if (train != null && coachId != null) {
		    coach = coachRepo.findByIdAndTrain(coachId, train)
		            .orElseThrow(() -> new RuntimeException("Coach not found for this train"));
		} 
		else if (coachId != null) {
		    coach = coachRepo.findById(coachId)
		            .orElseThrow(() -> new RuntimeException("Coach not found : " + coachId));
		}
		else if (trainId != null && coachId == null) {
		        throw new RuntimeException("Please provide coachId when trainId is given");
		    }
		else {
			throw new RuntimeException("Unexpected condition");
		}

	    coachRepo.delete(coach);
		return coach;
	}


}
