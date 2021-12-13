package com.example.superbank.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.superbank.R;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AccountInfoActivity extends AppCompatActivity {

    private TextView firstNameTV, lastNameTV, patronymicTV, birthDateTV, countryTV, townTV;
    private Button buttonToMainPage;

    private Map<String, Object> customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        setUpActivity();

    }

    private void setUpActivity() {
        firstNameTV = findViewById(R.id.tv_first_name);
        lastNameTV = findViewById(R.id.tv_last_name);
        patronymicTV = findViewById(R.id.tv_patronymic);

        birthDateTV = findViewById(R.id.tv_birth_date);
        countryTV = findViewById(R.id.tv_country);
        townTV = findViewById(R.id.tv_town);
        buttonToMainPage = findViewById(R.id.b_back_to_main_page);

        buttonToMainPage.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        Bundle params = getIntent().getExtras();

        if(params != null){
            customer = (Map<String, Object>) params.get("customer");

            firstNameTV.setText( (String) customer.get("firstName"));
            lastNameTV.setText( (String)  customer.get("lastName"));

            String patronymic = (String) customer.get("patronymic");

            if(patronymic != null && !patronymic.isEmpty()){
                patronymicTV.setText(patronymic);
            }

            Format formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ROOT);
            String birthDate = formatter.format(customer.get("birthDate"));
            birthDateTV.setText(birthDate);

            countryTV.setText((String) customer.get("country"));
            townTV.setText((String) customer.get("town"));
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}