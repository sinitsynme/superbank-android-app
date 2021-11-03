package com.example.superbank.activity;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.superbank.R;
import com.example.superbank.SuperBankApplication;
import com.example.superbank.helper.NotificationHelper;
import com.example.superbank.manager.TransactionManager;
import com.example.superbank.payload.request.transaction.requestDto.TransactionRequestDtoBuilder;
import com.example.superbank.payload.response.TransactionResponseDto;
import com.example.superbank.repository.RepositoryStorage;
import com.example.superbank.service.BankAccountService;
import com.example.superbank.service.impl.BankAccountServiceImpl;
import com.example.superbank.service.impl.TransactionServiceImpl;
import com.example.superbank.values.annotations.Currency;
import com.example.superbank.values.annotations.TransactionCategory;
import com.example.superbank.values.strings.StringsArrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class NewTransactionActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etSender;
    private EditText etReceiver;
    private EditText etTransactionSum;
    private EditText etComment;

    private TextView tvSender;
    private TextView tvReceiver;
    private TextView tvTransactionSum;

    private Spinner spCurrency;
    private Spinner spCategory;

    private String[] errorStrings;
    private String[] categoriesStrings;
    private String[] currenciesStrings;


    @TransactionCategory
    private int chosenTransactionCategory;

    @Currency
    private int chosenCurrency;

    private final BankAccountService bankAccountService = new BankAccountServiceImpl(RepositoryStorage.bankAccountRepository,
            RepositoryStorage.customerRepository);

    private final TransactionManager transactionManager = new TransactionManager(bankAccountService,
            new TransactionServiceImpl(RepositoryStorage.transactionRepository, RepositoryStorage.bankAccountRepository));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transaction);

        Button butMakeTransaction = findViewById(R.id.b_make_transaction);

        etSender = findViewById(R.id.et_sender_account_num);
        etReceiver = findViewById(R.id.et_receiver_account_num);
        etTransactionSum = findViewById(R.id.et_transaction_sum);
        etComment = findViewById(R.id.et_comment);

        tvTransactionSum = findViewById(R.id.tv_transaction_sum);
        tvReceiver = findViewById(R.id.tv_receiver_account_num);
        tvSender = findViewById(R.id.tv_sender_account_num);

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

        Bundle params = getIntent().getExtras();

        if (params != null) {
            etSender.setText(params.get("sender").toString());
        }

        butMakeTransaction.setOnClickListener(this);

        errorStrings = StringsArrays.ERROR_TRANSACTION_STRINGS;
        categoriesStrings = StringsArrays.TRANSACTION_CATEGORY_STRINGS;
        currenciesStrings = StringsArrays.CURRENCIES;
    }


    @Override
    public void onClick(View view) {


        String senderIdString = etSender.getText().toString();
        String receiverIdString = etReceiver.getText().toString();
        String transactionSumString = etTransactionSum.getText().toString();

        if (areVitalTextViewsEmpty(senderIdString, receiverIdString, transactionSumString)) return;

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

        TransactionRequestDtoBuilder builder = transactionManager.prepareTransaction();
        builder.setReceiver(Long.parseLong(etReceiver.getText().toString()))
                .setSender(Long.parseLong(etSender.getText().toString()))
                .setAmountOfMoney(transactionSum)
                .setComment(etComment.getText().toString())
                .setCategory(chosenTransactionCategory)
                .setCurrency(chosenCurrency);

        TransactionResponseDto responseDto = transactionManager.commitTransaction(builder.build());

        if (responseDto.isError()) {

            StringBuilder errorString = new StringBuilder();

            ArrayList<Integer> errorCodes = responseDto.getErrorCodes();
            for (int i : errorCodes) {
                errorString.append("· ").append(errorStrings[i - 1]).append("\n");
            }

            AlertDialog.Builder errorDialogBuilder = new AlertDialog.Builder(this);
            errorDialogBuilder.setTitle(getResources().getString(R.string.label_error))
                    .setMessage(errorString)
                    .setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.cancel());

            AlertDialog errorDialog = errorDialogBuilder.create();
            errorDialog.show();

            return;
        }

        AlertDialog.Builder successDialogBuilder = new AlertDialog.Builder(this);
        successDialogBuilder.setTitle(getResources().getString(R.string.label_success))
                .setMessage(getResources().getString(R.string.label_successful_transaction))
                .setPositiveButton("OK", (dialogInterface, i) -> {

                    //SHOW SUCCESSFUL TRANSACTION ACTIVITY
                    dialogInterface.cancel();

                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }).setOnCancelListener(dialogInterface -> {
            //SHOW SUCCESSFUL TRANSACTION ACTIVITY
            dialogInterface.cancel();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        AlertDialog successDialog = successDialogBuilder.create();
        successDialog.show();

        showSuccessfulTransactionNotification(responseDto);
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

        List<String> lines = Arrays.asList(transfer, sender, receiver, category);

        SuperBankApplication.notificationHelper.sendNotification(this,
                MainActivity.class, lines);

    }



    private boolean areVitalTextViewsEmpty(String senderIdString, String receiverIdString, String transactionSumString) {
        boolean isEmpty = senderIdString.isEmpty() || receiverIdString.isEmpty() || transactionSumString.isEmpty();

        if (isEmpty) {
            if (senderIdString.isEmpty()) tvSender.setTextColor(Color.parseColor("#B22222"));
            else tvSender.setTextColor(Color.parseColor("#808080"));

            if (receiverIdString.isEmpty()) tvReceiver.setTextColor(Color.parseColor("#B22222"));
            else tvReceiver.setTextColor(Color.parseColor("#808080"));

            if (transactionSumString.isEmpty())
                tvTransactionSum.setTextColor(Color.parseColor("#B22222"));
            else tvTransactionSum.setTextColor(Color.parseColor("#808080"));
        } else {
            tvSender.setTextColor(Color.parseColor("#808080"));
            tvReceiver.setTextColor(Color.parseColor("#808080"));
            tvTransactionSum.setTextColor(Color.parseColor("#808080"));
        }
        return isEmpty;
    }
}