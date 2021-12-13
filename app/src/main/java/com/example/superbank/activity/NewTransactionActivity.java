package com.example.superbank.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.superbank.R;
import com.example.superbank.SuperBankApplication;
import com.example.superbank.payload.request.transaction.requestDto.TransactionMapBuilder;
import com.example.superbank.payload.response.TransactionResponseDto;
import com.example.superbank.service.BankAccountBackendlessService;
import com.example.superbank.service.TransactionBackendlessService;
import com.example.superbank.values.annotations.Currency;
import com.example.superbank.values.annotations.TransactionCategory;
import com.example.superbank.values.strings.StringsArrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class NewTransactionActivity extends AppCompatActivity implements View.OnClickListener {

    TransactionBackendlessService transactionBackendlessService = new TransactionBackendlessService(
            new BankAccountBackendlessService());

    private EditText etReceiver, etTransactionSum, etComment;

    private TextView tvReceiver;
    private TextView tvTransactionSum;

    private Spinner spCurrency;
    private Spinner spCategory;

    private String[] errorStrings;
    private String[] categoriesStrings;
    private String[] currenciesStrings;

    private HashMap<String, Object> senderBankAccount;

    @TransactionCategory
    private int chosenTransactionCategory;

    @Currency
    private int chosenCurrency;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transaction);

        setUpActivity();
    }

    private void setUpActivity() {
        Button butMakeTransaction = findViewById(R.id.b_make_transaction);

        etReceiver = findViewById(R.id.et_receiver_account_num);
        etTransactionSum = findViewById(R.id.et_amount_of_money);
        etComment = findViewById(R.id.et_comment);

        tvTransactionSum = findViewById(R.id.tv_amount_of_money);
        tvReceiver = findViewById(R.id.tv_receiver_account_num);

        spCategory = findViewById(R.id.category_spinner);
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, @TransactionCategory int i, long l) {
                chosenTransactionCategory = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                chosenTransactionCategory = TransactionCategory.MONEY_TRANSFER;
            }
        });

        spCurrency = findViewById(R.id.currency_spinner);
        spCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chosenCurrency = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                chosenCurrency = Currency.CURRENCY_RUB;
            }
        });

        //Get current bank account
        Bundle params = getIntent().getExtras();

        if (params != null) {
            senderBankAccount = (HashMap<String, Object>) params.get("senderBankAccount");
        }

        butMakeTransaction.setOnClickListener(this);

        //Preload application strings
        errorStrings = StringsArrays.ERROR_TRANSACTION_STRINGS;
        categoriesStrings = StringsArrays.TRANSACTION_CATEGORY_STRINGS;
        currenciesStrings = StringsArrays.CURRENCIES;
    }


    @Override
    @SuppressLint("HandlerLeak")
    public void onClick(View view) {

        String receiverIdString = etReceiver.getText().toString();
        String transactionSumString = etTransactionSum.getText().toString();

        if (areVitalTextViewsEmpty(receiverIdString, transactionSumString)) return;

        double transactionSum = Double.parseDouble(etTransactionSum.getText().toString());

        if (transactionSum <= 0) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle(getResources().getString(R.string.label_error))
                    .setMessage(getResources().getString(R.string.label_negative_transaction_sum))
                    .setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.cancel());

            AlertDialog negativeMoneyDialog = builder1.create();
            negativeMoneyDialog.show();
            return;
        }

        int senderAccountNumber = (int) senderBankAccount.get("accountNumber");

        TransactionMapBuilder builder = new TransactionMapBuilder();
        builder.setReceiver(Integer.parseInt(etReceiver.getText().toString()))
                .setSender(senderAccountNumber)
                .setAmountOfMoney(transactionSum)
                .setComment(etComment.getText().toString())
                .setCategory(chosenTransactionCategory)
                .setCurrency(chosenCurrency);


        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                TransactionResponseDto responseDto = (TransactionResponseDto) bundle.get("responseDto");
                if (responseDto.isError()) {

                    StringBuilder errorString = new StringBuilder();

                    List<Integer> errorCodes = responseDto.getErrorCodes();
                    for (int i : errorCodes) {
                        errorString.append("· ").append(errorStrings[i - 1]).append("\n");
                    }

                    AlertDialog.Builder errorDialogBuilder = new AlertDialog.Builder(NewTransactionActivity.this);
                    errorDialogBuilder.setTitle(getResources().getString(R.string.label_error))
                            .setMessage(errorString)
                            .setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.cancel());

                    AlertDialog errorDialog = errorDialogBuilder.create();
                    errorDialog.show();

                    return;
                }

                showSuccessfulTransactionDialog();
                showSuccessfulTransactionNotification(responseDto);
            }
        };

        Runnable runnable = () -> {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            TransactionResponseDto responseDto = transactionBackendlessService.synchronousMakeTransaction(builder.build());
            bundle.putSerializable("responseDto", responseDto);
            msg.setData(bundle);
            handler.sendMessage(msg);
        };

        Thread thread = new Thread(runnable);
        thread.start();

    }

    private void showSuccessfulTransactionDialog() {
        AlertDialog.Builder successDialogBuilder = new AlertDialog.Builder(this);
        successDialogBuilder.setTitle(getResources().getString(R.string.label_success))
                .setMessage(getResources().getString(R.string.label_successful_transaction))
                .setPositiveButton("OK", (dialogInterface, i) -> {

                    dialogInterface.cancel();

                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }).setOnCancelListener(dialogInterface -> {

            dialogInterface.cancel();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        AlertDialog successDialog = successDialogBuilder.create();
        successDialog.show();
    }

    private void showSuccessfulTransactionNotification(TransactionResponseDto responseDto) {
        String transfer = getResources().getString(R.string.label_transfer) + ": "
                + responseDto.getAmountOfMoney() + currenciesStrings[chosenCurrency];

        String sender = getResources().getString(R.string.label_sender) + ": "
                + responseDto.getSender().getAccountId();

        String receiver = getResources().getString(R.string.label_receiver) + ": "
                + responseDto.getReceiver().getAccountId();

        String category = getResources().getString(R.string.label_category) + ": "
                + categoriesStrings[chosenTransactionCategory];

        List<String> lines = new ArrayList<>(Arrays.asList(transfer, sender, receiver, category));

        if (!responseDto.getComment().isEmpty()) {
            lines.add(getResources().getString(R.string.label_comment)
                    + ": " + responseDto.getComment());
        }

        SuperBankApplication.notificationHelper.sendNotification(this,
                MainActivity.class, lines);
    }


    private boolean areVitalTextViewsEmpty(String receiverIdString, String transactionSumString) {

        boolean isEmpty = false;

        if (receiverIdString.isEmpty()) {
            isEmpty = true;
            tvReceiver.setTextColor(Color.parseColor("#B22222"));
        } else tvReceiver.setTextColor(Color.parseColor("#808080"));


        if (transactionSumString.isEmpty()) {
            isEmpty = true;
            tvTransactionSum.setTextColor(Color.parseColor("#B22222"));
        } else tvTransactionSum.setTextColor(Color.parseColor("#808080"));

        return isEmpty;


    }
}