package com.railconnect.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;	
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

//✅ CRUD Completed

@Entity
@Table(name = "trains")
@EntityListeners(AuditingEntityListener.class)
public class Train {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Train name is required")
	@Size(min = 2, max = 100, message = "Train name must be between 2 and 100 characters")
	private String trainName;

	@NotBlank(message = "Train number is required")
	@Pattern(regexp = "^[0-9]{3,6}$", message = "Train number must be 3-6 digits")
	@Column(unique = true, nullable = false)
	private String trainNumber;

	@NotBlank(message = "Train type is required")
	@Pattern(regexp = "Express|Superfast|Passenger|Rajdhani|Shatabdi", message = "Train type must be one of: Express, Superfast, Passenger, Rajdhani, Shatabdi")
	private String trainType;

	@NotBlank(message = "Status is required")
	@Pattern(regexp = "Active|Cancelled|Delayed", message = "Status must be one of: Active, Cancelled, Delayed")
	private String status;

	@Positive(message = "Total distance must be positive")
	private Double totalDistance; // in KM

	@NotBlank(message = "Source station is required")
	@Size(min = 2, max = 100, message = "Source station must be between 2 and 100 characters")
	private String source;

	@NotBlank(message = "Destination station is required")
	@Size(min = 2, max = 100, message = "Destination station must be between 2 and 100 characters")
	private String destination;

	// Relation with coaches (One Train → Many Coaches)
	@OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("train-coaches")
	private List<Coach> coaches = new ArrayList<>();

	// One train has many running days
	@OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TrainSchedule> schedules = new ArrayList<>();

	// One train has many routes (stations)
	@OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("train-routes")
	private List<TrainRoute> routes = new ArrayList<>();

	// One train has many fares (per coach type)
	@OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("train-fares")
	private List<Fare> fares = new ArrayList<>();

	@OneToMany(mappedBy = "train", fetch = FetchType.EAGER)
	@JsonManagedReference("train-bookings")
	private List<Booking> bookings = new ArrayList<>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "created_by", updatable = false)
	private Users createdBy;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "updated_by")
	private Users updatedBy;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	//	*************************************  Getter, Setter & Constructors  *************************************

	public Train() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Train(Long id,
			@NotBlank(message = "Train name is required") @Size(min = 2, max = 100, message = "Train name must be between 2 and 100 characters") String trainName,
			@NotBlank(message = "Train number is required") @Pattern(regexp = "^[0-9]{3,6}$", message = "Train number must be 3-6 digits") String trainNumber,
			@NotBlank(message = "Train type is required") @Pattern(regexp = "Express|Superfast|Passenger|Rajdhani|Shatabdi", message = "Train type must be one of: Express, Superfast, Passenger, Rajdhani, Shatabdi") String trainType,
			@NotBlank(message = "Status is required") @Pattern(regexp = "Active|Cancelled|Delayed", message = "Status must be one of: Active, Cancelled, Delayed") String status,
			@Positive(message = "Total distance must be positive") Double totalDistance,
			@NotBlank(message = "Source station is required") @Size(min = 2, max = 100, message = "Source station must be between 2 and 100 characters") String source,
			@NotBlank(message = "Destination station is required") @Size(min = 2, max = 100, message = "Destination station must be between 2 and 100 characters") String destination,
			List<Coach> coaches, List<TrainSchedule> schedules, List<TrainRoute> routes, List<Fare> fares,
			List<Booking> bookings, Users createdBy, LocalDateTime createdAt, Users updatedBy,
			LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.trainName = trainName;
		this.trainNumber = trainNumber;
		this.trainType = trainType;
		this.status = status;
		this.totalDistance = totalDistance;
		this.source = source;
		this.destination = destination;
		this.coaches = coaches;
		this.schedules = schedules;
		this.routes = routes;
		this.fares = fares;
		this.bookings = bookings;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.updatedBy = updatedBy;
		this.updatedAt = updatedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public String getTrainNumber() {
		return trainNumber;
	}

	public void setTrainNumber(String trainNumber) {
		this.trainNumber = trainNumber;
	}

	public String getTrainType() {
		return trainType;
	}

	public void setTrainType(String trainType) {
		this.trainType = trainType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(Double totalDistance) {
		this.totalDistance = totalDistance;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public List<Coach> getCoaches() {
		return coaches;
	}

	public void setCoaches(List<Coach> coaches) {
		this.coaches = coaches;
	}

	public List<TrainSchedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<TrainSchedule> schedules) {
		this.schedules = schedules;
	}

	public List<TrainRoute> getRoutes() {
		return routes;
	}

	public void setRoutes(List<TrainRoute> routes) {
		this.routes = routes;
	}

	public List<Fare> getFares() {
		return fares;
	}

	public void setFares(List<Fare> fares) {
		this.fares = fares;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public Users getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Users createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Users getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Users updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Train [id=" + id + ", trainName=" + trainName + ", trainNumber=" + trainNumber + ", trainType="
				+ trainType + ", status=" + status + ", totalDistance=" + totalDistance + ", source=" + source
				+ ", destination=" + destination + ", coaches=" + coaches + ", schedules=" + schedules + ", routes="
				+ routes + ", fares=" + fares + ", bookings=" + bookings + ", createdBy=" + createdBy + ", createdAt="
				+ createdAt + ", updatedBy=" + updatedBy + ", updatedAt=" + updatedAt + "]";
	}

}
