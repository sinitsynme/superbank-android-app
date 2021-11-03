package com.example.superbank;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.res.Resources;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.superbank.helper.NotificationHelper;
import com.example.superbank.payload.request.CustomerRequestDto;
import com.example.superbank.repository.RepositoryStorage;
import com.example.superbank.service.CustomerService;
import com.example.superbank.service.impl.CustomerServiceImpl;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * ADMIN CLIENT VERSION
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class SuperBankApplication extends Application {

    CustomerService customerService = new CustomerServiceImpl(RepositoryStorage.customerRepository, RepositoryStorage.bankAccountRepository);

    private static Resources resources;
    private static NotificationManager notificationManager;
    public static NotificationHelper notificationHelper;

    public static final String NOTIFICATION_CHANNEL_ID = "com.example.superbank";

    public SuperBankApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        addCustomers();
        resources = getResources();

        setUpNotificationManager();

        notificationHelper = new NotificationHelper(notificationManager);
    }

    private void addCustomers() {

        CustomerRequestDto sidor = new CustomerRequestDto("Sidor", "Sidorov",
                new GregorianCalendar(2001, Calendar.JANUARY, 1).getTime(), "Russia", "Samara");

        CustomerRequestDto galya = new CustomerRequestDto("Galina", "Galinova",
                new GregorianCalendar(2002, Calendar.APRIL, 30).getTime(), "Russia", "Samara");

        customerService.add(sidor);
        customerService.add(galya);

    }

    public static Resources getRes(){
        return resources;
    }

//    public static NotificationManager getNotificationManager() {
//        return notificationManager;
//    }

    private void setUpNotificationManager() {

        notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "SuperBank",
                    NotificationManager.IMPORTANCE_HIGH);

            notificationManager.createNotificationChannel(channel);

        }
    }

}
