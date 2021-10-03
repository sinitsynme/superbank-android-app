package com.example.superbank.service.impl;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.superbank.R;
import com.example.superbank.entity.BankAccount;
import com.example.superbank.entity.Customer;
import com.example.superbank.exception.ResourceNotFountException;
import com.example.superbank.mapper.BankAccountMapper;
import com.example.superbank.mapper.CustomerMapper;
import com.example.superbank.payload.request.CustomerRequestDto;
import com.example.superbank.payload.response.BankAccountResponseDto;
import com.example.superbank.payload.response.CustomerResponseDto;
import com.example.superbank.repository.BankAccountRepository;
import com.example.superbank.repository.CustomerRepository;
import com.example.superbank.repository.impl.CustomerListRepository;
import com.example.superbank.service.BankAccountService;
import com.example.superbank.service.CustomerService;

import java.util.List;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.N)
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponseDto add(CustomerRequestDto requestDto) {

        return customerMapper.toResponseDto(customerRepository.add(customerMapper.toEntity(requestDto)));
    }


    @Override
    public CustomerResponseDto get(Long entityId) {
        return customerMapper.toResponseDto(customerRepository.get(entityId)
                .orElseThrow(() -> new ResourceNotFountException(String.format(String.valueOf(R.string.exception_customer_not_exists), entityId))));
    }

    @Override
    public List<CustomerResponseDto> getAll() {
        return customerRepository.getAll().stream().map(customerMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponseDto update(CustomerRequestDto requestDto, Long entityId) {
        Customer customerFromDb = getSecured(entityId);
        Customer updatedCustomer = customerMapper.toEntity(requestDto);
        updatedCustomer.setCustomerId(entityId);
        updatedCustomer.setBankAccount(customerFromDb.getBankAccount());

        return customerMapper.toResponseDto(customerRepository.update(updatedCustomer, entityId));

    }

    @Override
    public boolean existsById(Long entityId) {
        return customerRepository.existsById(entityId);
    }

    @Override
    public void delete(Long entityId) {
        if (!existsById(entityId)) {
            throw new ResourceNotFountException(String.format(String.valueOf(R.string.exception_customer_not_exists), entityId));
        }
        customerRepository.delete(entityId);
    }


    public Customer getSecured(Long entityId) {
        return customerRepository.get(entityId)
                .orElseThrow(() -> new ResourceNotFountException(String.format(String.valueOf(R.string.exception_customer_not_exists), entityId)));
    }

    public List<Customer> getAllSecured() {
        return customerRepository.getAll();
    }

}
