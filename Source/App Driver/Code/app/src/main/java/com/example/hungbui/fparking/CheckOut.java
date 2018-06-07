package com.example.hungbui.fparking;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Service.HttpHandler;

public class CheckOut extends AppCompatActivity {

    ImageView imageViewCheckOut;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    TextView textViewAdress, textViewCheckinTime, textViewPrice, textViewLicensePlate;
    private ProgressDialog progressDialog;

    String address = "N/A";
    double price = 0;
    String checkinTime = "N/A";
    String licensePlate = "N/A";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        getSupportActionBar().hide();

        pref = getApplicationContext().getSharedPreferences("positionParking", 0);// 0 - là chế độ private

        textViewAdress = findViewById(R.id.textViewAdress);
        textViewCheckinTime = findViewById(R.id.textViewCheckinTime);
        textViewPrice = findViewById(R.id.textViewPrice);
        textViewLicensePlate = findViewById(R.id.textViewLicensePlate);


        imageViewCheckOut = (ImageView) findViewById(R.id.imageViewCheckout);
        imageViewCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookingID = pref.getString("bookingID","null");
                new PushServer(bookingID).execute();
            }
        });

        new GetDetailParking().execute();
    }

    private class GetDetailParking extends AsyncTask<Void, Void, Void> {

        private String jSonStr;

        @SuppressLint("LongLogTag")
        @Override
        protected Void doInBackground(Void... voids) {

            Log.e("Bat dau doinbackground du lieu", "vl that");

//            Intent intent = getIntent();
//            Bundle bundlPosition = intent.getBundleExtra("BundlePosition");
//            String location = bundlPosition.getString("Position");

            String bookingID = pref.getString("bookingID","null");

            if (bookingID == null || bookingID.isEmpty()) {
                imageViewCheckOut.setEnabled(false);
            } else {
                HttpHandler httpHandler = new HttpHandler();
                jSonStr = httpHandler.makeServiceCall("https://fparking.net/realtimeTest/driver/get_checkOut_Detail.php?bookingID=" + bookingID);
                Log.e("SQL - Check Out", "https://fparking.net/realtimeTest/driver/get_checkOut_Detail.php?bookingID=" + bookingID);
                if (jSonStr != null) {
                    try {

                        JSONObject jsonObject = new JSONObject(jSonStr);
                        // Getting JSON Array node
                        JSONArray contacts = jsonObject.getJSONArray("checkout_detail");
                        // looping through All Contacts
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);

                            address = c.getString("address");
                            //phoneNumber = c.getString("phoneNumber");
                            price = c.getDouble("price");
                            checkinTime = c.getString("checkinTime");
                            licensePlate = c.getString("licensePlate");
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
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            textViewAdress.setText(address);
            textViewCheckinTime.setText(checkinTime + "");
            textViewPrice.setText("/" + price + " chỗ");
            textViewLicensePlate.setText(licensePlate + "/h");
        }
    }

    //get thong tin position tu getArguments
    public String[] getLatLng(String location) {
        String[] lat_lng = location.substring(location.indexOf("(") + 1, location.indexOf(")")).split(",");
        return lat_lng;
    }

    class PushServer extends AsyncTask<Void, Void, Boolean> {

        boolean success = false;
        String bookingID;

        public PushServer(String bookingID) {
            this.bookingID = bookingID;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(Void... voids) {

            HttpHandler httpHandler = new HttpHandler();
            try {
                JSONObject formData = new JSONObject();
                System.out.println(formData.toString());
                formData.put("bookingID", bookingID);
                formData.put("carID", "2");
                formData.put("licensePlate", "34X2-32242");
                formData.put("action", "checkout");
                String json = httpHandler.post("https://fparking.net/realtimeTest/driver/booking.php", formData.toString());
                System.out.println(json);
//                JSONObject jsonObj = new JSONObject(json);


            } catch (Exception ex) {
                Log.e("Error:", ex.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progressDialog.dismiss();

        }
    }
}
