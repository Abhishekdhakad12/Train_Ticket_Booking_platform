package com.railconnect.DTO;

import java.util.List;

public class BookingRequest {

	private Long trainId;
	private Long coachId;
	private String passengerName;
	private Integer passengerAge;
	private String gender;
	private List<String> seatNumbers;

	// 👇 Added fields
	private String fromStation;
	private String toStation;

	// Getters & Setters
	public Long getTrainId() {
		return trainId;
	}

	public void setTrainId(Long trainId) {
		this.trainId = trainId;
	}

	public Long getCoachId() {
		return coachId;
	}

	public void setCoachId(Long coachId) {
		this.coachId = coachId;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public Integer getPassengerAge() {
		return passengerAge;
	}

	public void setPassengerAge(Integer passengerAge) {
		this.passengerAge = passengerAge;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<String> getSeatNumbers() {
		return seatNumbers;
	}

	public void setSeatNumbers(List<String> seatNumbers) {
		this.seatNumbers = seatNumbers;
	}

	public String getFromStation() {
		return fromStation;
	}

	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}

	public String getToStation() {
		return toStation;
	}

	public void setToStation(String toStation) {
		this.toStation = toStation;
	}

	@Override
	public String toString() {
		return "BookingRequest [trainId=" + trainId + ", coachId=" + coachId + ", passengerName=" + passengerName
				+ ", passengerAge=" + passengerAge + ", gender=" + gender + ", seatNumbers=" + seatNumbers
				+ ", fromStation=" + fromStation + ", toStation=" + toStation + "]";
	}

}
