package com.example.superbank.repository.impl;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.superbank.entity.BankAccount;
import com.example.superbank.repository.BankAccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiresApi(api = Build.VERSION_CODES.N)
public class BankAccountListRepository implements BankAccountRepository {

    private final List<BankAccount> accountList = new ArrayList<>();

    private static long accountIdCounter = 1000000000;

    @Override
    public BankAccount add(BankAccount entity) {
        entity.setAccountId(accountIdCounter++);
        accountList.add(entity);

        return entity;
    }


    @Override
    public BankAccount update(BankAccount entity, Long entityId) {
        accountList.removeIf(it -> it.getAccountId().equals(entityId));
        accountList.add(entity);

        return entity;
    }

    @Override
    public Optional<BankAccount> get(Long entityId) {
        return accountList.stream().filter(it -> it.getAccountId().equals(entityId)).findFirst();
    }

    @Override
    public List<BankAccount> getAll() {
        return accountList;
    }

    @Override
    public boolean existsById(Long entityId) {
        return accountList.stream().anyMatch(it -> it.getAccountId().equals(entityId));
    }

    @Override
    public void delete(Long entityId) {
        accountList.removeIf(it -> it.getAccountId().equals(entityId));
    }
}
