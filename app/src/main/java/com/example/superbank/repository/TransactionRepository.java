package com.example.superbank.repository;

import com.example.superbank.entity.Transaction;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    List<Transaction> getAllTransactionsOfAccount(Long accountId);
}
