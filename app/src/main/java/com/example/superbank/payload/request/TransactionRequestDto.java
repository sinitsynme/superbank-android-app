package com.example.superbank.payload.request;

import com.example.superbank.entity.BankAccount;
import com.example.superbank.enums.TransactionCategory;

import java.util.Currency;

public class TransactionRequestDto {

    private BankAccount sender;

    private BankAccount receiver;

    private Long amountOfMoney;

    private Currency currency;

    private TransactionCategory category;

    private String comment;

    public TransactionRequestDto(BankAccount sender, BankAccount receiver, Long amountOfMoney,
                                 Currency currency, TransactionCategory category) {
        this.sender = sender;
        this.receiver = receiver;
        this.amountOfMoney = amountOfMoney;
        this.currency = currency;
        this.category = category;
    }

    public TransactionRequestDto() {
    }

    public BankAccount getSender() {
        return sender;
    }

    public void setSender(BankAccount sender) {
        this.sender = sender;
    }

    public BankAccount getReceiver() {
        return receiver;
    }

    public void setReceiver(BankAccount receiver) {
        this.receiver = receiver;
    }

    public Long getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(Long amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
