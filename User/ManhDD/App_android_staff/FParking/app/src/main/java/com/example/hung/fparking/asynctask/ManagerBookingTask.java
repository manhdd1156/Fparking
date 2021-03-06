package com.example.hung.fparking.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by klot on 3/7/2018.
 */

public class ManagerBookingTask {

    public ManagerBookingTask(String method, BookingDTO bookingDTO, IAsyncTaskHandler container) {

        if (method.equals("get")) {
            new GetBookingTask(bookingDTO.getParkingID(), container).execute((Void) null);
        } else if (method.equals("create")) {
            new CreateBooking(container, bookingDTO).execute((Void) null);
        } else if (method.equals("update")) {
            new UpdateBooking(container, bookingDTO).execute((Void) null);
        } else if (method.equals("updatebystatus")) {
            new UpdateBookingByStatus(container,bookingDTO).execute((Void) null);
        }else if (method.equals("cancel")) {
            new CancelTask(container,bookingDTO).execute((Void) null);
        }
    }

}


class GetBookingTask extends AsyncTask<Void, Void, List> {

    private final int parkingID;
    private JSONObject oneBooking;
    private Activity activity;
    private SharedPreferences spref;
    private IAsyncTaskHandler container;
    ProgressDialog pdLoading;

    public GetBookingTask(int parkingID, IAsyncTaskHandler container) {
        this.parkingID = parkingID;
        this.activity = (Activity) container;
        this.container = container;
//        spref = activity.getSharedPreferences("info",0);
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
            System.out.println("GetBookingTask : " + jsonObj);
            for (int i = 0; i < jsonObj.length(); i++) {
                oneBooking = jsonObj.getJSONObject(i);
                int bookingID = oneBooking.getInt("id");
                int status = oneBooking.getInt("status");
                if (status != 1 && status != 2) {
                    continue;
                }
                double price = oneBooking.getDouble("price");
                String timein = oneBooking.getString("timein").substring(0, oneBooking.getString("timein").length() - 1);
                String timeout = oneBooking.getString("timeout").substring(0, oneBooking.getString("timeout").length() - 1);
                int parkingID = oneBooking.getJSONObject("parking").getInt("id");
                int vehicleID = oneBooking.getJSONObject("drivervehicle").getJSONObject("vehicle").getInt("id");
                String licenseplate = oneBooking.getJSONObject("drivervehicle").getJSONObject("vehicle").getString("licenseplate");
                String color = oneBooking.getJSONObject("drivervehicle").getJSONObject("vehicle").getString("color");
                String type = oneBooking.getJSONObject("drivervehicle").getJSONObject("vehicle").getJSONObject("vehicletype").getString("type");
                String rating = httpHandler.get(Constants.API_URL + "vehicles/ratings/" + vehicleID);
                System.out.println("Rating : " + rating);
                if (rating.contains("NaN")) {
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
    }


}

class CreateBooking extends AsyncTask<Void, Void, Boolean> {

    IAsyncTaskHandler container;
    BookingDTO b;
    Activity activity;
    boolean success = false;
    private SharedPreferences spref;

    public CreateBooking(IAsyncTaskHandler container, BookingDTO b) {
        this.container = container;
        this.activity = (Activity) container;
        this.b = b;
//        spref = activity.getSharedPreferences("info",0);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            JSONObject formData = new JSONObject();
            formData.put("parking_id", b.getParkingID());
            formData.put("drivervehicle_id", b.getDrivervehicleID());

            String json = httpHandler.requestMethod(Constants.API_URL + "bookings", formData.toString(), "POST");
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


class UpdateBooking extends AsyncTask<Void, Void, Boolean> {

    IAsyncTaskHandler container;
    BookingDTO b;
    Activity activity;
    boolean success = false;
    private SharedPreferences spref;

    public UpdateBooking(IAsyncTaskHandler container, BookingDTO b) {
        this.container = container;
        this.activity = (Activity) container;
        this.b = b;
//        spref = activity.getSharedPreferences("info",0);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            Log.e("Update-Async", b.toString());
            JSONObject formData = new JSONObject();
            formData.put("id", b.getBookingID());
            formData.put("status", b.getStatus());
            if (b.getStatus() == 2) {
                formData.put("timein", b.getTimein());
            } else if (b.getStatus() == 3) {
                formData.put("timeout", b.getTimeout());
            }
            String json = httpHandler.requestMethod(Constants.API_URL + "bookings/update/", formData.toString(), "PUT");

            JSONObject jsonObj = new JSONObject(json);
            Log.e(" Updatebooking : ", jsonObj.toString());
            success = true;


        } catch (Exception ex) {
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

class UpdateBookingByStatus extends AsyncTask<Void, Void, Boolean> {

    IAsyncTaskHandler container;
    Activity activity;
    boolean success = false;
    BookingDTO bookingDTO;

    public UpdateBookingByStatus(IAsyncTaskHandler container,BookingDTO bookingDTO) {
        this.container = container;
        this.activity = (Activity) container;
        this.bookingDTO = bookingDTO;
//        spref = activity.getSharedPreferences("info",0);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            JSONObject formData = new JSONObject();
            formData.put("parking_id", bookingDTO.getParkingID());
            formData.put("type", 1);
            if(bookingDTO.getStatus()==1) {  // lưu tạm biến bookingDTO.status là event. 1: order, 2: checkin, 3 : checkout
                formData.put("event", "order");
            }else if(bookingDTO.getStatus()==2) {
                formData.put("event", "checkin");
            }else if(bookingDTO.getStatus()==3) {
                formData.put("event", "checkout");
            }

            formData.put("status", 0);
            String json = httpHandler.requestMethod(Constants.API_URL + "bookings/update/status", formData.toString(), "PUT");

            JSONObject jsonObj = new JSONObject(json);
            System.out.println("updatebookingbystatusTask : " + json);
            Log.e(" Updatebooking : ", jsonObj.toString());
            success = true;


        } catch (Exception ex) {
            Log.e("Error update booking:", ex.getMessage());
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
class CancelTask extends AsyncTask<Void, Void, Boolean> {

    IAsyncTaskHandler container;
    Activity activity;
    boolean success = false;
    BookingDTO bookingDTO;

    public CancelTask(IAsyncTaskHandler container,BookingDTO bookingDTO) {
        this.container = container;
        this.activity = (Activity) container;
        this.bookingDTO = bookingDTO;
//        spref = activity.getSharedPreferences("info",0);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            JSONObject formData = new JSONObject();
            formData.put("parking_id", bookingDTO.getParkingID());
            formData.put("type", 1);
            if(bookingDTO.getStatus()==1) {  // lưu tạm biến bookingDTO.status là event. 1: order, 2: checkin, 3 : checkout
                formData.put("event", "order");
            }else if(bookingDTO.getStatus()==2) {
                formData.put("event", "checkin");
            }else if(bookingDTO.getStatus()==3) {
                formData.put("event", "checkout");
            }
            formData.put("status", 0);
            String json = httpHandler.requestMethod(Constants.API_URL + "notifications/cancel", formData.toString(), "PUT");

            JSONObject jsonObj = new JSONObject(json);
            System.out.println("deletebookingbystatusTask : " + json);
            Log.e(" CancelTask : ", jsonObj.toString());
            success = true;


        } catch (Exception ex) {
            Log.e("Error CancelTask:", ex.getMessage());
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