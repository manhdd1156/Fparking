package com.example.hungbui.fparking;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Entity.DetailInformationParking;
import Entity.NearParking;
import Service.HttpHandler;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;


public class OrderParking extends AppCompatActivity {

    DetailInformationParking detailInformationParkings;
    Button buttonDt_Cho;
    TextView textViewEmptySpace, textViewAdress, textViewSlots, textViewSpace, textViewTime, textViewPrice, textViewIDParking, textViewName;
    ImageView direction;

    String address = "N/A";
    int parkingID = 0;
    String phoneNumber;
    double price = 0;
    String timeoc = "N/A";
    int parking = 0;
    int space = 0;
    String urlImage = "";
    double latitude = 0;
    double longitude = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder_parking);
        getSupportActionBar().hide();

        textViewAdress = findViewById(R.id.textViewAddress);
        textViewEmptySpace = findViewById(R.id.textViewEmptySpace);
        textViewSlots = findViewById(R.id.textViewSlots);
        textViewTime = findViewById(R.id.textViewTime);
        textViewPrice = findViewById(R.id.textViewPrice);
        textViewIDParking = findViewById(R.id.textViewIDParking);
        textViewName = findViewById(R.id.textViewName);

        buttonDt_Cho = findViewById(R.id.buttonDat_Cho_Ngay);

        buttonDt_Cho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_License_Plate add_license_plate = new Add_License_Plate();
                add_license_plate.show(getFragmentManager(), "Day la fragment");
            }
        });

        new GetDetailParking().execute();

//        PusherOptions options = new PusherOptions();
//        options.setCluster("ap1");
//        Pusher pusher = new Pusher("d8e15d0b0ecad0c93a5e", options);
//
//        Channel channel = pusher.subscribe("my-channel");
//
//        channel.bind("my-event", new SubscriptionEventListener() {
//            @Override
//            public void onEvent(String channelName, String eventName, final String data) {
//                System.out.println("data : " + data);
//
//            }
//        });
//        pusher.connect();
    }

    private class GetDetailParking extends AsyncTask<Void, Void, Void> {

        private String jSonStr;

        @SuppressLint("LongLogTag")
        @Override
        protected Void doInBackground(Void... voids) {

            Log.e("Bat dau doinbackground du lieu", "vl that");

            Intent intent = getIntent();
            Bundle bundlPosition = intent.getBundleExtra("BundlePosition");
            String location = bundlPosition.getString("Position");

            String[] latLng = getLatLng(location);

            HttpHandler httpHandler = new HttpHandler();

//            jSonStr = httpHandler.makeServiceCall("http://192.168.119.226:8005/Fpraking/get_Detail_Parking.php?latitude=" + latLng[0] + "&" + "longitude=" + latLng[1]);
//            Log.e("SQL", "http://192.168.119.226:8005/Fpraking/get_Detail_Parking.php.php?latitude=" + latLng[0] + "&" + "longitude=" + latLng[1]);


            jSonStr = httpHandler.makeServiceCall("https://fparking.net/realtimeTest/driver/get_Detail_Parking.php?latitude=" + latLng[0] + "&" + "longitude=" + latLng[1]);
            Log.e("SQL", "https://fparking.net/realtimeTest/driver/get_Detail_Parking.php?latitude=" + latLng[0] + "&" + "longitude=" + latLng[1]);
            if (jSonStr != null) {
                try {

                    JSONObject jsonObject = new JSONObject(jSonStr);
                    // Getting JSON Array node
                    JSONArray contacts = jsonObject.getJSONArray("detail_parking");
                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        address = c.getString("address");
                        phoneNumber = c.getString("phoneNumber");
                        price = c.getDouble("price");
                        timeoc = c.getString("timeoc");
                        space = c.getInt("space");
                        parking = c.getInt("currentSpace");
                        urlImage = c.getString("url");
                        longitude = Double.parseDouble(c.getString("longitude"));
                        latitude = Double.parseDouble(c.getString("latitude"));
                    }

                } catch (final JSONException e) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(),
//                                    "Json parsing error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG)
//                                    .show();
//                        }
//                    });
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(getApplicationContext(),
//                                "Couldn't get json from server. Check LogCat for possible errors!",
//                                Toast.LENGTH_LONG)
//                                .show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            textViewAdress.setText(address);
            textViewEmptySpace.setText(parking + "");
            textViewSlots.setText("/" + space + " chỗ");
            textViewIDParking.setText(parkingID + "");
            textViewPrice.setText(price + "/h");
            textViewTime.setText(timeoc);
        }
    }

    private class GetImagePaking extends AsyncTask<Void, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
        }

    }

    //get thong tin position tu getArguments
    public String[] getLatLng(String location) {
        String[] lat_lng = location.substring(location.indexOf("(") + 1, location.indexOf(")")).split(",");
        return lat_lng;
    }
}
