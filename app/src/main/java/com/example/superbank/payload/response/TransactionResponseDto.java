package com.example.superbank.payload.response;

import androidx.recyclerview.widget.LinearSmoothScroller;

import com.example.superbank.values.annotations.Currency;
import com.example.superbank.values.annotations.TransactionCategory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * When {@link TransactionResponseDto } is being processed during the commit,
 * firstly field error is being checked.
 *
 * If it is true, then errorCodes will be checked and user will get specified error messages in UI.
 *
 * If it is false, then the transaction has been committed successfully.
 *
 */

public class TransactionResponseDto implements Serializable {

    private boolean error = false;

    /**
     * Error codes:
     * 1 - senderAccountNumber is not valid
     * 2 - receiverAccountNumber is not valid
     * 3 - not enough amount of money on sender's account
     * 4 - invalid category
     * 5 - senderAccountNumber and receiverAccountNumber are equal
     * 6 - unidentified error
     */

    private List<Integer> errorCodes = new ArrayList<>();

    private Long transactionId;

    private BankAccountResponseDto sender;

    private BankAccountResponseDto receiver;

    private Double amountOfMoney;

    @Currency
    private int currency;

    @TransactionCategory
    private int category;

    private String comment;

    public TransactionResponseDto(
            BankAccountResponseDto sender, BankAccountResponseDto receiver,
            Double amountOfMoney, @Currency int currency, @TransactionCategory int category) {

        this.sender = sender;
        this.receiver = receiver;
        this.amountOfMoney = amountOfMoney;
        this.currency = currency;
        this.category = category;
    }

    public TransactionResponseDto() {
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Integer> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(List<Integer> errorCodes) {
        this.errorCodes = errorCodes;
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
