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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;


@Entity
@Table(
    name = "fares",
    uniqueConstraints = @UniqueConstraint(columnNames = {"train_id", "coach_type"})
)
public class Fare {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Coach type is required")
	@Pattern(regexp = "^(Sleeper|AC|General|ChairCar|FirstClass)$", message = "Coach type must be one of: Sleeper, AC, General, ChairCar, FirstClass")
	private String coachType;

	
	@NotNull(message = "Per km rate is required")
    @Positive(message = "Per km rate must be positive")
    private Double perKmRate;  // e.g. AC=2.0, Sleeper=1.0


	@ManyToOne
	@JoinColumn(name = "train_id", nullable = false)
	@JsonBackReference("train-fares")
	private Train train;

//	******************  Getter, Setter & Constructors  *****************

	public Fare() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Fare(Long id,
			@NotBlank(message = "Coach type is required") @Pattern(regexp = "^(Sleeper|AC|General|ChairCar|FirstClass)$", message = "Coach type must be one of: Sleeper, AC, General, ChairCar, FirstClass") String coachType,
			@NotNull(message = "Per km rate is required") @Positive(message = "Per km rate must be positive") Double perKmRate,
			Train train) {
		super();
		this.id = id;
		this.coachType = coachType;
		this.perKmRate = perKmRate;
		this.train = train;
	}

	@Override
	public String toString() {
		return "Fare [id=" + id + ", coachType=" + coachType + ", perKmRate=" + perKmRate + ", train=" + train + "]";
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

	public Double getPerKmRate() {
		return perKmRate;
	}

	public void setPerKmRate(Double perKmRate) {
		this.perKmRate = perKmRate;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

}
