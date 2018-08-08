package com.example.hung.fparking.asynctask;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;


import com.example.hung.fparking.R;
import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.BookingDTO;
import com.example.hung.fparking.dto.OwnerDTO;
import com.example.hung.fparking.dto.ParkingDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by klot on 3/7/2018.
 */

public class ManagerParkingTask {

    public ManagerParkingTask(String method,ParkingDTO parkingDTO,IAsyncTaskHandler container) {

        if (method.equals("getbyowner")) {
//            new GetParkingTask(OwnerDTO.getId(),container).execute((Void) null);
        }else if(method.equals("update")) {
            new UpdateParkingTask(container,parkingDTO).execute((Void) null);
        }
    }

}

class GetParkingTask extends AsyncTask<Void, Void, Boolean> {

    int id;
    IAsyncTaskHandler container;
    private SharedPreferences spref;
    Activity activity;
    ArrayList<ParkingDTO> parkinglist;
    public GetParkingTask(int ownerID,IAsyncTaskHandler container) {
        this.id = ownerID;
        this.container = container;
        this.activity = (Activity)container;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            parkinglist = new ArrayList<>();
            String json = httpHandler.get(Constants.API_URL + "parkings/owners" + id );
//            JSONArray parkings = jsonObj.getJSONArray("parkingInfor");
//            oneParking = jsonObj;


            JSONArray jsonArray = new JSONArray(json);

//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject c = jsonArray.getJSONObject(i);
//
//                int id = c.getInt("id");
//
//                Session.currentParking.setAddress(jsonObj.getString("address"));
//                Session.currentParking.setCurrentspace(jsonObj.getInt("currentspace"));
//                Session.currentParking.setDeposits(jsonObj.getDouble("deposits"));
//                Session.currentParking.setImage(jsonObj.getString("image"));
//                Session.currentParking.setLatitude(jsonObj.getString("latitude"));
//                Session.currentParking.setLongitude(jsonObj.getString("longitude"));
//                Session.currentParking.setStatus(jsonObj.getInt("status"));
//                Session.currentParking.setTimeoc(jsonObj.getString("timeoc"));
//                Session.currentParking.setTotalspace(jsonObj.getInt("totalspace"));
//
//                booking.add(new BookingDTO(bookingID, vehicleID, parkingID, address, timein, timeout, price, status, licenseplate, amount, comission, totalfine, type, color));
//            }

        } catch (Exception ex) {
            Log.e("Error:", ex.getMessage());
        }
        return null;
    }
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }


}
class UpdateParkingTask extends AsyncTask<Void, Void, Boolean> {

    IAsyncTaskHandler container;
    ParkingDTO p;
    Activity activity;
    boolean success = false;
    private SharedPreferences spref;
    public UpdateParkingTask(IAsyncTaskHandler container, ParkingDTO p){
        this.container = container;
        this.activity = (Activity)container;
        this.p = p;
//        spref = activity.getSharedPreferences("info",0);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try{
            Log.e("Update-Async", p.toString());
            JSONObject formData = new JSONObject();
            formData.put("id", p.getId());
            formData.put("currentspace", p.getCurrentspace());
            String json = httpHandler.requestMethod(Constants.API_URL + "parkings/update/", formData.toString(),"POST");
            JSONObject jsonObj = new JSONObject(json);
            Log.e(" Updateparking : ", jsonObj.toString());
            success = true;
//            TextView tv = activity.findViewById(R.id.tvSpace);
//            tv.setText(Session.currentParking.getCurrentspace() + "/" + Session.currentParking.getTotalspace());

        }catch (Exception ex){
            Log.e("Error:", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
//        Intent intent = new Intent();
//        if(success)
//            intent.putExtra("result", "success!");
//        else
//            intent.putExtra("result", "failed");
//        this.activity.setResult(RESULT_OK, intent);
//        this.activity.finish();
    }

}