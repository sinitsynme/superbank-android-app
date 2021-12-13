package com.example.superbank.payload.response;

public class BankAccountResponseDto {

    private Integer accountId;

    private CustomerResponseDto customerResponseDto;

    public BankAccountResponseDto(Integer accountId, CustomerResponseDto customerResponseDto) {
        this.accountId = accountId;
        this.customerResponseDto = customerResponseDto;
    }

    public BankAccountResponseDto() {
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public CustomerResponseDto getCustomerResponseDto() {
        return customerResponseDto;
    }

    public void setCustomerResponseDto(CustomerResponseDto customerResponseDto) {
        this.customerResponseDto = customerResponseDto;
    }
}
