package com.lena.androidrest.net;


import android.os.AsyncTask;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lena.androidrest.dataobjects.Meeting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetTask extends AsyncTask<String, Void, ArrayList<Meeting>> {
    private MyAsyncResponse delegate = null;

    public GetTask(MyAsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected ArrayList<Meeting> doInBackground(String... params) {
        String urlString = params[0];
        String login = params[1];
        String password = params[2];

        URL url;
        OutputStreamWriter out;
        HttpURLConnection httpConnection = null;
        String basicAuthData = login + ":" + password;
        String basicAuth = "Basic " + Base64.encodeToString(basicAuthData.getBytes(), Base64.NO_WRAP);

        try {
            url = new URL(urlString);

            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Authorization", basicAuth);
            httpConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpConnection.setUseCaches(false);

            int responseCode = httpConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream in = httpConnection.getInputStream();
                BufferedReader reader = null;
                StringBuilder response = new StringBuilder();

                try {
                    reader = new BufferedReader(new InputStreamReader(in));
                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                return getMeetingsFromJSON(response.toString());
            }

            return null;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
        }
        return null;
    }


    @Override
    protected void onPostExecute(ArrayList<Meeting> meetings) {
       delegate.processFinish(meetings);
    }

    public interface MyAsyncResponse {
        void processFinish(ArrayList<Meeting> meetings);
    }

    private ArrayList<Meeting> getMeetingsFromJSON(String json) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<ArrayList<Meeting>>(){}.getType();

        return gson.fromJson(json, collectionType);
    }
}
