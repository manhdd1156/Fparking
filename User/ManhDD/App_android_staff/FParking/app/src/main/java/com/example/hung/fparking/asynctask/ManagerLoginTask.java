package com.example.hung.fparking.asynctask;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.ListView;

import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.ParkingDTO;
import com.example.hung.fparking.dto.StaffDTO;

import org.json.JSONObject;

/**
 * Created by klot on 3/7/2018.
 */

public class ManagerLoginTask {

    public ManagerLoginTask(String type, String phone, String password, IAsyncTaskHandler container) {

        if (type.equals("first_time")) {
            new StaffLoginTask(phone, password, container).execute((Void) null);
        } else if (type.equals("second_time")) {
            new GetInfoTask(phone, container).execute((Void) null);
        }
    }

}

class StaffLoginTask extends AsyncTask<Void, Void, Boolean> {

    private final String mPhone;
    private final String mPassword;
    private final IAsyncTaskHandler container;

    public StaffLoginTask(String phone, String password, IAsyncTaskHandler container) {
        mPhone = phone;
        mPassword = password;
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
            String json = httpHandler.post(Constants.API_URL + "staffs/login/", formData.toString());
            JSONObject jsonObj = new JSONObject(json);
            Session.currentStaff = new StaffDTO();
            Session.currentParking = new ParkingDTO();
            Session.currentStaff.setId(jsonObj.getLong("id"));
            Session.currentStaff.setAddress(jsonObj.getString("address"));
            Session.currentStaff.setName(jsonObj.getString("name"));
            Session.currentStaff.setPhone(jsonObj.getString("phone"));
            Session.currentStaff.setPassword(jsonObj.getString("password"));
            JSONObject parking = jsonObj.getJSONObject("parking");
            Session.currentStaff.setParking_id(parking.getInt("id"));
            Session.currentParking.setId(parking.getInt("id"));
            Session.currentParking.setAddress(parking.getString("address"));
            Session.currentParking.setCurrentspace(parking.getInt("currentspace"));
            Session.currentParking.setDeposits(parking.getDouble("deposits"));
            Session.currentParking.setImage(parking.getString("image"));
            Session.currentParking.setLatitude(parking.getString("latitude"));
            Session.currentParking.setLongitude(parking.getString("longitude"));
            Session.currentParking.setStatus(parking.getInt("status"));
            Session.currentParking.setTimeoc(parking.getString("timeoc"));
            Session.currentParking.setTotalspace(parking.getInt("totalspace"));

            return true;
//            }
        } catch (Exception e) {
            Log.e("Exception", "Login fail : " + e);
        }
        return false;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        container.onPostExecute(success);
    }

    @Override
    protected void onCancelled() {
        container.onPostExecute(false);
    }
}

class GetInfoTask extends AsyncTask<Void, Void, Boolean> {

    private final String mPhone;
    private final IAsyncTaskHandler container;

    public GetInfoTask(String phone, IAsyncTaskHandler container) {
        mPhone = phone;
        this.container = container;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... params) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            String json = httpHandler.get(Constants.API_URL + "staffs/login?phone=" + mPhone);
            JSONObject jsonObj = new JSONObject(json);
            Session.currentStaff = new StaffDTO();
            Session.currentParking = new ParkingDTO();
            Session.currentStaff.setId(jsonObj.getLong("id"));
            Session.currentStaff.setAddress(jsonObj.getString("address"));
            Session.currentStaff.setName(jsonObj.getString("name"));
            Session.currentStaff.setPhone(jsonObj.getString("phone"));
            Session.currentStaff.setPassword(jsonObj.getString("password"));
            JSONObject parking = jsonObj.getJSONObject("parking");
            Session.currentStaff.setParking_id(parking.getInt("id"));
            Session.currentParking.setId(parking.getInt("id"));
            Session.currentParking.setAddress(parking.getString("address"));
            Session.currentParking.setCurrentspace(parking.getInt("currentspace"));
            Session.currentParking.setDeposits(parking.getDouble("deposits"));
            Session.currentParking.setImage(parking.getString("image"));
            Session.currentParking.setLatitude(parking.getString("latitude"));
            Session.currentParking.setLongitude(parking.getString("longitude"));
            Session.currentParking.setStatus(parking.getInt("status"));
            Session.currentParking.setTimeoc(parking.getString("timeoc"));
            Session.currentParking.setTotalspace(parking.getInt("totalspace"));

            return true;
        } catch (Exception e) {
            Log.e("Exception", "get login fail : " + e);
        }
        return false;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        container.onPostExecute(success);
    }

    @Override
    protected void onCancelled() {
        container.onPostExecute(false);
    }
}

