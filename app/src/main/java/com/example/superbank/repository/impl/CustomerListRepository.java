package com.example.superbank.repository.impl;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.superbank.entity.Customer;
import com.example.superbank.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiresApi(api = Build.VERSION_CODES.N)
public class CustomerListRepository implements CustomerRepository {

    private final List<Customer> customerList = new ArrayList<>();

    private static long customerIdCounter = 1;

    @Override
    public Customer add(Customer entity) {
        entity.setCustomerId(customerIdCounter++);
        customerList.add(entity);

        return entity;
    }


    @Override
    public Customer update(Customer entity, Long entityId) {
        customerList.removeIf(it -> it.getCustomerId().equals(entityId));
        customerList.add(entity);
        return entity;
    }

    @Override
    public Optional<Customer> get(Long entityId) {
        return customerList.stream().filter(it -> it.getCustomerId().equals(entityId)).findFirst();
    }

    @Override
    public List<Customer> getAll() {
        return customerList;
    }

    @Override
    public boolean existsById(Long entityId) {
        return customerList.stream().anyMatch(it -> it.getCustomerId().equals(entityId));
    }

    @Override
    public void delete(Long entityId) {
        customerList.removeIf(it -> it.getCustomerId().equals(entityId));
    }

    @Override
    public boolean contains(Customer customer) {
        return customerList.contains(customer);
    }
}
