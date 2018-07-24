package com.example.hung.fparking.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.dto.BookingDTO;
import com.example.hung.fparking.dto.VehicleDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookingTask {
    public BookingTask(String type, String data, String action, IAsyncTaskHandler container) {
        if (type.equals("bookingID")) {
            new GetBookingTaskByID(data, action, container).execute((Void) null);
        } else if (type.equals("phone")) {
            new GetBookingTaskByPhone(data, action, container).execute((Void) null);
        }
    }
}

class GetBookingTaskByID extends AsyncTask<Void, Void, Boolean> {

    private String bookingID, action;
    private ArrayList<BookingDTO> booking;
    private IAsyncTaskHandler container;

    public GetBookingTaskByID(String bookingID, String action, IAsyncTaskHandler container) {
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
            Log.e("toa do: ", Constants.API_URL + "bookings/" + bookingID);
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

            booking.add(new BookingDTO(bookingID, vehicleID, parkingID, address, timein, timeout, price, status, licenseplate, amount, comission, totalfine));
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

class GetBookingTaskByPhone extends AsyncTask<Void, Void, Boolean> {

    private String mphone, action;
    private ArrayList<BookingDTO> booking;
    private IAsyncTaskHandler container;

    public GetBookingTaskByPhone(String mphone, String action, IAsyncTaskHandler container) {
        this.mphone = mphone;
        this.action = action;
        this.container = container;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        booking = new ArrayList<>();
        HttpHandler httpHandler = new HttpHandler();
        try {
            String json = httpHandler.get(Constants.API_URL + "bookings/drivers?phone=" + mphone);
            Log.e("toa do: ", Constants.API_URL + "bookings/drivers?phone=" + mphone);
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);
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

                booking.add(new BookingDTO(bookingID, vehicleID, parkingID, address, timein, timeout, price, status, licenseplate, amount, comission, totalfine));
            }

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
