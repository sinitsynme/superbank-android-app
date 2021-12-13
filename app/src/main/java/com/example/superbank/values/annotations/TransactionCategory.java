package com.example.superbank.values.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ADMIN CLIENT VERSION
 */
@Retention(RetentionPolicy.SOURCE)
public @interface TransactionCategory {

    //Admins are able to choose between these types of transactions

    public static final int MONEY_TRANSFER = 0;
    public static final int CAFE_AND_RESTAURANTS = 1;
    public static final int SUPERMARKETS = 2;
    public static final int OTHER_EXPENSES = 3;


    //The system doesn't allow admins to independently choose these types of transaction

    public static final int CASH_SUPPLY = -1;
    public static final int CASH_WITHDRAW = -2;
    public static final int ROLLBACK = -3;
}
