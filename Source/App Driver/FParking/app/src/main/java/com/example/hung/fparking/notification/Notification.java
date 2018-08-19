package com.example.hung.fparking.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.hung.fparking.CheckOut;
import com.example.hung.fparking.Direction;
import com.example.hung.fparking.HomeActivity;
import com.example.hung.fparking.OrderParking;
import com.example.hung.fparking.R;
import com.example.hung.fparking.Theme;
import com.example.hung.fparking.asynctask.BookingTask;
import com.example.hung.fparking.asynctask.DriverLoginTask;
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
        Session.spref = getSharedPreferences("intro", 0);

        //        Bundle extras = intent.getExtras();
        final PusherOptions options = new PusherOptions();
        options.setCluster("ap1");
        if (!Session.spref.getString("driverid", "").equals("")) {
            new BookingTask("newbooking", Session.spref.getString("driverid", ""), "", "newbooking", this);
            Session.pusher = new Pusher(Constants.PUSHER_KEY, options);
            Session.channel = Session.pusher.subscribe(Session.spref.getString("driverid", "") + "dchannel");
            Log.e("Notification: ", Session.spref.getString("driverid", "") + "dchannel");

//            System.out.println("channel : " + Session.currentParking.getId() + "channel");

            Session.channel.bind(Constants.PUSHER_ORDER_FOR_BOOKING, this);
            Session.channel.bind(Constants.PUSHER_CHECKIN_FOR_BOOKING, this);
            Session.channel.bind(Constants.PUSHER_CHECKOUT_FOR_BOOKING, this);
            connect();
            new DriverLoginTask("second_time", null, "", new IAsyncTaskHandler() {
                @Override
                public void onPostExecute(Object o, String action) {
                }
            });
        }


        return START_STICKY;
    }

    public void connect() {
        Session.pusher.connect();
        Log.d("NotificationService", "Connected to pusher");
    }

    public void disconnect() {
        Session.pusher.disconnect();
        Log.d("NotificationService", "Disconnected to pusher");
    }

    @Override
    public void onEvent(String channelName, String eventName, final String data) { //data = ok/cancel
        try {
            if (data.contains("ok")) {
                if (eventName.toLowerCase().contains("order")) {
                    new BookingTask("getorder", mPreferences.getString("drivervehicleID", ""), mPreferences.getString("parkingID", ""), "bookingid", Notification.this);
                    new NotificationTask("cancelorder", mPreferences.getString("vehicleID", ""), mPreferences.getString("parkingID", ""), "", null);
                    createNotification("Bạn đã đặt chỗ thành công");
                    if (HomeActivity.yourCountDownTimer != null) {
                        HomeActivity.yourCountDownTimer.cancel();
                    }
                    if (OrderParking.yourCountDownTimer != null) {
                        OrderParking.yourCountDownTimer.cancel();
                    }
                    Intent intentDirection = new Intent(Notification.this, Direction.class);
                    intentDirection.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intentDirection);
                } else if (eventName.toLowerCase().contains("checkin")) {
                    new NotificationTask("cancelcheckin", mPreferences.getString("vehicleID", ""), mPreferences.getString("parkingID", ""), "", null);
                    if (mPreferences.getInt("status", 5) == 1) {
                        mPreferencesEditor.putInt("status", 2);
                        mPreferencesEditor.commit();
                        createNotification("Xe của bạn đã được chấp nhận vào bãi");
                        Intent intentCheckout = new Intent(Notification.this, CheckOut.class);
                        intentCheckout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intentCheckout);
                    }
                } else if (eventName.toLowerCase().contains("checkout")) {
                    new NotificationTask("cancelcheckout", mPreferences.getString("vehicleID", ""), mPreferences.getString("parkingID", ""), "", null);
                    if (mPreferences.getInt("status", 5) == 2) {
                        mPreferencesEditor.putInt("status", 3);
                        mPreferencesEditor.commit();
                        createNotification("Bạn đã thanh toán thành công");
                        Intent intentCheckout = new Intent(Notification.this, CheckOut.class);
                        intentCheckout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intentCheckout);
                    }
                }
            } else if (data.contains("cancel")) {
                if (eventName.toLowerCase().contains("order") && Session.quickbook != 1) {
                    createNotification("Bãi xe không chấp nhận yêu cầu đặt chỗ của bạn");
                    if (OrderParking.yourCountDownTimer != null) {
                        OrderParking.yourCountDownTimer.cancel();
                    }
                    if (OrderParking.progessDialog != null) {
                        OrderParking.progessDialog.cancel();
                    }
                    new NotificationTask("cancelorder", mPreferences.getString("vehicleID", ""), mPreferences.getString("parkingID", ""), "", null);
                } else if (eventName.toLowerCase().contains("checkin")) {
                    createNotification("Bãi xe không chấp nhận yêu cầu vào bãi của bạn");
                    new NotificationTask("cancelcheckin", mPreferences.getString("vehicleID", ""), mPreferences.getString("parkingID", ""), "", null);
                } else if (eventName.toLowerCase().contains("checkout")) {
                    createNotification("Bãi xe không chấp nhận yêu cầu lấy xe của bạn");
                    new NotificationTask("cancelcheckout", mPreferences.getString("vehicleID", ""), mPreferences.getString("parkingID", ""), "", null);
                }
            } else if (data.contains("after")) {
                if (eventName.toLowerCase().contains("order")) {
                    new NotificationTask("after", mPreferences.getString("vehicleID", ""), mPreferences.getString("parkingID", ""), "", null);
                    createNotification("Bãi xe đã hủy yêu cầu đặt chỗ của bạn");
                    mPreferencesEditor.clear().commit();
                    Intent intentCancel = new Intent(Notification.this, HomeActivity.class);
                    intentCancel.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentCancel);
                }
            }
//            createDialog(eventName, data);
        } catch (Exception e) {
            Log.e("Error notification : ", e.getMessage());
        }
    }

    public void createNotification(String contentTitle) {
        Intent intent = new Intent(this, HomeActivity.class);
//        intent.putExtra("NotificationMessage", "order");
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
//                .setDefaults(Notification.DE)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.icon_noti)
                .setTicker("Hearty365")
                .setPriority(3)
                .setContentTitle("Fparking")
                .setContentIntent(pIntent)
                .setContentText(contentTitle)
                .setContentInfo("Info");
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        notificationManager.notify(/*notification id*/1, notificationBuilder.build());
    }

    @Override
    public void onPostExecute(Object o, String action) {
        ArrayList<BookingDTO> booking = (ArrayList<BookingDTO>) o;
        if (action.equals("bookingid")) {
            if (booking.size() > 0) {
                mPreferencesEditor.putString("bookingid", booking.get(0).getBookingID() + "");
                mPreferencesEditor.putInt("status", booking.get(0).getStatus());
                mPreferencesEditor.commit();
            }
        } else if (action.equals("newbooking")) {
            booking = (ArrayList<BookingDTO>) o;
            if (booking.size() > 0) {
                BookingDTO bookingDTO = booking.get(0);
                if (bookingDTO.getStatus() != 0 && bookingDTO.getStatus() != 3) {
                    mPreferencesEditor.putString("drivervehicleID", bookingDTO.getDriverVehicleID() + "");
                    mPreferencesEditor.putString("vehicleID", bookingDTO.getVehicleID() + "");
                    mPreferencesEditor.putString("parkingID", bookingDTO.getParkingID() + "");
                    mPreferencesEditor.putInt("status", bookingDTO.getStatus());
                    mPreferencesEditor.putString("bookingid", bookingDTO.getBookingID() + "").commit();
                } else {
                    mPreferencesEditor.clear().commit();
                }
            } else {
                mPreferencesEditor.clear().commit();
            }
        }
    }

    @Override
    public void onDestroy() {
        disconnect();
        super.onDestroy();
    }
}
