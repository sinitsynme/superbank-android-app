package com.example.superbank;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.backendless.Backendless;
import com.example.superbank.helper.NotificationHelper;
import com.example.superbank.values.Defaults;


@RequiresApi(api = Build.VERSION_CODES.N)
public class SuperBankApplication extends Application {

    private static Resources resources;
    private static NotificationManager notificationManager;
    public static NotificationHelper notificationHelper;

    public static final String NOTIFICATION_CHANNEL_ID = "com.example.superbank";

    public SuperBankApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        setUpBackendless();

        resources = getResources();

        setUpNotificationManager();

        notificationHelper = new NotificationHelper(notificationManager);
    }

    public static Resources getRes() {
        return resources;
    }

    public static NotificationManager getNotificationManager() {
        return notificationManager;
    }

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

    private void setUpBackendless() {
        Backendless.initApp(getApplicationContext(), Defaults.APPLICATION_ID, Defaults.API_KEY);
        Backendless.setUrl(Defaults.SERVER_URL);
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
