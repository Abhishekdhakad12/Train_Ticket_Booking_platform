package com.railconnect.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Amount is required")
    private Double totalamount;

    @Size(max = 100)
    private String paypalOrderId;  // PayPal Order ID

    @NotBlank(message = "Payment method is required")
    @Size(max = 50, message = "Method too long")
    private String paymentMethod;  // CARD, UPI etc.

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Payment status is required")
    private PaymentStatus status;  // PENDING, SUCCESS, FAILED

    @OneToOne
    @JoinColumn(name = "booking_id", unique = true)
    private Booking booking;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Payment() {
        super();
    }

    public Payment(Long id, Double totalamount, String paypalOrderId, String paymentMethod,
                   PaymentStatus status, Booking booking, LocalDateTime createdAt,
                   LocalDateTime updatedAt) {
        this.id = id;
        this.totalamount = totalamount;
        this.paypalOrderId = paypalOrderId;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.booking = booking;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Payment [id=" + id + ", totalamount=" + totalamount + ", paypalOrderId=" + paypalOrderId
                + ", paymentMethod=" + paymentMethod + ", status=" + status + ", booking=" + booking + ", createdAt="
                + createdAt + ", updatedAt=" + updatedAt + "]";
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getTotalamount() { return totalamount; }
    public void setTotalamount(Double totalamount) { this.totalamount = totalamount; }

    public String getPaypalOrderId() { return paypalOrderId; }
    public void setPaypalOrderId(String paypalOrderId) { this.paypalOrderId = paypalOrderId; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }

    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
