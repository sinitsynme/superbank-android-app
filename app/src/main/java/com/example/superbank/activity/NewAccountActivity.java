package com.example.superbank.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.backendless.exceptions.BackendlessFault;
import com.example.superbank.R;
import com.example.superbank.payload.request.UserRequestDto;
import com.example.superbank.service.AuthenticationBackendlessService;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.N)
public class NewAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MIN_YEARS_OLD = 14;

    private UserRequestDto userRequestDto;

    private AuthenticationBackendlessService authenticationBackendlessService = new AuthenticationBackendlessService();

    private EditText firstNameET, lastNameET, patronymicET, townET;

    private TextView firstNameTV, lastNameTV, birthDateTV, townTV, birthDateLabel;

    private Spinner countrySpinner;

    private Calendar date = Calendar.getInstance();
    private boolean dateIsSet = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);


        firstNameET = findViewById(R.id.et_first_name);
        lastNameET = findViewById(R.id.et_last_name);
        patronymicET = findViewById(R.id.et_patronymic);
        townET = findViewById(R.id.et_town);

        firstNameTV = findViewById(R.id.l_first_name);
        lastNameTV = findViewById(R.id.l_last_name);
        townTV = findViewById(R.id.l_town);
        birthDateTV = findViewById(R.id.et_current_date);

        birthDateLabel = findViewById(R.id.l_birth_date);

        countrySpinner = findViewById(R.id.spinner_country);

        Button btnCreateAccount = findViewById(R.id.b_create_account);
        btnCreateAccount.setOnClickListener(this);

        Bundle params = getIntent().getExtras();
        if (params != null) {
            userRequestDto = (UserRequestDto) params.get("userRequestDto");
        }

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

        String firstName = firstNameET.getText().toString().trim();
        String lastName = lastNameET.getText().toString().trim();
        String patronymic = patronymicET.getText().toString().trim();
        Date birthDate = date.getTime();
        String country = countrySpinner.getSelectedItem().toString();
        String town = townET.getText().toString().trim();

        boolean isEmpty = firstName.isEmpty() || lastName.isEmpty() || town.isEmpty() || !dateIsSet;

        if (!isEmpty) {

            Map<String, Object> customerProperties = new HashMap<>();
            customerProperties.put("firstName", firstName);
            customerProperties.put("lastName", lastName);
            customerProperties.put("birthDate", birthDate);
            customerProperties.put("country", country);
            customerProperties.put("town", town);
            if (!patronymic.isEmpty()) {
                customerProperties.put("patronymic", patronymic);
            }

            Map<String, Object> userProperties = new HashMap<>();
            userProperties.put("name", userRequestDto.getName());
            userProperties.put("password", userRequestDto.getPassword());
            userProperties.put("email", userRequestDto.getEmail());

            synchronizedRegistration(userProperties, customerProperties);

        } else
            changeViableFieldsColor(firstName, lastName, town);

    }

    private void changeViableFieldsColor(String firstName, String lastName, String town) {
        if (firstName.isEmpty()) firstNameTV.setTextColor(Color.parseColor("#B22222"));
        else firstNameTV.setTextColor(Color.parseColor("#808080"));

        if (lastName.isEmpty()) lastNameTV.setTextColor(Color.parseColor("#B22222"));
        else lastNameTV.setTextColor(Color.parseColor("#808080"));

        if (town.isEmpty()) townTV.setTextColor(Color.parseColor("#B22222"));
        else townTV.setTextColor(Color.parseColor("#808080"));

        if (!dateIsSet) birthDateLabel.setTextColor(Color.parseColor("#B22222"));
        else birthDateLabel.setTextColor(Color.parseColor("#808080"));
    }

    @SuppressLint("HandlerLeak")
    private void synchronizedRegistration(Map<String, Object> userProperties, Map<String, Object> customerProperties){

        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                Map<String, Object> responseMap = (Map<String, Object>) bundle.get("responseMap");
                if((boolean) responseMap.get("error")){
                    showErrorDialog((String) responseMap.get("errorMessage"));
                }
                else{
                    Toast.makeText(NewAccountActivity.this, "Successful registration", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NewAccountActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }
        };

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putSerializable("responseMap", (Serializable) authenticationBackendlessService.synchronizedRegistration(userProperties, customerProperties));
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();




    }

    private void showSuccessfulRegistrationDialog() {
        AlertDialog.Builder successDialogBuilder = new AlertDialog.Builder(NewAccountActivity.this);
        successDialogBuilder.setTitle(getResources().getString(R.string.label_success))
                .setMessage(getResources().getString(R.string.label_successful_account_add))
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    dialogInterface.cancel();

                    Intent intent = new Intent(NewAccountActivity.this, LoginActivity.class);
                    startActivity(intent);
                });

        AlertDialog successDialog = successDialogBuilder.create();
        successDialog.show();
    }

    private void showErrorDialog(String message) {
        AlertDialog.Builder faultDialogBuilder = new AlertDialog.Builder(NewAccountActivity.this);
        faultDialogBuilder.setTitle(getResources().getString(R.string.label_error))
                .setMessage(message)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    dialogInterface.cancel();
                    onBackPressed();
                });

        AlertDialog faultDialog = faultDialogBuilder.create();
        faultDialog.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        if (userRequestDto != null) {
            intent.putExtra("username", userRequestDto.getName());
            intent.putExtra("email", userRequestDto.getEmail());
        }

        startActivity(intent);
    }


}