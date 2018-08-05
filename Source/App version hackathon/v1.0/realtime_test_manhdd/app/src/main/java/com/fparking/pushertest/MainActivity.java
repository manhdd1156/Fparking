package com.fparking.pushertest;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
//import android.support.v7.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.fparking.pushertest.asynctask.HttpHandler;
import com.fparking.pushertest.asynctask.IAsyncTaskHandler;
import com.fparking.pushertest.asynctask.ManagerBookingTask;
import com.fparking.pushertest.config.Constants;
import com.fparking.pushertest.dto.BookingDTO;
import com.fparking.pushertest.dto.ParkingInforDTO;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements IAsyncTaskHandler {
    String ownerPhoneNumber, bookingSpace, totalSpace, parkingID;
    public int currentSpace, outOfBooking;
    ManagerBookingTask getBookings;
    ListView lv;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    TextView tvSpace, tvAddress;
    String wantPermission = android.Manifest.permission.READ_PHONE_STATE;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (!checkPermission(wantPermission)) {
            requestPermission(wantPermission);
        } else {
            ownerPhoneNumber = getPhone();
        }
        /*setting toolbar*/
        System.out.println(ownerPhoneNumber);
        getSupportActionBar().hide();


//        getSupportActionBar().hide();
        tvSpace = (TextView) findViewById(R.id.tvSpace);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        lv = (ListView) findViewById(R.id.cars_list);

        /*get the number of cars in parking without booking */
        pref = getApplicationContext().getSharedPreferences("CountOutOfBooking", 0);// 0 - là chế độ private
        editor = pref.edit();
        outOfBooking = Integer.parseInt(pref.getString("numberOutOfBooking", "0"));
        /*start pusher*/
        PusherOptions options = new PusherOptions();
        options.setCluster("ap1");
        Pusher pusher = new Pusher(Constants.PUSHER_KEY, options);

        Channel channel = pusher.subscribe(Constants.PUSHER_CHANNEL);

        channel.bind(Constants.PUSHER_EVENT_ORDER_FOR_OWNER, new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {

                try {
                    System.out.println("data order : " + data);
                    JSONObject json = new JSONObject(data);
                    handleDataMessage(json,"order");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                createNotification();
            }
        });
        channel.bind(Constants.PUSHER_EVENT_CHECKIN_FOR_OWNER, new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {

                try {
                    JSONObject json = new JSONObject(data);
                    handleDataMessage(json,"checkin");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                createNotification();
                System.out.println("data checkin là : " + data);
            }
        });
        channel.bind(Constants.PUSHER_EVENT_CHECKOUT_FOR_OWNER, new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {

                try {
                    JSONObject json = new JSONObject(data);
                    handleDataMessage(json,"checkout");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                createNotification();
                System.out.println("data checkout là : " + data);
            }
        });
        pusher.connect();

        /*End pusher*/
        new GetParkingTask().execute((Void) null);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private void handleDataMessage(final JSONObject json,String action) {
//        Log.e(TAG, "push json: " + json.toString());

        try {
//            JSONObject data = json.getJSONObject("data");
            final String carID = json.getString("carID");
            final String licensePlate = json.getString("licensePlate");

//

            if (action.equals("order")) { // người dùng order => insert booking với status = 1

                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {

                        final BookingDTO b = new BookingDTO(0, Integer.parseInt(parkingID), Integer.parseInt(carID), "1", "", "", "", "", Double.parseDouble("0"));
                        createNotification("Có xe muốn đỗ", "Xe :" + licensePlate);
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int choice) {
                                switch (choice) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        createBooing(b);

                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE:
                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        try {
                            builder.setMessage("Bạn có đồng ý không")
                                    .setPositiveButton("Có", dialogClickListener)
                                    .setNegativeButton("Không", dialogClickListener).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                String bookingID = json.getString("bookingID");
                Date currentTime = Calendar.getInstance().getTime();
                DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//                String licensePlate = json.getString("licensePlate");

                if (action.equals("checkin")) { // người dùng muốn checkIn => đổi status = 2
                    BookingDTO b = new BookingDTO(37, 3, 1, "2", "2018-06-06 09:03:55", "2018-06-06 21:06:53", "33L6-3318", "4-6", Double.parseDouble("50000"));
                    createNotification("Xe đã đến và đã checkin", "Xe : " + licensePlate);
                    new ManagerBookingTask("update", getWindow().getDecorView().getRootView(), parkingID, MainActivity.this, lv, b);
                } else if (action.equals("checkout")) {
//                    String type = json.getString("type");
//                    String price = json.getString("price");
                    String checkoutTime = json.getString("checkoutTime");
                    final BookingDTO b = new BookingDTO(Integer.parseInt(bookingID), Integer.parseInt(parkingID), Integer.parseInt(carID), "3", "", checkoutTime, "", "", Double.parseDouble("60000"));
                    createNotification("Xe muốn checkout", "Xe : " + licensePlate);
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int choice) {
                            switch (choice) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    new ManagerBookingTask("update", getWindow().getDecorView().getRootView(), parkingID, MainActivity.this, lv, b);


                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    try {
                        builder.setMessage("Bạn có đồng ý không")
                                .setPositiveButton("Có", dialogClickListener)
                                .setNegativeButton("Không", dialogClickListener).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
//            else
        } catch (JSONException e) {
//            Log.e(TAG, "Json Exception: " + e.getMessage());
            System.out.println("Json Exception: " + e.getMessage());
        } catch (Exception e) {
//            Log.e(TAG, "Exception: " + e.getMessage());
            System.out.println("Json Exception: " + e.getMessage());
        }
    }

    public void createBooing(BookingDTO b) {
        new ManagerBookingTask("insert", getWindow().getDecorView().getRootView(), "not need", MainActivity.this, lv, b);

    }

    public void createNotification(String title, String content) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
//
//        // Build notification
//        // Actions are just fake
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle("Có xe muốn order ")
                .setContentText("Xe với biển số : ... ").setSmallIcon(R.drawable.apply)
                .setAutoCancel(true)
                .setContentIntent(pIntent)
                .addAction(R.drawable.apply, "Đồng ý", pIntent)
                .addAction(R.drawable.apply, "Hủy", pIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, mBuilder.build());
    }

    private String getPhone() {
        TelephonyManager phoneMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, wantPermission) != PackageManager.PERMISSION_GRANTED) {
            return "??";
        }
        return phoneMgr.getLine1Number();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ownerPhoneNumber = getPhone();
                } else {

                    Toast.makeText(MainActivity.this, "Permission Denied. We can't get phone number.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void requestPermission(String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {
            Toast.makeText(MainActivity.this, "Phone state permission allows us to get phone number. Please allow it for additional functionality.", Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, PERMISSION_REQUEST_CODE);
    }

    private boolean checkPermission(String permission) {
        if (Build.VERSION.SDK_INT >= 23) {
            int result = ContextCompat.checkSelfPermission(MainActivity.this, permission);
            if (result == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    class GetParkingTask extends AsyncTask<Void, Void, Boolean> {

        private JSONObject oneParking;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(Void... voids) {
            HttpHandler httpHandler = new HttpHandler();
            try {
                String json = httpHandler.get(Constants.API_URL + "parking/get.php?ownerPhoneNumber=0968949064");
                JSONObject jsonObj = new JSONObject(json);
                JSONArray parkings = jsonObj.getJSONArray("parkingInfor");
                oneParking = parkings.getJSONObject(0);
                String parkingid = oneParking.getString("parkingID");
                String address = oneParking.getString("address");
                String space = oneParking.getString("space");
                String currentSpace = oneParking.getString("currentSpace");
                parkingID = parkingid;
                setText(tvAddress, address);
                totalSpace = space;
                tvSpace.setText(currentSpace + "/" + space);
//                json = httpHandler.get(Constants.API_URL + "booking/get_countBookingInfor.php?parkingID=" + parkingID);
//                jsonObj = new JSONObject(json);
//                JSONArray bookingInfors = jsonObj.getJSONArray("numberCarInParking");
//                JSONObject c = bookingInfors.getJSONObject(0);
//                bookingSpace = c.getString("NumberOfBooking");
            } catch (Exception ex) {
                Log.e("Error:", ex.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
//            currentSpace = Integer.parseInt(bookingSpace) + Integer.parseInt(pref.getString("numberOutOfBooking", "0"));
//
//            tvSpace.setText(currentSpace + "/" + totalSpace);

            new ManagerBookingTask("get", getWindow().getDecorView().getRootView(), parkingID, MainActivity.this, lv, null);
        }
    }

    private void setText(final TextView text, final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }

    @Override
    public void onPostExecute(Object o) {
    }
}
