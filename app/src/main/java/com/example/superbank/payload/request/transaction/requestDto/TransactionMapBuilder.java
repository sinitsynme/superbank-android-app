package com.example.superbank.payload.request.transaction.requestDto;

import com.example.superbank.values.annotations.Currency;
import com.example.superbank.values.annotations.TransactionCategory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TransactionMapBuilder {

    private Integer senderAccountNumber;

    private Integer receiverAccountNumber;

    private Double amountOfMoney;

    private Date transactionDate;

    @Currency
    private int currency;

    @TransactionCategory
    private int category;

    private String comment;

    public TransactionMapBuilder() {
    }

    public TransactionMapBuilder setSender(Integer sender){
        this.senderAccountNumber = sender;
        return this;
    }

    public TransactionMapBuilder setReceiver(Integer receiver){
        this.receiverAccountNumber = receiver;
        return this;
    }

    public TransactionMapBuilder setCurrency(@Currency int currency){
        this.currency = currency;
        return this;
    }

    public TransactionMapBuilder setAmountOfMoney(Double amountOfMoney){
        this.amountOfMoney = amountOfMoney;
        return this;
    }

    public TransactionMapBuilder setCategory(@TransactionCategory int category){
        this.category = category;
        return this;
    }

    public TransactionMapBuilder setComment(String comment){
        this.comment = comment;
        return this;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Map<String, Object> build(){
        Map<String, Object> result = new HashMap<>();
        result.put("senderAccountNumber", senderAccountNumber);
        result.put("receiverAccountNumber", receiverAccountNumber);
        result.put("transactionDate", transactionDate);
        result.put("comment", comment);
        result.put("amountOfMoney", amountOfMoney);
        result.put("currency", currency);
        result.put("category", category);


       return result;

    }

}
