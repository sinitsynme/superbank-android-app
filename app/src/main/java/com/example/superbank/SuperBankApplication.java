package com.example.superbank;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.superbank.payload.request.CustomerRequestDto;
import com.example.superbank.repository.RepositoryStorage;
import com.example.superbank.service.CustomerService;
import com.example.superbank.service.impl.CustomerServiceImpl;

import java.util.Calendar;
import java.util.GregorianCalendar;

@RequiresApi(api = Build.VERSION_CODES.N)
public class SuperBankApplication extends Application {

    CustomerService customerService = new CustomerServiceImpl(RepositoryStorage.customerRepository, RepositoryStorage.bankAccountRepository);

    public SuperBankApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        addCustomers();

    }

    private void addCustomers() {

        CustomerRequestDto sidor = new CustomerRequestDto("Sidor", "Sidorov",
                new GregorianCalendar(2001, Calendar.JANUARY, 1).getTime(), "Russia", "Samara");

        CustomerRequestDto galya = new CustomerRequestDto("Galina", "Galinova",
                new GregorianCalendar(2002, Calendar.APRIL, 30).getTime(), "Russia", "Samara");

        customerService.add(sidor);
        customerService.add(galya);

    }
}
