package com.example.hung.fparking.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.hung.fparking.CheckOut;
import com.example.hung.fparking.HomeActivity;
import com.example.hung.fparking.OrderParking;
import com.example.hung.fparking.R;
import com.example.hung.fparking.asynctask.BookingTask;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.NotificationTask;
import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.BookingDTO;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Notification extends Service implements SubscriptionEventListener, IAsyncTaskHandler {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mPreferencesEditor;

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

        mPreferences = getSharedPreferences("driver", 0);
        mPreferencesEditor = mPreferences.edit();

        //        Bundle extras = intent.getExtras();
        PusherOptions options = new PusherOptions();
        options.setCluster("ap1");
        if (Session.currentDriver != null) {
            Session.pusher = new Pusher(Constants.PUSHER_KEY, options);
            Session.channel = Session.pusher.subscribe(mPreferences.getString("drivervehicleID", "") + "channel");
            Log.e("Notification", mPreferences.getString("drivervehicleID", ""));
//            System.out.println("channel : " + Session.currentParking.getId() + "channel");
        }

//        Session.channel.bind(extras.getString("eventName"), this);
        Session.channel.bind(Constants.PUSHER_ORDER_FOR_BOOKING, this);
        Session.channel.bind(Constants.PUSHER_CHECKIN_FOR_BOOKING, this);
        Session.channel.bind(Constants.PUSHER_CHECKOUT_FOR_BOOKING, this);
        connect();


//        return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    public void connect() {
        Session.pusher.connect();
        Log.d("NotificationService", "Connected to pusher");
    }

    @Override
    public void onEvent(String channelName, String eventName, final String data) { //data = ok/cancel
        try {
            if (data.contains("ok")) {
                if (eventName.toLowerCase().contains("order")) {
                    new BookingTask("getorder", mPreferences.getString("drivervehicleID", ""), mPreferences.getString("parkingID", ""), "bookingid",Notification.this);
                    new NotificationTask("cancelorder", mPreferences.getString("drivervehicleID", ""), mPreferences.getString("parkingID", ""), "", null);
                    createNotification("Có xe muốn đặt chỗ");
                } else if (eventName.toLowerCase().contains("checkin")) {
                    new NotificationTask("cancelcheckin", mPreferences.getString("drivervehicleID", ""), mPreferences.getString("parkingID", ""), "", null);
                    mPreferencesEditor.putInt("status",2);
                    mPreferencesEditor.commit();
                    createNotification("Có xe muốn vào bãi");
                } else if (eventName.toLowerCase().contains("checkout")) {
                    new NotificationTask("cancelcheckout", mPreferences.getString("drivervehicleID", ""), mPreferences.getString("parkingID", ""), "", null);
//                    mPreferencesEditor.putInt("status",3);
                    mPreferencesEditor.clear().commit();
                    createNotification("Có xe muốn thanh toán");
                }
            } else if (data.contains("cancel")) {
                if (eventName.toLowerCase().contains("order")) {
                    new NotificationTask("cancelorder", mPreferences.getString("drivervehicleID", ""), mPreferences.getString("parkingID", ""), "", null);
                } else if (eventName.toLowerCase().contains("checkin")) {
                    new NotificationTask("cancelcheckin", mPreferences.getString("drivervehicleID", ""), mPreferences.getString("parkingID", ""), "", null);
                } else if (eventName.toLowerCase().contains("checkout")) {
                    new NotificationTask("cancelcheckout", mPreferences.getString("drivervehicleID", ""), mPreferences.getString("parkingID", ""), "", null);
                }
            }
//            createDialog(eventName, data);
        } catch (Exception e) {
            Log.e("Error notification : ", e.getMessage());
        }
    }

    public void createNotification(String contentTitle) {
        try {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            mBuilder.setVibrate(new long[]{500, 500, 500, 500, 500, 500, 500});
            mBuilder.setContentTitle("Vào Bãi Thôi");
            mBuilder.setContentText(contentTitle);


            Log.d("NotificationService", "Received event");


            mNotificationManager.notify((int) (new Date().getTime() / 1000), mBuilder.build());

            // chuyển đến activity
//            Intent dialogIntent = new Intent(this, OrderParking.class);
//            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(dialogIntent);
        } catch (Exception e) {
            System.out.println("lỗi notification : " + e);
        }
    }

    @Override
    public void onPostExecute(Object o, String action) {
        ArrayList<BookingDTO> booking = (ArrayList<BookingDTO>) o;
        if(action.equals("bookingid")){
            mPreferencesEditor.putString("bookingid",booking.get(0).getBookingID()+"");
            mPreferencesEditor.putInt("status",booking.get(0).getStatus());
            mPreferencesEditor.commit();
        }
    }
}
