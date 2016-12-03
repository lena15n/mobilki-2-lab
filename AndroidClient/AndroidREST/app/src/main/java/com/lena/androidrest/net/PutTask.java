package com.lena.androidrest.net;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
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
        String urlString = params[0];
        String login = params[1];
        String password = params[2];
        String meetingJSON1 = params[3];
        String meetingJSON = "";
        byte ptext[] = new byte[0];
        try {
            ptext = meetingJSON1.getBytes();
            meetingJSON = new String(ptext, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

      //  String f = Charset.forName("UTF-8").encode(meetingJSON);

        URL url;
        OutputStreamWriter outin;
        HttpURLConnection httpConnection = null;
        String basicAuthData = login + ":" + password;
        String basicAuth = "Basic " + Base64.encodeToString(basicAuthData.getBytes(), Base64.NO_WRAP);

        try {
            url = new URL(urlString);

            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("PUT");
            httpConnection.setRequestProperty ("Authorization", basicAuth);
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.setUseCaches(false);

            //outin = new OutputStreamWriter(httpConnection.getOutputStream());
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(httpConnection.getOutputStream(), "UTF-8"));
            out.write(meetingJSON);
            out.close();

            int responseCode = httpConnection.getResponseCode();
            fullResponseMessage = "" + responseCode + ": " + httpConnection.getResponseMessage();

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
