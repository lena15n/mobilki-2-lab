package com.lena.androidrest;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FindMeetingActivity extends AppCompatActivity {
    public static final String URL = "FindMeetingActivityURL";
    private static final String URL_POSTFIX = "name/?";
    private static final String URL_WORDS_POSTFIX = "name=";
    private static final String URL_DATE_POSTFIX = "date=";
    private Date date;

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
                DateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss");
                return URLEncoder.encode(dateFormat.format(date), "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth, 0, 0);
        date = calendar.getTime();

        TextView startDateTextView = (TextView) findViewById(R.id.find_set_date_label_textView);
        startDateTextView.setText(dayOfMonth + "." + String.valueOf(monthOfYear + 1) + "." + year);
    }
}
