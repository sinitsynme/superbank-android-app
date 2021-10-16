package com.example.superbank.payload.request;

import com.example.superbank.enums.TransactionCategory;

import java.util.Currency;

public class TransactionRequestDto {

    private Long senderNumber;

    private Long receiverNumber;

    private Double amountOfMoney;

    private Currency currency;

    private TransactionCategory category;

    private String comment;

    public TransactionRequestDto(Long senderNumber, Long receiverNumber, Double amountOfMoney,
                                 Currency currency, TransactionCategory category) {
        this.senderNumber = senderNumber;
        this.receiverNumber = receiverNumber;
        this.amountOfMoney = amountOfMoney;
        this.currency = currency;
        this.category = category;
    }

    public TransactionRequestDto() {
    }

    public Long getSenderNumber() {
        return senderNumber;
    }

    public void setSenderNumber(Long senderNumber) {
        this.senderNumber = senderNumber;
    }

    public Long getReceiverNumber() {
        return receiverNumber;
    }

    public void setReceiverNumber(Long receiverNumber) {
        this.receiverNumber = receiverNumber;
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
