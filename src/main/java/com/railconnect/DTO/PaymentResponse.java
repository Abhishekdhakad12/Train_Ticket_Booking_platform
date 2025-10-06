package com.railconnect.DTO;

import com.railconnect.entities.PaymentStatus;

public class PaymentResponse {

	private String paymentId;
	private PaymentStatus paymentStatus;
	private String paymentMethod;
	private Double amount;
	private String message;
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "PaymentResponse [paymentId=" + paymentId + ", paymentStatus=" + paymentStatus + ", paymentMethod="
				+ paymentMethod + ", amount=" + amount + ", message=" + message + "]";
	}
	
	
	
}
