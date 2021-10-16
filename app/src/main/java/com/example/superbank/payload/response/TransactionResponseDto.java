package com.example.superbank.payload.response;

import com.example.superbank.enums.TransactionCategory;

import java.util.Currency;

public class TransactionResponseDto {

    private BankAccountResponseDto sender;

    private BankAccountResponseDto receiver;

    private Double amountOfMoney;

    private Currency currency;

    private TransactionCategory category;

    private String comment;

    public TransactionResponseDto(BankAccountResponseDto sender, BankAccountResponseDto receiver, Double amountOfMoney, Currency currency, TransactionCategory category) {
        this.sender = sender;
        this.receiver = receiver;
        this.amountOfMoney = amountOfMoney;
        this.currency = currency;
        this.category = category;
    }

    public TransactionResponseDto() {
    }

    public BankAccountResponseDto getSender() {
        return sender;
    }

    public void setSender(BankAccountResponseDto sender) {
        this.sender = sender;
    }

    public BankAccountResponseDto getReceiver() {
        return receiver;
    }

    public void setReceiver(BankAccountResponseDto receiver) {
        this.receiver = receiver;
    }

    public Double getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(Double amountOfMoney) {
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
