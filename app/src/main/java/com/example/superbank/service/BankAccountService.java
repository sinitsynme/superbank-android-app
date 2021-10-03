package com.example.superbank.service;

import com.example.superbank.payload.request.CustomerRequestDto;
import com.example.superbank.payload.response.BankAccountResponseDto;

public interface BankAccountService extends PayloadService
        <CustomerRequestDto, BankAccountResponseDto, Long> {



}
