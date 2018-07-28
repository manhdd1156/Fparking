package com.example.hung.fparking.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.dto.VehicleDTO;
import com.example.hung.fparking.entity.GetNearPlace;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class VehicleTask extends AsyncTask<Void, Void, Boolean> {

    private String action;
    private long driverID;
    private ArrayList<VehicleDTO> vehicle;
    private IAsyncTaskHandler container;

    public VehicleTask(long driverID, String action, IAsyncTaskHandler container) {
        this.driverID = driverID;
        this.action = action;
        this.container = container;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        vehicle = new ArrayList<>();
        HttpHandler httpHandler = new HttpHandler();
        try {
            String json = httpHandler.get(Constants.API_URL + "vehicles/drivers/" + driverID);
            Log.e("toa do: ", Constants.API_URL + "vehicles/drivers/" + driverID);
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);
                int driverVehicleID = c.getInt("id");
                int status = c.getInt("status");

                JSONObject vehicleObject = c.getJSONObject("vehicle");
                int vehicleID = vehicleObject.getInt("id");
                String licenseplate = vehicleObject.getString("licenseplate");

                JSONObject vehicletype = vehicleObject.getJSONObject("vehicletype");
                int vehicleTypeID = vehicletype.getInt("id");
                String type = vehicletype.getString("type");

                vehicle.add(new VehicleDTO(driverVehicleID,status,vehicleID,licenseplate,vehicleTypeID,type));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        container.onPostExecute(vehicle, action);
    }
}
