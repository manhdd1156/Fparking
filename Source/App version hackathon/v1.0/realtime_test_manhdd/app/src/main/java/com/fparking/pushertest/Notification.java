package com.fparking.pushertest;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.fparking.pushertest.config.Constants;
import com.fparking.pushertest.config.Session;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.SubscriptionEventListener;


/**
 * Created by DungNA on 10/22/17.
 */

public class Notification extends Service implements SubscriptionEventListener {

    private String eventName;

    public Notification() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();
        PusherOptions options = new PusherOptions();
        options.setCluster("ap1");
        Session.pusher = new Pusher(Constants.PUSHER_KEY, options);
        Session.channel = Session.pusher.subscribe(Constants.PUSHER_CHANNEL);
        Session.channel.bind(extras.getString("my-event"), this);
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

//        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
//        mBuilder.setSmallIcon(R.mipmap.ic_shop);
//        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
//        mBuilder.setVibrate(new long[]{500, 500, 500, 500, 500, 500, 500});
//        mBuilder.setContentTitle("MY RESTAURANT");
//        try {
//            JSONObject jsonObject = new JSONObject(data);
//            mBuilder.setContentText(jsonObject.getString("message"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        Log.d("NotificationService", "Received event");
//
//
//        mNotificationManager.notify((int) (new Date().getTime() / 1000), mBuilder.build());

        System.out.println(data);
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }
}
