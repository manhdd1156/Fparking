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
private int drivervehicleID;
    private SharedPreferences spref;
    public GetVehicleTask(int drivervehicleID, Activity activity) {
        this.activity = activity;
        this.drivervehicleID = drivervehicleID;
//        spref = activity.getSharedPreferences("info",0);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... params) {
        HttpHandler httpHandler = new HttpHandler();
        try {
//            JSONObject jsonObj = new JSONObject(json);
            String json = httpHandler.get(Constants.API_URL + "vehicles/drivervehicles/" + drivervehicleID);
            JSONObject jsonObj = new JSONObject(json);
            TextView tvLicensePlate = (TextView) activity.findViewById(R.id.tvLP);
            TextView tvType = (TextView) activity.findViewById(R.id.tvType);
            TextView tvColor = (TextView) activity.findViewById(R.id.tvColor);
            tvLicensePlate.setText(jsonObj.getString("licenseplate"));
            tvColor.setText(jsonObj.getString("color"));
            JSONObject jsonVehicleType = new JSONObject(jsonObj.getString("vehicletype"));
            tvType.setText(jsonVehicleType.getString("type"));
        } catch (Exception e) {
            Log.e("Exception", " get driverVehicle fail in GetVehicleTask " + e);
        }
        return false;
    }

}

