package com.example.superbank.entity;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {

    //ID
    private Long accountId;

    private Customer customer;

    private Long availableMoney;

    private List<Card> cards = new ArrayList<>();

    private List<Transaction> transactionHistory = new ArrayList<>();

    public BankAccount() {
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(Long availableMoney) {
        this.availableMoney = availableMoney;
    }

    public void topUpMoney(Long money) {
        availableMoney += money;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void setTransactionHistory(List<Transaction> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
