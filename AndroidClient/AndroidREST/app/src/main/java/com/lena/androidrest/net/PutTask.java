package com.lena.androidrest.net;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import com.lena.androidrest.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class PutTask extends AsyncTask<String, Void, String> {
    private Context mContext;

    public PutTask (Context context){
        mContext = context;
    }

    @Override
    protected String doInBackground(String... params) {
        URL url;
        OutputStreamWriter out = null;
        HttpURLConnection httpConnection = null;

        try {
            url = new URL("http://677244a3.ngrok.io/rest/meetings/send-meeting");

            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("PUT");
            httpConnection.setRequestProperty("Authorization", "basic " +
                    Arrays.toString(Base64.encode("user:admin".getBytes(), Base64.NO_WRAP)));
            out = new OutputStreamWriter(httpConnection.getOutputStream());
            httpConnection.setRequestProperty("Content-Type", "application/json");
            String meetingJSON = params[0];
            out.write(meetingJSON);

            int responseCode = httpConnection.getResponseCode();
            if (responseCode >= 400 && responseCode <= 499) {
                throw new IOException("Bad authentication status: " + responseCode); //provide a more meaningful exception message
            }
            else {
                InputStream in = httpConnection.getInputStream();
                //etc...
            }

            return httpConnection.getResponseMessage();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
        }
        return null;
    }


    @Override
    protected void onPostExecute(String responseMessage) {
        if (responseMessage != null) {
            if (responseMessage.equals("200")) {
                Toast.makeText(mContext, R.string.meeting_result_ok, Toast.LENGTH_SHORT).show();
            } else if (responseMessage.equals("500")) {
                Toast.makeText(mContext, R.string.meeting_result_not_ok, Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(mContext, responseMessage, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(mContext, R.string.meeting_result_not_ok, Toast.LENGTH_SHORT).show();
        }
    }
}
