package com.lena.androidrest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class FindMeetingActivity extends AppCompatActivity {
    public static final String URL_NAME_POSTFIX = "name/?name=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_meeting);

        final Button findButton = (Button) findViewById(R.id.find_button);
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameEditText = (EditText) findViewById(R.id.find_edittext);
                String name = nameEditText.getText().toString();
                sendFindMeetingRequest(encodeToTransferAsURLParameter(name));

            }
        });
    }

    private void sendFindMeetingRequest(String name) {
        Intent intent = new Intent(this, OpenMeetingsActivity.class);
        intent.putExtra(URL_NAME_POSTFIX, name);
        startActivity(intent);
    }

    private String encodeToTransferAsURLParameter(String name) {
        try {
            return URLEncoder.encode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, R.string.find_encode_error, Toast.LENGTH_LONG).show();
        return null;
    }
}
