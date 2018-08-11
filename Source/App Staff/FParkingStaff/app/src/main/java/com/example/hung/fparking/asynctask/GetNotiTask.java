package com.example.hung.fparking.asynctask;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.RatingBar;

import com.example.hung.fparking.R;
import com.example.hung.fparking.config.Constants;

import org.json.JSONObject;


public class GetNotiTask extends AsyncTask<Void, Void, Boolean> {

    public GetNotiTask() {
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... params) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            String json = httpHandler.get(Constants.API_URL + "notifications/check");
            JSONObject jsonObject = new JSONObject(json);

        } catch (Exception e) {
            Log.e("Exception", "  GetNotiTask fail : " + e);
        }
        return false;
    }

}

