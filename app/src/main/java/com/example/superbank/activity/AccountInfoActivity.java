package com.example.superbank.activity;

import static android.content.DialogInterface.*;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.superbank.R;
import com.example.superbank.dialog.OkDialog;
import com.example.superbank.entity.BankAccount;
import com.example.superbank.entity.Customer;
import com.example.superbank.repository.RepositoryStorage;
import com.example.superbank.service.BankAccountService;
import com.example.superbank.service.CustomerService;
import com.example.superbank.service.impl.BankAccountServiceImpl;
import com.example.superbank.service.impl.CustomerServiceImpl;

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


    private final BankAccountService customerService = new BankAccountServiceImpl(RepositoryStorage.bankAccountRepository,
            RepositoryStorage.customerRepository);


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
            customerService.delete(bankAccount.getAccountId());


            Bundle args = new Bundle();
            args.putString("header", getResources().getString(R.string.label_success));
            args.putString("msg", getResources().getString(R.string.label_successful_removal));

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


    }
}