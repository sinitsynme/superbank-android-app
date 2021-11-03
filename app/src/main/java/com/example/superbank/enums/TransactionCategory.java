package com.example.superbank.enums;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.SOURCE)
public @interface TransactionCategory {

    public static final int MONEY_TRANSFER = 1;
    public static final int CAFE_AND_RESTAURANTS = 2;

    public static final int SUPERMARKETS = 3;

    public static final int OTHER_EXPENSES = 4;

    public static final int CASH_SUPPLY = 5;

    public static final int CASH_WITHDRAW = 6;
}
