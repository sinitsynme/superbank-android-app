package com.example.superbank.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.superbank.R;
import com.example.superbank.payload.request.CustomerRequestDto;
import com.example.superbank.repository.RepositoryStorage;
import com.example.superbank.service.CustomerService;
import com.example.superbank.service.impl.CustomerServiceImpl;

import java.util.Calendar;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.N)
public class NewAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MIN_YEARS_OLD = 14;

    private EditText firstNameET;
    private EditText lastNameET;
    private EditText patronymicET;
    private TextView birthDateTV;
    private Spinner countrySpinner;
    private EditText townET;


    private Calendar date = Calendar.getInstance();

    private CustomerService customerService = new CustomerServiceImpl(RepositoryStorage.customerRepository,
            RepositoryStorage.bankAccountRepository);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        Button btnCreateAccount = findViewById(R.id.b_create_account);
        firstNameET = findViewById(R.id.et_first_name);
        lastNameET = findViewById(R.id.et_last_name);
        patronymicET = findViewById(R.id.et_patronymic);
        countrySpinner = findViewById(R.id.spinner_country);
        townET = findViewById(R.id.et_town);

        btnCreateAccount.setOnClickListener(this);

        birthDateTV = findViewById(R.id.et_current_date);
    }

    private void setCalendarDate(){
        birthDateTV.setText(DateUtils.formatDateTime(this,
                date.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));

    }

    public void setDate(View v){
        DatePickerDialog dpd = new DatePickerDialog(NewAccountActivity.this, dateListener,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH)
        );

        dpd.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis() - MIN_YEARS_OLD*DateUtils.YEAR_IN_MILLIS);
        dpd.show();
    }

    DatePickerDialog.OnDateSetListener dateListener = (view, year, month, day) -> {
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, day);
        setCalendarDate();
    };

    @Override
    public void onClick(View view) {

        String firstName = firstNameET.getText().toString();
        String lastName = lastNameET.getText().toString();
        String patronymic = patronymicET.getText().toString();
        Date birthDate = date.getTime();
        String country = countrySpinner.getSelectedItem().toString();
        String town = townET.getText().toString();

        CustomerRequestDto customerRequestDto = new CustomerRequestDto(firstName, lastName, birthDate, country, town);
        if(!patronymic.isEmpty()){
            customerRequestDto.setPatronymic(patronymic);
        }

        Log.d("new account req dto", customerRequestDto.toString());
        Log.d("saved account resp dto", customerService.add(customerRequestDto).toString());

        Intent intent = new Intent(this, AccountManagementActivity.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}