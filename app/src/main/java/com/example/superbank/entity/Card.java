package com.example.superbank.entity;

import java.util.Date;

public class Card {

    //ID
    private Long cardNumber;

    private BankAccount account;

    private Long availableMoney;

    private String holder;

    private Date validUntil;

    private boolean isBlocked;

    public Card(BankAccount account, Date validUntil) {
        this.account = account;
        this.validUntil = validUntil;
    }

    public Card() {
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public BankAccount getAccount() {
        return account;
    }

    public void setAccount(BankAccount account) {
        this.account = account;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public Long getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(Long availableMoney) {
        this.availableMoney = availableMoney;
    }
}
