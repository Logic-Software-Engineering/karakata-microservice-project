package com.karakata.buyerservice.staticdata;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PaymentStatus {
    PAID("Paid"),
    PENDING("Pending");

    private final String paymentStatus;

    public String getPaymentStatus() {
        return paymentStatus;
    }

    @Override
    public String toString() {
        return "PaymentStatus{" +
                "paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}
