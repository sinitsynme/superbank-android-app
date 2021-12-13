package com.example.superbank.payload.request.transaction.requestDto;

import com.example.superbank.values.annotations.TransactionCategory;
import com.example.superbank.values.annotations.Currency;

import java.util.Date;

public class TransactionRequestDto {

    private Integer senderId;

    private Integer receiverId;

    private Double amountOfMoney;

    private Date transactionDate;

    @Currency
    private int currency;

    @TransactionCategory
    private int category;

    private String comment;

    public TransactionRequestDto(Integer senderId, Integer receiverId, Double amountOfMoney,
                                 @Currency int currency, @TransactionCategory int category, Date transactionDate) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amountOfMoney = amountOfMoney;
        this.currency = currency;
        this.category = category;
        this.transactionDate = transactionDate;
    }

    public TransactionRequestDto() {
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
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

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
