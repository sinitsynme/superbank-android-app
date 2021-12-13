package com.example.superbank.service;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.UserService;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationBackendlessService {

    private UserService userService = Backendless.UserService;
    private CustomerBackendlessService customerBackendlessService = new CustomerBackendlessService();
    private BankAccountBackendlessService bankAccountBackendlessService = new BankAccountBackendlessService();

    public Map<String, Object> synchronizedLogin(String username, String password) {
        Map<String, Object> responseMap = new HashMap<>();

        try {
            BackendlessUser user = userService.login(username, password);
            responseMap.put("loggedIn", true);
            return responseMap;
        }
        catch (BackendlessException exception){
            responseMap.put("loggedIn", false);
            responseMap.put("errorCode", exception.getCode());
            return responseMap;
        }
    }


    public Map<String, Object> synchronizedRegistration(Map<String, Object> userProperties, Map<String, Object> customerProperties){
        Map<String, Object> responseMap = new HashMap<>();

        BackendlessUser user = new BackendlessUser();
        user.setProperties(userProperties);

        try {
            user = userService.register(user);

            Map<String, Object> bankAccountMap = new HashMap<>();
            customerProperties.put("bankAccount", bankAccountMap);
            customerProperties.put("userId", user.getUserId());

            Map<String, Object> customerResponse =  customerBackendlessService.synchronizedCustomerDeepSave(customerProperties);

            Map<String, Object> savedBankAccount = (Map<String, Object>) customerResponse.get("bankAccount");
            savedBankAccount.put("customerId", customerResponse.get("objectId"));

            bankAccountBackendlessService.synchronizedSave(savedBankAccount);
            responseMap.put("error", false);

            return responseMap;
        }
        catch (BackendlessException e){
            responseMap.put("error", true);
            responseMap.put("errorMessage", e.getMessage());
            return responseMap;
        }

        catch (NullPointerException e){
            responseMap.put("error", true);
            responseMap.put("errorMessage", "Something went wrong");
            return responseMap;
        }


    }





}
