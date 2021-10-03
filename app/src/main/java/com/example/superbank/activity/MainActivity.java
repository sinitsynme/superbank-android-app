package com.example.superbank.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.superbank.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button butNewAccount = findViewById(R.id.b_new_account);
        Button butAccManage = findViewById(R.id.b_acc_management);
        Button butNewTransaction = findViewById(R.id.b_new_transaction);

        butNewAccount.setOnClickListener(this);
        butAccManage.setOnClickListener(this);
        butNewTransaction.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Class<?> targetClass = null;

        if (id == R.id.b_acc_management) targetClass = AccountManagementActivity.class;
        else if (id == R.id.b_new_account) targetClass = NewAccountActivity.class;
        else if (id == R.id.b_new_transaction) targetClass = NewTransactionActivity.class;

        Intent intent = new Intent(this, targetClass);
        startActivity(intent);

    }
}