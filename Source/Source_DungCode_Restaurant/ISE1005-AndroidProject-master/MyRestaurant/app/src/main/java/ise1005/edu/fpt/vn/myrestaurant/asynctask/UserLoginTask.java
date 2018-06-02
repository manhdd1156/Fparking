package ise1005.edu.fpt.vn.myrestaurant.asynctask;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import ise1005.edu.fpt.vn.myrestaurant.config.Constants;
import ise1005.edu.fpt.vn.myrestaurant.config.Session;
import ise1005.edu.fpt.vn.myrestaurant.dto.UserDTO;

/**
 * Created by DungNA on 10/20/17.
 */

public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

    private final String mUsername;
    private final String mPassword;
    private final IAsyncTaskHandler container;

    public UserLoginTask(String email, String password, IAsyncTaskHandler container) {
        mUsername = email;
        mPassword = password;
        this.container = container;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... params) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            JSONObject formData = new JSONObject();
            formData.put("username", mUsername);
            formData.put("password", mPassword);
            String json = httpHandler.post(Constants.API_URL + "user/check/", formData.toString());
            JSONObject jsonObj = new JSONObject(json);
            if (jsonObj.getInt("size") > 0) {
                JSONArray resultList = jsonObj.getJSONArray("result");
                JSONObject result = resultList.getJSONObject(0);
                Session.currentUser = new UserDTO();
                Session.currentUser.setId(result.getInt("id"));
                Session.currentUser.setName(result.getString("name"));
                Session.currentUser.setUsername(result.getString("username"));
                Session.currentUser.setRole_id(result.getInt("role_id"));
                Session.currentUser.setStatus(result.getInt("status"));
                return true;
            }
        } catch (Exception e) {
            Log.e("Exception", "Login fail");
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

