package com.example.superbank.repository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.superbank.repository.impl.BankAccountListRepository;
import com.example.superbank.repository.impl.CardListRepository;
import com.example.superbank.repository.impl.CustomerListRepository;
import com.example.superbank.repository.impl.TransactionListRepository;

@RequiresApi(api = Build.VERSION_CODES.N)
public class RepositoryStorage {

    public static final CustomerRepository customerRepository = new CustomerListRepository();

    public static final CardRepository cardRepository = new CardListRepository();

    public static final TransactionRepository transactionRepository = new TransactionListRepository();

    public static final BankAccountRepository bankAccountRepository = new BankAccountListRepository();


}
