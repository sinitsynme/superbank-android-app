package com.example.superbank.service;

import com.example.superbank.payload.request.TransactionRequestDto;
import com.example.superbank.payload.response.TransactionResponseDto;

public interface TransactionService extends PayloadService<TransactionRequestDto, TransactionResponseDto, Long> {
}
