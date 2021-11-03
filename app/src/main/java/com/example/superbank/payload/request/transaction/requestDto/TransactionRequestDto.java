package com.example.superbank.payload.request.transaction.requestDto;

import com.example.superbank.values.annotations.TransactionCategory;
import com.example.superbank.values.annotations.Currency;

public class TransactionRequestDto {

    private Long senderId;

    private Long receiverId;

    private Double amountOfMoney;

    @Currency
    private int currency;

    @TransactionCategory
    private int category;

    private String comment;

    public TransactionRequestDto(Long senderId, Long receiverId, Double amountOfMoney,
                                 @Currency int currency, @TransactionCategory int category) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amountOfMoney = amountOfMoney;
        this.currency = currency;
        this.category = category;
    }

    public TransactionRequestDto() {
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Double getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(Double amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(@Currency int currency) {
        this.currency = currency;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(@TransactionCategory int category) {
        this.category = category;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
