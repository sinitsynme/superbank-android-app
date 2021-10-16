package com.example.superbank.repository.impl;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.superbank.entity.Transaction;
import com.example.superbank.repository.TransactionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiresApi(api = Build.VERSION_CODES.N)
public class TransactionListRepository implements TransactionRepository {

    private final List<Transaction> transactionList = new ArrayList<>();

    private static long transactionIdCounter = 1;

    @Override
    public Transaction add(Transaction entity) {
        entity.setTransactionId(transactionIdCounter++);
        transactionList.add(entity);

        return entity;
    }

    @Override
    public Transaction update(Transaction entity, Long entityId) {
        transactionList.removeIf(it -> it.getTransactionId().equals(entityId));
        transactionList.add(entity);
        return entity;
    }

    @Override
    public Optional<Transaction> get(Long entityId) {
        return transactionList.stream().filter(it -> it.getTransactionId().equals(entityId)).findFirst();
    }

    @Override
    public List<Transaction> getAll() {
        return transactionList;
    }

    @Override
    public boolean existsById(Long entityId) {
        return transactionList.stream().anyMatch(it -> it.getTransactionId().equals(entityId));
    }

    @Override
    public void delete(Long entityId) {
        transactionList.removeIf(it -> it.getTransactionId().equals(entityId));
    }
}
