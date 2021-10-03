package com.example.superbank.payload.response;

public class BankAccountResponseDto {

    private Long accountId;

    private CustomerResponseDto customerResponseDto;

    public BankAccountResponseDto(Long accountId, CustomerResponseDto customerResponseDto) {
        this.accountId = accountId;
        this.customerResponseDto = customerResponseDto;
    }

    public BankAccountResponseDto() {
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public CustomerResponseDto getCustomerResponseDto() {
        return customerResponseDto;
    }

    public void setCustomerResponseDto(CustomerResponseDto customerResponseDto) {
        this.customerResponseDto = customerResponseDto;
    }
}
