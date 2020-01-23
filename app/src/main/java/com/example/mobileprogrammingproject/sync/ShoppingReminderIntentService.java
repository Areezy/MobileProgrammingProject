package com.example.mobileprogrammingproject.sync;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class ShoppingReminderIntentService extends IntentService {
    public ShoppingReminderIntentService() {
        super("ShoppingReminderIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        ReminderTasks.executeTask(this, action);
    }
}