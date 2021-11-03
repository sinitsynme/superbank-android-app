package com.example.superbank.manager;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.superbank.entity.BankAccount;
import com.example.superbank.entity.Transaction;
import com.example.superbank.enums.TransactionCategory;
import com.example.superbank.enums.TransactionError;
import com.example.superbank.payload.request.transaction.requestDto.TransactionRequestDto;
import com.example.superbank.payload.request.transaction.requestDto.TransactionRequestDtoBuilder;
import com.example.superbank.payload.response.TransactionResponseDto;
import com.example.superbank.service.BankAccountService;
import com.example.superbank.service.TransactionService;

import java.util.ArrayList;

import javax.net.ssl.SSLEngineResult;

/**
 * {@link TransactionManager} is a link between UI and {@link TransactionService}.
 * <p>
 * While {@link TransactionService} cares only about saving the results of transactions into the database
 * and account's transaction history, {@link TransactionManager} validates transactions and makes rollbacks if
 * it is requested.
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class TransactionManager {

    private final BankAccountService bankAccountService;
    private final TransactionService transactionService;

    public TransactionManager(BankAccountService bankAccountService,
                              TransactionService transactionService) {

        this.bankAccountService = bankAccountService;
        this.transactionService = transactionService;
    }

    public TransactionRequestDtoBuilder prepareTransaction() {
        return new TransactionRequestDtoBuilder();
    }

    public TransactionResponseDto commitTransaction(TransactionRequestDto requestDto) {
        Long senderId = requestDto.getSenderId();
        Long receiverId = requestDto.getReceiverId();
        Double amountOfMoney = requestDto.getAmountOfMoney();

        ArrayList<Integer> errorCodes = new ArrayList<>();

        if (!bankAccountService.existsById(senderId)) {
            errorCodes.add(TransactionError.SENDER_ID_IS_NOT_VALID);
        }

        if (!bankAccountService.existsById(receiverId)) {
            errorCodes.add(TransactionError.RECEIVER_ID_IS_NOT_VALID);
        }

        if (senderId.equals(receiverId)) {
            errorCodes.add(TransactionError.SENDER_ID_EQUALS_TO_RECEIVER_ID);
        }

        if (errorCodes.size() != 0) return formErrorDto(errorCodes);

        BankAccount sender = bankAccountService.getSecured(senderId);

        if (sender.getAvailableMoney() - amountOfMoney < 0) {
            errorCodes.add(TransactionError.NOT_ENOUGH_MONEY);
        }

        if (errorCodes.size() == 0) {
            return transactionService.add(requestDto);
        }

        return formErrorDto(errorCodes);

    }

    /**
     * @param transactionId Id of the transaction which is needed to be rollbacked
     * @return New rollback transaction
     * <p>
     * Forms a new transaction based on the initial transaction (works just like giving money back to the sender)
     * and saves it, leaving the initial one untouched in the database
     * <p>
     * Is not safe!
     * <p>
     * (Is not ready yet)
     */

    public TransactionResponseDto rollbackTransaction(Long transactionId) {
        Transaction transaction = transactionService.getSecured(transactionId);

        return new TransactionResponseDto();
    }

    //Refactoring will be soon

    public TransactionResponseDto commitCashTransaction(Long receiverId, Double amountOfMoney, @TransactionCategory int category) {
        ArrayList<Integer> errorCodes = new ArrayList<>();

        if (!bankAccountService.existsById(receiverId))
            errorCodes.add(TransactionError.RECEIVER_ID_IS_NOT_VALID);


        if (category != TransactionCategory.CASH_WITHDRAW && category != TransactionCategory.CASH_SUPPLY)
            errorCodes.add(TransactionError.INVALID_CATEGORY);

        if (errorCodes.size() != 0) {
            return formErrorDto(errorCodes);
        }

        TransactionRequestDtoBuilder builder = prepareTransaction();
        builder.setAmountOfMoney(amountOfMoney);
        builder.setCategory(category);
        builder.setReceiver(receiverId);

        return transactionService.add(builder.build());
    }

    public TransactionResponseDto formErrorDto(ArrayList<Integer> errorCodes) {
        TransactionResponseDto responseDto = new TransactionResponseDto();
        responseDto.setError(true);
        responseDto.setErrorCodes(errorCodes);
        return responseDto;
    }
}
