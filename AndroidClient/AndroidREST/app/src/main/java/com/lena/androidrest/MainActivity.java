package com.lena.androidrest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public static final String URL = "http://a530004b.ngrok.io/sampel-glassfish-0.0.1-SNAPSHOT/rest/meetings/";
    public static final String LOG_TAG = "Mine";
    public static final String DATE_FORMAT = "yyy-MM-dd'T'HH:mm:ss";
    public static final String BEAUTY_DATE_FORMAT = "dd.MM.yyyy    HH:mm";
    public static final int REQUEST_CODE = 11;
    public static final int MILLIS_TO_SLEEP = 5000;
    private int createAlarm = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cancelAlarm(getApplicationContext());

        Button createMeetingButton = (Button) findViewById(R.id.create_meeting_button);
        if (createMeetingButton != null) {
            createMeetingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createAlarm = 0;
                    Intent intent = new Intent(getApplicationContext(), CreateMeetingActivity.class);
                    startActivity(intent);
                }
            });
        }

        Button openMeetingsButton = (Button) findViewById(R.id.open_meetings_button);
        if (openMeetingsButton != null) {
            openMeetingsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createAlarm = 0;
                    Intent intent = new Intent(getApplicationContext(), OpenMeetingsActivity.class);
                    startActivity(intent);
                }
            });
        }

        Button findMeetingButton = (Button) findViewById(R.id.find_meeting_button);
        if (findMeetingButton != null) {
            findMeetingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createAlarm = 0;
                    Intent intent = new Intent(getApplicationContext(), FindMeetingActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        cancelAlarm(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings: {
                createAlarm = 0;
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void cancelAlarm(Context context) {
        Intent intent = new Intent(context, MeetingsService.class);
        PendingIntent pintent = PendingIntent.getService(context, REQUEST_CODE, intent, 0);
        AlarmManager alarm_stop = (AlarmManager)context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarm_stop.cancel(pintent);
        Log.d(MainActivity.LOG_TAG, "delete alarm");
    }

    public static void createAlarm(Context context) {
        Calendar calendar = Calendar.getInstance();
        Intent intent = new Intent(context, MeetingsService.class);
        PendingIntent pIntent = PendingIntent.getService(context, REQUEST_CODE, intent, 0);
        AlarmManager alarm = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), MILLIS_TO_SLEEP, pIntent);
    }

    @Override
    public void onBackPressed() {
        createAlarm = 1;
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (createAlarm == 1) {
            createAlarm(getApplicationContext());
            Log.d(MainActivity.LOG_TAG, "create alarm on stop");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (createAlarm == 1) {
            createAlarm(getApplicationContext());
            Log.d(MainActivity.LOG_TAG, "create alarm");
        }
    }
}
