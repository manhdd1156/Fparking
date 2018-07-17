package com.example.hung.fparking.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.dto.ParkingDTO;
import com.example.hung.fparking.dto.VehicleDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParkingInforTask extends AsyncTask<Void, Void, Boolean> {

    private String parkingID, action;
    private ArrayList<ParkingDTO> parkingInfo;
    private IAsyncTaskHandler container;

    public ParkingInforTask(String parkingID, String action, IAsyncTaskHandler container) {
        this.parkingID = parkingID;
        this.action = action;
        this.container = container;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        parkingInfo = new ArrayList<>();
        HttpHandler httpHandler = new HttpHandler();
        try {
            String json = httpHandler.get(Constants.API_URL + "parkings/" + parkingID );
            Log.e("toa do: ", Constants.API_URL + "parkings/" + parkingID);
            JSONObject jsonObject = new JSONObject(json);

            int parkingID = jsonObject.getInt("id");
            String address = jsonObject.getString("address");
            int currentspace = jsonObject.getInt("currentspace");
            Double deposits = jsonObject.getDouble("deposits");
            String image = jsonObject.getString("image");
            double latitude = jsonObject.getDouble("latitude");
            double longitude = jsonObject.getDouble("longitude");
            String timeoc = jsonObject.getString("timeoc");
            int totalspace = jsonObject.getInt("totalspace");

            parkingInfo.add(new ParkingDTO(parkingID,address,currentspace,totalspace,deposits,image,latitude,longitude,timeoc));

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
