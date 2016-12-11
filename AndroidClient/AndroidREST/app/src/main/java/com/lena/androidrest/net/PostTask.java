package com.lena.androidrest.net;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import com.lena.androidrest.R;
import com.lena.androidrest.dataobjects.Meeting;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PostTask extends AsyncTask<String, Void, String> {
    private MyAsyncResponse delegate = null;

    private Context mContext;
    private String fullResponseMessage;

    public PostTask(Context context) {
        mContext = context;
    }

    public PostTask(Context context, MyAsyncResponse delegate) {
        mContext = context;
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(String... params) {
        String urlString = params[0];
        String login = params[1];
        String password = params[2];
        String meetingJSON = params[3];

        URL url;
        OutputStreamWriter out;
        HttpURLConnection httpConnection = null;
        String basicAuthData = login + ":" + password;
        String basicAuth = "Basic " + Base64.encodeToString(basicAuthData.getBytes(), Base64.NO_WRAP);

        try {
            url = new URL(urlString);

            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Authorization", basicAuth);
            httpConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpConnection.setUseCaches(false);

            out = new OutputStreamWriter(httpConnection.getOutputStream());
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
        if (fullResponseMessage.equals("200: OK")) {
            Toast.makeText(mContext, R.string.show_result_ok, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, R.string.meeting_result_not_ok, Toast.LENGTH_SHORT).show();
        }
    }

    public interface MyAsyncResponse {
        void processFinish(ArrayList<Meeting> meetings);
    }
}
