package com.example.hung.fparking.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.hung.fparking.R;
import com.example.hung.fparking.config.Constants;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParsePosition;
import java.util.List;


public class GetVehicleTask extends AsyncTask<Void, Void, Boolean> {

    private Activity activity;
private int parkingID;
    private String event;

    ProgressDialog pdLoading;
    public GetVehicleTask(int parkingID,String event, Activity activity) {
        this.activity = activity;
        this.parkingID = parkingID;
        this.event = event;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pdLoading = new ProgressDialog(activity);
        //this method will be running on UI thread
        pdLoading.setMessage("\tĐợi xíu...");
        pdLoading.setCancelable(false);
        pdLoading.show();

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... params) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            String json = httpHandler.get(Constants.API_URL + "vehicles/notifications?parkingid=" + parkingID +"&event=" + event);
            final JSONObject jsonObj = new JSONObject(json);
            System.out.println("GetVehicleTask : " + jsonObj);
            final TextView tvLicensePlate = (TextView) activity.findViewById(R.id.tvLP);
            final TextView tvType = (TextView) activity.findViewById(R.id.tvType);
            final TextView tvColor = (TextView) activity.findViewById(R.id.tvColor);
            final TextView tvTitle = (TextView) activity.findViewById(R.id.title);
            final TextView tvRate  = (TextView) activity.findViewById(R.id.tvRate);
            final JSONObject jsonVehicleType = new JSONObject(jsonObj.getString("vehicletype"));
            final String jsonRating = httpHandler.get(Constants.API_URL + "vehicles/ratings/" + jsonObj.getString("id"));
            System.out.println("json Rate <" + jsonRating +">");


            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    try {
                        if(event.contains("order")) {
                            tvTitle.setText("Có xe muốn đặt chỗ !");
                        }else if(event.contains("checkin")) {
                            tvTitle.setText("Có xe muốn vào bãi !");
                        }else if(event.contains("checkout")) {
                            tvTitle.setText("Có xe muốn thanh toán !");
                        }else if(event.contains("cancel")) {
                            tvTitle.setText("Xe " + jsonObj.getString("licenseplate") + " đã hủy đặt chỗ!");
                        }
                        tvLicensePlate.setText(jsonObj.getString("licenseplate"));
                        if(jsonRating.isEmpty() || jsonRating.contains("NaN") || Double.parseDouble(jsonRating) >= 3) {
                            tvRate.setText("Tốt");
                        }else if(Double.parseDouble(jsonRating) < 3 )
                        {
                            tvRate.setText("Không tốt");
                        }

                    tvColor.setText(jsonObj.getString("color"));

                    tvType.setText(jsonVehicleType.getString("type"));
                    // Stuff that updates the UI
                    } catch (JSONException e) {
                        System.out.println("lỗi getvehicletask : " + e);
                    }
                }
            });

        } catch (Exception e) {
            System.out.println(" Error GetVehicleTask : " + e);
            Log.e("Exception", " get driverVehicle fail in GetVehicleTask " + e);
        }
        return false;
    }
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        pdLoading.dismiss();
    }

}

