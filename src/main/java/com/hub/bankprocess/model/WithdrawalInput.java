package com.hub.bankprocess.model;

import java.math.BigInteger;

public class WithdrawalInput {
    private String action;
    private BigInteger cardnumber;
    private Double amount;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public BigInteger getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(BigInteger cardnumber) {
        this.cardnumber = cardnumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
