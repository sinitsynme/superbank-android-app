package com.example.superbank.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.superbank.R;
import com.example.superbank.repository.RepositoryStorage;
import com.example.superbank.service.BankAccountService;
import com.example.superbank.service.impl.BankAccountServiceImpl;

@RequiresApi(api = Build.VERSION_CODES.N)
public class NewTransactionActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etSender;
    private final BankAccountService bankAccountService = new BankAccountServiceImpl(RepositoryStorage.bankAccountRepository,
            RepositoryStorage.customerRepository);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transaction);
        Button butMakeTransaction = findViewById(R.id.b_make_transaction);
        etSender = findViewById(R.id.et_sender_account_num);

        Bundle params = getIntent().getExtras();

        String senderId = params.get("sender").toString();

        etSender.setText(!senderId.isEmpty() ? senderId : "");

        butMakeTransaction.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {
//        int id = view.getId();
//        Class<?> targetClass = null;

//        if (id == R.id.b_to_main_page) targetClass = MainActivity.class;
//        else if (id == R.id.b_make_transaction) targetClass = NewAccountActivity.class;

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}