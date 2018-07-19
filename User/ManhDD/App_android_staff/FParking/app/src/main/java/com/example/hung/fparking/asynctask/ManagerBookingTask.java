package com.example.hung.fparking.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.ListView;

import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.BookingDTO;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by klot on 3/7/2018.
 */

public class ManagerBookingTask {

    public ManagerBookingTask(String method, BookingDTO bookingDTO, IAsyncTaskHandler container) {

        if (method.equals("get")) {
            new GetBookingTask(bookingDTO.getParkingID(), container).execute((Void) null);
        }else if(method.equals("update")) {
         new UpdateBooking(container,bookingDTO).execute((Void) null);
        }

    }

}


class GetBookingTask extends AsyncTask<Void, Void, List> {

    private final int parkingID;
    private JSONObject oneBooking;
    private Activity activity;
    private IAsyncTaskHandler container;
    ProgressDialog pdLoading;
    public GetBookingTask(int parkingID, IAsyncTaskHandler container) {
        this.parkingID = parkingID;
        this.activity = (Activity) container;
        this.container = container;
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
            ArrayList<BookingDTO> list = new ArrayList<>();
            String json = httpHandler.get(Constants.API_URL + "bookings/parkings/" + parkingID);
            JSONArray jsonObj = new JSONArray(json);
//            JSONArray bookings = jsonObj.getJSONArray();
            for (int i = 0; i < jsonObj.length(); i++) {
                oneBooking = jsonObj.getJSONObject(i);
                int bookingID = oneBooking.getInt("id");
                int status = oneBooking.getInt("status");
                if (status !=1 && status!=2) {
                    continue;
                }
                double price = oneBooking.getDouble("price");
                String timein = oneBooking.getString("timein");
                String timeout = oneBooking.getString("timeout");
                int parkingID = oneBooking.getJSONObject("parking").getInt("id");
                int vehicleID = oneBooking.getJSONObject("vehicle").getInt("id");
                String licenseplate = oneBooking.getJSONObject("vehicle").getString("licenseplate");
                String type = oneBooking.getJSONObject("vehicle").getJSONObject("vehicletype").getString("type");
                String rating = httpHandler.get(Constants.API_URL + "ratings/vehicles/" + vehicleID);
                if(rating.contains("NaN")) {
                rating = "3";
                }
                BookingDTO b = new BookingDTO(bookingID, parkingID, vehicleID, timein, timeout, price, licenseplate, type, Double.parseDouble(rating), status);
                list.add(b);
            }
            System.out.println("list ra listview : " + list);
            return list;

        } catch (Exception ex) {
            Log.e("Error GetBookingTask :", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(List list) {
        super.onPostExecute(list);
        pdLoading.dismiss();
        container.onPostExecute(list);
//        CarAdapter adapter = new CarAdapter(getWindow().getDecorView().getRootView(), MainActivity.this, list);
//        lv.setAdapter(adapter);
//            }

    }


}
class UpdateBooking extends AsyncTask<Void, Void, Boolean> {

    IAsyncTaskHandler container;
    BookingDTO b;
    Activity activity;
    boolean success = false;

    public UpdateBooking(IAsyncTaskHandler container, BookingDTO b){
        this.container = container;
        this.activity = (Activity)container;
        this.b = b;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try{
            Log.e("Update-Async", b.toString());
            JSONObject formData = new JSONObject();
            formData.put("id", b.getBookingID());
            formData.put("status", b.getStatus());
            if(b.getStatus()==2) {
                formData.put("timein", b.getTimein());
            }else if(b.getStatus()==3) {
                formData.put("timeout", b.getTimeout());
            }
            String json = httpHandler.post(Constants.API_URL + "bookings/update/", formData.toString());

//            formData.put("name", u.getName());
//            formData.put("username", u.getUsername());
//            formData.put("password", ""+ u.getPassword());
//            formData.put("role_id", ""+u.getRole_id());
//            formData.put("status",""+u.getStatus());
//            String json = httpHandler.post(Constants.API_URL + "user/update/", formData.toString());
            JSONObject jsonObj = new JSONObject(json);
            Log.e(" Updatebooking : ", jsonObj.toString());
            success = true;


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