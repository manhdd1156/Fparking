package com.example.hung.fparking.notification;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.hung.fparking.OrderParking;
import com.example.hung.fparking.R;
import com.example.hung.fparking.config.Constants;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Notification extends Service implements SubscriptionEventListener {

    public Notification() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("Service", "Running");
        PusherOptions options = new PusherOptions();
        options.setCluster("ap1");
        Pusher pusher = new Pusher(Constants.PUSHER_KEY, options);

        Channel channel = pusher.subscribe(Constants.PUSHER_CHANNEL);

        channel.bind("ORDER_FOR_BOOKING", this);
        pusher.connect();
        return START_STICKY;
    }

    @Override
    public void onEvent(String channelName, String eventName, final String data) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        mBuilder.setVibrate(new long[]{500, 500, 500, 500, 500, 500, 500});
        mBuilder.setContentTitle("Vào Bãi Thôi");
        try {
            JSONObject jsonObject = new JSONObject(data);
            mBuilder.setContentText(jsonObject.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("NotificationService", "Received event");


        mNotificationManager.notify((int) (new Date().getTime() / 1000), mBuilder.build());

        // chuyển đến activity
        Intent dialogIntent = new Intent(this, OrderParking.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dialogIntent);
    }

}
