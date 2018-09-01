package com.example.hung.fparking.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.TypeDTO;
import com.example.hung.fparking.dto.VehicleDTO;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class VehicleTask {

    public VehicleTask(String type, VehicleDTO vehicleDTO, String action, IAsyncTaskHandler container) {
        if (type.equals("select")) {
            new GetVehicle(action, container).execute((Void) null);
        } else if (type.equals("update")) {
            new UpdateVehicle(vehicleDTO, action, container).execute((Void) null);
        } else if (type.equals("type")) {
            new GetVehicleType(action, container).execute((Void) null);
        } else if (type.equals("create")) {
            new AddVehicle(vehicleDTO, action, container).execute((Void) null);
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
            String json = httpHandler.get(Constants.API_URL + "vehicles/drivers/" + action);
            Log.e("toa do: ", Constants.API_URL + "vehicles/drivers/" + action);
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

                vehicle.add(new VehicleDTO((long) vehicleID, driverVehicleID, status, vehicleID, licenseplate, type, vehicleTypeID, color));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        container.onPostExecute(vehicle);
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
            formData.put("id", v.getId()); // lưu tạm biến bookingid vào id
            formData.put("driverid", action);
            formData.put("licenseplate", v.getLicenseplate());
            formData.put("color", v.getColor());
            formData.put("type", v.getType());
            String result = httpHandler.requestMethod(Constants.API_URL + "vehicles", formData.toString(), "POST");
            JSONObject jsonObject = new JSONObject(result);
//            Log.e("toa do: ", Constants.API_URL + "vehicles" + Session.currentDriver.getId() + v.getLicenseplate() + result);

//            if (result.contains("ok")) {
//                String json = httpHandler.get(Constants.API_URL + "vehicles/drivers/" + action);
////                Log.e("toa do: ", Constants.API_URL + "vehicles/drivers/" + action);
//                JSONArray jsonArray = new JSONArray(json);
//
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject c = jsonArray.getJSONObject(i);
//
//                    int driverVehicleID = c.getInt("id");
//                    int status = c.getInt("status");
//
//                    JSONObject vehicleObject = c.getJSONObject("vehicle");
//                    int vehicleID = vehicleObject.getInt("id");
//                    String licenseplate = vehicleObject.getString("licenseplate");
//                    String color = vehicleObject.getString("color");
//
//                    JSONObject vehicletype = vehicleObject.getJSONObject("vehicletype");
//                    int vehicleTypeID = vehicletype.getInt("id");
//                    String type = vehicletype.getString("type");
////                    (Long id, int driverVehicleID, int status, int vehicleID, String licenseplate, String type, int vehicleTypeID, String color) {
//                    vehicle.add(new VehicleDTO((long) vehicleID, driverVehicleID, status, vehicleID, licenseplate, type, vehicleTypeID, color));
//                }
                return true;
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        container.onPostExecute(aBoolean);
    }
}
class UpdateVehicle extends AsyncTask<Void, Void, Boolean> {
    private VehicleDTO v;
    private String action;
//    private ArrayList<VehicleDTO> vehicle;
    private IAsyncTaskHandler container;

    public UpdateVehicle(VehicleDTO v, String action, IAsyncTaskHandler container) {
        this.v = v;
        this.action = action;
        this.container = container;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
//        vehicle = new ArrayList<>();
        HttpHandler httpHandler = new HttpHandler();
        try {
            JSONObject formData = new JSONObject();
            formData.put("id", v.getDriverVehicleID());
            formData.put("driverid", v.getId()); // lưu tạm biến booking_id vào driverid
            formData.put("vehicleid", v.getVehicleID());
//            formData.put("color", v.getColor());
//            formData.put("type", v.getType());
            String result = httpHandler.requestMethod(Constants.API_URL + "vehicles", formData.toString(), "PUT");
            System.out.println(formData);
//            Log.e("toa do: ", Constants.API_URL + "vehicles" + Session.currentDriver.getId() + v.getLicenseplate() + result);
            JSONObject jsonObj = new JSONObject(result);

//            if (result.contains("ok")) {
//                String json = httpHandler.get(Constants.API_URL + "vehicles/drivers/" + action);
////                Log.e("toa do: ", Constants.API_URL + "vehicles/drivers/" + action);
//                JSONArray jsonArray = new JSONArray(json);
//
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject c = jsonArray.getJSONObject(i);
//
//                    int driverVehicleID = c.getInt("id");
//                    int status = c.getInt("status");
//
//                    JSONObject vehicleObject = c.getJSONObject("vehicle");
//                    int vehicleID = vehicleObject.getInt("id");
//                    String licenseplate = vehicleObject.getString("licenseplate");
//                    String color = vehicleObject.getString("color");
//
//                    JSONObject vehicletype = vehicleObject.getJSONObject("vehicletype");
//                    int vehicleTypeID = vehicletype.getInt("id");
//                    String type = vehicletype.getString("type");
////                    (Long id, int driverVehicleID, int status, int vehicleID, String licenseplate, String type, int vehicleTypeID, String color) {
//                    vehicle.add(new VehicleDTO((long) vehicleID, driverVehicleID, status, vehicleID, licenseplate, type, vehicleTypeID, color));
//                }
                return true;
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        container.onPostExecute(aBoolean);
    }
}
class GetVehicleType extends AsyncTask<Void, Void, Boolean> {

    private String action;
    private ArrayList<TypeDTO> typeDTOS;
    private IAsyncTaskHandler container;

    public GetVehicleType(String action, IAsyncTaskHandler container) {
        this.action = action;
        this.container = container;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        typeDTOS = new ArrayList<>();
        HttpHandler httpHandler = new HttpHandler();
        try {
            String json = httpHandler.get(Constants.API_URL + "vehicles/types/");
            Log.e("toa do: ", Constants.API_URL + "vehicles/types/");
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);
                int vehicleTypeID = c.getInt("id");
                String type = c.getString("type");

                typeDTOS.add(new TypeDTO(vehicleTypeID, type));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        container.onPostExecute(typeDTOS);
    }
}