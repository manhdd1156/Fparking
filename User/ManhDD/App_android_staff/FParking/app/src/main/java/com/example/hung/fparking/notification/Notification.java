package com.example.hung.fparking.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.hung.fparking.R;
import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.login.MainActivity;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class Notification extends Service implements SubscriptionEventListener {

//    private String eventName;

    public Notification() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Bundle extras = intent.getExtras();
        PusherOptions options = new PusherOptions();
        options.setCluster("ap1");
        Session.pusher = new Pusher(Constants.PUSHER_KEY, options);
        Session.channel = Session.pusher.subscribe(Constants.PUSHER_CHANNEL);
//        Session.channel.bind(extras.getString("eventName"), this);
        Session.channel.bind(Constants.PUSHER_ORDER_FROM_DRIVER, this);
        Session.channel.bind(Constants.PUSHER_CHECKIN_FROM_DRIVER, new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {

                try {
                    System.out.println("data checkin là : " + data);
                    JSONObject json = new JSONObject(data);
//                    handleDataMessage(json, "checkin");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
        Session.channel.bind(Constants.PUSHER_CHECKOUT_FROM_DRIVER, new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {

                try {
                    System.out.println("data checkout là : " + data);
                    JSONObject json = new JSONObject(data);
//                    onEvent("","",data);
//                    handleDataMessage(json, "checkout");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        connect();
//        return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void connect() {
        Session.pusher.connect();
        Log.d("NotificationService", "Connected to pusher");
    }

    @Override
    public void onEvent(String channelName, String eventName, final String data) {
//        try {
//            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
//            mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
//            mBuilder.setPriority(2);
//            mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
//            mBuilder.setVibrate(new long[]{500, 500, 500, 500, 500, 500, 500});
//            mBuilder.setContentTitle("MY RESTAURANT");
//            try {
//                JSONObject jsonObject = new JSONObject(data);
//                mBuilder.setContentText(jsonObject.getString("message"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel("notify_001",
//                    "Channel human readable title",
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            mNotificationManager.createNotificationChannel(channel);
//        }
//            Log.d("NotificationService", "Received event");
//
//
//            mNotificationManager.notify(0, mBuilder.build());
//        } catch (Exception e) {
//            System.out.println(e);
//        }
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this.getApplicationContext(), "notify_001");
        Intent ii = new Intent(this.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        if (eventName.contains("ORDER")) {
            bigText.bigText("FParking");
            bigText.setBigContentTitle("Có xe muốn đặt chỗ");
            bigText.setSummaryText("Nhấn vào để xem chi tiết");

            mBuilder.setContentIntent(pendingIntent);
            mBuilder.setSmallIcon(R.drawable.change);
            mBuilder.setContentTitle("Your Title");
            mBuilder.setContentText("Your text");
            mBuilder.setPriority(2);
            mBuilder.setStyle(bigText);

            NotificationManager mNotificationManager =
                    (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("notify_001",
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT);
                mNotificationManager.createNotificationChannel(channel);
            }

            mNotificationManager.notify(0, mBuilder.build());
        }
    }
}
