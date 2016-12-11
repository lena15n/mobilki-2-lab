package com.lena.androidrest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lena.androidrest.dataobjects.Meeting;

import java.text.SimpleDateFormat;

public class ShowMeetingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_meeting);

        String json = getIntent().getStringExtra(getString(R.string.meeting));
        if (json != null) {
            Meeting meeting = getMeetingFromJSON(json);

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
        }
    }

    private Meeting getMeetingFromJSON(String json) {
        Gson gson = new GsonBuilder().setDateFormat("yyy-MM-dd'T'HH:mm:ss").create();
        return gson.fromJson(json, Meeting.class);
    }
}
