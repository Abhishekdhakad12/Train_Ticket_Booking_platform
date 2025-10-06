package com.railconnect.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "bookings")
@EntityListeners(AuditingEntityListener.class)
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "passenger name is required")
	@Size(min = 2, max = 100, message = "passenger name must be between 2 and 100 characters")
	private String passengerName;

	@NotNull(message = "Passenger age is required")
	@Min(value = 0, message = "Passenger age must be 0 or greater")
	@Max(value = 120, message = "Passenger age must be less than or equal to 120")
	private Integer passengerAge;

	@NotBlank(message = "Gender is required")
	@Pattern(regexp = "Male|Female|Other", message = "Gender must be one of: Male, Female, Other")
	private String gender;

	@Column(nullable = false)
	private String fromStation;

	@Column(nullable = false)
	private String toStation;

	@NotBlank(message = "Booking status is required")
	@Pattern(regexp = "CONFIRMED|WAITING|CANCELLED|PENDING", message = "Booking status must be one of: CONFIRMED, WAITING, CANCELLED")
	private String bookingStatus; // CONFIRMED / WAITING / CANCELLED

	// Booking Date
	@NotNull(message = "Booking date is required")
	@FutureOrPresent(message = "Booking date cannot be in the past")
	private LocalDate bookingDate;

	@NotNull(message = "Total fare is required")
	@DecimalMin(value = "0.0", inclusive = false, message = "Total fare must be greater than 0")
	private Double totalFare;

	// User relation (ek user ke multiple bookings)
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private Users user;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "updated_By")
	private Users updatedBy;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	// Train relation (ek booking ek train ke liye hoti hai)
	@ManyToOne(optional = false)
	@JoinColumn(name = "train_id", nullable = false)
	@JsonBackReference(value = "train-bookings")
	private Train train;

	@ManyToOne
	@JoinColumn(name = "coach_id", nullable = false)
//	@JsonBackReference
	@JsonBackReference(value = "coach-bookings")
	private Coach coach;

	// Booking ↔ Seats (One booking has many seats)
	@OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
	@Size(min = 1, message = "At least one seat must be booked")
//	@JsonBackReference
	@JsonManagedReference(value = "booking-seats")
	private List<Seat> seats = new ArrayList<>();

	@OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
	private Payment payment;

	public Booking() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Booking(Long id,
			@NotBlank(message = "passenger name is required") @Size(min = 2, max = 100, message = "passenger name must be between 2 and 100 characters") String passengerName,
			@NotNull(message = "Passenger age is required") @Min(value = 0, message = "Passenger age must be 0 or greater") @Max(value = 120, message = "Passenger age must be less than or equal to 120") Integer passengerAge,
			@NotBlank(message = "Gender is required") @Pattern(regexp = "Male|Female|Other", message = "Gender must be one of: Male, Female, Other") String gender,
			String fromStation, String toStation,
			@NotBlank(message = "Booking status is required") @Pattern(regexp = "CONFIRMED|WAITING|CANCELLED|PENDING", message = "Booking status must be one of: CONFIRMED, WAITING, CANCELLED") String bookingStatus,
			@NotNull(message = "Booking date is required") @FutureOrPresent(message = "Booking date cannot be in the past") LocalDate bookingDate,
			@NotNull(message = "Total fare is required") @DecimalMin(value = "0.0", inclusive = false, message = "Total fare must be greater than 0") Double totalFare,
			Users user, LocalDateTime createdAt, Users updatedBy, LocalDateTime updatedAt, Train train, Coach coach,
			@Size(min = 1, message = "At least one seat must be booked") List<Seat> seats, Payment payment) {
		super();
		this.id = id;
		this.passengerName = passengerName;
		this.passengerAge = passengerAge;
		this.gender = gender;
		this.fromStation = fromStation;
		this.toStation = toStation;
		this.bookingStatus = bookingStatus;
		this.bookingDate = bookingDate;
		this.totalFare = totalFare;
		this.user = user;
		this.createdAt = createdAt;
		this.updatedBy = updatedBy;
		this.updatedAt = updatedAt;
		this.train = train;
		this.coach = coach;
		this.seats = seats;
		this.payment = payment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Double getTotalFare() {
		return totalFare;
	}

	public void setTotalFare(Double totalFare) {
		this.totalFare = totalFare;
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

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	public Coach getCoach() {
		return coach;
	}

	public void setCoach(Coach coach) {
		this.coach = coach;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
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
		return "Booking [id=" + id + ", passengerName=" + passengerName + ", passengerAge=" + passengerAge + ", gender="
				+ gender + ", fromStation=" + fromStation + ", toStation=" + toStation + ", bookingStatus="
				+ bookingStatus + ", bookingDate=" + bookingDate + ", totalFare=" + totalFare + ", user=" + user
				+ ", createdAt=" + createdAt + ", updatedBy=" + updatedBy + ", updatedAt=" + updatedAt + ", train="
				+ train + ", coach=" + coach + ", seats=" + seats + ", payment=" + payment + "]";
	}

}
