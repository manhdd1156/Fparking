package com.example.hung.fparking.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.hung.fparking.entity.GetNearPlace;
import com.example.hung.fparking.config.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParkingTask {
    public ParkingTask(String type, double lat, double lng, String action, IAsyncTaskHandler container) {
        if (type.equals("list")) {
            new GetParking(lat, lng, action, container).execute((Void) null);
        }else if (type.equals("order")) {
            new GetParking(lat, lng, action, container).execute((Void) null);
        }
    }
}

class GetParking extends AsyncTask<Void, Void, Boolean> {
    private double lat, lng;
    private IAsyncTaskHandler container;
    private ArrayList<GetNearPlace> nearParkingList;
    private String action;

    public GetParking(double lat, double lng, String action, IAsyncTaskHandler container) {
        this.lat = lat;
        this.lng = lng;
        this.action = action;
        this.container = container;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        nearParkingList = new ArrayList<>();
        HttpHandler httpHandler = new HttpHandler();
        try {
            String json = httpHandler.get(Constants.API_URL + "parkings?" + "latitude=" + lat + "&longitude=" + lng);
            Log.e("toa do: ", "" + Constants.API_URL + "parkings?" + "latitude=" + lat + "&longitude=" + lng);
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
        container.onPostExecute(nearParkingList, action);
    }
}