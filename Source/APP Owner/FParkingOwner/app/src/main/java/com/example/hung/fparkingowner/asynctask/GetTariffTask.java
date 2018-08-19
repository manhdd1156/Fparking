package com.example.hung.fparkingowner.asynctask;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.hung.fparkingowner.R;
import com.example.hung.fparkingowner.config.Constants;
import com.example.hung.fparkingowner.dto.ParkingDTO;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParsePosition;


public class GetTariffTask extends AsyncTask<Void, Void, ParkingDTO> {

private int parkingID;
ParkingDTO parkingDTO;
IAsyncTaskHandler container;
    public GetTariffTask(int parkingID,IAsyncTaskHandler container) {
        this.parkingID = parkingID;
        this.container = container;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected ParkingDTO doInBackground(Void... params) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            String json = httpHandler.get(Constants.API_URL + "parkings/" + parkingID + "/tariffs");
            final JSONObject jsonObj = new JSONObject(json);
            System.out.println("GetTariffTask : " + jsonObj);
//            jsonObj.getJSONObject("city").getInt("id");
            int id = jsonObj.getJSONObject("parking").getInt("id");
            String address = jsonObj.getJSONObject("parking").getString("address");
            int currentspace = jsonObj.getJSONObject("parking").getInt("currentspace");
            Double deposits = jsonObj.getJSONObject("parking").getDouble("deposits");
            String image = jsonObj.getJSONObject("parking").getString("image");
            String latitude = jsonObj.getJSONObject("parking").getString("latitude");
            String longitude = jsonObj.getJSONObject("parking").getString("longitude");
            int status = jsonObj.getJSONObject("parking").getInt("status");

            String timeoc = jsonObj.getJSONObject("parking").getString("timeoc");
            int totalspace = jsonObj.getJSONObject("parking").getInt("totalspace");
            int city_id = jsonObj.getJSONObject("parking").getJSONObject("city").getInt("id");


            JSONArray jsonArray =jsonObj.getJSONArray("tariffList");
            double price9 = jsonArray.getJSONObject(0).getDouble("price");
            double price16 = jsonArray.getJSONObject(1).getDouble("price");
            double price29 = jsonArray.getJSONObject(2).getDouble("price");
               parkingDTO = new ParkingDTO(id, address, currentspace, deposits, image, latitude, longitude, status, timeoc, totalspace, city_id, price9, price16, price29);

        } catch (Exception e) {
            System.out.println(" Error GetTariffTask : " + e);
            Log.e("Exception", " get fail in GetTariffTask " + e);
        }
        return parkingDTO;
    }

    @Override
    protected void onPostExecute(ParkingDTO parkingDTO) {
        super.onPostExecute(parkingDTO);
        container.onPostExecute(parkingDTO);
    }
}

