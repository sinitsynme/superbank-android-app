package com.example.superbank.service.impl;

import com.example.superbank.payload.request.TransactionRequestDto;
import com.example.superbank.payload.response.TransactionResponseDto;
import com.example.superbank.repository.TransactionRepository;
import com.example.superbank.service.TransactionService;

import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionResponseDto add(TransactionRequestDto requestDto) {
        return null;
    }

    @Override
    public TransactionResponseDto get(Long entityId) {
        return null;
    }

    @Override
    public List<TransactionResponseDto> getAll() {
        return null;
    }

    @Override
    public TransactionResponseDto update(TransactionRequestDto requestDto, Long entityId) {
        return null;
    }

    @Override
    public boolean existsById(Long entityId) {
        return false;
    }

    @Override
    public void delete(Long entityId) {

    }
}
