package com.lena.androidrest;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CreateMeetingActivity extends AppCompatActivity {
    private Button createButton;
    private Context mContext;

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
        TextView startDateTextView = (TextView) findViewById(R.id.meeting_start_date_textview);
        startDateTextView.setText(dayOfMonth + "." + String.valueOf(monthOfYear + 1) + "." + year);
    }

    public void onEndDataSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        TextView endDateTextView = (TextView) findViewById(R.id.meeting_end_date_textview);
        endDateTextView.setText(dayOfMonth + "." + String.valueOf(monthOfYear + 1) + "." + year);
    }
}
