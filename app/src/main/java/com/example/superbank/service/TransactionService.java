package com.example.superbank.service;

import com.example.superbank.entity.Transaction;
import com.example.superbank.payload.request.transaction.requestDto.TransactionRequestDto;
import com.example.superbank.payload.response.TransactionResponseDto;

import java.util.List;

public interface TransactionService extends PayloadService<TransactionRequestDto, TransactionResponseDto, Long> {

    Transaction getSecured(Long entityId);

    List<Transaction> getAllSecured();

}
