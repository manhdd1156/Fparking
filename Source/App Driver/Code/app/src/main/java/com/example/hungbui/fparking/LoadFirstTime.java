package com.example.hungbui.fparking;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONObject;

import Models.GPSTracker;
import Service.HttpHandler;

public class LoadFirstTime extends AppCompatActivity {

    Location myLocation = null;
    double myLocationLat = 0;
    double myLocationLng = 0;
    Button button;

    public Context mContext;

    protected LocationManager locationManager;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    public LoadFirstTime(Context mContext) {
        this.mContext = mContext;
    }

    public LoadFirstTime() {
    }

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
                finish();
            }
        },SPLASH_TIME_OUT);


//        PusherOptions options = new PusherOptions();
//        options.setCluster("ap1");
//        Pusher pusher = new Pusher("d8e15d0b0ecad0c93a5e", options);
//
//        Channel channel = pusher.subscribe("Fparking");
//
//        channel.bind("BOOKING_MANAGER", new SubscriptionEventListener() {
//            @Override
//            public void onEvent(String channelName, String eventName, final String data) {
//                System.out.println("data : " + data);
//
//            }
//        });
////
//        button = findViewById(R.id.buttonSend);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new UpdateBookingTask().execute();
//            }
//        });
//        pusher.connect();



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


    }
}

class UpdateBookingTask extends AsyncTask<Void, Void, Boolean> {

    boolean success = false;

    public UpdateBookingTask() {
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            JSONObject formData = new JSONObject();
            System.out.println(formData.toString());
            String json = httpHandler.post("https://fparking.net/realtimeTest/driver/update_BookingInfor.php", formData.toString());
            System.out.println(json);
            JSONObject jsonObj = new JSONObject(json);


        } catch (Exception ex) {
            Log.e("Error:", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
//        Intent intent = new Intent();
//        if (success)
//            intent.putExtra("result", "success!");
//        else
//            intent.putExtra("result", "failed");
//        this.activity.setResult(RESULT_OK, intent);
//        this.activity.finish();
    }


}
