package com.example.superbank.payload.request;

public class CardRequestDto {

    private Long bankAccountId;

    public CardRequestDto(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }
}
