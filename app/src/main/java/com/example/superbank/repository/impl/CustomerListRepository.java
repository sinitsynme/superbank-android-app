package com.example.superbank.repository.impl;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.superbank.entity.BankAccount;
import com.example.superbank.entity.Customer;
import com.example.superbank.repository.CrudRepository;
import com.example.superbank.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiresApi(api = Build.VERSION_CODES.N)
public class CustomerListRepository implements CustomerRepository {

    private final List<Customer> accountList = new ArrayList<>();

    private static long accountIdCounter = 1000000000;

    @Override
    public Customer add(Customer entity) {
        entity.setCustomerId(accountIdCounter++);
        accountList.add(entity);

        return entity;
    }


    @Override
    public Customer update(Customer entity, Long entityId) {
        accountList.removeIf(it -> it.getCustomerId().equals(entityId));
        accountList.add(entity);
        return entity;
    }

    @Override
    public Optional<Customer> get(Long entityId) {
        return accountList.stream().filter(it -> it.getCustomerId().equals(entityId)).findFirst();
    }

    @Override
    public List<Customer> getAll() {
        return accountList;
    }

    @Override
    public boolean existsById(Long entityId) {
        return accountList.stream().anyMatch(it -> it.getCustomerId().equals(entityId));
    }

    @Override
    public void delete(Long entityId) {
        accountList.removeIf(it -> it.getCustomerId().equals(entityId));
    }
}
