package com.lena.androidrest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lena.androidrest.dataobjects.Meeting;
import com.lena.androidrest.net.PostTask;

import java.text.SimpleDateFormat;

public class ShowMeetingActivity extends AppCompatActivity {
    private static final String URL_POSTFIX = "participants";
    private static Meeting meeting;

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

        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private Meeting getMeetingFromJSON(String json) {
        Gson gson = new GsonBuilder().setDateFormat("yyy-MM-dd'T'HH:mm:ss").create();
        return gson.fromJson(json, Meeting.class);
    }

    private void updateMeetingView(Meeting meeting) {
        TextView nameTextView = (TextView) findViewById(R.id.show_value_name_textview);
        nameTextView.setText(meeting.getName());

        TextView descTextView = (TextView) findViewById(R.id.show_value_desc_textview);
        descTextView.setText(meeting.getDescription());

        TextView startTextView = (TextView) findViewById(R.id.show_value_start_date_textview);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
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

        Gson gson = new GsonBuilder().setDateFormat("yyy-MM-dd'T'HH:mm:ss").create();
        String meetingJSON = gson.toJson(meeting);

        if (!login.equals("") && !password.equals("")) {
            new PostTask(this).execute(MainActivity.URL + URL_POSTFIX, login, password, meetingJSON);
        } else {
            Toast.makeText(this, R.string.meeting_fill_serverdata, Toast.LENGTH_LONG).show();
        }
    }
}
