
package com.example.mobileprogrammingproject.sync;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

import androidx.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class ReminderUtilities {
    private static final int REMINDER_INTERVAL_MINUTES = 1;
    private static final int REMINDER_INTERVAL_SECONDS = (int) (TimeUnit
            .MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES));
    private static final int SYNC_FLEXTIME_SECONDS =
            REMINDER_INTERVAL_SECONDS / 2;

    private static final String  REMINDER_SHOPPINGJOB_TAG = "shopping_reminder_tag";
    private static boolean sInitialized;

    synchronized public static void scheduleShoppingReminder(
            @NonNull final Context context) {
        if (!sInitialized) {



            Driver driver = new GooglePlayDriver(context);
            FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

            Job constraintReminderJob = dispatcher.newJobBuilder()
                    .setService(FirebaseJobService.class)
                    .setTag(REMINDER_SHOPPINGJOB_TAG)
                    .setConstraints(null)

                    .setLifetime(Lifetime.FOREVER)
                    .setRecurring(true)
                    .setTrigger(Trigger.executionWindow(
                            REMINDER_INTERVAL_SECONDS,
                            REMINDER_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                    .setReplaceCurrent(true)
                    .build();

            dispatcher.schedule(constraintReminderJob);
            sInitialized = true;
        }
    }
}
