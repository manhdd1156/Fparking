package com.example.hung.fparking.asynctask;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import com.example.hung.fparking.config.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class GetCityTask extends AsyncTask<Void, Void, List> {

    private IAsyncTaskHandler container;
    private SharedPreferences spref;
    public GetCityTask(IAsyncTaskHandler container) {
        this.container = container;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected List doInBackground(Void... params) {
        HttpHandler httpHandler = new HttpHandler();
        List<String> listCity = new ArrayList<>();
        try {

            String json = httpHandler.get(Constants.API_URL + "parkings/citys");
            JSONArray jsonarray = new JSONArray(json);
        for(int i=0;i<jsonarray.length();i++) {
            JSONObject jsonObject = jsonarray.getJSONObject(i);
            listCity.add(jsonObject.getString("name"));
        }
        } catch (Exception e) {
            Log.e("Exception", " get rating fail in GetRateTask" + e);
        }
        return listCity;
    }

    @Override
    protected void onPostExecute(List list) {
        super.onPostExecute(list);
        container.onPostExecute(list);
    }
}

