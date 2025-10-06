package com.railconnect.DTO;

import java.time.LocalDate;
import java.util.List;

public class BookingResponse {

	private Long bookingId;
	private String passengerName;
	private Integer passengerAge;
	private String gender;
	private String bookingStatus;
	private String trainNumber;
	private String fromStation;
	private String toStation;
	private String departureTime;
	private String arrivalTime;
//	    private String 
	private double totalFare;
	private LocalDate bookingDate;
	private String userEmail;
	private String trainName;
	private String coachType;
	private List<String> bookedSeats;
	public Long getBookingId() {
		return bookingId;
	}
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
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
	public String getBookingStatus() {
		return bookingStatus;
	}
	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	public String getTrainNumber() {
		return trainNumber;
	}
	public void setTrainNumber(String trainNumber) {
		this.trainNumber = trainNumber;
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
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public double getTotalFare() {
		return totalFare;
	}
	public void setTotalFare(double totalFare) {
		this.totalFare = totalFare;
	}
	public LocalDate getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getTrainName() {
		return trainName;
	}
	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}
	public String getCoachType() {
		return coachType;
	}
	public void setCoachType(String coachType) {
		this.coachType = coachType;
	}
	public List<String> getBookedSeats() {
		return bookedSeats;
	}
	public void setBookedSeats(List<String> bookedSeats) {
		this.bookedSeats = bookedSeats;
	}
	@Override
	public String toString() {
		return "BookingResponse [bookingId=" + bookingId + ", passengerName=" + passengerName + ", passengerAge="
				+ passengerAge + ", gender=" + gender + ", bookingStatus=" + bookingStatus + ", trainNumber="
				+ trainNumber + ", fromStation=" + fromStation + ", toStation=" + toStation + ", departureTime="
				+ departureTime + ", arrivalTime=" + arrivalTime + ", totalFare=" + totalFare + ", bookingDate="
				+ bookingDate + ", userEmail=" + userEmail + ", trainName=" + trainName + ", coachType=" + coachType
				+ ", bookedSeats=" + bookedSeats + "]";
	}



	

}
