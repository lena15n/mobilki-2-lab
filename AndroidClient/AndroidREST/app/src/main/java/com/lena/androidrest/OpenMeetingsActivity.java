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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lena.androidrest.dataobjects.Meeting;
import com.lena.androidrest.net.GetTask;

import java.util.ArrayList;

public class OpenMeetingsActivity extends AppCompatActivity implements GetTask.MyAsyncResponse {
    private static final String URL = "http://c10f0db6.ngrok.io/sampel-glassfish-0.0.1-SNAPSHOT/rest/meetings/";
    private Context mContext;

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
        } else {
            Toast.makeText(context, R.string.meeting_fill_serverdata, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void processFinish(ArrayList<Meeting> meetings) {
        if (meetings != null) {
            openMeetings(meetings);
        }
        else {
            Toast.makeText(mContext, R.string.meeting_result_not_ok, Toast.LENGTH_LONG).show();
        }
    }

    private void openMeetings(final ArrayList<Meeting> meetings) {
        ListView meetingsListView = (ListView) findViewById(R.id.all_meetings_listview);
        if (meetingsListView != null) {
            ArrayAdapter<Meeting> arrayAdapter = new ArrayAdapter<Meeting>(this, android.R.layout.simple_list_item_2, android.R.id.text1, meetings) {
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

            final String[] catNames = new String[] {
                    "Рыжик", "Барсик", "Мурзик", "Мурка", "Васька",
                    "Томасина", "Кристина", "Пушок", "Дымка", "Кузя",
                    "Китти", "Масяня", "Симба"
            };

            ArrayAdapter<String> ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, catNames);
            meetingsListView.setAdapter(arrayAdapter);
        }
    }


}
