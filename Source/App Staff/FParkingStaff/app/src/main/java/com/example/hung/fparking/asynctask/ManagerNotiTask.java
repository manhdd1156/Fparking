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

public class ManagerNotiTask {
    public ManagerNotiTask(String type, IAsyncTaskHandler container) {

        if (type.equals("get")) {
            new GetNotiTask().execute((Void) null);
        } else if (type.equals("delete")) {
            new DeleteNotiTask(container).execute((Void) null);
        }

    }

}

class GetNotiTask extends AsyncTask<Void, Void, Boolean> {

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
            Log.e("Exception", "  ManagerNotiTask fail : " + e);
        }
        return false;
    }
}

class DeleteNotiTask extends AsyncTask<Void, Void, Boolean> {
    IAsyncTaskHandler container;

    public DeleteNotiTask(IAsyncTaskHandler container) {
        this.container = container;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... params) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            String json = httpHandler.get(Constants.API_URL + "notifications/check");
            JSONObject jsonObject = new JSONObject(json);
            System.out.println("");
        } catch (Exception e) {
            Log.e("Exception", "  ManagerNotiTask fail : " + e);
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        container.onPostExecute(aBoolean);
    }
}

