package com.railconnect.DTO;

public class PaymentInitiateRequest {
	
	private Long bookingId;
	private String paymentMethod;
	private Double amount;
	public Long getBookingId() {
		return bookingId;
	}
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "PaymentInitiateRequest [bookingId=" + bookingId + ", paymentMethod=" + paymentMethod + ", amount="
				+ amount + "]";
	}

	
}
