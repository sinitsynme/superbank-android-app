package com.example.superbank.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.superbank.R;
import com.example.superbank.entity.BankAccount;
import com.example.superbank.entity.Customer;
import com.example.superbank.enums.TransactionCategory;
import com.example.superbank.manager.TransactionManager;
import com.example.superbank.repository.RepositoryStorage;
import com.example.superbank.service.BankAccountService;
import com.example.superbank.service.impl.BankAccountServiceImpl;
import com.example.superbank.service.impl.TransactionServiceImpl;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AccountInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView firstNameTV;
    private TextView lastNameTV;
    private TextView patronymicTV;
    private TextView birthDateTV;
    private TextView countryTV;
    private TextView townTV;
    private TextView currencyTV;
    private TextView availableMoneyTV;

    private BankAccount bankAccount;

    private Button btnDeleteCustomer;
    private Button btnSupplyCash;
    private Button btnGetTransactionHistory;


    private final BankAccountService bankAccountService = new BankAccountServiceImpl(RepositoryStorage.bankAccountRepository,
            RepositoryStorage.customerRepository);

    private final TransactionManager transactionManager = new TransactionManager(bankAccountService,
            new TransactionServiceImpl(RepositoryStorage.transactionRepository, RepositoryStorage.bankAccountRepository));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        firstNameTV = findViewById(R.id.tv_first_name);
        lastNameTV = findViewById(R.id.tv_last_name);
        patronymicTV = findViewById(R.id.tv_patronymic);

        birthDateTV = findViewById(R.id.tv_birth_date);
        countryTV = findViewById(R.id.tv_country);
        townTV = findViewById(R.id.tv_town);

        currencyTV = findViewById(R.id.tv_currency);
        availableMoneyTV = findViewById(R.id.tv_available_money);

        btnDeleteCustomer = findViewById(R.id.b_delete_account);
        btnSupplyCash = findViewById(R.id.b_cash);
        btnGetTransactionHistory = findViewById(R.id.b_transaction_history);

        btnDeleteCustomer.setOnClickListener(this);
        btnSupplyCash.setOnClickListener(this);
        btnGetTransactionHistory.setOnClickListener(this);


        Bundle params = getIntent().getExtras();


        if(params != null){
            bankAccount = (BankAccount) params.getSerializable(BankAccount.class.getSimpleName());

            Customer customer = bankAccount.getCustomer();
            firstNameTV.setText(customer.getFirstName());
            lastNameTV.setText(customer.getLastName());
            if(customer.getPatronymic() != null && !customer.getPatronymic().isEmpty()){
                patronymicTV.setText(customer.getPatronymic());
            }

            Format formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ROOT);
            String s = formatter.format(customer.getBirthDate());
            birthDateTV.setText(s);

            countryTV.setText(customer.getCountry());
            townTV.setText(customer.getTown());

            availableMoneyTV.setText(bankAccount.getAvailableMoney().toString());
        }

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.b_delete_account){
            bankAccountService.delete(bankAccount.getAccountId());

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.label_success))
                    .setMessage(getResources().getString(R.string.label_successful_removal))
                    .setPositiveButton("OK", (dialogInterface, i) -> {
                        Intent intent = new Intent(AccountInfoActivity.this,
                                AccountManagementActivity.class);
                        startActivity(intent);
                    });

            AlertDialog dialog = builder.create();

            dialog.show();

        }

        else if(id == R.id.b_cash){
            showCashDialog();
        }


    }

    @SuppressLint("SetTextI18n")
    private void showCashDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.label_input_cash_value));

        final EditText input = new EditText(this);

        input.setGravity(View.TEXT_ALIGNMENT_CENTER);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {

            double amountOfCash = Double.parseDouble(input.getText().toString());

            if(amountOfCash == 0){
                dialog.cancel();

                AlertDialog.Builder builder1 = new AlertDialog.Builder(AccountInfoActivity.this);
                builder1.setTitle(getResources().getString(R.string.label_error))
                        .setMessage(getResources().getString(R.string.label_negative_amount_cash))
                        .setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.cancel());

                AlertDialog negativeMoneyDialog = builder1.create();
                negativeMoneyDialog.show();
                return;

            }

            transactionManager.commitCashTransaction(bankAccount.getAccountId(), amountOfCash, TransactionCategory.CASH_SUPPLY);

            AlertDialog.Builder successDialogBuilder = new AlertDialog.Builder(this);
            successDialogBuilder.setTitle(getResources().getString(R.string.label_success))
                    .setMessage(getResources().getString(R.string.label_successful_cash_suply))
                    .setPositiveButton("OK", (dialogInterface, i) -> dialog.cancel());

            AlertDialog successfulCashSupplyDialog = successDialogBuilder.create();

            successfulCashSupplyDialog.show();

            bankAccount = bankAccountService.getSecured(bankAccount.getAccountId());
            availableMoneyTV.setText(bankAccount.getAvailableMoney().toString());
        });


        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AccountManagementActivity.class);
        startActivity(intent);
    }
}