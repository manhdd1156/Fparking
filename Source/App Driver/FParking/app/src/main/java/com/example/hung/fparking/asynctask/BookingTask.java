package com.example.hung.fparking.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.dto.BookingDTO;
import com.example.hung.fparking.dto.VehicleDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookingTask extends AsyncTask<Void, Void, Boolean> {

    private String bookingID, action;
    private ArrayList<BookingDTO> booking;
    private IAsyncTaskHandler container;

    public BookingTask(String bookingID, String action, IAsyncTaskHandler container) {
        this.bookingID = bookingID;
        this.action = action;
        this.container = container;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        booking = new ArrayList<>();
        HttpHandler httpHandler = new HttpHandler();
        try {
            String json = httpHandler.get(Constants.API_URL + "bookings/" + bookingID);
            Log.e("toa do: ", Constants.API_URL + "bookings/"  + bookingID);
//            JSONArray jsonArray = new JSONArray(json);

//            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = new JSONObject(json);
                int bookingID = c.getInt("id");
                Double price = c.getDouble("price");
                int status = c.getInt("status");
                String timein = c.getString("timein");
                String timeout = c.getString("timeout");
                double amount = c.getDouble("amount");
                double comission = c.getDouble("comission");
                double totalfine = c.getDouble("totalfine");

                JSONObject parking = c.getJSONObject("parking");
                int parkingID = parking.getInt("id");
                String address = parking.getString("address");

                JSONObject drivervehicle = c.getJSONObject("drivervehicle");
                JSONObject vehicle = drivervehicle.getJSONObject("vehicle");
                int vehicleID = vehicle.getInt("id");
                String licenseplate = vehicle.getString("licenseplate");

                booking.add(new BookingDTO(bookingID,vehicleID,parkingID,address,timein,timeout,price,status,licenseplate,amount,comission,totalfine));
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        container.onPostExecute(booking, action);
    }
}
