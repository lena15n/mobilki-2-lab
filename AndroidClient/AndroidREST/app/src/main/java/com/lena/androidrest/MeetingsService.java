package com.lena.androidrest;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import com.lena.androidrest.dataobjects.Meeting;
import com.lena.androidrest.net.GetTask;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MeetingsService extends IntentService implements GetTask.MyAsyncResponse {
    NotificationManager notificationManager;
    private static final int MINUTES_TO_SLEEP = 10;
    private static final String serviceName = "MeetingsService";
    private static int id;
    ArrayList<Meeting> meetings;

    public MeetingsService() {
        super(serviceName);
        Log.d(MainActivity.LOG_TAG, "construct zzz");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(MainActivity.LOG_TAG, "create zzz");
        meetings = new ArrayList<>();
        id = 0;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d(MainActivity.LOG_TAG, "start command zzz");
        return START_STICKY;//START_STICKY tells the OS to recreate the service after it has enough
                            // memory and call onStartCommand() again with a null intent

                            //START_REDELIVER_INTENT -  tells the OS to recreate
                            // the service AND redelivery the same intent to onStartCommand()

                            //START_NOT_STICKY - tells the OS to not bother recreating the service again
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        while (true) {
            Log.d(MainActivity.LOG_TAG, "handle zzz");
            checkNewMeetings(getApplicationContext());

            //sleep
            try {
                TimeUnit.SECONDS.sleep(MINUTES_TO_SLEEP);//MINUTES.sleep(MINUTES_TO_SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void prepareMyNotification(Meeting meeting) {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Context context = getApplicationContext();
        Notification.Builder builder = new Notification.Builder(context);
        Resources resouces = context.getResources();
        String meetingName = meeting.getName();
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);//FLAG_ONE_SHOT);


        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_supervisor_meeting_white_24px)
                // большая картинка
                .setLargeIcon(BitmapFactory.decodeResource(resouces, R.drawable.ic_supervisor_meeting_white_24px))
                //.setTicker(res.getString(R.string.warning)) // текст в строке состояния
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true) // уведомление исчезает как только пользователь касается его
                .setContentTitle(resouces.getString(R.string.notify_title)) // Заголовок уведомления
                .setContentText(resouces.getString(R.string.notify_text) + meetingName) // Текст уведомления
                .setTicker(resouces.getString(R.string.ticker_text)); // Текст бегушей строки


        // Notification notification = builder.getNotification(); // до API 16
        Notification notification = builder.build();

        // в эмуляторе не работает, на устройствах - не всегда, зависит от них самих
        notification.ledARGB = 0xff0000ff;//задать их цвет
        notification.ledOffMS = 0;//включить светодиоды
        notification.ledOnMS = 1;
        notification.flags = notification.flags | Notification.FLAG_SHOW_LIGHTS;

        notificationManager.notify(id++, notification);
    }

    private void checkNewMeetings(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String login = sharedPreferences.getString("pref_login", "");
        String password = sharedPreferences.getString("pref_password", "");
        String url = MainActivity.URL;

        if (!login.equals("") && !password.equals("")) {
            new GetTask(this).execute(url, login, password);
        } else {
            Log.d(MainActivity.LOG_TAG, getString(R.string.meeting_fill_serverdata));
        }
    }

    @Override
    public void processFinish(ArrayList<Meeting> newMeetings) {
        if (newMeetings != null) {
            newMeetings.removeAll(meetings);//leave only new meetings, that don't exist in meetings
            meetings.addAll(newMeetings);

            for (Meeting meeting : newMeetings) {
                prepareMyNotification(meeting);
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(MainActivity.LOG_TAG, "Service.onDestroy()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
