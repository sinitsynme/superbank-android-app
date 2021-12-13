package com.example.superbank.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.superbank.R;
import com.example.superbank.SuperBankApplication;
import com.example.superbank.payload.response.TransactionResponseDto;
import com.example.superbank.service.AuthenticationBackendlessService;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Button buttonLogIn;
    private Button buttonRegister;

    private EditText etUsername;
    private EditText etPassword;

    private TextView tvUsername;
    private TextView tvPassword;

    private AuthenticationBackendlessService authenticationBackendlessService = new AuthenticationBackendlessService();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogIn = findViewById(R.id.b_login);
        buttonRegister = findViewById(R.id.b_register);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);

        tvUsername = findViewById(R.id.l_username);
        tvPassword = findViewById(R.id.l_password);

        buttonLogIn.setOnClickListener(view -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            
            if(areVitalTextViewsEmpty(username, password)){
                Toast.makeText(LoginActivity.this, "Fill in the empty gaps", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!SuperBankApplication.isOnline(LoginActivity.this)){
                Toast.makeText(LoginActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }

            synchronizedLogin(username, password);
        });

        buttonRegister.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
        });

    }

    @SuppressLint("HandlerLeak")
    private void synchronizedLogin(String username, String password){

        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                Map<String, Object> loginMap = (Map<String, Object>) bundle.get("loginMap");
                if((boolean) loginMap.get("loggedIn")){
                    Toast.makeText(LoginActivity.this, String.format("Hello, %s", username), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    if(((String) loginMap.get("errorCode")).equals("3003")){
                        Toast.makeText(LoginActivity.this, "Invalid login or password", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Problems with bank. Try again later.", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        };

        Runnable runnable = () -> {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putSerializable("loginMap", (Serializable) authenticationBackendlessService.synchronizedLogin(username, password));
            msg.setData(bundle);
            handler.sendMessage(msg);
        };

        Thread thread = new Thread(runnable);
        thread.start();

    }

    private boolean areVitalTextViewsEmpty(String usernameString, String passwordString) {

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
        
        return isEmpty;
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

}