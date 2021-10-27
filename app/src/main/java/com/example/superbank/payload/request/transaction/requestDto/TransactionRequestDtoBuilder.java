package com.example.superbank.payload.request.transaction.requestDto;

import com.example.superbank.enums.TransactionCategory;

import java.util.Currency;

public class TransactionRequestDtoBuilder {

    private Long senderNumber;

    private Long receiverNumber;

    private Double amountOfMoney;

    private Currency currency;

    private TransactionCategory category;

    private String comment;

    public TransactionRequestDtoBuilder() {
    }

    public TransactionRequestDtoBuilder setSender(Long sender){
        this.senderNumber = sender;
        return this;
    }

    public TransactionRequestDtoBuilder setReceiver(Long receiver){
        this.receiverNumber = receiver;
        return this;
    }

    public TransactionRequestDtoBuilder setCurrency(Currency currency){
        this.currency = currency;
        return this;
    }

    public TransactionRequestDtoBuilder setAmountOfMoney(Double amountOfMoney){
        this.amountOfMoney = amountOfMoney;
        return this;
    }

    public TransactionRequestDtoBuilder setCategory(TransactionCategory category){
        this.category = category;
        return this;
    }

    public TransactionRequestDtoBuilder setComment(String comment){
        this.comment = comment;
        return this;
    }


    public TransactionRequestDto build(){
        TransactionRequestDto requestDto =  new TransactionRequestDto(senderNumber, receiverNumber, amountOfMoney, currency, category);
        requestDto.setComment(comment);
        return requestDto;

    }

}
