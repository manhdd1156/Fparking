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

        if (method.equals("get")) {
            new GetParkingTask(parkingDTO.getId(),container).execute((Void) null);
        }else if(method.equals("update")) {
            new UpdateParkingTask(container,parkingDTO).execute((Void) null);
        }else if(method.equals("getFines")) {
            new GetFinesTask(container,parkingDTO).execute((Void) null);
        }
    }

}

class GetParkingTask extends AsyncTask<Void, Void, Boolean> {

    int id;
    IAsyncTaskHandler container;
    private SharedPreferences spref;
    Activity activity;
    public GetParkingTask(int parkingID,IAsyncTaskHandler container) {
        this.id = parkingID;
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
            String json = httpHandler.get(Constants.API_URL + "parkings/" + id );
            JSONObject jsonObj = new JSONObject(json);
//            JSONArray parkings = jsonObj.getJSONArray("parkingInfor");
//            oneParking = jsonObj;
            Session.currentParking.setId(jsonObj.getInt("id"));
            Session.currentParking.setAddress(jsonObj.getString("address"));
            Session.currentParking.setCurrentspace(jsonObj.getInt("currentspace"));
            Session.currentParking.setDeposits(jsonObj.getDouble("deposits"));
            Session.currentParking.setImage(jsonObj.getString("image"));
            Session.currentParking.setCity_id(jsonObj.getJSONObject("city").getInt("id"));
            Session.currentParking.setLatitude(jsonObj.getString("latitude"));
            Session.currentParking.setLongitude(jsonObj.getString("longitude"));
            Session.currentParking.setStatus(jsonObj.getInt("status"));
            Session.currentParking.setTimeoc(jsonObj.getString("timeoc"));
            Session.currentParking.setTotalspace(jsonObj.getInt("totalspace"));
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    try {
                        TextView tv = activity.findViewById(R.id.tvSpace);
                        tv.setText(Session.currentParking.getCurrentspace() + "/" + Session.currentParking.getTotalspace());
                        System.out.println("textview currentspace  = "  + tv.getText());
                    } catch (Exception e) {
                        System.out.println("lỗi getvehicletask : " + e);
                    }
                }
            });

        } catch (Exception ex) {
            Log.e("Get Parking Error:", ex.getMessage());
        }finally {

        }
        return null;
    }
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }


}
class GetFinesTask extends AsyncTask<Void, Void, String> {

    int id;
    IAsyncTaskHandler container;
    String time;
    ParkingDTO parkingDTO;
    public GetFinesTask(IAsyncTaskHandler container,ParkingDTO parkingDTO) {
        this.time = time;
        this.parkingDTO = parkingDTO;
        this.container = container;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        String json="";
        try {
            json = httpHandler.get(Constants.API_URL + "parkings/time?parkingid=" + parkingDTO.getId()
                    + "&fromtime=" + parkingDTO.getTimeoc() + "&totime=" + parkingDTO.getAddress()
                    +"&method=" + parkingDTO.getStatus()); // lưu tạm biến fromTime là timein, biến address là ToTime, biến status là loại get full hay get theo date


            System.out.println(Constants.API_URL + "parkings/time?parkingid=" + parkingDTO.getId()
                    + "&fromtime=" + parkingDTO.getTimeoc() + "&totime=" + parkingDTO.getAddress()
                    +"&method=" + parkingDTO.getStatus());

        } catch (Exception ex) {
            Log.e("Error:", ex.getMessage());
        }finally {

        }
        return json;
    }
    @Override
    protected void onPostExecute(String returnString) {
        super.onPostExecute(returnString);
        container.onPostExecute(returnString);
    }


}
class UpdateParkingTask extends AsyncTask<Void, Void, Boolean> {

    IAsyncTaskHandler container;
    ParkingDTO p;
    private SharedPreferences spref;
    public UpdateParkingTask(IAsyncTaskHandler container, ParkingDTO p){
        this.container = container;
        this.p = p;
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
            if(json.isEmpty() || json.equals("")) {
                return false;
            }

            JSONObject jsonObj = new JSONObject(json);
            Log.e(" Updateparking : ", jsonObj.toString());


        }catch (Exception ex){
            Log.e("Error:", ex.getMessage());
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        container.onPostExecute(aBoolean);
    }

}