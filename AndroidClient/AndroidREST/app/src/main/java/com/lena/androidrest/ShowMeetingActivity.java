package com.lena.androidrest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lena.androidrest.dataobjects.Meeting;
import com.lena.androidrest.net.DeleteTask;
import com.lena.androidrest.net.PostTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import static com.lena.androidrest.MainActivity.cancelAlarm;
import static com.lena.androidrest.MainActivity.createAlarm;

public class ShowMeetingActivity extends AppCompatActivity {
    private static final String URL_PARTICIPANTS_POSTFIX = "participants";
    private static final String URL_DELETE_POSTFIX = "delete/?name=param1&desc=param2";
    private static Meeting meeting;
    private int createAlarm = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_meeting);

        String json = getIntent().getStringExtra(getString(R.string.meeting));
        if (json != null) {
            meeting = getMeetingFromJSON(json);
            updateMeetingView(meeting);
        }

        final Button participateButton = (Button) findViewById(R.id.participate_button);
        participateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewParticipantOfMeeting(participateButton);
            }
        });

        Button toGoogleCalendarButton = (Button) findViewById(R.id.to_google_button);
        toGoogleCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMeetingToGoogleCalendar(meeting);
            }
        });

        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelMeeting(meeting);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        cancelAlarm(getApplicationContext());
    }

    private Meeting getMeetingFromJSON(String json) {
        Gson gson = new GsonBuilder().setDateFormat(MainActivity.DATE_FORMAT).create();
        return gson.fromJson(json, Meeting.class);
    }

    private void updateMeetingView(Meeting meeting) {
        TextView nameTextView = (TextView) findViewById(R.id.show_value_name_textview);
        nameTextView.setText(meeting.getName());

        TextView descTextView = (TextView) findViewById(R.id.show_value_desc_textview);
        descTextView.setText(meeting.getDescription());

        TextView startTextView = (TextView) findViewById(R.id.show_value_start_date_textview);
        SimpleDateFormat format = new SimpleDateFormat(MainActivity.BEAUTY_DATE_FORMAT);
        startTextView.setText(format.format(meeting.getStartDate()));

        TextView endTextView = (TextView) findViewById(R.id.show_value_end_date_textview);
        endTextView.setText(format.format(meeting.getEndDate()));

        TextView participantsTextView = (TextView) findViewById(R.id.show_value_part_textview);
        StringBuilder participants = new StringBuilder();
        for (String participant : meeting.getParticipants()) {
            participants.append(participant + "\n");
        }
        participantsTextView.setText(participants);

        TextView priorityTextView = (TextView) findViewById(R.id.show_value_priority_textview);
        priorityTextView.setText(meeting.getPriority());

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userFio = sharedPreferences.getString("pref_fio", "");
        String userPost = sharedPreferences.getString("pref_post", "");
        if (!userFio.equals("") && !userPost.equals("")) {
            String userData = userFio + ", " + userPost;
            Button participateButton = (Button) findViewById(R.id.participate_button);

            if (meeting != null && meeting.getParticipants().contains(userData)) {
                participateButton.setText(R.string.meeting_do_not_participate);
            }
            else {
                participateButton.setText(R.string.meeting_participate);
            }
        }
    }

    private void setNewParticipantOfMeeting(Button participateButton) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userFio = sharedPreferences.getString("pref_fio", "");
        String userPost = sharedPreferences.getString("pref_post", "");
        if (!userFio.equals("") && !userPost.equals("")) {
            String userData = userFio + ", " + userPost;

            if (participateButton.getText().equals(getString(R.string.meeting_participate))) {
                meeting.addParticipant(userData);
            } else {
                meeting.removeParticipant(userData);
            }
        } else {
            Toast.makeText(this, R.string.meeting_fill_userdata, Toast.LENGTH_LONG).show();
        }

        sendMeetingToServer(meeting);
        updateMeetingView(meeting);
    }

    private void sendMeetingToServer(Meeting meeting) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String login = sharedPreferences.getString("pref_login", "");
        String password = sharedPreferences.getString("pref_password", "");

        Gson gson = new GsonBuilder().setDateFormat(MainActivity.DATE_FORMAT).create();
        String meetingJSON = gson.toJson(meeting);

        if (!login.equals("") && !password.equals("")) {
            new PostTask(this).execute(MainActivity.URL + URL_PARTICIPANTS_POSTFIX, login, password, meetingJSON);
        } else {
            Toast.makeText(this, R.string.meeting_fill_serverdata, Toast.LENGTH_LONG).show();
        }
    }

    private void sendMeetingToGoogleCalendar(Meeting meeting) {
        createAlarm = 1;
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, meeting.getStartDate().getTime())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, meeting.getEndDate().getTime())
                .putExtra(CalendarContract.Events.TITLE, meeting.getName())
                .putExtra(CalendarContract.Events.DESCRIPTION, meeting.getDescription())
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
        startActivity(intent);
    }

    private void cancelMeeting(Meeting meeting) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String login = sharedPreferences.getString("pref_login", "");
        String password = sharedPreferences.getString("pref_password", "");
        String name = null;
        String desc = null;
        try {
            name = URLEncoder.encode(meeting.getName(), "UTF-8");
            desc = URLEncoder.encode(meeting.getDescription(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String urlString = MainActivity.URL + URL_DELETE_POSTFIX;
        urlString = urlString.replace("param1", name);
        urlString = urlString.replace("param2", desc);

        if (!login.equals("") && !password.equals("")) {
            new DeleteTask(this).execute(urlString, login, password);
            createAlarm = 0;
            Intent intent = new Intent(this, OpenMeetingsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.meeting_fill_serverdata, Toast.LENGTH_LONG).show();
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
