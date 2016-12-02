package com.lena.androidrest.net;


import android.os.AsyncTask;
import android.util.Base64;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class PutTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        URL url;//rest/meetings

        try {
            url = new URL("http://e2228d9a.ngrok.io/rest/meetings/send-meeting");

            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("PUT");
            httpConnection.setRequestProperty("Authorization", "basic " +
                    Arrays.toString(Base64.encode("user:admin".getBytes(), Base64.NO_WRAP)));
            OutputStreamWriter out = new OutputStreamWriter(httpConnection.getOutputStream());
            String meetingJSON = params[0];
            out.write(meetingJSON);
            out.close();

            httpConnection.getInputStream();
            return httpConnection.getResponseMessage();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


   /* @Override
    protected void onPostExecute(String responseMessage) {

    }*/
}
