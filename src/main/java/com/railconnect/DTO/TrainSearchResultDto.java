package com.railconnect.DTO;

public class TrainSearchResultDto {

	private String trainNumber;
	private String trainName;
	private String trainType;
	private String fromStation;
	private String toStation;
	private String departureTime;
	private String arrivalTime;

	public TrainSearchResultDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TrainSearchResultDto(String trainNumber, String trainName, String trainType, String fromStation,
			String toStation, String departureTime, String arrivalTime) {
		super();
		this.trainNumber = trainNumber;
		this.trainName = trainName;
		this.trainType = trainType;
		this.fromStation = fromStation;
		this.toStation = toStation;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
	}

	public String getTrainNumber() {
		return trainNumber;
	}

	public void setTrainNumber(String trainNumber) {
		this.trainNumber = trainNumber;
	}

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public String getTrainType() {
		return trainType;
	}

	public void setTrainType(String trainType) {
		this.trainType = trainType;
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

	@Override
	public String toString() {
		return "TrainSearchResultDto [trainNumber=" + trainNumber + ", trainName=" + trainName + ", trainType="
				+ trainType + ", fromStation=" + fromStation + ", toStation=" + toStation + ", departureTime="
				+ departureTime + ", arrivalTime=" + arrivalTime + "]";
	}

}
