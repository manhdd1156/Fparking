package com.example.hung.fparking.asynctask;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.DriverDTO;

import org.json.JSONObject;

public class DriverLoginTask extends AsyncTask<Void, Void, Boolean> {

    private final String mPhone;
    private final String mPassword;
    private final IAsyncTaskHandler container;

    public DriverLoginTask(String mPhone, String mPassword, IAsyncTaskHandler container) {
        this.mPhone = mPhone;
        this.mPassword = mPassword;
        this.container = container;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... params) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            JSONObject formData = new JSONObject();
            formData.put("phone", mPhone);
            formData.put("password", mPassword);
            String json = httpHandler.post(Constants.API_URL + "driver/login/", formData.toString());

            JSONObject jsonObj = new JSONObject(json);
//            if (jsonObj.getInt("size") > 0) {
//                JSONArray resultList = jsonObj.getJSONArray("result");
//                JSONObject result = json.getJSONObject();
            Session.currentDriver = new DriverDTO();
            Session.currentDriver.setId(jsonObj.getLong("id"));
            Session.currentDriver.setName(jsonObj.getString("name"));
            Session.currentDriver.setPhone(jsonObj.getString("phone"));
            Session.currentDriver.setPassword(jsonObj.getString("password"));
            Session.currentDriver.setStatus(jsonObj.getString("status"));
            return true;
//            }
        } catch (Exception e) {
            Log.e("Exception", "Login fail");
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        container.onPostExecute(aBoolean);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        container.onPostExecute(false);
    }
}
