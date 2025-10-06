package com.railconnect.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Booking ID is required")
     private String paymentId;        // Random unique id
;

	@NotNull(message = "Amount is required")
	@DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
	private Double amount;

	@NotBlank(message = "Payment method is required")
	private String paymentMethod; // CARD, UPI, NETBANKING etc.

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Payment status is required")
	private PaymentStatus status; // PENDING, SUCCESS, FAILED

	@OneToOne
	@JoinColumn(name ="id")
	private Booking booking;

	private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


	public Payment() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getPaymentId() {
		return paymentId;
	}


	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}


	public Double getAmount() {
		return amount;
	}


	public void setAmount(Double amount) {
		this.amount = amount;
	}


	public String getPaymentMethod() {
		return paymentMethod;
	}


	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}


	public PaymentStatus getStatus() {
		return status;
	}


	public void setStatus(PaymentStatus status) {
		this.status = status;
	}


	public Booking getBooking() {
		return booking;
	}


	public void setBooking(Booking booking) {
		this.booking = booking;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}


	public Payment(Long id, @NotBlank(message = "Booking ID is required") String paymentId,
			@NotNull(message = "Amount is required") @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0") Double amount,
			@NotBlank(message = "Payment method is required") String paymentMethod,
			@NotNull(message = "Payment status is required") PaymentStatus status, Booking booking,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.paymentId = paymentId;
		this.amount = amount;
		this.paymentMethod = paymentMethod;
		this.status = status;
		this.booking = booking;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}


	@Override
	public String toString() {
		return "Payment [id=" + id + ", paymentId=" + paymentId + ", amount=" + amount + ", paymentMethod="
				+ paymentMethod + ", status=" + status + ", booking=" + booking + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}


	
}
