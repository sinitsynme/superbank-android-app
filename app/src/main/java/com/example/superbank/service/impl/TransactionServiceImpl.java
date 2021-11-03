package com.example.superbank.service.impl;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.superbank.entity.BankAccount;
import com.example.superbank.entity.Transaction;
import com.example.superbank.enums.TransactionCategory;
import com.example.superbank.exception.ResourceNotFountException;
import com.example.superbank.mapper.TransactionMapper;
import com.example.superbank.payload.request.transaction.requestDto.TransactionRequestDto;
import com.example.superbank.payload.response.TransactionResponseDto;
import com.example.superbank.repository.BankAccountRepository;
import com.example.superbank.repository.TransactionRepository;
import com.example.superbank.service.TransactionService;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.N)
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;
    private final TransactionMapper transactionMapper = TransactionMapper.INSTANCE;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  BankAccountRepository bankAccountRepository) {
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    //Add two-sided transaction
    @Override
    public TransactionResponseDto add(TransactionRequestDto requestDto) {

        if (requestDto.getCategory() == TransactionCategory.CASH_SUPPLY || requestDto.getCategory() == TransactionCategory.CASH_WITHDRAW) {
            return addSingleSidedTransaction(requestDto);
        }

        return addDoubleSidedTransaction(requestDto);
    }

    private TransactionResponseDto addDoubleSidedTransaction(TransactionRequestDto requestDto) {
        BankAccount sender = bankAccountRepository.get(requestDto.getSenderId())
                .orElseThrow(() -> new ResourceNotFountException("Sender bank account doesn't exist"));
        BankAccount receiver = bankAccountRepository.get(requestDto.getReceiverId())
                .orElseThrow(() -> new ResourceNotFountException("Receiver bank account doesn't exist"));

        sender.setAvailableMoney(sender.getAvailableMoney() - requestDto.getAmountOfMoney());
        receiver.setAvailableMoney(receiver.getAvailableMoney() + requestDto.getAmountOfMoney());

        Transaction transaction = transactionMapper.toEntity(requestDto);
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setTransactionDate(Calendar.getInstance().getTime());

        sender.getTransactionHistory().add(transaction);
        receiver.getTransactionHistory().add(transaction);

        transactionRepository.add(transaction);
        bankAccountRepository.update(sender, requestDto.getSenderId());
        bankAccountRepository.update(receiver, requestDto.getReceiverId());

        return transactionMapper.toResponseDto(transaction);
    }

    private TransactionResponseDto addSingleSidedTransaction(TransactionRequestDto requestDto) {
        BankAccount receiver = bankAccountRepository.get(requestDto.getReceiverId())
                .orElseThrow(() -> new ResourceNotFountException("Receiver bank account doesn't exist"));

        if (requestDto.getCategory() == TransactionCategory.CASH_WITHDRAW)
            receiver.setAvailableMoney(receiver.getAvailableMoney() - requestDto.getAmountOfMoney());

        else if (requestDto.getCategory() == TransactionCategory.CASH_SUPPLY)
            receiver.setAvailableMoney(receiver.getAvailableMoney() + requestDto.getAmountOfMoney());

        Transaction transaction = transactionMapper.toEntity(requestDto);
        transaction.setReceiver(receiver);
        transaction.setTransactionDate(Calendar.getInstance().getTime());

        receiver.getTransactionHistory().add(transaction);
        transactionRepository.add(transaction);
        bankAccountRepository.update(receiver, requestDto.getReceiverId());

        return transactionMapper.toResponseDto(transaction);
    }

    @Override
    public TransactionResponseDto get(Long entityId) {
        return transactionMapper.toResponseDto(transactionRepository
                .get(entityId).orElseThrow(() -> new ResourceNotFountException(
                        String.format(Locale.US, "Transaction with ID = %d wasn't found", entityId))));
    }

    @Override
    public List<TransactionResponseDto> getAll() {
        return transactionRepository.getAll().stream()
                .map(transactionMapper::toResponseDto).collect(Collectors.toList());
    }


    //Soon
    @Override
    public TransactionResponseDto update(TransactionRequestDto requestDto, Long entityId) {
        return null;
    }

    @Override
    public boolean existsById(Long entityId) {
        return transactionRepository.existsById(entityId);
    }

    @Override
    public void delete(Long entityId) {
        if (!existsById(entityId)) throw new ResourceNotFountException(
                String.format(Locale.US, "Transaction with ID = %d wasn't found", entityId));
    }

    //Temporary
    @Override
    public Transaction getSecured(Long entityId) {
        return transactionRepository.get(entityId).orElseThrow(() -> new ResourceNotFountException(
                String.format(Locale.US, "Transaction with ID = %d wasn't found", entityId)));
    }

    //Temporary
    @Override
    public List<Transaction> getAllSecured() {
        return transactionRepository.getAll();
    }
}
