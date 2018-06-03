package ise1005.edu.fpt.vn.myrestaurant.notification;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import ise1005.edu.fpt.vn.myrestaurant.R;
import ise1005.edu.fpt.vn.myrestaurant.config.Constants;
import ise1005.edu.fpt.vn.myrestaurant.config.Session;

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
        Session.channel.bind(extras.getString("eventName"), this);
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

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
        mBuilder.setSmallIcon(R.mipmap.ic_shop);
        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        mBuilder.setVibrate(new long[]{500, 500, 500, 500, 500, 500, 500});
        mBuilder.setContentTitle("MY RESTAURANT");
        try {
            JSONObject jsonObject = new JSONObject(data);
            mBuilder.setContentText(jsonObject.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("NotificationService", "Received event");


        mNotificationManager.notify((int) (new Date().getTime() / 1000), mBuilder.build());
    }
}
