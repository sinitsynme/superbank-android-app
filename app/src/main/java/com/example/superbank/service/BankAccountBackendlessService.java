package com.example.superbank.service;

import com.backendless.Backendless;
import com.backendless.IDataStore;
import com.backendless.exceptions.BackendlessException;
import com.backendless.persistence.DataQueryBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankAccountBackendlessService {

    private IDataStore<Map> bankAccountStore = Backendless.Data.of("BankAccount");

    public Map<String, Object> synchronousRequestBankAccount(int accountNumber) {

        Map<String, Object> responseMap = new HashMap<>();

        String whereClause = String.format("accountNumber = '%d'", accountNumber);
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);

        List<Map> bankAccountList = bankAccountStore.find(queryBuilder);
        if (bankAccountList.isEmpty()) {
            responseMap.put("error", true);
        } else {
            responseMap.put("error", false);
            responseMap.put("bankAccount", bankAccountList.get(0));
        }

        return responseMap;
    }

    public Map<String, Object> synchronizedSave(Map<String, Object> bankAccount) throws BackendlessException {

        return (Map<String, Object>) bankAccountStore.save(bankAccount);

    }


}
