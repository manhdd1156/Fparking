package com.example.hung.fparkingowner.asynctask;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.RatingBar;

import com.example.hung.fparking.R;
import com.example.hung.fparkingowner.config.Constants;
import com.example.hung.fparkingowner.config.Session;

import com.example.hung.fparkingowner.config.Constants;
import com.example.hung.fparkingowner.config.Session;


public class GetRateTask extends AsyncTask<Void, Void, Boolean> {

    private Activity activity;
private int parkingid;
    private SharedPreferences spref;
    public GetRateTask(int parkingid, Activity activity) {
        this.activity = activity;
        this.parkingid = parkingid;
//        spref = activity.getSharedPreferences("info",0);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... params) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            String json = httpHandler.get(Constants.API_URL + "parkings/" + parkingid + "/rates");
//            System.out.println(json);
            if(json.contains("NaN")) {
                json = "3";
            }
            RatingBar ratingbar = (RatingBar) activity.findViewById(R.id.ratingBar2);
            ratingbar.setRating(Float.parseFloat(json));
        } catch (Exception e) {
            Log.e("Exception", " get rating fail in GetRateTask" + e);
        }
        return false;
    }

}

