package com.railconnect.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

//✅ CRUD Completed ..Seat

@Entity
@Table(name = "coaches")
public class Coach {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Coach type is required")
	@Pattern(regexp = "^(Sleeper|AC|General|ChairCar|FirstClass)$", message = "Coach type must be one of: Sleeper, AC, General, ChairCar, FirstClass")
	private String coachType; // e.g. Sleeper, AC, General, ChairCar, FirstClass

	@NotNull(message = "Total seats are required")
	@Min(value = 10, message = "A coach must have at least 10 seats")
	@Max(value = 150, message = "A coach cannot have more than 150 seats")
	private Integer totalSeats;

	// Many coaches belong to one train

	@ManyToOne(optional = false) // train must not be null
	@JoinColumn(name = "train_id", nullable = false)
	@JsonBackReference("train-coaches")
	private Train train;

	@OneToMany(mappedBy = "coach", cascade = CascadeType.ALL)
	@JsonManagedReference(value = "coach-bookings")
	private List<Booking> bookings;

	// One coach has many seats
	@OneToMany(mappedBy = "coach", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("coach-seats")
	private List<Seat> seats = new ArrayList<>();

	public Coach() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Coach(Long id,
			@NotBlank(message = "Coach type is required") @Pattern(regexp = "^(Sleeper|AC|General|ChairCar|FirstClass)$", message = "Coach type must be one of: Sleeper, AC, General, ChairCar, FirstClass") String coachType,
			@NotNull(message = "Total seats are required") @Min(value = 10, message = "A coach must have at least 10 seats") @Max(value = 150, message = "A coach cannot have more than 150 seats") Integer totalSeats,
			Train train, List<Seat> seats) {
		super();
		this.id = id;
		this.coachType = coachType;
		this.totalSeats = totalSeats;
		this.train = train;
		this.seats = seats;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCoachType() {
		return coachType;
	}

	public void setCoachType(String coachType) {
		this.coachType = coachType;
	}

	public Integer getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(Integer totalSeats) {
		this.totalSeats = totalSeats;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	@Override
	public String toString() {
		return "Coach [id=" + id + ", coachType=" + coachType + ", totalSeats=" + totalSeats + ", train=" + train
				+ ", seats=" + seats + "]";
	}

}
