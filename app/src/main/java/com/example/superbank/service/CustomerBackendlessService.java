package com.example.superbank.service;

import com.backendless.Backendless;
import com.backendless.IDataStore;
import com.backendless.exceptions.BackendlessException;

import java.util.HashMap;
import java.util.Map;

public class CustomerBackendlessService {

    private IDataStore<Map> customerStore = Backendless.Data.of("Customer");

    public Map<String, Object> synchronizedCustomerDeepSave(Map<String, Object> customerProperties) throws BackendlessException {

        Map<String, Object> responseMap = Backendless.Data.of("Customer").deepSave(customerProperties);
        return responseMap;

    }



}
