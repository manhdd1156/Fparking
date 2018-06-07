package com.example.hungbui.fparking;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import Models.GPSTracker;
import Service.HttpHandler;

public class LoadFirstTime extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;
    Location myLocation = null;
    double myLocationLat = 0;
    double myLocationLng = 0;
    Button button;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_load_first_time);

        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(LoadFirstTime.this, HomeActivity.class);
                startActivity(homeIntent);
                GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
                myLocationLat = gpsTracker.getLatitude();
                myLocationLng = gpsTracker.getLongitude();

                Intent intent = new Intent(LoadFirstTime.this, HomeActivity.class);
                double[] locaton;
                locaton = new double[2];
                locaton[0] = myLocationLat;
                locaton[1] = myLocationLng;
                Log.e("Ahihi", myLocationLat + "" + myLocationLng);
                intent.putExtra("Location", locaton);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
        pref = getApplicationContext().getSharedPreferences("positionParking", 0);// 0 - là chế độ private


        PusherOptions options = new PusherOptions();
        options.setCluster("ap1");
        Pusher pusher = new Pusher("d8e15d0b0ecad0c93a5e", options);

        Channel channel = pusher.subscribe("Fparking");

        channel.bind("ORDER_FOR_BOOKING", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                JSONObject jsonObjectData = null;

                System.out.println("nhan order:" + data);
                try {
                    jsonObjectData = new JSONObject(data);
                    String carID = jsonObjectData.getString("carID");
                    String bookingID = jsonObjectData.getString("bookingID");
                    String message = jsonObjectData.getString("message");
                    if (carID.equals("2")) {
                        if (message.contains("OK")) {
                            editor = pref.edit();
                            editor.putString("bookingID", bookingID);
                            editor.commit();
                            Intent intent = new Intent(LoadFirstTime.this, Direction_Activity.class);
                            startActivity(intent);

                            //Toast.makeText(LoadFirstTime.this, "dm met vl nhe. nhan duoc roi", Toast.LENGTH_LONG).show();
                        } else if (message.contains("CANCEL")) {

                        }
                    }
                } catch (JSONException e) {
//            Log.e(TAG, "Json Exception: " + e.getMessage());
                    System.out.println("Json Exception: " + e.getMessage());
                } catch (Exception e) {
//            Log.e(TAG, "Exception: " + e.getMessage());
                    System.out.println("Json Exception: " + e.getMessage());
                }

            }
        });

        channel.bind("CHECKIN_FOR_BOOKING", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                JSONObject jsonObjectData = null;

                System.out.println("nhan check in:" + data);
                System.out.println("nhan check in:" + data);
                try {
                    jsonObjectData = new JSONObject(data);
                    String carID = jsonObjectData.getString("carID");
                    String bookingID = jsonObjectData.getString("bookingID");
                    String message = jsonObjectData.getString("message");
                    if (carID.equals("2")) {
                        if (message.contains("OK")) {
                            editor = pref.edit();
                            editor.putString("bookingID", bookingID);
                            Intent intent = new Intent(LoadFirstTime.this, CheckOut.class);
                            startActivity(intent);

                            //Toast.makeText(LoadFirstTime.this, "dm met vl nhe. nhan duoc roi", Toast.LENGTH_LONG).show();
                        } else if (message.contains("CANCEL")) {

                        }
                    }
                } catch (JSONException e) {
//            Log.e(TAG, "Json Exception: " + e.getMessage());
                    System.out.println("Json Exception: " + e.getMessage());
                } catch (Exception e) {
//            Log.e(TAG, "Exception: " + e.getMessage());
                    System.out.println("Json Exception: " + e.getMessage());
                }

            }
        });

        channel.bind("CHECKOUT_FOR_BOOKING", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                JSONObject jsonObjectData = null;

                System.out.println(data);
                try {
                    jsonObjectData = new JSONObject(data);
                    String carID = jsonObjectData.getString("carID");
                    String bookingID = jsonObjectData.getString("bookingID");
                    String message = jsonObjectData.getString("message");
                    if (carID.equals("2")) {
                        if (message.contains("OK")) {
                            editor = pref.edit();
                            editor.putString("bookingID", bookingID);
                            Intent intent = new Intent(LoadFirstTime.this, HomeActivity.class);
                            startActivity(intent);

                            //Toast.makeText(LoadFirstTime.this, "dm met vl nhe. nhan duoc roi", Toast.LENGTH_LONG).show();
                        } else if (message.contains("CANCEL")) {

                        }
                    }
                } catch (JSONException e) {
//            Log.e(TAG, "Json Exception: " + e.getMessage());
                    System.out.println("Json Exception: " + e.getMessage());
                } catch (Exception e) {
//            Log.e(TAG, "Exception: " + e.getMessage());
                    System.out.println("Json Exception: " + e.getMessage());
                }
            }
        });
        pusher.connect();
System.out.println("Json Exception: " + e.getMessage());
    }
}
