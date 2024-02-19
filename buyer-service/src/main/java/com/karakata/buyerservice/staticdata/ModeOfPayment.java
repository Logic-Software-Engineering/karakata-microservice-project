package com.karakata.buyerservice.staticdata;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ModeOfPayment {
    BANK_TRANSFER("Bank transfer"),
    CARD("Card");

    private final String modeOfPayment;

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    @Override
    public String toString() {
        return "ModeOfPayment{" +
                "modeOfPayment='" + modeOfPayment + '\'' +
                '}';
    }
}
