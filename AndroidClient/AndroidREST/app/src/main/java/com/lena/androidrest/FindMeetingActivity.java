package com.lena.androidrest;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.lena.androidrest.time.DatePickerFragment;
import com.lena.androidrest.time.TimePickerFragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FindMeetingActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {
    public static final String URL = "FindMeetingActivityURL";
    private static final String URL_POSTFIX = "search/?";
    private static final String URL_WORDS_POSTFIX = "words=";
    private static final String URL_DATE_POSTFIX = "date=";
    private Date date;
    private int mYear;
    private int mMonthOfYear;
    private int mDayOfMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_meeting);

        TextView startDateTextView = (TextView) findViewById(R.id.find_set_date_label_textView);
        startDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(fragmentManager, getString(R.string.find_date_label));
            }
        });

        final Button findButton = (Button) findViewById(R.id.find_button);
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameEditText = (EditText) findViewById(R.id.find_edittext);
                String name = nameEditText.getText().toString();
                sendFindMeetingRequest(encodeToTransferAsURLParameter(name),
                        encodeToTransferAsURLParameter(date));

            }
        });
    }

    private void sendFindMeetingRequest(String name, String strDate) {
        Intent intent = new Intent(this, OpenMeetingsActivity.class);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(MainActivity.URL);
        stringBuilder.append(URL_POSTFIX);

        if (name != null) {
            stringBuilder.append(URL_WORDS_POSTFIX);
            stringBuilder.append(name);
            if (date != null) {
                stringBuilder.append("&");
                stringBuilder.append(URL_DATE_POSTFIX);
                stringBuilder.append(strDate);
            }
        }
        else if (date != null) {
            stringBuilder.append(URL_DATE_POSTFIX);
            stringBuilder.append(strDate);
        }

        intent.putExtra(URL, stringBuilder.toString());
        startActivity(intent);
    }

    private String encodeToTransferAsURLParameter(String name) {
        try {
            if (!name.equals("")) {
                return URLEncoder.encode(name, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String encodeToTransferAsURLParameter(Date date) {
        try {
            if (date != null) {
                DateFormat dateFormat = new SimpleDateFormat(MainActivity.DATE_FORMAT);
                return URLEncoder.encode(dateFormat.format(date), "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        FragmentManager fragmentManager = getFragmentManager();
        TimePickerFragment timePickerFragment = new TimePickerFragment();

        if (fragmentManager.findFragmentByTag(getString(R.string.find_date_label)) != null) {
            mYear = year;
            mMonthOfYear = monthOfYear;
            mDayOfMonth = dayOfMonth;
            timePickerFragment.show(fragmentManager, getString(R.string.find_date_label));
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat(MainActivity.BEAUTY_DATE_FORMAT);
        if (getFragmentManager().findFragmentByTag(getString(R.string.find_date_label)) != null) {
            calendar.set(mYear, mMonthOfYear, mDayOfMonth, hourOfDay, minute);
            date = calendar.getTime();
            String startDateString = dateFormat.format(date);
            TextView startDateTextView = (TextView) findViewById(R.id.find_set_date_label_textView);
            startDateTextView.setText(startDateString);
        }
    }
}
