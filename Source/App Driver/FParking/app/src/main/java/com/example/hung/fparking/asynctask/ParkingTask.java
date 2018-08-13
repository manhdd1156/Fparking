package com.example.hung.fparking.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.hung.fparking.dto.ParkingDTO;
import com.example.hung.fparking.entity.GetNearPlace;
import com.example.hung.fparking.config.Constants;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParkingTask {
    public ParkingTask(String type, double lat, double lng, String vehicleID, String action, IAsyncTaskHandler container) {
        if (type.equals("list")) {
            new GetParking(lat, lng, action, container).execute((Void) null);
        } else if (type.equals("order")) {
            new GetSortParking(lat, lng, vehicleID, action, container).execute((Void) null);
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

class GetSortParking extends AsyncTask<Void, Void, Boolean> {
    private double lat, lng;
    private IAsyncTaskHandler container;
    private ArrayList<ParkingDTO> parkingInfo;
    private String action, vehicleID;

    public GetSortParking(double lat, double lng, String vehicleID, String action, IAsyncTaskHandler container) {
        this.lat = lat;
        this.lng = lng;
        this.vehicleID = vehicleID;
        this.action = action;
        this.container = container;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        parkingInfo = new ArrayList<>();
        HttpHandler httpHandler = new HttpHandler();
        try {
            String json = httpHandler.get(Constants.API_URL + "parkings/sort?latitude=" + lat + "&longitude=" + lng + "&vehicleid=" + vehicleID);
            Log.e("toa do: ", Constants.API_URL + "parkings/sort?latitude=" + lat + "&longitude=" + lng + "&vehicleid=" + vehicleID);
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);
                int parkingID = c.getInt("id");
                String address = c.getString("address");
                int currentspace = c.getInt("currentspace");
                Double deposits = c.getDouble("deposits");
                String image = c.getString("image");
                double latitude = c.getDouble("latitude");
                double longitude = c.getDouble("longitude");
                String timeoc = c.getString("timeoc");
                int totalspace = c.getInt("totalspace");

                parkingInfo.add(new ParkingDTO(parkingID, address, currentspace, totalspace, deposits, image, latitude, longitude, timeoc));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        container.onPostExecute(parkingInfo, action);
    }
}