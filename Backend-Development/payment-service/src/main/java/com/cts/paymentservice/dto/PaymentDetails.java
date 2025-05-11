package com.cts.paymentservice.dto;

import java.util.UUID;

import com.cts.paymentservice.enums.PaymentMethod;
import com.cts.paymentservice.enums.PaymentStatus;

public class PaymentDetails {
	private UUID paymentId;
	private double amountPaid;
	private PaymentMethod paymentMode;
	private PaymentStatus paymentStatus;
	
	/**
	 * @param amountPaid
	 * @param paymentMode
	 * @param paymentStatus
	 */
	public PaymentDetails(double amountPaid, PaymentMethod paymentMode, PaymentStatus paymentStatus, UUID paymentId) {
		this.amountPaid = amountPaid;
		this.paymentMode = paymentMode;
		this.paymentStatus = paymentStatus;
		this.paymentId = paymentId;
	}
	/**
	 * @return the paymentId
	 */
	public UUID getPaymentId() {
		return paymentId;
	}
	/**
	 * @param paymentId the paymentId to set
	 */
	public void setPaymentId(UUID paymentId) {
		this.paymentId = paymentId;
	}
	/**
	 * @return the amountPaid
	 */
	public double getAmountPaid() {
		return amountPaid;
	}
	/**
	 * @param amountPaid the amountPaid to set
	 */
	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}
	/**
	 * @return the paymentMode
	 */
	public PaymentMethod getPaymentMode() {
		return paymentMode;
	}
	/**
	 * @param paymentMode the paymentMode to set
	 */
	public void setPaymentMode(PaymentMethod paymentMode) {
		this.paymentMode = paymentMode;
	}
	/**
	 * @return the paymentStatus
	 */
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	/**
	 * @param paymentStatus the paymentStatus to set
	 */
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	
}
