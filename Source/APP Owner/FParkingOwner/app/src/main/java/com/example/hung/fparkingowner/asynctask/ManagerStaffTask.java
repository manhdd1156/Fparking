package com.example.hung.fparkingowner.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.hung.fparkingowner.config.Constants;
import com.example.hung.fparkingowner.dto.ParkingDTO;
import com.example.hung.fparkingowner.dto.StaffDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by klot on 3/7/2018.
 */

public class ManagerStaffTask {

    public ManagerStaffTask(String method, StaffDTO staffDTO, IAsyncTaskHandler container) {

        if (method.equals("getbyowner")) {
            new GetStaffTask(container).execute((Void) null);
        }else if(method.equals("create")) {
            new CreateStaffTask(container,staffDTO).execute((Void) null);
        }else if(method.equals("update")) {
            new UpdateStaffTask(container,staffDTO).execute((Void) null);
        }else if(method.equals("delete")) {
            new DeleteStaffTask(container,staffDTO).execute((Void) null);
        }
    }

}

class GetStaffTask extends AsyncTask<Void, Void, List> {

    IAsyncTaskHandler container;
    private SharedPreferences spref;
    Activity activity;
    ArrayList<StaffDTO> stafflist;
    ProgressDialog pdLoading;
    public GetStaffTask(IAsyncTaskHandler container) {
        this.container = container;
        this.activity = (Activity)container;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pdLoading = new ProgressDialog(activity);
        //this method will be running on UI thread
        pdLoading.setMessage("\tĐang xử lý...");
        pdLoading.setCancelable(false);
        pdLoading.show();

    }

    @Override
    protected List doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            stafflist = new ArrayList<>();
            String json = httpHandler.get(Constants.API_URL + "staffs/owners");
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject s = jsonArray.getJSONObject(i);

                int id = s.getInt("id");
                String address = s.getString("address");
                String name = s.getString("name");
                String phone = s.getString("phone");
                String password = s.getString("password");
                int parking_id = s.getJSONObject("parking").getInt("id");
                String parking_address = s.getJSONObject("parking").getString("address");
                stafflist.add(new StaffDTO(id,parking_id, name,phone,address,parking_address,password));
            }
            return stafflist;
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
class CreateStaffTask extends AsyncTask<Void, Void, Boolean> {

    IAsyncTaskHandler container;
    StaffDTO s;
    public CreateStaffTask(IAsyncTaskHandler container, StaffDTO s){
        this.container = container;
        this.s = s;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try{
            Log.e("Update-Async", s.toString());
            JSONObject formData = new JSONObject();
            formData.put("name", s.getName());
            formData.put("phone", s.getPhone());
            formData.put("address", s.getAddress());
            formData.put("password",s.getPass());
            formData.put("parking",new JSONObject().put("id",s.getParking_id()));

            String json = httpHandler.requestMethod(Constants.API_URL + "staffs", formData.toString(),"POST");
            JSONObject jsonObj = new JSONObject(json);
            Log.e(" Update staff : ", jsonObj.toString());
            return true;

        }catch (Exception ex){
            Log.e("Error:", ex.getMessage());
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        container.onPostExecute(aBoolean);
    }

}
class UpdateStaffTask extends AsyncTask<Void, Void, Boolean> {

    IAsyncTaskHandler container;
    StaffDTO s;
    public UpdateStaffTask(IAsyncTaskHandler container, StaffDTO s){
        this.container = container;
        this.s = s;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try{
            Log.e("Update-Async", s.toString());
            JSONObject formData = new JSONObject();
            formData.put("id", s.getId());
            formData.put("name", s.getName());
            formData.put("phone", s.getPhone());
            formData.put("address", s.getAddress());
            formData.put("password",s.getPass());
            formData.put("parking",new JSONObject().put("id",s.getParking_id()));

            String json = httpHandler.requestMethod(Constants.API_URL + "staffs/update", formData.toString(),"PUT");
            JSONObject jsonObj = new JSONObject(json);
            Log.e(" Update staff : ", jsonObj.toString());
            return true;

        }catch (Exception ex){
            Log.e("Error:", ex.getMessage());
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        container.onPostExecute(aBoolean);
    }

}
class DeleteStaffTask extends AsyncTask<Void, Void, Boolean> {

    IAsyncTaskHandler container;
    StaffDTO s;
    public DeleteStaffTask(IAsyncTaskHandler container, StaffDTO s){
        this.container = container;
        this.s = s;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try{
            Log.e("Update-Async", s.toString());
            JSONObject formData = new JSONObject();
            formData.put("id", s.getId());

            String json = httpHandler.requestMethod(Constants.API_URL + "staffs", formData.toString(),"DELETE");
            JSONObject jsonObj = new JSONObject(json);
            Log.e(" Delete staff : ", jsonObj.toString());
            return true;

        }catch (Exception ex){
            Log.e("Error:", ex.getMessage());
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        container.onPostExecute(aBoolean);
    }

}