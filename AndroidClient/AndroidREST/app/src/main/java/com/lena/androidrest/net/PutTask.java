package com.lena.androidrest.net;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class PutTask extends AsyncTask<String, Void, String> {
    private Context mContext;
    private String fullResponseMessage;

    public PutTask (Context context){
        mContext = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String meetingJSON = params[0];
        meetingJSON = "{\"description\":\"u u u looks like shit when u dance\",\"endDate\":\"2016-12-03T14:32:08.957+04:00\",\"name\":\"Meeting\",\"participants\":[\"Galya Smith\",\"Artemii Lebedev\"],\"priority\":\"Critical\",\"startDate\":\"2016-12-03T14:32:08.957+04:00\"}";/*= "[\n" +
                "  {\n" +
                "    \"description\": \"This is new meeting!\",\n" +
                "    \"endDate\": \"2016-12-03T14:28:59.876+04:00\",\n" +
                "    \"name\": \"Meeting\",\n" +
                "    \"participants\": [\n" +
                "      \"Galya Smith\",\n" +
                "      \"Artemii Lebedev\"\n" +
                "    ],\n" +
                "    \"priority\": \"Critical\",\n" +
                "    \"startDate\": \"2016-12-03T14:28:59.876+04:00\"\n" +
                "  }\n" +
                "]";*/

        //"{\"description\": \"desc of meet \",\"endDate\":\"Dec 10, 2016 00:00:34\",\"name\":\"MEEETT\",\"participants\":[\"pa1, pa2\"],\"priority\":\"Plan\",\"startDate\":\"Dec 3, 2016 00:00:36\"}";//URLEncoder.encode(meetingJSON, "utf-8");

        byte[] bytes = meetingJSON.getBytes();

        URL url;
        OutputStreamWriter out = null;
        HttpURLConnection httpConnection = null;
        String basicAuth = "Basic " + Base64.encodeToString("user:admin".getBytes(), Base64.NO_WRAP);

        try {
            url = new URL("http://89f39319.ngrok.io/sampel-glassfish-0.0.1-SNAPSHOT/rest/meetings/send-meeting");

            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("PUT");
            httpConnection.setRequestProperty ("Authorization", basicAuth);
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.setUseCaches(false);

            out = new OutputStreamWriter(httpConnection.getOutputStream());
            out.write(meetingJSON);
            out.close();

            int responseCode = httpConnection.getResponseCode();
            fullResponseMessage = "" + responseCode + ": " + httpConnection.getResponseMessage();


            if (responseCode >= 400 && responseCode <= 499) {
                //provide a more meaningful exception message
            }
            else {
                InputStream in = httpConnection.getInputStream();
            }

            return fullResponseMessage;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
        }
        return fullResponseMessage;
    }


    @Override
    protected void onPostExecute(String responseMessage) {
        /*if (fullResponseMessage != null) {
            if (fullResponseMessage.equals("200")) {
                Toast.makeText(mContext, R.string.meeting_result_ok, Toast.LENGTH_SHORT).show();
            } else if (fullResponseMessage.equals("500")) {
                Toast.makeText(mContext, R.string.meeting_result_not_ok, Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(mContext, fullResponseMessage, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(mContext, R.string.meeting_result_not_ok, Toast.LENGTH_SHORT).show();
        }*/

        Toast.makeText(mContext, responseMessage, Toast.LENGTH_SHORT).show();
    }
}
