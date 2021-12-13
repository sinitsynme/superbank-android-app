package com.example.superbank.values.annotations;

import static com.example.superbank.values.annotations.Currency.CURRENCY_EUR;
import static com.example.superbank.values.annotations.Currency.CURRENCY_RUB;
import static com.example.superbank.values.annotations.Currency.CURRENCY_USD;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({CURRENCY_RUB, CURRENCY_USD, CURRENCY_EUR})
@Retention(RetentionPolicy.SOURCE)
public @interface Currency {

    public static final int CURRENCY_RUB = 1;
    public static final int CURRENCY_USD = 2;
    public static final int CURRENCY_EUR = 3;

}
