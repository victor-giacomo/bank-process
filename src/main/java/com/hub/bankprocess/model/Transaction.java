package com.hub.bankprocess.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class Transaction {

    private BigInteger id;
    private BigInteger cardNumber;
    private LocalDateTime date;
    private Double amount;

    public Transaction() { }

    public Transaction(BigInteger id, BigInteger cardNumber, LocalDateTime date, Double amount) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.date = date;
        this.amount = amount;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(BigInteger cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
