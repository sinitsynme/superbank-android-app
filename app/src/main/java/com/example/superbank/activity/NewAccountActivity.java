package com.example.superbank.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.superbank.R;
import com.example.superbank.dialog.OkDialog;
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

    private TextView firstNameTV;
    private TextView lastNameTV;
    private TextView townTV;
    private TextView birthDateLabel;

    private Calendar date = Calendar.getInstance();
    private boolean dateIsSet = false;

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
        firstNameTV = findViewById(R.id.l_first_name);
        lastNameTV = findViewById(R.id.l_last_name);
        townTV = findViewById(R.id.l_town);
        birthDateLabel = findViewById(R.id.l_birth_date);

        btnCreateAccount.setOnClickListener(this);

        birthDateTV = findViewById(R.id.et_current_date);
    }

    private void setCalendarDate() {
        birthDateTV.setText(DateUtils.formatDateTime(this,
                date.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));

    }

    public void setDate(View v) {
        DatePickerDialog dpd = new DatePickerDialog(NewAccountActivity.this, dateListener,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH)
        );

        dpd.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis() - MIN_YEARS_OLD * DateUtils.YEAR_IN_MILLIS);
        dpd.show();
    }

    DatePickerDialog.OnDateSetListener dateListener = (view, year, month, day) -> {
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, day);
        setCalendarDate();
        dateIsSet = true;
    };

    @Override
    public void onClick(View view) {

        String firstName = firstNameET.getText().toString();
        String lastName = lastNameET.getText().toString();
        String patronymic = patronymicET.getText().toString();
        Date birthDate = date.getTime();
        String country = countrySpinner.getSelectedItem().toString();
        String town = townET.getText().toString();

        boolean isEmpty = firstName.isEmpty() || lastName.isEmpty() || town.isEmpty() || !dateIsSet;

        if (!isEmpty) {

            CustomerRequestDto customerRequestDto = new CustomerRequestDto(firstName, lastName, birthDate, country, town);
            if (!patronymic.isEmpty()) {
                customerRequestDto.setPatronymic(patronymic);
            }

            try {
                customerService.add(customerRequestDto);
                Intent intent = new Intent(this, AccountManagementActivity.class);
                startActivity(intent);

            }
            catch (RuntimeException e){
                Bundle args = new Bundle();
                args.putString("header", getResources().getString(R.string.label_error));
                args.putString("msg", getResources().getString(R.string.exception_customer_exists));
                OkDialog okDialog = new OkDialog();
                okDialog.setArguments(args);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                okDialog.show(transaction, "error_customer_exists");

            }


        } else {

            if (firstName.isEmpty()) firstNameTV.setTextColor(Color.parseColor("#B22222"));
            else firstNameTV.setTextColor(Color.parseColor("#808080"));

            if (lastName.isEmpty()) lastNameTV.setTextColor(Color.parseColor("#B22222"));
            else lastNameTV.setTextColor(Color.parseColor("#808080"));

            if (town.isEmpty()) townTV.setTextColor(Color.parseColor("#B22222"));
            else townTV.setTextColor(Color.parseColor("#808080"));

            if (!dateIsSet) birthDateLabel.setTextColor(Color.parseColor("#B22222"));
            else birthDateLabel.setTextColor(Color.parseColor("#808080"));

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}