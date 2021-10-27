package com.example.superbank.activity;

import static android.view.ViewGroup.LayoutParams.*;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.superbank.R;
import com.example.superbank.entity.BankAccount;
import com.example.superbank.payload.response.BankAccountResponseDto;
import com.example.superbank.repository.RepositoryStorage;
import com.example.superbank.service.BankAccountService;
import com.example.superbank.service.impl.BankAccountServiceImpl;

import java.util.List;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AccountManagementActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int COLS = 1;
    private TableLayout accountTable;
    private final BankAccountService bankAccountService = new BankAccountServiceImpl(RepositoryStorage.bankAccountRepository,
            RepositoryStorage.customerRepository);

    private Button newAccountButton;

    private List<BankAccountResponseDto> responseDtoList;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);

        accountTable = findViewById(R.id.tbl_accounts);

        newAccountButton = findViewById(R.id.b_new_account);
        newAccountButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshData();
    }

    private void refreshData(){
        accountTable.removeAllViews();

        responseDtoList = bankAccountService.getAll();

        if(responseDtoList.size() == 0){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            tableRow.setGravity(View.TEXT_ALIGNMENT_CENTER);

            TextView textView = new TextView(this);
            textView.setText(getResources().getString(R.string.label_no_accounts));
            textView.setTextSize(18);
            tableRow.addView(textView, 0);

            accountTable.addView(tableRow, 0);
        }

        for(int i = 0; i < responseDtoList.size(); i++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            tableRow.setGravity(View.TEXT_ALIGNMENT_CENTER);

            for(int j = 0; j < COLS; j++){

                Button button = new Button(this);
                button.setId(i);
                button.setOnClickListener(this);
                button.setText(responseDtoList.get(i).getAccountId().toString());
                button.setTextSize(15);
                tableRow.addView(button, j);

            }
            accountTable.addView(tableRow, i);

        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent;

        if (id == R.id.b_new_account) intent = new Intent(this, NewAccountActivity.class);
        else{
            BankAccount bankAccount = bankAccountService.getSecured(responseDtoList.get(id).getAccountId());
            intent = new Intent(this, AccountInfoActivity.class);
            intent.putExtra(BankAccount.class.getSimpleName(), bankAccount);
        }

        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}