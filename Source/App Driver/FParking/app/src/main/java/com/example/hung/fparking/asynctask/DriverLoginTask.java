package com.example.hung.fparking.asynctask;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.DriverDTO;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DriverLoginTask {

    public DriverLoginTask(String type, String phone, String password, IAsyncTaskHandler container) {
        if (type.equals("first_time")) {
            new LoginTask(phone, password, container).execute((Void) null);
        } else if (type.equals("second_time")) {
            new GetProfileTask(container).execute((Void) null);
        }
    }
}

class LoginTask extends AsyncTask<Void, Void, Boolean> {

    private final String mPhone;
    private final String mPassword;
    private final IAsyncTaskHandler container;
    private SharedPreferences.Editor editor;
    private String action;

    public LoginTask(String mPhone, String mPassword, IAsyncTaskHandler container) {
        this.mPhone = mPhone;
        this.mPassword = mPassword;
        this.container = container;
        editor = Session.spref.edit();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... params) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            JSONObject formData = new JSONObject();
            formData.put("username", mPhone);
            String passMD5 = getMD5Hex(mPassword);
            formData.put("password", passMD5);
            formData.put("type", "DRIVER");
            String json = httpHandler.requestMethod(Constants.API_URL + "login/", formData.toString(), "POST");
            // Check Json token
            JSONObject jsonObj = new JSONObject(json);
            if (!jsonObj.getString("token").isEmpty()) {

                String token = jsonObj.getString("token");
                editor.putString("token", token);
                editor.putBoolean("first_time", false);
                editor.commit();
                // Get profile by token
                String jsonGetInfo = httpHandler.get(Constants.API_URL + "drivers/profile");
                JSONObject jsonObj2 = new JSONObject(jsonGetInfo);
                Session.currentDriver = new DriverDTO();
                Session.currentDriver.setId(jsonObj2.getLong("id"));
                Session.currentDriver.setName(jsonObj2.getString("name"));
                Session.currentDriver.setPhone(jsonObj2.getString("phone"));
                Session.currentDriver.setStatus(jsonObj2.getString("status"));
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        container.onPostExecute(aBoolean, action);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        container.onPostExecute(false, action);
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
    private String action;

    public GetProfileTask(IAsyncTaskHandler container) {
        this.container = container;

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... params) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            // Get profile by token
            String jsonGetInfo = httpHandler.get(Constants.API_URL + "drivers/profile");
            JSONObject jsonObj2 = new JSONObject(jsonGetInfo);
            Session.currentDriver = new DriverDTO();
            Session.currentDriver.setId(jsonObj2.getLong("id"));
            Session.currentDriver.setName(jsonObj2.getString("name"));
            Session.currentDriver.setPhone(jsonObj2.getString("phone"));
            Session.currentDriver.setStatus(jsonObj2.getString("status"));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        container.onPostExecute(aBoolean, action);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        container.onPostExecute(false, action);
    }

}
