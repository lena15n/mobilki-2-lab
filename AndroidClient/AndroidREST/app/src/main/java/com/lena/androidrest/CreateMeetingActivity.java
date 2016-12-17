package com.lena.androidrest;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lena.androidrest.dataobjects.Meeting;
import com.lena.androidrest.net.PutTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreateMeetingActivity extends AppCompatActivity {//implements GetTask.MyAsyncResponse {
    private static final String URL = MainActivity.URL + "send-meeting";
    private Button createButton;
    private Context mContext;
    private Date startDate;
    private Date endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);

        createButton = (Button) findViewById(R.id.meeting_create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext = getApplicationContext();
                Meeting newMeeting = prepareNewMeeting(mContext);

                if (newMeeting != null) {
                    String newMeetingJSON = meetingToJSON(newMeeting);
                    sendMeetingToServer(mContext, newMeetingJSON);
                }
            }
        });

        mContext = getApplicationContext();

        Spinner prioritiesSpinner = (Spinner) findViewById(R.id.meeting_priority_spinner);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(this, R.array.priorities, R.layout.spinner_item);
        prioritiesSpinner.setAdapter(arrayAdapter);

        TextView startDateTextView = (TextView) findViewById(R.id.meeting_start_date_textview);
        startDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(fragmentManager, getString(R.string.meeting_enter_start_date));
            }
        });

        TextView endDateTextView = (TextView) findViewById(R.id.meeting_end_date_textview);
        endDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(fragmentManager, getString(R.string.meeting_enter_end_date));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onStartDataSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth, 0, 0);
        startDate = calendar.getTime();

        TextView startDateTextView = (TextView) findViewById(R.id.meeting_start_date_textview);
        startDateTextView.setText(dayOfMonth + "." + String.valueOf(monthOfYear + 1) + "." + year);
    }

    public void onEndDataSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth, 0, 0);
        endDate = calendar.getTime();

        TextView endDateTextView = (TextView) findViewById(R.id.meeting_end_date_textview);
        endDateTextView.setText(dayOfMonth + "." + String.valueOf(monthOfYear + 1) + "." + year);
    }

    private Meeting prepareNewMeeting(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String userFio = sharedPreferences.getString("pref_fio", "");
        String userPost = sharedPreferences.getString("pref_post", "");
        if (!userFio.equals("") && !userPost.equals("")) {
            ArrayList<String> participants = new ArrayList<>();
            participants.add(userFio + ", " + userPost);
            String meetingName = ((TextView) findViewById(R.id.meeting_name_edittext)).getText().toString();
            String meetingDescription = ((TextView) findViewById(R.id.meeting_desc_edittext)).getText().toString();
            Spinner spinner = (Spinner) findViewById(R.id.meeting_priority_spinner);
            String meetingPriority = spinner.getSelectedItem().toString();

            if (startDate.before(endDate)) {
                return new Meeting(meetingName, meetingDescription, startDate, endDate, participants, meetingPriority);
            } else {
                Toast.makeText(context, R.string.meeting_startdate_should_be_before, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(context, R.string.meeting_fill_userdata, Toast.LENGTH_LONG).show();
        }

        return null;
    }

    private String meetingToJSON(Meeting meeting) {
        Gson gson = new GsonBuilder().setDateFormat("yyy-MM-dd'T'HH:mm:ss").create();
        String meetingJSON = gson.toJson(meeting);

        Log.i("GSON", gson.toJson(meetingJSON));
        return  meetingJSON;
    }

    private void sendMeetingToServer(Context context, String meetingJSON) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String login = sharedPreferences.getString("pref_login", "");
        String password = sharedPreferences.getString("pref_password", "");

        if (!login.equals("") && !password.equals("")) {
            new PutTask(mContext).execute(URL, login, password, meetingJSON);

            Intent intent = new Intent(this, OpenMeetingsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else {
            Toast.makeText(context, R.string.meeting_fill_serverdata, Toast.LENGTH_LONG).show();
        }
    }


}
