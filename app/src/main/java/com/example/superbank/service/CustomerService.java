package com.example.superbank.service;

import com.example.superbank.payload.request.CustomerRequestDto;
import com.example.superbank.payload.response.CustomerResponseDto;
import com.example.superbank.service.secured.Updater;

public interface CustomerService extends PayloadService<CustomerRequestDto, CustomerResponseDto, Long>,
        Updater<CustomerRequestDto, CustomerResponseDto, Long> {
}
