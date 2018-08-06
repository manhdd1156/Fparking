package com.example.hung.fparking.asynctask;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.BookingDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookingTask {
    public BookingTask(String type, String data1, String data2, String action, IAsyncTaskHandler container) {
        if (type.equals("bookingID")) {
            new GetBookingTaskByID(data1, action, container).execute((Void) null);
        } else if (type.equals("phone")) {
            new GetBookingTaskByDriverID(data1, action, container).execute((Void) null);
        } else if (type.equals("create")) {
            new CreateBooking(data1, data2, action, container).execute((Void) null);
        } else if (type.equals("getorder")) {
            new GetBookingTaskWhenOrder(data1, data2, action, container).execute((Void) null);
        } else if (type.equals("cancel")) {
            new CancelBooking(data1, data2, action, container).execute((Void) null);
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
            Log.e("Get Booking By ID: ", Constants.API_URL + "bookings/" + bookingID);

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
            String color = vehicle.getString("color");

            JSONObject vehicletype = vehicle.getJSONObject("vehicletype");
            String type = vehicletype.getString("type");

            booking.add(new BookingDTO(bookingID, vehicleID, parkingID, address, timein, timeout, price, status, licenseplate, amount, comission, totalfine, type, color));
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

class GetBookingTaskByDriverID extends AsyncTask<Void, Void, Boolean> {

    private String driverID, action;
    private ArrayList<BookingDTO> booking;
    private IAsyncTaskHandler container;

    public GetBookingTaskByDriverID(String driverID, String action, IAsyncTaskHandler container) {
        this.driverID = driverID;
        this.action = action;
        this.container = container;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        booking = new ArrayList<>();
        HttpHandler httpHandler = new HttpHandler();
        try {
            String json = httpHandler.get(Constants.API_URL + "bookings/drivers/" + driverID);
            Log.e("Get Booking By Phone: ", Constants.API_URL + "bookings/drivers/" + driverID);
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
                String color = vehicle.getString("color");

                JSONObject vehicletype = vehicle.getJSONObject("vehicletype");
                String type = vehicletype.getString("type");

                booking.add(new BookingDTO(bookingID, vehicleID, parkingID, address, timein, timeout, price, status, licenseplate, amount, comission, totalfine, type, color));
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

class CreateBooking extends AsyncTask<Void, Void, Boolean> {

    IAsyncTaskHandler container;
    String vehicleid, parkingid, action;
    boolean success = false;

    public CreateBooking(String vehicleid, String parkingid, String action, IAsyncTaskHandler container) {
        this.container = container;
        this.vehicleid = vehicleid;
        this.parkingid = parkingid;
        this.action = action;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            JSONObject formData = new JSONObject();
            formData.put("driverid", Session.currentDriver.getId());
            formData.put("vehicleid", vehicleid);
            formData.put("parkingid", parkingid);
            formData.put("status", 5);

            String json = httpHandler.requestMethod(Constants.API_URL + "bookings/create", formData.toString(), "POST");
            JSONObject jsonObj = new JSONObject(json);
            if (jsonObj != null) {
                success = true;
            }
        } catch (Exception ex) {
            Log.e("Error CreateBooking:", ex.getMessage());
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

class GetBookingTaskWhenOrder extends AsyncTask<Void, Void, Boolean> {

    String drivervehicleid, parkingid, action;
    private ArrayList<BookingDTO> booking;
    private IAsyncTaskHandler container;

    public GetBookingTaskWhenOrder(String drivervehicleid, String parkingid, String action, IAsyncTaskHandler container) {
        this.drivervehicleid = drivervehicleid;
        this.parkingid = parkingid;
        this.action = action;
        this.container = container;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        booking = new ArrayList<>();
        HttpHandler httpHandler = new HttpHandler();
        try {
            String json = httpHandler.get(Constants.API_URL + "bookings/drivervehicle?parkingid=" + parkingid + "&drivervehicleid=" + drivervehicleid + "&status=1");
            Log.e("Get booking when order: ", Constants.API_URL + "bookings/drivervehicle?parkingid=" + parkingid + "&drivervehicleid=" + drivervehicleid + "&status=1");
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
            String color = vehicle.getString("color");

            JSONObject vehicletype = vehicle.getJSONObject("vehicletype");
            String type = vehicletype.getString("type");

            booking.add(new BookingDTO(bookingID, vehicleID, parkingID, address, timein, timeout, price, status, licenseplate, amount, comission, totalfine, type, color));
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

class CancelBooking extends AsyncTask<Void, Void, Boolean> {

    IAsyncTaskHandler container;
    String vehicleid, parkingid, action;
    boolean success = false;

    public CancelBooking(String vehicleid, String parkingid, String action, IAsyncTaskHandler container) {
        this.container = container;
        this.vehicleid = vehicleid;
        this.parkingid = parkingid;
        this.action = action;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            JSONObject formData = new JSONObject();
            formData.put("driverid", Session.currentDriver.getId());
            formData.put("vehicleid", vehicleid);
            formData.put("parkingid", parkingid);
            if (action.equals("cancel")) {
                formData.put("status", 1);
            } else if (action.equals("timeout")) {
                formData.put("status", 1);
            }

            String json = httpHandler.requestMethod(Constants.API_URL + "bookings/drivers/cancel", formData.toString(), "PUT");
            JSONObject jsonObj = new JSONObject(json);
            if (jsonObj != null) {
                success = true;
            }
        } catch (Exception ex) {
            Log.e("Error CreateBooking:", ex.getMessage());
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
