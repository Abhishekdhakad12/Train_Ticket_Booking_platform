package com.railconnect.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

//✅ Completed -- > TrainController

@Entity
@Table(name = "train_schedules", uniqueConstraints = { @UniqueConstraint(columnNames = { "train_id", "dayOfWeek" }) })
public class TrainSchedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@Enumerated(EnumType.STRING)
	private DayOfWeekEnum dayOfWeek;

	@NotNull(message = "Train reference is required")
	@ManyToOne
	@JoinColumn(name = "train_id", nullable = false)
	@JsonIgnore
	private Train train;

//	*************************************  Getter, Setter & Constructors  *************************************

	
	public TrainSchedule() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TrainSchedule(Long id, DayOfWeekEnum dayOfWeek,
			@NotNull(message = "Train reference is required") Train train) {
		super();
		this.id = id;
		this.dayOfWeek = dayOfWeek;
		this.train = train;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DayOfWeekEnum getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(DayOfWeekEnum dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	@Override
	public String toString() {
		return "TrainSchedule [id=" + id + ", dayOfWeek=" + dayOfWeek + ", train=" + train + "]";
	}


	
	
}

	
