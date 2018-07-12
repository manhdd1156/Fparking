package com.example.hung.fparking.asynctask;

import android.os.AsyncTask;

import com.example.hung.fparking.entity.GetNearPlace;
import com.example.hung.fparking.config.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParkingTask extends AsyncTask<Void, Void, Boolean> {
    double lat, lng;
    private IAsyncTaskHandler container;
    ArrayList<GetNearPlace> nearParkingList;
    String strJSON = null;

    public ParkingTask(double lat, double lng, IAsyncTaskHandler container) {
        this.lat = lat;
        this.lng = lng;
        this.container = container;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        nearParkingList = new ArrayList<>();
        HttpHandler httpHandler = new HttpHandler();
        try {
            String json = httpHandler.get(Constants.API_URL + "parkings?" + "latitude=" + lat + "&longitude=" + lng);

            JSONArray jsonObj = new JSONArray(json);

            for (int i = 0; i < jsonObj.length(); i++) {
                JSONObject c = jsonObj.getJSONObject(i);
                int id = c.getInt("id");
                double lat = c.getDouble("latitude");
                double lng = c.getDouble("longitude");
                String addressParking = c.getString("address");

                nearParkingList.add(new GetNearPlace(id, lat, lng, addressParking));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        container.onPostExecute(nearParkingList);
    }
}
