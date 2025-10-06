package com.railconnect.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "seats")
public class Seat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Seat number is required")
	@Pattern(regexp = "^[A-Z]\\d{1,2}-\\d{1,3}$", message = "Seat number must follow format like S1-01, A2-12, etc.")
	@Column(nullable = false)
	private String seatNumber; // e.g. "S1-01"

	@NotNull(message = "Booking status is required")
	private Boolean isBooked = false;

	// Relation with Coach
	@ManyToOne(optional = false)
	@JoinColumn(name = "coach_id", nullable = false)
	@JsonBackReference("coach-seats")
	private Coach coach;

	@ManyToOne
	@JoinColumn(name = "booking_id", nullable = true)
	@JsonBackReference(value = "booking-seats")
	private Booking booking;

	public Seat() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Seat(Long id,
			@NotBlank(message = "Seat number is required") @Pattern(regexp = "^[A-Z]\\d{1,2}-\\d{1,3}$", message = "Seat number must follow format like S1-01, A2-12, etc.") String seatNumber,
			@NotNull(message = "Booking status is required") Boolean isBooked, Coach coach, Booking booking) {
		super();
		this.id = id;
		this.seatNumber = seatNumber;
		this.isBooked = isBooked;
		this.coach = coach;
		this.booking = booking;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public Boolean getIsBooked() {
		return isBooked;
	}

	public void setIsBooked(Boolean isBooked) {
		this.isBooked = isBooked;
	}

	public Coach getCoach() {
		return coach;
	}

	public void setCoach(Coach coach) {
		this.coach = coach;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	@Override
	public String toString() {
		return "Seat [id=" + id + ", seatNumber=" + seatNumber + ", isBooked=" + isBooked + ", coach=" + coach
				+ ", booking=" + booking + "]";
	}

}
