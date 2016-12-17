package com.lena.androidrest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static final String URL = "http://ea6b671c.ngrok.io/sampel-glassfish-0.0.1-SNAPSHOT/rest/meetings/";
    public static final String LOG_TAG = "Mine";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, MeetingsService.class);
        intent.putExtra("app", "on");
        Log.d(MainActivity.LOG_TAG, "onCreate main");
        stopService(intent);


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
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Intent intent = new Intent(this, MeetingsService.class);
        intent.putExtra("app", "off");
        Log.d(MainActivity.LOG_TAG, "onDestroy main");
        startService(intent);
    }
}
