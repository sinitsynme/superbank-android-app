package com.example.superbank.values.strings;

import android.content.res.Resources;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.superbank.R;
import com.example.superbank.SuperBankApplication;

@RequiresApi(api = Build.VERSION_CODES.N)
public class StringsArrays {

    public static final Resources resources = SuperBankApplication.getRes();

    public static final String[] ERROR_TRANSACTION_STRINGS = resources.getStringArray(R.array.transaction_errors);

    public static final String[] TRANSACTION_CATEGORY_STRINGS = resources.getStringArray(R.array.categories);

    public static final String[] CURRENCIES = resources.getStringArray(R.array.currencies);

}
