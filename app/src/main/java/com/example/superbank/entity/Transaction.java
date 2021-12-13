package com.example.superbank.entity;

import com.example.superbank.values.annotations.TransactionCategory;
import com.example.superbank.values.annotations.Currency;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {

    private Integer transactionNumber;

    private BankAccount sender;

    private BankAccount receiver;

    private Double amountOfMoney;

    private Date transactionDate;

    @Currency
    private int currency;

    @TransactionCategory
    private int category;

    private String comment;

    private String objectId;

    public Transaction(BankAccount sender, BankAccount receiver, Double amountOfMoney, @Currency int currency,
                       Date transactionDate, @TransactionCategory int category, String comment) {
        this.sender = sender;
        this.receiver = receiver;
        this.amountOfMoney = amountOfMoney;
        this.currency = currency;
        this.transactionDate = transactionDate;
        this.category = category;
        this.comment = comment;
    }

    public Transaction() {
    }

    public Integer getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(Integer transactionNumber) {
        this.transactionNumber = transactionNumber;
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

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
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

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
