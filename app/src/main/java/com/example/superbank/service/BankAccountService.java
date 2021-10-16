package com.example.superbank.service;

import com.example.superbank.entity.BankAccount;
import com.example.superbank.payload.request.BankAccountRequestDto;
import com.example.superbank.payload.request.CustomerRequestDto;
import com.example.superbank.payload.response.BankAccountResponseDto;

public interface BankAccountService extends PayloadService
        <BankAccountRequestDto, BankAccountResponseDto, Long> {

    BankAccount getSecured(Long entityId);

}
