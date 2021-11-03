package com.example.superbank.payload.request.transaction.requestDto;

import com.example.superbank.values.annotations.Currency;
import com.example.superbank.values.annotations.TransactionCategory;

public class TransactionRequestDtoBuilder {

    private Long senderNumber;

    private Long receiverNumber;

    private Double amountOfMoney;

    @Currency
    private int currency;

    @TransactionCategory
    private int category;

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

    public TransactionRequestDtoBuilder setCurrency(@Currency int currency){
        this.currency = currency;
        return this;
    }

    public TransactionRequestDtoBuilder setAmountOfMoney(Double amountOfMoney){
        this.amountOfMoney = amountOfMoney;
        return this;
    }

    public TransactionRequestDtoBuilder setCategory(@TransactionCategory int category){
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
