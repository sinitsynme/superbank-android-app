package com.example.superbank.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.superbank.R;

import java.util.Calendar;

public class NewAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MIN_YEARS_OLD = 14;
    TextView currentDate;
    Calendar date = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        Button btnCreateAccount = findViewById(R.id.b_create_account);
        btnCreateAccount.setOnClickListener(this);

        currentDate = findViewById(R.id.et_current_date);
    }

    private void setCalendarDate(){
        currentDate.setText(DateUtils.formatDateTime(this,
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
        int id = view.getId();
//        Class<?> targetClass;
//
//        if(id == R.id.b_create_account){
//            targetClass = MainActivity.class;
//        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}