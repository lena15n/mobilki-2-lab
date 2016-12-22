package com.lena.androidrest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lena.androidrest.dataobjects.Meeting;
import com.lena.androidrest.net.GetTask;

import java.util.ArrayList;

import static com.lena.androidrest.MainActivity.cancelAlarm;
import static com.lena.androidrest.MainActivity.createAlarm;

public class OpenMeetingsActivity extends AppCompatActivity implements GetTask.MyAsyncResponse {
    private Context mContext;
    private int createAlarm = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_meetings);

        cancelAlarm(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAlarm = 0;
                Intent intent = new Intent(getApplicationContext(), FindMeetingActivity.class);
                startActivity(intent);
            }
        });

        mContext = getApplicationContext();
        prepareRequestToServer(mContext);
    }

    @Override
    protected void onResume() {
        super.onResume();

        cancelAlarm(getApplicationContext());
    }

    private void prepareRequestToServer(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String login = sharedPreferences.getString("pref_login", "");
        String password = sharedPreferences.getString("pref_password", "");
        Intent intent = getIntent();
        String findMeetingsUrl = intent.getStringExtra(FindMeetingActivity.URL);
        String url = MainActivity.URL;

        if (!login.equals("") && !password.equals("")) {
            if (findMeetingsUrl != null) {
                url = findMeetingsUrl;
            }

            new GetTask(this).execute(url, login, password);
        } else {
            Toast.makeText(context, R.string.meeting_fill_serverdata, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void processFinish(ArrayList<Meeting> meetings) {
        if (meetings != null) {
            openMeetings(meetings);
        } else {
            Toast.makeText(mContext, R.string.meeting_result_empty, Toast.LENGTH_LONG).show();
        }
    }

    private void openMeetings(final ArrayList<Meeting> meetings) {
        ListView meetingsListView = (ListView) findViewById(R.id.all_meetings_listview);
        if (meetingsListView != null) {
            final ArrayAdapter<Meeting> arrayAdapter = new ArrayAdapter<Meeting>(this,
                    android.R.layout.simple_list_item_2, android.R.id.text1, meetings) {
                @NonNull
                @Override
                public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                    text1.setText(meetings.get(position).getName());
                    text2.setText(meetings.get(position).getDescription());
                    return view;
                }
            };

            meetingsListView.setAdapter(arrayAdapter);
            meetingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                    Meeting currentMeeting = arrayAdapter.getItem(position);

                    createAlarm = 0;
                    Context context = getApplicationContext();
                    Intent intent = new Intent(context, ShowMeetingActivity.class);
                    Gson gson = new GsonBuilder().setDateFormat(MainActivity.DATE_FORMAT).create();
                    intent.putExtra(context.getString(R.string.meeting), gson.toJson(currentMeeting));
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        createAlarm = 0;
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
