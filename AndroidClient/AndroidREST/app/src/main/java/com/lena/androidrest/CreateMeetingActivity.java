package com.lena.androidrest;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateMeetingActivity extends AppCompatActivity implements TimePicker.OnTimeChangedListener{
    private Button createButton;
    private Context mContext;
    //private

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);

        createButton = (Button) findViewById(R.id.meeting_create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send meeting
                mContext = getApplicationContext();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
                String userFio = sharedPreferences.getString("pref_fio", "");
                String userPost = sharedPreferences.getString("pref_post", "");
                String meetingName = ((TextView)findViewById(R.id.meeting_name_textview)).getText().toString();
                String meetingDescription = ((TextView)findViewById(R.id.meeting_desc_textview)).getText().toString();
                Spinner spinner = (Spinner) findViewById(R.id.meeting_priority_spinner);
                String meetingPriority = spinner.getSelectedItem().toString();
                Toast.makeText(mContext, meetingPriority, Toast.LENGTH_SHORT).show();
                //Date startDate = (TextView) findViewById(R.id.meeting_start_edittext).get


            }
        });

        mContext = getApplicationContext();

        Spinner prioritiesSpinner = (Spinner) findViewById(R.id.meeting_priority_spinner);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(this, R.array.priorities, R.layout.spinner_item);
        prioritiesSpinner.setAdapter(arrayAdapter);

        Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.date_time_picker_layout);
        dialog.setTitle("Custom Dialog");
        TimePicker timePicker = (TimePicker)dialog.findViewById(R.id.date_time_timePicker);
        timePicker.setOnTimeChangedListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();



    }


    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

    }
}
