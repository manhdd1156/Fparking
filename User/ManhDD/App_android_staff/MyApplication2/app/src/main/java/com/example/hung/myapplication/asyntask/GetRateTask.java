package com.example.hung.myapplication.asyntask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.RatingBar;

import com.example.hung.myapplication.R;
import com.example.hung.myapplication.config.Constants;
import com.example.hung.myapplication.config.Session;
import com.example.hung.myapplication.dto.StaffDTO;

import org.json.JSONObject;


public class GetRateTask extends AsyncTask<Void, Void, Boolean> {

    private Activity activity;

    public GetRateTask(Activity activity) {
        this.activity = activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... params) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            String json = httpHandler.get(Constants.API_URL + "parkings/" + Session.currentStaff.getParking_id() + "rates");
//            String json = httpHandler.get(Constants.API_URL + "staffs/");
            System.out.println(json);
//            JSONObject jsonObj = new JSONObject(json);
////            if (jsonObj.getInt("size") > 0) {
////                JSONArray resultList = jsonObj.getJSONArray("result");
////                JSONObject result = json.getJSONObject();
//                Session.currentStaff = new StaffDTO();
//                Session.currentStaff.setId(jsonObj.getLong("id"));
//                Session.currentStaff.setAddress(jsonObj.getString("address"));
//                Session.currentStaff.setName(jsonObj.getString("name"));
//                Session.currentStaff.setPhone(jsonObj.getString("phone"));
//                Session.currentStaff.setPassword(jsonObj.getString("password"));
//                JSONObject parking = jsonObj.getJSONObject("parking");
//                Session.currentStaff.setParking_id(parking.getInt("id"));
//                return true;
            RatingBar ratingbar = (RatingBar) activity.findViewById(R.id.ratingBar2);
            ratingbar.setRating(Float.parseFloat(json));
//            }
        } catch (Exception e) {
            Log.e("Exception", "Login fail");
        }
        return false;
    }

}

