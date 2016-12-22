package com.lena.androidrest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static com.lena.androidrest.MainActivity.createAlarm;

// To run MeetingsService after system will be turned on
public class ServiceBroadcastReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Log.d(MainActivity.LOG_TAG, "onReceive " + intent.getAction());

        createAlarm(context);
    }
}