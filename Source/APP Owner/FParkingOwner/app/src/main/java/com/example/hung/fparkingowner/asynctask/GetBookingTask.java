package com.example.hung.fparkingowner.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.hung.fparkingowner.config.Constants;
import com.example.hung.fparkingowner.dto.BookingDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetBookingTask extends AsyncTask<Void, Void, List> {

    private JSONObject oneBooking;
    private Activity activity;
    private IAsyncTaskHandler container;
    ProgressDialog pdLoading;

    public GetBookingTask(IAsyncTaskHandler container) {
        this.activity = (Activity) container;
        this.container = container;
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
            ArrayList<BookingDTO> list = new ArrayList<>();
            String json = httpHandler.get(Constants.API_URL + "bookings/owners");
            System.out.println(Constants.API_URL + "bookings/owners");
            JSONArray jsonObj = new JSONArray(json);
            System.out.println("GetBookingTask : " + jsonObj);
            for (int i = 0; i < jsonObj.length(); i++) {
                oneBooking = jsonObj.getJSONObject(i);
                int bookingID = oneBooking.getInt("id");
                int status = oneBooking.getInt("status");

                if (oneBooking.getString("timein") == null || oneBooking.getString("timeout")==null) {
                    continue;
                }
                double price = oneBooking.getDouble("price");
                int driverVehicleID = oneBooking.getInt("id");
                String timein = oneBooking.getString("timein");
                String timeout = oneBooking.getString("timeout");
                int parkingID = oneBooking.getJSONObject("parking").getInt("id");
                int vehicleID = oneBooking.getJSONObject("drivervehicle").getJSONObject("vehicle").getInt("id");
                int driverID = oneBooking.getJSONObject("drivervehicle").getJSONObject("driver").getInt("id");
                String licenseplate = oneBooking.getJSONObject("drivervehicle").getJSONObject("vehicle").getString("licenseplate");
                String color = oneBooking.getJSONObject("drivervehicle").getJSONObject("vehicle").getString("color");
                String type = oneBooking.getJSONObject("drivervehicle").getJSONObject("vehicle").getJSONObject("vehicletype").getString("type");
                BookingDTO b = new BookingDTO(bookingID, parkingID,driverID, vehicleID,driverVehicleID, timein, timeout, price, licenseplate, type, status);
                b.setTotalfine( oneBooking.getDouble("totalfine"));
                b.setAmount( oneBooking.getDouble("amount"));
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
    }


}