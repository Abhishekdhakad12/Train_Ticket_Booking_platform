package com.railconnect.Serviceimp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.railconnect.DTO.BookingRequest;
import com.railconnect.DTO.BookingResponse;
import com.railconnect.entities.Booking;
import com.railconnect.entities.Coach;
import com.railconnect.entities.Seat;
import com.railconnect.entities.Train;
import com.railconnect.entities.TrainRoute;
import com.railconnect.entities.Users;
import com.railconnect.repo.BookingRepo;
import com.railconnect.repo.CoachRepo;
import com.railconnect.repo.SeatRepo;
import com.railconnect.repo.TrainRepo;
import com.railconnect.repo.TrainRouteRepo;
import com.railconnect.repo.UserRepo;
import com.railconnect.service.BookingService;

import jakarta.transaction.Transactional;

@Service
public class BookingServiceimp implements BookingService {

	@Autowired
	private BookingRepo bookingRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private TrainRepo trainRepo;

	@Autowired
	private TrainRouteRepo trainRouteRepo;

	@Autowired
	private FaresServiceimp faresServiceimp;

	@Autowired
	private CoachRepo coachRepo;
	@Autowired
	private SeatRepo seatRepo;

	@Override
	public BookingResponse createBooking(BookingRequest request, String userEmail) {

		Users users = userRepo.findByEmail(userEmail);
		if (users == null) {
			throw new RuntimeException("User is not found");
		}

		Train train = trainRepo.findById(request.getTrainId())
				.orElseThrow(() -> new RuntimeException("Train not found"));

		TrainRoute from = trainRouteRepo.findByTrain_IdAndStationName(train.getId(), request.getFromStation())
				.orElseThrow(() -> new RuntimeException("Invalid fromStation for train " + train.getId()));

		TrainRoute to = trainRouteRepo.findByTrain_IdAndStationName(train.getId(), request.getToStation())
				.orElseThrow(() -> new RuntimeException("Invalid toStation for train" + train.getId()));

		Coach coach = coachRepo.findById(request.getCoachId())
				.orElseThrow(() -> new RuntimeException("Coach not found"));

		Booking booking = new Booking();

		booking.setPassengerName(request.getPassengerName());
		booking.setPassengerAge(request.getPassengerAge());
		booking.setGender(request.getGender());
		booking.setBookingStatus("PENDING");
		booking.setFromStation(request.getFromStation());
		booking.setToStation(request.getToStation());
		booking.setTotalFare(0.01);
		booking.setBookingDate(LocalDate.now());
		booking.setUser(users);
		booking.setCreatedAt(LocalDateTime.now());
		booking.setTrain(train);
		booking.setCoach(coach);

		List<String> bookedSeat = new ArrayList<>();
		for (String seatNo : request.getSeatNumbers()) {

			Seat seat = seatRepo.findByCoachIdAndSeatNumber(coach.getId(), seatNo)
					.orElseThrow(() -> new RuntimeException("Seat " + seatNo + " not found"));

			if (Boolean.TRUE.equals(seat.getIsBooked())) {
				throw new RuntimeException("Seat " + seatNo + " already booked");

			}
			seat.setIsBooked(true);
			seat.setBooking(booking);
			booking.getSeats().add(seat);
			bookedSeat.add(seatNo);
		}

		Booking saved = bookingRepo.save(booking);

//		TrainRoute fromRoute = trainRouteRepo.findByTrainIdAndStationName(train.getId(), saved.getFromStation())
//				.orElseThrow(() -> new RuntimeException("Invalid fromStation"));
//		TrainRoute toRoute = trainRouteRepo.findByTrainIdAndStationName(train.getId(), saved.getToStation())
//				.orElseThrow(() -> new RuntimeException("Invalid toStation"));

		BookingResponse response = new BookingResponse();

		response.setBookingId(saved.getId());
		response.setPassengerName(saved.getPassengerName());
		response.setPassengerAge(saved.getPassengerAge());
		response.setGender(saved.getGender());
		response.setTrainNumber(train.getTrainNumber()); // train number
		response.setTrainName(train.getTrainName());
		response.setFromStation(saved.getFromStation()); // from station
		response.setToStation(saved.getToStation()); // to station
		response.setDepartureTime(from.getDepartureTime().toString()); // departure time
		response.setArrivalTime(to.getArrivalTime().toString());
		response.setBookingStatus(saved.getBookingStatus());
		response.setBookingDate(saved.getBookingDate());
		response.setUserEmail(users.getEmail());
		response.setTrainName(train.getTrainName());
		response.setCoachType(coach.getCoachType());
		response.setBookedSeats(bookedSeat);

		return response;
	}

	@Transactional
	@Override
	public BookingResponse calculateFare(Long bookingId) {

		if (bookingId == null) {
			throw new RuntimeException("Booking id is null");
		}

		Booking existbooking = bookingRepo.findById(bookingId)
				.orElseThrow(() -> new RuntimeException("Booking id not found :_" + bookingId));

		double totalfare = faresServiceimp.calculateFare(existbooking.getId());
		
		
		
		existbooking.setTotalFare(totalfare);
//		existbooking.setBookingStatus("CONFIRMED");
		Train train = existbooking.getTrain();
	    bookingRepo.save(existbooking);
		
		BookingResponse response = new BookingResponse();
		
		
		response.setBookingId(existbooking.getId());
		response.setPassengerName(existbooking.getPassengerName());
		response.setPassengerAge(existbooking.getPassengerAge());
		response.setGender(existbooking.getGender());
		response.setTrainNumber(train.getTrainNumber()); // train number
		response.setTrainName(train.getTrainName());
		response.setFromStation(existbooking.getFromStation()); // from station
		response.setToStation(existbooking.getToStation()); // to station
//		response.setDepartureTime(from.getDepartureTime().toString()); // departure time
//		response.setArrivalTime(to.getArrivalTime().toString());
		response.setTotalFare(totalfare);
		response.setBookingStatus(existbooking.getBookingStatus());
		response.setBookingDate(existbooking.getBookingDate());
		response.setUserEmail(existbooking.getUser().getEmail());
		response.setTrainName(train.getTrainName());
		response.setCoachType(existbooking.getCoach().getCoachType());

		return response;

	}

}
