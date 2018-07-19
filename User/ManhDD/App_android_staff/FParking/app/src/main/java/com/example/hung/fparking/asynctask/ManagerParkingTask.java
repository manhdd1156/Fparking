package com.example.hung.fparking.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;


import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.BookingDTO;
import com.example.hung.fparking.dto.ParkingDTO;

import org.json.JSONObject;

/**
 * Created by klot on 3/7/2018.
 */

public class ManagerParkingTask {

    public ManagerParkingTask(String method,String text,IAsyncTaskHandler container, ListView lv) {

        if (method.equals("get")) {
            new GetParkingTask(Integer.parseInt(text)).execute((Void) null);
        }
//        else if (method.equals("create")) {
//            new CreateMenuTask(container, p).execute((Void) null);
//        } else if (method.equals("delete")) {
//            new DeleteMenuTask(p).execute((Void) null);
//        } else if (method.equals("updateGetForm")) {
//            new UpdateFormMenuTask(container, p).execute((Void) null);
//        } else if (method.equals("update")) {
//            new Update(container, p).execute((Void) null);
//        }

    }

}

class GetParkingTask extends AsyncTask<Void, Void, Boolean> {

    int id;

    public GetParkingTask(int parkingID) {
        this.id = parkingID;
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
            Session.currentParking.setLatitude(jsonObj.getString("latitude"));
            Session.currentParking.setLongitude(jsonObj.getString("longitude"));
            Session.currentParking.setStatus(jsonObj.getInt("status"));
            Session.currentParking.setTimeoc(jsonObj.getString("timeoc"));
            Session.currentParking.setTotalspace(jsonObj.getInt("totalspace"));
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
class UpdateParking extends AsyncTask<Void, Void, Boolean> {

    IAsyncTaskHandler container;
    ParkingDTO p;
    Activity activity;
    boolean success = false;

    public UpdateParking(IAsyncTaskHandler container, ParkingDTO p){
        this.container = container;
        this.activity = (Activity)container;
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
            String json = httpHandler.post(Constants.API_URL + "parkings/update/", formData.toString());
            JSONObject jsonObj = new JSONObject(json);
            Log.e(" Updatebooking : ", jsonObj.toString());
            success = true;
            TextView tv = activity.findViewById(R.id.apply_button);
            tv.setText(Session.currentParking.getCurrentspace() + "/" + Session.currentParking.getTotalspace());

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