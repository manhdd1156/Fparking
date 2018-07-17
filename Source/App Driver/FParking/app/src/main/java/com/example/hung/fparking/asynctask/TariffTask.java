package com.example.hung.fparking.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.dto.TariffDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TariffTask extends AsyncTask<Void, Void, Boolean> {

    private String parkingID, action;
    private ArrayList<TariffDTO> tariff;
    private IAsyncTaskHandler container;

    public TariffTask(String parkingID, String action, IAsyncTaskHandler container) {
        this.parkingID = parkingID;
        this.action = action;
        this.container = container;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        tariff = new ArrayList<>();
        HttpHandler httpHandler = new HttpHandler();
        try {
            String json = httpHandler.get(Constants.API_URL + "parkings/" + parkingID+"/tariffs" );
            Log.e("toa do: ", Constants.API_URL + "parkings/" + parkingID+"/tariffs");
            JSONObject jsonObject = new JSONObject(json);

            JSONArray tariffList = jsonObject.getJSONArray("tariffList");
            for (int i = 0; i < tariffList.length(); i++) {
                JSONObject tariffobj = tariffList.getJSONObject(i);
                int tariffID = tariffobj.getInt("id");
                Double price = tariffobj.getDouble("price");

                JSONObject vehicletype = tariffobj.getJSONObject("vehicletype");
                int vehicleTypeID = vehicletype.getInt("id");
                String type = vehicletype.getString("type");

                tariff.add(new TariffDTO(tariffID,Integer.parseInt(parkingID),vehicleTypeID,price));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        container.onPostExecute(tariff,action);
    }
}
