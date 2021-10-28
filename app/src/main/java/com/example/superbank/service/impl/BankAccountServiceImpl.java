package com.example.superbank.service.impl;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.superbank.entity.BankAccount;
import com.example.superbank.entity.Customer;
import com.example.superbank.exception.ResourceNotFountException;
import com.example.superbank.mapper.BankAccountMapper;
import com.example.superbank.payload.request.BankAccountRequestDto;
import com.example.superbank.payload.response.BankAccountResponseDto;
import com.example.superbank.repository.BankAccountRepository;
import com.example.superbank.repository.CustomerRepository;
import com.example.superbank.service.BankAccountService;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.N)
public class BankAccountServiceImpl implements BankAccountService {

    private final CustomerRepository customerRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper = BankAccountMapper.INSTANCE;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, CustomerRepository customerRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public BankAccountResponseDto add(BankAccountRequestDto requestDto) {
        Customer customer = customerRepository.get(requestDto.getCustomerId()).orElseThrow(() -> new ResourceNotFountException("No such customer"));
        BankAccount bankAccount = new BankAccount();
        customer.setBankAccount(bankAccount);
        bankAccount.setCustomer(customer);
        return bankAccountMapper.toResponseDto(bankAccountRepository.add(bankAccount));

    }

    @Override
    public BankAccountResponseDto get(Long entityId) {
        return bankAccountMapper.toResponseDto(bankAccountRepository.get(entityId)
                .orElseThrow(() -> new ResourceNotFountException("Bank account doesn't exist")));
    }

    @Override
    public List<BankAccountResponseDto> getAll() {
        return bankAccountRepository.getAll().stream().map(bankAccountMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public BankAccountResponseDto update(BankAccountRequestDto requestDto, Long entityId) {
        BankAccount bankAccountFromDb = bankAccountRepository.get(entityId)
                .orElseThrow(() -> new ResourceNotFountException(String.format(Locale.US,
                        "Bank account with id = %d doesn't exist", entityId)));

        bankAccountFromDb.setAvailableMoney(requestDto.getAvailableMoney());
        return bankAccountMapper.toResponseDto(bankAccountRepository.update(bankAccountFromDb, entityId));
    }

    @Override
    public boolean existsById(Long entityId) {
        return bankAccountRepository.existsById(entityId);
    }

    @Override
    public void delete(Long entityId) {
        if(!existsById(entityId)) throw new ResourceNotFountException("Bank account doesn't exist");
        customerRepository.delete(getSecured(entityId).getCustomer().getCustomerId());
        bankAccountRepository.delete(entityId);
    }

    @Override
    public BankAccount getSecured(Long entityId) {
        return bankAccountRepository.get(entityId)
                .orElseThrow(() -> new ResourceNotFountException("Bank account doesn't exist"));
    }
}
