package com.example.hung.fparking.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.FineDTO;
import com.example.hung.fparking.dto.VehicleDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FineTask {

    public FineTask(String type, FineDTO fineDTO, String action, IAsyncTaskHandler container) {
        if (type.equals("select")) {
            new GetFineByDriverID(action, container).execute((Void) null);
        } else if (type.equals("delete")) {

        } else if (type.equals("type")) {

        } else if (type.equals("create")) {

        }
    }
}


class GetFineByDriverID extends AsyncTask<Void, Void, Boolean> {

    private String action;
    private ArrayList<FineDTO> fineDTOS;
    private IAsyncTaskHandler container;

    public GetFineByDriverID(String action, IAsyncTaskHandler container) {
        this.action = action;
        this.container = container;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        fineDTOS = new ArrayList<>();
        HttpHandler httpHandler = new HttpHandler();
        try {
            String json = httpHandler.get(Constants.API_URL + "drivers/" + Session.currentDriver.getId() + "/fines");
            Log.e("Lich su phat: ", Constants.API_URL + "drivers/" + Session.currentDriver.getId() + "/fines");
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);
                int fineID = c.getInt("id");
                String date = c.getString("date");
                int status = c.getInt("status");
                int type = c.getInt("type");
                double price = c.getInt("price");

                JSONObject driverVehicleObject = c.getJSONObject("drivervehicle");
                int driverVehicleID = driverVehicleObject.getInt("id");

                JSONObject vehicle = driverVehicleObject.getJSONObject("vehicle");
                int vehicleID = vehicle.getInt("id");
                String licenseplate = vehicle.getString("licenseplate");
                String color = vehicle.getString("color");

                JSONObject vehicletype = vehicle.getJSONObject("vehicletype");
                int vehicleTypeID = vehicletype.getInt("id");
                String vehicleType = vehicletype.getString("type");

                JSONObject parkingObject = c.getJSONObject("parking");
                int parkingID = parkingObject.getInt("id");
                String address = parkingObject.getString("address");

                fineDTOS.add(new FineDTO(fineID,date,status,type,price,driverVehicleID,vehicleID,licenseplate,color,vehicleTypeID,vehicleType,parkingID,address));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        container.onPostExecute(fineDTOS, action);
    }
}
