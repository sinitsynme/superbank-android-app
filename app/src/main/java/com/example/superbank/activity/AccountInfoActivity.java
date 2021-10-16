package com.example.superbank.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.superbank.R;
import com.example.superbank.entity.BankAccount;
import com.example.superbank.entity.Customer;

public class AccountInfoActivity extends AppCompatActivity {

    private TextView firstNameTV;
    private TextView lastNameTV;
    private TextView patronymicTV;
    private TextView availableMoneyTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firstNameTV = findViewById(R.id.tv_first_name);
        lastNameTV = findViewById(R.id.tv_last_name);

        patronymicTV = findViewById(R.id.tv_patronymic);
        availableMoneyTV = findViewById(R.id.tv_available_money);

        Bundle params = getIntent().getExtras();

        BankAccount bankAccount;

        if(params != null){
            bankAccount = (BankAccount) params.getSerializable(BankAccount.class.getSimpleName());

            Customer customer = bankAccount.getCustomer();
            firstNameTV.setText(customer.getFirstName());
            lastNameTV.setText(customer.getLastName());
            if(customer.getPatronymic() != null && !customer.getPatronymic().isEmpty()){
                patronymicTV.setText(customer.getPatronymic());
            }
            availableMoneyTV.setText(bankAccount.getAvailableMoney().toString());
        }

        setContentView(R.layout.activity_account_info);
    }
}