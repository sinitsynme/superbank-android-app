package com.example.superbank.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.superbank.R;
import com.example.superbank.payload.request.UserRequestDto;

public class RegistrationActivity extends AppCompatActivity {

    private TextView tvUsername;
    private TextView tvEmail;
    private TextView tvPassword;
    private TextView tvConfirmPassword;

    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;

    private Button buttonNextStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        tvUsername = findViewById(R.id.l_username);
        tvPassword = findViewById(R.id.l_password);
        tvConfirmPassword = findViewById(R.id.l_confirm_password);
        tvEmail = findViewById(R.id.l_email);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        etEmail = findViewById(R.id.et_email);

        buttonNextStep = findViewById(R.id.b_next_step);

        buttonNextStep.setOnClickListener(view -> {

            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            if(areVitalTextViewsEmpty(username, password, confirmPassword, email)){
                Toast.makeText(RegistrationActivity.this, "Fill in the empty gaps", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!password.equals(confirmPassword)){
                Toast.makeText(RegistrationActivity.this, "Passwords are not equal", Toast.LENGTH_SHORT).show();
                return;
            }

            UserRequestDto userRequestDto = new UserRequestDto(username, password, email);

            Intent intent = new Intent(this, NewAccountActivity.class);
            intent.putExtra("userRequestDto", userRequestDto);
            startActivity(intent);
        });

        Bundle params = getIntent().getExtras();
        if(params != null){
            etUsername.setText(params.get("username").toString());
            etEmail.setText(params.get("email").toString());
        }


    }


    private boolean areVitalTextViewsEmpty(String usernameString, String passwordString,
                                           String confirmPasswordString, String emailString) {

        boolean isEmpty = false;

        if(usernameString.isEmpty()){
            isEmpty = true;
            tvUsername.setTextColor(Color.parseColor("#B22222"));
        }
        else tvUsername.setTextColor(Color.parseColor("#808080"));


        if(passwordString.isEmpty()){
            isEmpty = true;
            tvPassword.setTextColor(Color.parseColor("#B22222"));
        }
        else tvPassword.setTextColor(Color.parseColor("#808080"));

        if(confirmPasswordString.isEmpty()){
            isEmpty = true;
            tvConfirmPassword.setTextColor(Color.parseColor("#B22222"));
        }
        else tvConfirmPassword.setTextColor(Color.parseColor("#808080"));

        if(emailString.isEmpty()){
            isEmpty = true;
            tvEmail.setTextColor(Color.parseColor("#B22222"));
        }
        else tvEmail.setTextColor(Color.parseColor("#808080"));

        return isEmpty;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


}