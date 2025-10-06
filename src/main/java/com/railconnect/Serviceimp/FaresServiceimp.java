package com.railconnect.Serviceimp;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railconnect.entities.Booking;
import com.railconnect.entities.Coach;
import com.railconnect.entities.Fare;
import com.railconnect.entities.Seat;
import com.railconnect.entities.Train;
import com.railconnect.entities.TrainRoute;
import com.railconnect.repo.BookingRepo;
import com.railconnect.repo.Faresrepo;
import com.railconnect.repo.TrainRepo;
import com.railconnect.repo.TrainRouteRepo;
import com.railconnect.service.FaresService;

import jakarta.transaction.Transactional;

@Service
public class FaresServiceimp implements FaresService {

	@Autowired
	private Faresrepo faresrepo;

	@Autowired
	private BookingRepo bookingRepo;

	@Autowired
	private TrainRouteRepo trainRouteRepo;

	@Autowired
	private TrainRepo trainRepo;

	@Transactional
	@Override
	public Fare addFareAmount(Fare fare) {
		Long trainid = fare.getTrain().getId();
		Train train = trainRepo.findById(trainid)
				.orElseThrow(() -> new RuntimeException("Train not found with id: " + trainid));
		fare.setTrain(train);
		return faresrepo.save(fare);
	}

	@Override
	public double calculateFare(Long bookingId) {

		double totalFare = 0.0;

		Booking booking = bookingRepo.findById(bookingId)
				.orElseThrow(() -> new RuntimeException("Invalid Booking id " + bookingId));

		Train train = booking.getTrain();
		String fromStation = booking.getFromStation();
		String toStation = booking.getToStation();
		List<Seat> seats = booking.getSeats();
		Coach coachs = booking.getCoach();

		Map<String, Long> seatsPerCoach = booking.getSeats().stream()
				.collect(Collectors.groupingBy(seat -> seat.getCoach().getCoachType(), Collectors.counting()));

		for (Map.Entry<String, Long> entry : seatsPerCoach.entrySet()) {
			String coachType = entry.getKey();
			Long seatCount = entry.getValue();

			TrainRoute from = trainRouteRepo.findByTrain_IdAndStationName(train.getId(), fromStation)
					.orElseThrow(() -> new RuntimeException("Invalid fromStation for train " + train.getId()));

			TrainRoute to = trainRouteRepo.findByTrain_IdAndStationName(train.getId(), toStation)
					.orElseThrow(() -> new RuntimeException("Invalid toStation for train" + train.getId()));

			Integer distance = Math.abs(to.getDistanceFromOrigin() - from.getDistanceFromOrigin());

			if (distance == 0) {
				throw new RuntimeException("Invalid route selection: same or wrong order");
			}

			Fare fare = faresrepo.findByTrainIdAndCoachType(train.getId(), coachs.getCoachType())
					.orElseThrow(() -> new RuntimeException("Fare not defined for coach " + coachs.getCoachType()));

			
			
			totalFare = distance * fare.getPerKmRate() * seatCount;

		}
		return Math.round(totalFare);
	}

}
