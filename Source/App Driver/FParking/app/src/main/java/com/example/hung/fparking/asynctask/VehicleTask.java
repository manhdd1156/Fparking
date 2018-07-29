package com.example.hung.fparking.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.VehicleDTO;
import com.example.hung.fparking.entity.GetNearPlace;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class VehicleTask {

    public VehicleTask(String type, VehicleDTO vehicleDTO, String action, IAsyncTaskHandler container) {
        if (type.equals("select")) {
            new GetVehicle(action, container).execute((Void) null);
        } else if (type.equals("delete")) {
            new DeleteVehicle(vehicleDTO, action, container).execute((Void) null);
        }
    }

}

class GetVehicle extends AsyncTask<Void, Void, Boolean> {

    private String action;
    private ArrayList<VehicleDTO> vehicle;
    private IAsyncTaskHandler container;

    public GetVehicle(String action, IAsyncTaskHandler container) {
        this.action = action;
        this.container = container;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        vehicle = new ArrayList<>();
        HttpHandler httpHandler = new HttpHandler();
        try {
            String json = httpHandler.get(Constants.API_URL + "vehicles/drivers/" + Session.currentDriver.getId());
            Log.e("toa do: ", Constants.API_URL + "vehicles/drivers/" + Session.currentDriver.getId());
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);
                int driverVehicleID = c.getInt("id");
                int status = c.getInt("status");

                JSONObject vehicleObject = c.getJSONObject("vehicle");
                int vehicleID = vehicleObject.getInt("id");
                String licenseplate = vehicleObject.getString("licenseplate");
                String color = vehicleObject.getString("color");

                JSONObject vehicletype = vehicleObject.getJSONObject("vehicletype");
                int vehicleTypeID = vehicletype.getInt("id");
                String type = vehicletype.getString("type");

                vehicle.add(new VehicleDTO(driverVehicleID, status, vehicleID, licenseplate, vehicleTypeID, type, color));
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

class DeleteVehicle extends AsyncTask<Void, Void, Boolean> {
    private VehicleDTO v;
    private String action;
    private ArrayList<VehicleDTO> vehicle;
    private IAsyncTaskHandler container;

    public DeleteVehicle(VehicleDTO v, String action, IAsyncTaskHandler container) {
        this.v = v;
        this.action = action;
        this.container = container;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        vehicle = new ArrayList<>();
        HttpHandler httpHandler = new HttpHandler();
        try {
            JSONObject formData = new JSONObject();
            formData.put("driverid", Session.currentDriver.getId());
            formData.put("licenseplate", v.getLicenseplate());
            String result = httpHandler.requestMethod(Constants.API_URL + "vehicles", formData.toString(), "DELETE");
            Log.e("toa do: ", Constants.API_URL + "vehicles" + Session.currentDriver.getId() + v.getLicenseplate() + result);

            if(result.contains("ok")){
                String json = httpHandler.get(Constants.API_URL + "vehicles/drivers/" + Session.currentDriver.getId());
                Log.e("toa do: ", Constants.API_URL + "vehicles/drivers/" + Session.currentDriver.getId());
                JSONArray jsonArray = new JSONArray(json);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject c = jsonArray.getJSONObject(i);
                    int driverVehicleID = c.getInt("id");
                    int status = c.getInt("status");

                    JSONObject vehicleObject = c.getJSONObject("vehicle");
                    int vehicleID = vehicleObject.getInt("id");
                    String licenseplate = vehicleObject.getString("licenseplate");
                    String color = vehicleObject.getString("color");

                    JSONObject vehicletype = vehicleObject.getJSONObject("vehicletype");
                    int vehicleTypeID = vehicletype.getInt("id");
                    String type = vehicletype.getString("type");

                    vehicle.add(new VehicleDTO(driverVehicleID, status, vehicleID, licenseplate, vehicleTypeID, type, color));
                }
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

class AddVehicle extends AsyncTask<Void, Void, Boolean> {
    private VehicleDTO v;
    private String action;
    private ArrayList<VehicleDTO> vehicle;
    private IAsyncTaskHandler container;

    public AddVehicle(VehicleDTO v, String action, IAsyncTaskHandler container) {
        this.v = v;
        this.action = action;
        this.container = container;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        vehicle = new ArrayList<>();
        HttpHandler httpHandler = new HttpHandler();
        try {
            JSONObject formData = new JSONObject();
            formData.put("driverid", Session.currentDriver.getId());
            formData.put("licenseplate", v.getLicenseplate());
            formData.put("color", v.getColor());
            formData.put("type", v.getType());
            String result = httpHandler.requestMethod(Constants.API_URL + "vehicles", formData.toString(), "POST");
            Log.e("toa do: ", Constants.API_URL + "vehicles" + Session.currentDriver.getId() + v.getLicenseplate() + result);

            if(result.contains("ok")){
                String json = httpHandler.get(Constants.API_URL + "vehicles/drivers/" + Session.currentDriver.getId());
                Log.e("toa do: ", Constants.API_URL + "vehicles/drivers/" + Session.currentDriver.getId());
                JSONArray jsonArray = new JSONArray(json);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject c = jsonArray.getJSONObject(i);
                    int driverVehicleID = c.getInt("id");
                    int status = c.getInt("status");

                    JSONObject vehicleObject = c.getJSONObject("vehicle");
                    int vehicleID = vehicleObject.getInt("id");
                    String licenseplate = vehicleObject.getString("licenseplate");
                    String color = vehicleObject.getString("color");

                    JSONObject vehicletype = vehicleObject.getJSONObject("vehicletype");
                    int vehicleTypeID = vehicletype.getInt("id");
                    String type = vehicletype.getString("type");

                    vehicle.add(new VehicleDTO(driverVehicleID, status, vehicleID, licenseplate, vehicleTypeID, type, color));
                }
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
