package com.example.superbank.enums;

import static com.example.superbank.enums.TransactionError.INVALID_CATEGORY;
import static com.example.superbank.enums.TransactionError.NOT_ENOUGH_MONEY;
import static com.example.superbank.enums.TransactionError.RECEIVER_ID_IS_NOT_VALID;
import static com.example.superbank.enums.TransactionError.SENDER_ID_EQUALS_TO_RECEIVER_ID;
import static com.example.superbank.enums.TransactionError.SENDER_ID_IS_NOT_VALID;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({SENDER_ID_IS_NOT_VALID, RECEIVER_ID_IS_NOT_VALID, NOT_ENOUGH_MONEY,
        INVALID_CATEGORY, SENDER_ID_EQUALS_TO_RECEIVER_ID})
@Retention(RetentionPolicy.SOURCE)
public @interface TransactionError {
    public static final int SENDER_ID_IS_NOT_VALID = 1;
    public static final int RECEIVER_ID_IS_NOT_VALID = 2;
    public static final int NOT_ENOUGH_MONEY = 3;
    public static final int INVALID_CATEGORY = 5;
    public static final int SENDER_ID_EQUALS_TO_RECEIVER_ID = 4;
}
