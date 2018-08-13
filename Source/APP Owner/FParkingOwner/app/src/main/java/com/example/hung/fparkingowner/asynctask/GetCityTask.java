package com.example.hung.fparkingowner.asynctask;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.hung.fparkingowner.config.Constants;
import com.example.hung.fparkingowner.dto.CityDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class GetCityTask extends AsyncTask<Void, Void, Boolean> {

    private IAsyncTaskHandler container;
    private SharedPreferences spref;
    ArrayList<CityDTO> cityDTOS;

    public GetCityTask(IAsyncTaskHandler container) {
        this.container = container;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... params) {
        HttpHandler httpHandler = new HttpHandler();
        cityDTOS = new ArrayList<>();
        try {
            String json = httpHandler.get(Constants.API_URL + "parkings/citys");
            Log.e("City Json", json);
            JSONArray jsonarray = new JSONArray(json);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonObject = jsonarray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("name");
                cityDTOS.add(new CityDTO(id, name));
            }
            return true;
        } catch (Exception e) {
            Log.e("Exception", " get rating fail in GetRateTask" + e);
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        container.onPostExecute(cityDTOS);
    }
}

