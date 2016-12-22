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
    private int requestCode = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, MeetingsService.class);
        PendingIntent pintent = PendingIntent.getService(this, requestCode, intent, 0);
        AlarmManager alarm_stop = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarm_stop.cancel(pintent);
        Log.d(MainActivity.LOG_TAG, "delete alarm");

        Button createMeetingButton = (Button) findViewById(R.id.create_meeting_button);
        if (createMeetingButton != null) {
            createMeetingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                    Intent intent = new Intent(getApplicationContext(), FindMeetingActivity.class);
                    startActivity(intent);
                }
            });
        }
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
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        Calendar calendar = Calendar.getInstance();
        Intent intent = new Intent(this, MeetingsService.class);
        PendingIntent pintent = PendingIntent.getService(this, requestCode, intent, 0);
        AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 5 * 1000, pintent);
        Log.d(MainActivity.LOG_TAG, "create alarm on stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Calendar calendar = Calendar.getInstance();
        Intent intent = new Intent(this, MeetingsService.class);
        PendingIntent pintent = PendingIntent.getService(this, requestCode, intent, 0);
        AlarmManager alarm = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 5 * 1000, pintent);
        Log.d(MainActivity.LOG_TAG, "create alarm");
    }
}
