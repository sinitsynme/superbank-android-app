package com.example.superbank.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.superbank.R;

public class NewTransactionActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transaction);

        Button butMakeTransaction = findViewById(R.id.b_make_transaction);

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