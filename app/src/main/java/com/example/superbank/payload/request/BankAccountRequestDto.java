package com.example.superbank.payload.request;

public class BankAccountRequestDto {

    private Long customerId;

    public BankAccountRequestDto(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
