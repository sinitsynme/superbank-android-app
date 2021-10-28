package com.example.superbank.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.superbank.R;
import com.example.superbank.manager.TransactionManager;
import com.example.superbank.payload.request.transaction.requestDto.TransactionRequestDtoBuilder;
import com.example.superbank.payload.response.TransactionResponseDto;
import com.example.superbank.repository.RepositoryStorage;
import com.example.superbank.service.BankAccountService;
import com.example.superbank.service.impl.BankAccountServiceImpl;
import com.example.superbank.service.impl.TransactionServiceImpl;

import java.util.ArrayList;

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

    //later in strings.xml
    private String[] errorStrings = {
            "- The sender's ID is invalid",
            "- The receiver's ID is invalid",
            "- Not enough money to make transaction",
            "- Invalid category", //for internal use
            "- The sender's ID and the receiver's ID mustn't be equal"
    };


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
        spCurrency = findViewById(R.id.currency_spinner);

        Bundle params = getIntent().getExtras();

        if (params != null) {
            etSender.setText(params.get("sender").toString());
        }

        butMakeTransaction.setOnClickListener(this);

    }


    //add category and currency to transaction

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
                .setComment(etComment.getText().toString());

        TransactionResponseDto responseDto = transactionManager.commitTransaction(builder.build());


        if (responseDto.isError()) {

            StringBuilder errorString = new StringBuilder();

            ArrayList<Integer> errorCodes = responseDto.getErrorCodes();
            for (int i : errorCodes) {
                errorString.append(errorStrings[i - 1]).append("\n");
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
                    dialogInterface.cancel();

                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                });

        AlertDialog successDialog = successDialogBuilder.create();
        successDialog.show();


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