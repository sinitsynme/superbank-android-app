package com.example.superbank.helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.superbank.R;
import com.example.superbank.SuperBankApplication;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class NotificationHelper {

    private final NotificationManager notificationManager;

    public NotificationHelper(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
    }

    public static int notificationId = 0;
    private static final Resources resources = SuperBankApplication.getRes();

    public void sendNotification(Context context, Class<?> targetClass, List<String> notificationText) {

        Intent resultIntent = new Intent(context, targetClass);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        for (String line : notificationText) {
            inboxStyle.addLine(line);
        }

        NotificationCompat.Builder notifBuilder =
                new NotificationCompat.Builder(context, SuperBankApplication.NOTIFICATION_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_option_dialog)
                        .setContentTitle(resources.getString(R.string.label_transaction_word))
                        .setContentIntent(resultPendingIntent)
                        .setAutoCancel(true)
                        .setStyle(inboxStyle);

        Notification notification = notifBuilder.build();

        notificationManager.notify(notificationId++, notification);


    }


}
