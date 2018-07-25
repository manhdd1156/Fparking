package com.example.hung.fparking.asynctask;

import android.app.Activity;
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

import org.json.JSONObject;


public class GetVehicleTask extends AsyncTask<Void, Void, Boolean> {

    private Activity activity;
private int parkingID;
    private String event;
    public GetVehicleTask(int parkingID,String event, Activity activity) {
        this.activity = activity;
        this.parkingID = parkingID;
        this.event = event;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... params) {
        HttpHandler httpHandler = new HttpHandler();
        try {
//            JSONObject jsonObj = new JSONObject(json);
            String json = httpHandler.get(Constants.API_URL + "vehicles/notifications/parkingid=" + parkingID +"&event=" + event);
            JSONObject jsonObj = new JSONObject(json);
            System.out.println("GetVehicleTask : " + jsonObj);
            TextView tvLicensePlate = (TextView) activity.findViewById(R.id.tvLP);
            TextView tvType = (TextView) activity.findViewById(R.id.tvType);
            TextView tvColor = (TextView) activity.findViewById(R.id.tvColor);
            JSONObject jsonVehicle = new JSONObject(jsonObj.getString("vehicle"));
            tvLicensePlate.setText(jsonVehicle.getString("licenseplate"));
            tvColor.setText(jsonObj.getString("color"));
            JSONObject jsonVehicleType = new JSONObject(jsonVehicle.getString("vehicletype"));
            tvType.setText(jsonVehicleType.getString("type"));
        } catch (Exception e) {
            Log.e("Exception", " get driverVehicle fail in GetVehicleTask " + e);
        }
        return false;
    }

}

