package com.example.hung.fparking.notification;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.hung.fparking.DialogActivity;
import com.example.hung.fparking.HomeActivity;
import com.example.hung.fparking.R;
import com.example.hung.fparking.asynctask.ManagerBookingTask;
import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.BookingDTO;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Notification extends Service implements SubscriptionEventListener {
    Thread t;
    //    private String eventName;
    private Activity activity;
    private HandlerThread mHandlerThread;
    private Handler mHandler;
    FragmentManager fm;
    DialogActivity myDialogFragment;
    public Notification() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Bundle extras = intent.getExtras();
        PusherOptions options = new PusherOptions();
        options.setCluster("ap1");
        if(Session.currentParking!=null) {
            Session.pusher = new Pusher(Constants.PUSHER_KEY, options);
            Session.channel = Session.pusher.subscribe(Session.currentParking.getId() + "channel");
//            System.out.println("channel : " + Session.currentParking.getId() + "channel");
        }

//        Session.channel.bind(extras.getString("eventName"), this);
        Session.channel.bind(Constants.PUSHER_ORDER_FROM_DRIVER, this);
        Session.channel.bind(Constants.PUSHER_CHECKIN_FROM_DRIVER, this);
        Session.channel.bind(Constants.PUSHER_CHECKOUT_FROM_DRIVER, this);
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
//            System.out.println(data);
            JSONObject json = new JSONObject(data);
            System.out.println(json);
//            BookingDTO b = new BookingDTO();
//                b.setParkingID(Session.currentParking.getId());
                if (eventName.toLowerCase().contains("order")) {
                    createNotification("Có xe muốn đặt chỗ");

                } else if (eventName.toLowerCase().contains("checkin")) {
                    createNotification("Có xe muốn vào bãi");

                } else if (eventName.toLowerCase().contains("checkout")) {
                    createNotification("Có xe muốn thanh toán");
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
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, 0);

            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
            bigText.bigText("Fparking");
            bigText.setBigContentTitle(contentTitle);
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
        } catch (Exception e) {
            System.out.println("lỗi notification : " + e);
        }
    }

    public void createDialog(String evenName, String data) {

        Intent i = new Intent(Notification.this, DialogActivity.class);
        i.putExtra("eventName",evenName);
        i.putExtra("data",data);
        startActivity(i);
//        final BookingDTO b = bookingDTO;
//        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
//        if (evenName.toLowerCase().contains("order")) {
//            try {
//
//                System.out.println("vào đến order trong create dialog");
//
//                final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//                final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int choice) {
//                        switch (choice) {
//                            case DialogInterface.BUTTON_POSITIVE:
//                                // create booking
//                                new ManagerBookingTask("create", b, null);
//                                break;
//                            case DialogInterface.BUTTON_NEGATIVE:
//                                new ManagerBookingTask("cancel", b, null);
//                        }
//                    }
//                };
//                Runnable r = new Runnable() {
//                    public void run() {
//
//                        System.out.println("====================================");
//                        builder.setMessage("Xe " + b.getLicensePlate() + " muốn đỗ xe trong bãi bạn có đồng ý không")
//                        .setPositiveButton("Có", dialogClickListener)
//                        .setNegativeButton("Không", dialogClickListener).setCancelable(false).show();
//                    }
//                };
//                t = new Thread(r);
//                t.start();
//
//                thí.runOnUiThread(new Runnable() {
//                    public void run() {
//
//                    }
//                });
//                Handler handler = new Handler(Looper.getMainLooper());
//
//                mHandlerThread.run(new Runnable() {
//                    @Override
//                    public void run() {
//                        System.out.println("vào đến handler");
//
//                    }
//                });
//
//
//
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                System.out.println("lỗi createdialog : " + e);
//            }
//
//        } else if (evenName.toLowerCase().contains("checkin")) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int choice) {
//                    switch (choice) {
//                        case DialogInterface.BUTTON_POSITIVE:
//                            Date date = new Date();
//
//                            final Date datetimein = new Date();
//                            b.setStatus(2);
//                            b.setTimein(df.format(datetimein));
//                            new ManagerBookingTask("update", b, null);
//                            reload();
//                            break;
//                        case DialogInterface.BUTTON_NEGATIVE:
//                            new ManagerBookingTask("cancel", b, null);
//                            break;
//                    }
//                }
//            };
//
//            try {
//                builder.setMessage("Xe " + b.getLicensePlate() + " muốn vào bãi bạn có đồng ý không")
//                        .setPositiveButton("Có", dialogClickListener)
//                        .setNegativeButton("Không", dialogClickListener).setCancelable(false).show();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (evenName.toLowerCase().contains("checkout")) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int choice) {
//                    switch (choice) {
//                        case DialogInterface.BUTTON_POSITIVE:
////                            dialog.dismiss();
//                            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                            final Date datetimeout = new Date();
//                            DialogInterface.OnClickListener dialogClickListener2 = new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int choice) {
//                                    switch (choice) {
//                                        case DialogInterface.BUTTON_POSITIVE:
//                                            b.setStatus(3);
//                                            b.setTimeout(df.format(datetimeout));
//                                            new ManagerBookingTask("update", b, null);
//                                            reload();
//                                            break;
//                                        case DialogInterface.BUTTON_NEGATIVE:
//                                            new ManagerBookingTask("cancel", b, null);
//                                            reload();
//                                            break;
//                                    }
//                                }
//                            };
//                            AlertDialog.Builder builder2 = new AlertDialog.Builder(activity);
//                            try {
//                                Date date1 = df.parse(b.getTimein());
//                                String dateformat1 = dateFormatter.format(date1);
//                                String dateformat2 = dateFormatter.format(datetimeout);
//                                long diff = datetimeout.getTime() - date1.getTime();
//                                double diffInHours = diff / ((double) 1000 * 60 * 60);
//                                NumberFormat formatter = new DecimalFormat("###,###");
//                                NumberFormat formatterHour = new DecimalFormat("0.00");
//                                String totalprice = "";
//                                if (diffInHours < 1) {
//                                    totalprice = b.getPrice() + "";
//                                } else {
//                                    totalprice = formatter.format(diffInHours * Double.parseDouble(b.getPrice() + ""));
//                                }
//                                builder2.setMessage("\tHóa đơn checkout \n"
//                                        + "Biển số :          " + b.getLicensePlate() + "\n"
//                                        + "loại xe :           " + b.getTypeCar() + "\n"
//                                        + "thời gian vào : " + dateformat1 + "\n"
//                                        + "thời gian ra :   " + dateformat2 + "\n"
//                                        + "giá đỗ :           " + formatter.format(b.getPrice()) + "vnđ\n"
//                                        + "thời gian đỗ :  " + formatterHour.format(diffInHours) + " giờ \n"
//                                        + "tổng giá :        " + totalprice + "vnđ")
//                                        .setPositiveButton("Yes", dialogClickListener2)
//                                        .setNegativeButton("No", dialogClickListener2).setCancelable(false).show();
//
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
//                            break;
//                        case DialogInterface.BUTTON_NEGATIVE:
//                            new ManagerBookingTask("cancel", b, null);
//                            reload();
//                            break;
//                    }
//                }
//            };
//
//
//            try {
//                builder.setMessage("Xe " + b.getLicensePlate() + "muốn thanh toán bạn có đồng ý không")
//                        .setPositiveButton("Có", dialogClickListener)
//                        .setNegativeButton("Không", dialogClickListener).setCancelable(false).show();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

    }

    public void reload() {

        Intent intent = new Intent(activity.getApplicationContext(), HomeActivity.class);
        activity.overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.finish();

        activity.overridePendingTransition(0, 0);
        activity.startActivity(intent);
    }
    public static Thread performOnBackgroundThread(final Runnable runnable) {
        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } finally {

                }
            }
        };
        t.start();
        return t;
    }
}
