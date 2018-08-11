package com.example.hung.fparking.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
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
import java.util.List;

/**
 * Created by klot on 3/7/2018.
 */

public class ManagerParkingTask {

    public ManagerParkingTask(String method,ParkingDTO parkingDTO,IAsyncTaskHandler container) {

        if (method.equals("getbyowner")) {
            new GetParkingTask(container).execute((Void) null);
        }else if(method.equals("update")) {
            new UpdateParkingTask(container,parkingDTO).execute((Void) null);
        }
    }

}

class GetParkingTask extends AsyncTask<Void, Void, List> {

    IAsyncTaskHandler container;
    private SharedPreferences spref;
    Activity activity;
    ArrayList<ParkingDTO> parkinglist;
    ProgressDialog pdLoading;
    public GetParkingTask(IAsyncTaskHandler container) {
        this.container = container;
        this.activity = (Activity)container;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pdLoading = new ProgressDialog(activity);
        //this method will be running on UI thread
        pdLoading.setMessage("\tĐợi xíu...");
        pdLoading.setCancelable(false);
        pdLoading.show();

    }

    @Override
    protected List doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            parkinglist = new ArrayList<>();
            String json = httpHandler.get(Constants.API_URL + "parkings/owners");
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject p = jsonArray.getJSONObject(i);

                int id = p.getInt("id");
                String address = p.getString("address");
                int currentspace= p.getInt("currentspace");
                Double deposits= p.getDouble("deposits");
                String image= p.getString("image");
                String latitude= p.getString("latitude");
                String longitude= p.getString("longitude");
                int status= p.getInt("status");
                String timeoc= p.getString("timeoc");
                int totalspace= p.getInt("totalspace");
                int city_id = p.getJSONObject("city").getInt("id");

                parkinglist.add(new ParkingDTO(id, address,currentspace,deposits, image, latitude, longitude, status, timeoc, totalspace,city_id));
            }
            return parkinglist;
        } catch (Exception ex) {
            Log.e("Error:", ex.getMessage());
        }
        return null;
    }
    @Override
    protected void onPostExecute(List list) {
        super.onPostExecute(list);
        pdLoading.dismiss();
        container.onPostExecute(list);
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
            formData.put("totalspace", p.getTotalspace());
            formData.put("timeoc", p.getTimeoc());

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