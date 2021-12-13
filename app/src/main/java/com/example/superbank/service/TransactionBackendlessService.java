package com.example.superbank.service;

import com.backendless.Backendless;
import com.backendless.IDataStore;
import com.backendless.exceptions.BackendlessException;
import com.example.superbank.payload.request.transaction.requestDto.TransactionMapBuilder;
import com.example.superbank.payload.response.BankAccountResponseDto;
import com.example.superbank.payload.response.TransactionResponseDto;
import com.example.superbank.values.annotations.Currency;
import com.example.superbank.values.annotations.TransactionCategory;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TransactionBackendlessService {

    private IDataStore<Map> transactionStore = Backendless.Data.of("Transaction");
    private BankAccountBackendlessService bankAccountBackendlessService;

    public TransactionBackendlessService(BankAccountBackendlessService bankAccountBackendlessService) {
        this.bankAccountBackendlessService = bankAccountBackendlessService;
    }

    public TransactionResponseDto synchronousMakeTransaction(Map<String, Object> transactionMap) {
        TransactionResponseDto transactionResponseDto = new TransactionResponseDto();

        int senderAccountNumber = (int) transactionMap.get("senderAccountNumber");
        int receiverAccountNumber = (int) transactionMap.get("receiverAccountNumber");
        double transactionSum = (double) transactionMap.get("amountOfMoney");

        if (senderAccountNumber == receiverAccountNumber) {
            transactionResponseDto.setError(true);
            transactionResponseDto.getErrorCodes().add(5);
        }

        Map<String, Object> senderResponseMap = bankAccountBackendlessService.synchronousRequestBankAccount(senderAccountNumber);

        if ((boolean) senderResponseMap.get("error")) {
            transactionResponseDto.setError(true);
            transactionResponseDto.getErrorCodes().add(1);
        }

        Map<String, Object> receiverResponseMap = bankAccountBackendlessService.synchronousRequestBankAccount(receiverAccountNumber);

        if ((boolean) receiverResponseMap.get("error")) {
            transactionResponseDto.setError(true);
            transactionResponseDto.getErrorCodes().add(2);
        }

        //If there are some errors at this point, return error DTO
        if (transactionResponseDto.isError()) {
            return transactionResponseDto;
        }

        Map<String, Object> senderBankAccount = (Map<String, Object>) senderResponseMap.get("bankAccount");
        Map<String, Object> receiverBankAccount = (Map<String, Object>) receiverResponseMap.get("bankAccount");


        double senderBalance;
        double receiverBalance;

        if (Double.class == senderBankAccount.get("availableMoney").getClass()) {
            senderBalance = (double) senderBankAccount.get("availableMoney");
        } else {
            int moneyAsInt = (int) senderBankAccount.get("availableMoney");
            senderBalance = (double) moneyAsInt;
        }

        if (Double.class == receiverBankAccount.get("availableMoney").getClass()) {
            receiverBalance = (double) receiverBankAccount.get("availableMoney");
        } else {
            int moneyAsInt = (int) receiverBankAccount.get("availableMoney");
            receiverBalance = (double) moneyAsInt;
        }


        if (senderBalance < transactionSum) {
            transactionResponseDto.setError(true);
            transactionResponseDto.getErrorCodes().add(3);
            return transactionResponseDto;
        }

        senderBankAccount.put("availableMoney", senderBalance - transactionSum);

        receiverBankAccount.put("availableMoney", receiverBalance + transactionSum);

        transactionMap.put("transactionDate", Calendar.getInstance().getTime());
        transactionMap.put("senderNumber", senderBankAccount);
        transactionMap.put("receiverNumber", receiverBankAccount);

        Map<String, Object> savedTransaction = synchronousSaveTransaction(transactionMap);

        transactionResponseDto.setCategory((int) savedTransaction.get("category"));
        transactionResponseDto.setComment((String) savedTransaction.get("comment"));
        transactionResponseDto.setSender(new BankAccountResponseDto(senderAccountNumber, null));
        transactionResponseDto.setReceiver(new BankAccountResponseDto(receiverAccountNumber, null));
        transactionResponseDto.setAmountOfMoney(transactionSum);

        return transactionResponseDto;

    }


    private Map<String, Object> synchronousSaveTransaction(Map<String, Object> transaction) {

        Map<String, Object> responseMap = (Map<String, Object>) transactionStore.deepSave(transaction);

        responseMap.put("error", false);
        return responseMap;

    }

    public Map<String, Object> synchronousSupplyOfCash(double amountOfCash, double balance, Map<String, Object> bankAccount) {

        bankAccount.put("availableMoney", balance + amountOfCash);

        Map<String, Object> responseMap = new HashMap<>();

        try {
            Backendless.Data.of("BankAccount").save(bankAccount);
            TransactionMapBuilder transactionMapBuilder = new TransactionMapBuilder();
            transactionMapBuilder.setCurrency(Currency.CURRENCY_RUB)
                    .setReceiver((Integer) bankAccount.get("accountNumber")).setCategory(TransactionCategory.CASH_SUPPLY).setAmountOfMoney(amountOfCash)
                    .setTransactionDate(Calendar.getInstance().getTime());

            Map<String, Object> transactionMap = transactionMapBuilder.build();
            transactionMap.put("receiverNumber", bankAccount);

            Backendless.Data.of("Transaction").deepSave(transactionMap);
            responseMap.put("error", false);
            return responseMap;
        } catch (BackendlessException e) {
            responseMap.put("error", true);
            responseMap.put("errorMessage", e.getMessage());
            return responseMap;
        }
    }


}
