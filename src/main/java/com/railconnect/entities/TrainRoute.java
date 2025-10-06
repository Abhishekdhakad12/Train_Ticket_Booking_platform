package com.railconnect.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// Completed

@Entity
@Table(name = "train_routes", uniqueConstraints = @UniqueConstraint(columnNames = {"train_id", "station_name"}))
public class TrainRoute {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Station name is required")
	@Size(min = 2, max = 100, message = "Station name must be between 2 and 100 characters")
	private String stationName;

	@Pattern(regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d$", message = "Arrival time must be in HH:mm format")
	private String arrivalTime; // HH:mm

	@Pattern(regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d$", message = "Departure time must be in HH:mm format")
	private String departureTime; // HH:mm

	@NotNull(message = "Stop number is required")
	@Min(value = 1, message = "Stop number must be at least 1")
	private Integer stopNumber; // 1,2,3...

	@NotNull(message = "Platform number is required")
	@Min(value = 1, message = "Platform number must be at least 1")
	private Integer platformNumber;

	 // e.g. Kanpur=0, Delhi=400, Jaipur=700
	@NotNull(message = "Distance from origin is required")
	@Min(value = 0, message = "Distance cannot be negative")
	private Integer distanceFromOrigin;

//    Relation with Train
	@ManyToOne(optional = false) // train must not be null
	@JoinColumn(name = "train_id", nullable = false)
	@JsonBackReference("train-routes")
	private Train train;

//	*************************************  Getter, Setter & Constructors  *************************************

	// Constructors
	public TrainRoute() {
	}

	public TrainRoute(Long id,
		@NotBlank(message = "Station name is required") @Size(min = 2, max = 100, message = "Station name must be between 2 and 100 characters") String stationName,
		@Pattern(regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d$", message = "Arrival time must be in HH:mm format") String arrivalTime,
		@Pattern(regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d$", message = "Departure time must be in HH:mm format") String departureTime,
		@NotNull(message = "Stop number is required") @Min(value = 1, message = "Stop number must be at least 1") Integer stopNumber,
		@NotNull(message = "Platform number is required") @Min(value = 1, message = "Platform number must be at least 1") Integer platformNumber,
		@NotNull(message = "Distance from origin is required") @Min(value = 0, message = "Distance cannot be negative") Integer distanceFromOrigin,
		Train train) {
	super();
	this.id = id;
	this.stationName = stationName;
	this.arrivalTime = arrivalTime;
	this.departureTime = departureTime;
	this.stopNumber = stopNumber;
	this.platformNumber = platformNumber;
	this.distanceFromOrigin = distanceFromOrigin;
	this.train = train;
}


	// Getters & Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public Integer getStopNumber() {
		return stopNumber;
	}

	public void setStopNumber(Integer stopNumber) {
		this.stopNumber = stopNumber;
	}

	public Integer getPlatformNumber() {
		return platformNumber;
	}

	public void setPlatformNumber(Integer platformNumber) {
		this.platformNumber = platformNumber;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}
	
	

	public Integer getDistanceFromOrigin() {
		return distanceFromOrigin;
	}

	public void setDistanceFromOrigin(Integer distanceFromOrigin) {
		this.distanceFromOrigin = distanceFromOrigin;
	}

	@Override
	public String toString() {
		return "TrainRoute [id=" + id + ", stationName=" + stationName + ", arrivalTime=" + arrivalTime
				+ ", departureTime=" + departureTime + ", stopNumber=" + stopNumber + ", platformNumber="
				+ platformNumber + ", distanceFromOrigin=" + distanceFromOrigin + ", train=" + train + "]";
	}

}
