package com.example.superbank.payload.response;

public class BankAccountResponseDto {

    private Long accountId;

    public BankAccountResponseDto(Long accountId, String firstName, String lastName) {
        this.accountId = accountId;
    }

    public BankAccountResponseDto() {
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

}
