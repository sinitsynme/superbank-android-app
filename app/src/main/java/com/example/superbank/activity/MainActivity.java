package com.example.superbank.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.superbank.R;
import com.example.superbank.SuperBankApplication;
import com.example.superbank.payload.request.transaction.requestDto.TransactionMapBuilder;
import com.example.superbank.payload.response.TransactionResponseDto;
import com.example.superbank.service.BankAccountBackendlessService;
import com.example.superbank.service.TransactionBackendlessService;
import com.example.superbank.values.annotations.Currency;
import com.example.superbank.values.annotations.TransactionCategory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button buttonLogout, buttonMakeTransaction, buttonSupplyCash,
            buttonTransactionHistory, buttonWithdrawCash, buttonToAccountInfo;

    private TextView tvBalance, tvAccountNumber;
    private SwipeRefreshLayout swipeRefreshLayout;

    private BackendlessUser currentUser;
    private HashMap<String, Object> bankAccount;
    private HashMap<String, Object> customerMap;
    private double balance;

    private String[] currenciesStrings;

    private TransactionBackendlessService transactionBackendlessService = new TransactionBackendlessService(new BankAccountBackendlessService());

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpActivity();

        loadBackendlessData();
    }

    private void loadBackendlessData() {
        currentUser = Backendless.UserService.CurrentUser();

        String whereClause = String.format("userId = '%s'", currentUser.getUserId());
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);
        queryBuilder.addRelated("bankAccount");

        Backendless.Data.of("Customer").find(queryBuilder, new AsyncCallback<List<Map>>() {
            @Override
            public void handleResponse(List<Map> response) {
                customerMap = (HashMap<String, Object>) response.get(0);
                bankAccount = (HashMap<String, Object>) customerMap.get("bankAccount");

                if (Double.class == bankAccount.get("availableMoney").getClass()) {
                    balance = (double) bankAccount.get("availableMoney");
                } else {
                    int moneyAsInt = (int) bankAccount.get("availableMoney");
                    balance = (double) moneyAsInt;
                }

                tvBalance.setText(String.format(Locale.ROOT, "%s ₽", balance));

                int accountNumber = (Integer) bankAccount.get("accountNumber");
                tvAccountNumber.setText(String.valueOf(accountNumber));
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                AlertDialog.Builder faultDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                faultDialogBuilder.setTitle(getResources().getString(R.string.label_error))
                        .setMessage(fault.getMessage())
                        .setPositiveButton("OK", (dialogInterface, i) -> {
                            dialogInterface.cancel();
                        });

                AlertDialog faultDialog = faultDialogBuilder.create();
                faultDialog.show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        finishAffinity();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUpActivity() {
        currenciesStrings = SuperBankApplication.getRes().getStringArray(R.array.currencies);

        buttonLogout = findViewById(R.id.b_logout);
        buttonMakeTransaction = findViewById(R.id.b_make_transaction);
        buttonSupplyCash = findViewById(R.id.b_supply_cash);
        buttonWithdrawCash = findViewById(R.id.b_withdraw_cash);

        buttonToAccountInfo = findViewById(R.id.b_account_info);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        tvBalance = findViewById(R.id.tv_balance);
        tvAccountNumber = findViewById(R.id.tv_account_number);

        buttonLogout.setOnClickListener(view -> Backendless.UserService.logout(new AsyncCallback<Void>() {
            @Override
            public void handleResponse(Void response) {
                Toast.makeText(MainActivity.this, "Successful logout", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }));

        buttonSupplyCash.setOnClickListener(view -> showCashDialog());

        buttonMakeTransaction.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewTransactionActivity.class);
            intent.putExtra("senderBankAccount", bankAccount);
            startActivity(intent);
        });

        buttonToAccountInfo.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AccountInfoActivity.class);
            intent.putExtra("customer", customerMap);
            startActivity(intent);
        });


        swipeRefreshLayout.setOnRefreshListener(() -> {
            new Handler().postDelayed(() -> {
                swipeRefreshLayout.setRefreshing(false);
                loadBackendlessData();
            }, 1000);
        });
    }

    @SuppressLint("SetTextI18n")
    private void showCashDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.label_supplied_cash_value));

        final EditText input = new EditText(this);

        input.setGravity(View.TEXT_ALIGNMENT_CENTER);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> supplyCash(input, dialog));


        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void supplyCash(EditText input, android.content.DialogInterface dialog) {
        double amountOfCash = Double.parseDouble(input.getText().toString());

        if (amountOfCash <= 0) {
            dialog.cancel();

            AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
            builder1.setTitle(getResources().getString(R.string.label_error))
                    .setMessage(getResources().getString(R.string.label_negative_amount_cash))
                    .setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.cancel());

            AlertDialog negativeMoneyDialog = builder1.create();
            negativeMoneyDialog.show();
            return;
        }

        synchronousSupplyCash(amountOfCash, balance, bankAccount);

    }

    private void showErrorDialog(String message) {
        AlertDialog.Builder faultDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        faultDialogBuilder.setTitle(getResources().getString(R.string.label_error))
                .setMessage(message)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    dialogInterface.cancel();
                    onBackPressed();
                });

        AlertDialog faultDialog = faultDialogBuilder.create();
        faultDialog.show();
    }

    @SuppressLint("HandlerLeak")
    private void synchronousSupplyCash(double amountOfCash, double balance, Map<String, Object> bankAccount){

        Handler handler = new Handler(){
            @SuppressLint("DefaultLocale")
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                Map<String, Object> responseMap = (Map<String, Object>) bundle.get("responseMap");
                if((boolean) responseMap.put("error", true)){
                    showErrorDialog((String) responseMap.get("errorMessage"));
                }
                else {
                    Toast.makeText(MainActivity.this, String.format("Successful cash supply: %.2f ₽", amountOfCash), Toast.LENGTH_LONG).show();
                    loadBackendlessData();
                }

            }
        };

        Runnable runnable = () -> {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putSerializable("responseMap", (Serializable) transactionBackendlessService.synchronousSupplyOfCash(amountOfCash, balance, bankAccount));
            msg.setData(bundle);
            handler.sendMessage(msg);
        };

        Thread thread = new Thread(runnable);
        thread.start();

    }
}