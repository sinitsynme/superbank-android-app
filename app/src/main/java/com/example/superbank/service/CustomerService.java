package com.example.superbank.service;

import com.example.superbank.payload.request.CustomerRequestDto;
import com.example.superbank.payload.response.CustomerResponseDto;

public interface CustomerService extends PayloadService<CustomerRequestDto, CustomerResponseDto, Long> {
}
