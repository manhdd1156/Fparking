package com.example.hung.fparking.notification;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.hung.fparking.dialog.DialogActivity;
import com.example.hung.fparking.HomeActivity;
import com.example.hung.fparking.R;
import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.config.Session;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONException;

public class Notification extends Service implements SubscriptionEventListener {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        PusherOptions options = new PusherOptions();
        options.setCluster("ap1");
        if(Session.currentParking!=null) {
            Session.pusher = new Pusher(Constants.PUSHER_KEY, options);
            Session.channel = Session.pusher.subscribe(Session.currentParking.getId() + "schannel");
        }
        System.out.println("class Notification");
        Session.channel.bind(Constants.PUSHER_ORDER_FROM_DRIVER, this);
        Session.channel.bind(Constants.PUSHER_CHECKIN_FROM_DRIVER, this);
        Session.channel.bind(Constants.PUSHER_CHECKOUT_FROM_DRIVER, this);
        Session.channel.bind(Constants.PUSHER_CANCEL_FROM_DRIVER, this);
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
        try {

                if (eventName.toLowerCase().contains("order")) {
                    createNotification("Có xe muốn đặt chỗ");
                    System.out.println("noti order");
                } else if (eventName.toLowerCase().contains("checkin")) {
                    createNotification("Có xe muốn vào bãi");
                    System.out.println("noti checkin");
                } else if (eventName.toLowerCase().contains("checkout")) {
                    createNotification("Có xe muốn thanh toán");
                    System.out.println("noti checkout");
                }else if(eventName.toLowerCase().contains("cancel") && data.contains("preorder")) {
                    Session.homeActivity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Intent myIntent = new Intent(Session.homeActivity, HomeActivity.class);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            Session.homeActivity.finish();
                        startActivity(myIntent);
                        Session.homeActivity.recreate();
//                        finish();


                        }
                    });
                    return;
                }
            createDialog(eventName, data);
        } catch (Exception e) {
            Log.e("Error notification : ", e.getMessage());
        }
    }

    public void createNotification(String contentTitle) {
        try {
            System.out.println("vào đến create notification");
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this.getApplicationContext(), "notify_001");
            Intent ii = new Intent(this.getApplicationContext(), HomeActivity.class);
            ii.putExtra("touchNoti","true");
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,ii, 0);

            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
            bigText.bigText("Nhấn vào để xem chi tiết");
            bigText.setBigContentTitle(contentTitle);

            mBuilder.setContentIntent(pendingIntent);
            mBuilder.setSmallIcon(R.drawable.icon_noti);
            mBuilder.setContentTitle("content Title");
            mBuilder.setContentText("Content Text");
            mBuilder.setPriority(2);
            mBuilder.setStyle(bigText).setAutoCancel(true);

            NotificationManager mNotificationManager =
                    (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("notify_001",
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT);
                mNotificationManager.createNotificationChannel(channel);
            }

            mNotificationManager.notify(0, mBuilder.build());
        } catch (Exception e) {
            System.out.println("lỗi notification : " + e);
        }
    }

    public void createDialog(String evenName, String data) {

        Intent i = new Intent(Notification.this, DialogActivity.class);
        i.putExtra("eventName",evenName);
        i.putExtra("data",data);
        startActivity(i);

    }
}
