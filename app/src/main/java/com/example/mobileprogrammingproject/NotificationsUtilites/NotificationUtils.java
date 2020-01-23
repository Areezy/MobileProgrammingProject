package com.example.mobileprogrammingproject.NotificationsUtilites;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationCompat.Action;
import androidx.core.content.ContextCompat;


import com.example.mobileprogrammingproject.MainActivity;
import com.example.mobileprogrammingproject.R;
import com.example.mobileprogrammingproject.sync.ReminderTasks;
import com.example.mobileprogrammingproject.sync.ShoppingReminderIntentService;

public class NotificationUtils {

    private static final int    SHOPPING_NOTIFICATION_ID         = 1144;
    private static final int    PENDING_INTENT_ID       = 3420;
    private static final String SHOPPING_NOTIFICATION_CHANNEL_ID = "shopping_reminder_notification_channel";

    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void remindUser(Context context) {
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    SHOPPING_NOTIFICATION_CHANNEL_ID,
                    "Primary",
                    NotificationManager.IMPORTANCE_HIGH);

            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat
                .Builder(context,SHOPPING_NOTIFICATION_CHANNEL_ID);
        notificationBuilder
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.products_one)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string
                        .save_money_notification_title))
                .setContentText(context.getString(R.string
                        .new_product_notification))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string
                                .new_product_notification)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true)
                .addAction(GoShoppingAction(context))
        .addAction(ignoreShoppinRemindergAction(context));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(SHOPPING_NOTIFICATION_ID, notificationBuilder.build());
    }

    private static final int    ACTION_IGNORE_PENDING_INTENT_ID        = 15;

    private static Action ignoreShoppinRemindergAction(Context context) {

        Intent ignoreReminderIntent = new Intent(context, ShoppingReminderIntentService.class);
        ignoreReminderIntent.setAction(ReminderTasks.ACTION_DISMISS_NOTIFICATION);

        PendingIntent ignoreReminderPendingIntent = PendingIntent.getService(
                context,
                ACTION_IGNORE_PENDING_INTENT_ID,
                ignoreReminderIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Action ignoreReminderAction = new Action(R.drawable.ic_cancel_black_24px,
                "No, thanks.",
                ignoreReminderPendingIntent);

        return ignoreReminderAction;
    }


    private static final int ACTION_SHOP_PENDING_INTENT_ID = 133;

    private static Action GoShoppingAction(Context context) {



        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(context, ACTION_SHOP_PENDING_INTENT_ID, intent,  PendingIntent.FLAG_UPDATE_CURRENT);

        Action GoShopAction = new Action(
                R.drawable.ic_cancel_black_24px,
                "Go Shopping!",
                contentIntent);

        return GoShopAction;
    }



    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(
                context,
                PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static Bitmap largeIcon(Context context) {

        Resources res       = context.getResources();
        Bitmap    largeIcon = BitmapFactory.decodeResource(res, R.drawable.shopping);
        return largeIcon;
    }
}
