package com.railconnect.service;

import com.railconnect.entities.Fare;

public interface FaresService {

	public Fare addFareAmount(Fare fare);

//	public double calculateFare(Long bookingId ,Long trainId, String fromStation, String toStation, String coachType);

	public double calculateFare(Long bookingId);

}
