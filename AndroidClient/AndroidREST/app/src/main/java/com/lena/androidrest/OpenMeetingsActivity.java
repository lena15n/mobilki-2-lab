package com.lena.androidrest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.lena.androidrest.dataobjects.Meeting;
import com.lena.androidrest.net.GetTask;

import java.util.ArrayList;

public class OpenMeetingsActivity extends AppCompatActivity implements GetTask.MyAsyncResponse {
    private static final String URL = "http://c10f0db6.ngrok.io/sampel-glassfish-0.0.1-SNAPSHOT/rest/meetings/";
    private Context mContext;
    private ArrayList<Meeting> meetings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_meetings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FindMeetingActivity.class);
                startActivity(intent);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        mContext = getApplicationContext();
        prepareRequestToServer(mContext);

    }

    private void prepareRequestToServer(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String login = sharedPreferences.getString("pref_login", "");
        String password = sharedPreferences.getString("pref_password", "");

        if (!login.equals("") && !password.equals("")) {
            new GetTask(this).execute(URL, login, password);
        }
        else {
            Toast.makeText(context, R.string.meeting_fill_serverdata, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void processFinish(ArrayList<Meeting> meetings) {
        if (meetings != null)
            Toast.makeText(mContext, "200 ok", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(mContext, "null", Toast.LENGTH_LONG).show();
    }
}
