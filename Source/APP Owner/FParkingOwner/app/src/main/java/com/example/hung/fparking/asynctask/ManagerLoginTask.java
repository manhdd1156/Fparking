package com.example.hung.fparking.asynctask;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.OwnerDTO;
import com.example.hung.fparking.dto.ParkingDTO;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by klot on 3/7/2018.
 */

public class ManagerLoginTask {

    public ManagerLoginTask(String type, String phone, String password, IAsyncTaskHandler container) {

        if (type.equals("first_time")) {
            new StaffLoginTask(phone, password, container).execute((Void) null);
        } else if (type.equals("second_time")) {
            new GetProfileTask(container).execute((Void) null);
        }else if(type.equals("updateProfile")) {
            new  UpdateProfileTask(container).execute((Void) null);
        }
    }

}

class StaffLoginTask extends AsyncTask<Void, Void, Boolean> {
    ProgressDialog pdLoading;
    private final String mPhone;
    private final String mPassword;
    private final IAsyncTaskHandler container;
    private SharedPreferences.Editor editor;
    public StaffLoginTask(String phone, String password, IAsyncTaskHandler container) {
        mPhone = phone;
        mPassword = password;
        this.container = container;
        editor = Session.spref.edit();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pdLoading = new ProgressDialog(Session.homeActivity);
        }
        //this method will be running on UI thread
        pdLoading.setMessage("\tĐang đăng nhập...");
        pdLoading.setCancelable(false);
        pdLoading.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... params) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            JSONObject formData = new JSONObject();
            String phone =mPhone;

//            System.out.println("phone = " + phone);
            formData.put("username", mPhone);
            // convert pass to md5
            String passMD5 = getMD5Hex(mPassword);
            formData.put("password", passMD5);
            formData.put("type", "STAFF");
            String jsonLogin = httpHandler.requestMethod(Constants.API_URL + "login", formData.toString(),"POST");
            JSONObject jsonObj = new JSONObject(jsonLogin);
            if(!jsonObj.getString("token").isEmpty()) {

            String token = jsonObj.getString("token");
                editor.putString("token",token);
                editor.commit();

            String jsonGetInfo = httpHandler.get(Constants.API_URL + "staffs/profile");
                JSONObject jsonObj2 = new JSONObject(jsonGetInfo);
            Session.currentStaff = new OwnerDTO();
            Session.currentParking = new ParkingDTO();
            Session.currentStaff.setId(jsonObj2.getLong("id"));
            Session.currentStaff.setAddress(jsonObj2.getString("address"));
            Session.currentStaff.setName(jsonObj2.getString("name"));
            Session.currentStaff.setPhone(jsonObj2.getString("phone"));
            Session.currentStaff.setPass(jsonObj2.getString("password"));
            JSONObject parking = jsonObj2.getJSONObject("parking");
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
            }

        } catch (Exception e) {
            Log.e("Exception", "Login fail : " + e);
        }
        return false;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        container.onPostExecute(success);
        pdLoading.dismiss();
    }

    @Override
    protected void onCancelled() {
        container.onPostExecute(false);
    }

    public static String getMD5Hex(final String inputString) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(inputString.getBytes());

        byte[] digest = md.digest();

        return convertByteToHex(digest);
    }

    private static String convertByteToHex(byte[] byteData) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}

class GetProfileTask extends AsyncTask<Void, Void, Boolean> {

    private final IAsyncTaskHandler container;

    public GetProfileTask(IAsyncTaskHandler container) {
        this.container = container;

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... params) {
        HttpHandler httpHandler = new HttpHandler();
        try {

            String json = httpHandler.get(Constants.API_URL + "staffs/profile");

            JSONObject jsonObj = new JSONObject(json);
            System.out.println(jsonObj);
            Session.currentStaff = new OwnerDTO();
            Session.currentParking = new ParkingDTO();
            Session.currentStaff.setId(jsonObj.getLong("id"));
            Session.currentStaff.setAddress(jsonObj.getString("address"));
            Session.currentStaff.setName(jsonObj.getString("name"));
            Session.currentStaff.setPhone(jsonObj.getString("phone"));
            Session.currentStaff.setPass(jsonObj.getString("password"));
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
            System.out.println("get login fail : " + e);
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

class UpdateProfileTask extends AsyncTask<Void, Void, Boolean> {

    private final IAsyncTaskHandler container;

    public UpdateProfileTask(IAsyncTaskHandler container) {
        this.container = container;

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... params) {
        HttpHandler httpHandler = new HttpHandler();
        try {

            JSONObject formData = new JSONObject();
            formData.put("id", Session.currentStaff.getId());
            formData.put("name", Session.currentStaff.getName());
            formData.put("phone", Session.currentStaff.getPhone());
            formData.put("address", Session.currentStaff.getAddress());


            String jsonUpdate = httpHandler.requestMethod(Constants.API_URL + "staffs/update", formData.toString(),"PUT");
            JSONObject jsonObj = new JSONObject(jsonUpdate);
            if(jsonObj!=null) {
                return true;
            }
        } catch (Exception e) {
            Log.e("Exception", "update profile : " + e);
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

