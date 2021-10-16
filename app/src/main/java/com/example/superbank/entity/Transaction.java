package com.example.superbank.entity;

import com.example.superbank.enums.TransactionCategory;

import java.util.Currency;
import java.util.Date;

public class Transaction {

    //ID
    private Long transactionId;

    private BankAccount sender;

    private BankAccount receiver;

    private Double amountOfMoney;

    private Date transactionDate;

    private Currency currency;

    private TransactionCategory category;

    public Transaction(BankAccount sender, BankAccount receiver, Double amountOfMoney, Currency currency,
                       Date transactionDate, TransactionCategory category) {
        this.sender = sender;
        this.receiver = receiver;
        this.amountOfMoney = amountOfMoney;
        this.currency = currency;
        this.transactionDate = transactionDate;
        this.category = category;
    }

    public Transaction() {
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public void setCategory(TransactionCategory category) {
        this.category = category;
    }
}
