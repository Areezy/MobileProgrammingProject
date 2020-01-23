package com.example.mobileprogrammingproject.sync;

import android.content.Context;


import com.example.mobileprogrammingproject.NotificationsUtilites.NotificationUtils;

public class ReminderTasks{
    public static final String ACTION_INCREMENT = "increment";
    public static final String ACTION_DISMISS_NOTIFICATION  = "dismiss-notification";
    public static final String ACTION_SHOPPING_REMINDER     = "shopping_reminder";

    public static void executeTask(Context context, String action){
        if (ACTION_INCREMENT.equals(action)){
            increment(context);
        }
        else if (ACTION_DISMISS_NOTIFICATION.equals(action)){
            NotificationUtils.clearAllNotifications(context);
        }
        else if (ACTION_SHOPPING_REMINDER.equals(action)){
            issueShoppingReminder(context);
        }
    }
    private static void increment(Context context){
        NotificationUtils.clearAllNotifications(context);
    }

    private static void issueShoppingReminder(Context context) {
        NotificationUtils.remindUser(context);
    }
}