package com.railconnect.Serviceimp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railconnect.entities.DayOfWeekEnum;
import com.railconnect.entities.Train;
import com.railconnect.entities.TrainSchedule;
import com.railconnect.entities.Users;
import com.railconnect.repo.TrainRepo;
import com.railconnect.repo.UserRepo;
import com.railconnect.service.TrainService;

import jakarta.transaction.Transactional;

@Service
public class TrainSeriviceimp implements TrainService {

	@Autowired
	private TrainRepo trainRepo;

	@Autowired
	private UserRepo userRepo;

	@Transactional
	@Override
	public Train addNewtrain(Train train, String userEmail) {

		// Duplicate train number check
		Optional<Train> existingTrain = trainRepo.findByTrainNumber(train.getTrainNumber());

		Users users = userRepo.findByEmail(userEmail);

		if (users == null) {
			throw new RuntimeException("user not found");

		}

		if (existingTrain.isPresent()) {
			throw new RuntimeException("Train with this number already exists");
		}

		if (train.getSource().equalsIgnoreCase(train.getDestination())) {
			throw new RuntimeException("Source and destination cannot match");
		}

		Set<DayOfWeekEnum> uniqueDays = new HashSet<>();
		for (TrainSchedule schedule : train.getSchedules()) {

			if (!uniqueDays.add(schedule.getDayOfWeek())) {
				throw new IllegalArgumentException("Duplicate schedule found for day: " + schedule.getDayOfWeek());
			}
			schedule.setTrain(train);
		}

		train.setCreatedBy(users);
		train.setCreatedAt(LocalDateTime.now());
		return trainRepo.save(train);
	}

	@Override
	public List<Train> getAllTrains() {
		List<Train> allTrains = trainRepo.findAll();
		return allTrains;
	}

	@Override
	public Train gettrainByIdORTrainNumber(Long id, String trainNumber) {

		Train train = null;

		if (id != null) {
			train = trainRepo.findById(id).orElseThrow(() -> new RuntimeException("Train not found with id: " + id));
			
		} else {
			train = trainRepo.findByTrainNumber(trainNumber)
					.orElseThrow(() -> new RuntimeException("Train not found with id: " + id));
			
		}

		return train;
	}

	@Transactional
	@Override
	public Train updateTrain(Train updates, Long id, String updatedByEmail, String trainNumber) {

		Users users = userRepo.findByEmail(updatedByEmail);
		if (users == null) {
			throw new RuntimeException("user not found");
		}
		
		// Fetch existing train
		Train train = null;
		if (id != null) {
			train = trainRepo.findById(id).orElseThrow(() -> new RuntimeException("Train not found with id: " + id));
		} else {
			train = trainRepo.findByTrainNumber(trainNumber)
					.orElseThrow(() -> new RuntimeException("Train not found with id: " + trainNumber));

		}

		// Partial updates
		if (updates.getTrainName() != null)
			train.setTrainName(updates.getTrainName());
		if (updates.getTrainType() != null)
			train.setTrainType(updates.getTrainType());
		if (updates.getStatus() != null)
			train.setStatus(updates.getStatus());
		if (updates.getTotalDistance() != null)
			train.setTotalDistance(updates.getTotalDistance());
		if (updates.getSource() != null)
			train.setSource(updates.getSource());
		if (updates.getDestination() != null)
			train.setDestination(updates.getDestination());
		if (updates.getUpdatedAt() != null)
			train.setUpdatedAt(LocalDateTime.now());
		 

		train.setUpdatedBy(users);

		if (updates.getSchedules() != null) {
			Set<DayOfWeekEnum> existingDays = train.getSchedules().stream().map(TrainSchedule::getDayOfWeek)
					.collect(Collectors.toSet());

			for (TrainSchedule trainSchedule : updates.getSchedules()) {
				if (existingDays.contains(trainSchedule.getDayOfWeek())) {
					continue;
				}
				TrainSchedule schedule = new TrainSchedule();
				schedule.setDayOfWeek(trainSchedule.getDayOfWeek());
				schedule.setTrain(train);
				train.getSchedules().add(schedule);
			}

		}

		return trainRepo.save(train);
	}

	@Override
	public Train deleteTrainByIdORTrainNumber(Long id, String trainNumber) {
		Train train = null;

		if (id != null) {
			train = trainRepo.findById(id).orElseThrow(() -> new RuntimeException("Train not found with id: " + id));
		} else {
			train = trainRepo.findByTrainNumber(trainNumber)
					.orElseThrow(() -> new RuntimeException("Train not found with id: " + id));

		}
		return train;

	}

}
